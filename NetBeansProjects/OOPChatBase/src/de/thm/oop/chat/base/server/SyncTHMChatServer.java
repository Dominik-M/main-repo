package de.thm.oop.chat.base.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import de.thm.oop.chat.base.contact.User;
import de.thm.oop.chat.base.message.GeolocationMessage;
import de.thm.oop.chat.base.message.ImageMessage;
import de.thm.oop.chat.base.message.Message;
import de.thm.oop.chat.base.message.RemoteImageMessage;
import de.thm.oop.chat.base.message.StringMessage;
import de.thm.oop.chat.base.message.TextMessage;

/**
 * A concrete implementation of a synchronous server connection using the "THM
 * Chat Server".
 */
public class SyncTHMChatServer implements SyncServer
{

    private static final String BASE_URL = "http://thm-chat.appspot.com/oop/";
    private static final String USERS_REQUEST = "users";
    private static final String MESSAGES_REQUEST = "messages";
    private static final String SENDTEXT_REQUEST = "sendTxt";
    private static final String UPLOADURL_REQUEST = "uploadURL";

    private static final String PARAMETER_USER = "user";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_SINCE = "since";

    private static final String PARAMETER_FROM_USER = "fromUser";
    private static final String PARAMETER_FROM_PASSWORD = "fromPassword";
    private static final String PARAMETER_TO_USER = "toUser";
    private static final String PARAMETER_MESSAGE_TYPE = "type";
    private static final String PARAMETER_MESSAGE_TEXT = "txt";
    private static final String PARAMETER_MESSAGE_IMAGE = "image";

    private static final String MESSAGE_DIRECTION_INCOMING = "in";

    private static final String CHARSET = "UTF-8";
    private static final String BOUNDARY = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
    private static final String CRLF = "\r\n"; // Line separator required by multipart/form-data.

    private String username;
    private String password;

    @Override
    public void login(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    public void logout()
    {
        this.username = null;
        this.password = null;
    }

    @Override
    public void sendMessage(Message message)
    {
        if (username == null || password == null)
        {
            return;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAMETER_FROM_USER, username);
        params.put(PARAMETER_FROM_PASSWORD, password);
        params.put(PARAMETER_TO_USER, message.getPartner());
        params.put(PARAMETER_MESSAGE_TYPE, message.getType());

        if (message instanceof StringMessage)
        {
            sendString(params, (StringMessage) message);
        }
        if (message instanceof ImageMessage)
        {
            sendImage(params, (ImageMessage) message);
        }
    }

    private void sendString(Map<String, String> params, StringMessage message)
    {
        final String endpoint = BASE_URL + SENDTEXT_REQUEST;
        params.put(PARAMETER_MESSAGE_TEXT, message.getMessageString());
        try
        {
            post(endpoint, params);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void sendImage(Map<String, String> params, ImageMessage message)
    {
        final String uploadURL = getUploadURL();
        try
        {
            post(uploadURL, params, PARAMETER_MESSAGE_IMAGE,
                    message.getInputStream(), message.getMimeType());
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String getUploadURL()
    {
        String endpoint = BASE_URL + UPLOADURL_REQUEST;
        Map<String, String> params = new HashMap<String, String>();
        try
        {
            String[] response = post(endpoint, params);
            return response[0];
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers()
    {
        List<User> result = new ArrayList<User>();
        String endpoint = BASE_URL + USERS_REQUEST;
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAMETER_USER, username);
        params.put(PARAMETER_PASSWORD, password);
        try
        {
            String[] response = post(endpoint, params);
            for (String username : response)
            {
                result.add(new User(username));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Message> getMessages()
    {
        return getMessages(0);
    }

    @Override
    public List<Message> getMessages(long since)
    {
        List<Message> result = new ArrayList<Message>();
        String endpoint = BASE_URL + MESSAGES_REQUEST;
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAMETER_USER, username);
        params.put(PARAMETER_PASSWORD, password);
        params.put(PARAMETER_SINCE, since + "");
        try
        {
            String[] response = post(endpoint, params);
            for (String message : response)
            {
                String[] parts = message.split("\\|");
                if (parts.length < 4)
                {
                    continue;
                }
                long timestamp = Long.parseLong(parts[0]);
                boolean incoming = parts[1].equals(MESSAGE_DIRECTION_INCOMING);
                String partner = parts[2];
                if (parts[3].equals(Message.TYPE_TXT))
                {
                    result.add(new TextMessage(timestamp, incoming, partner, parts[4]));
                }
                if (parts[3].equals(Message.TYPE_GEO))
                {
                    result.add(new GeolocationMessage(timestamp, incoming, partner, parts[4]));
                }
                if (parts[3].equals(Message.TYPE_IMG))
                {
                    result.add(new RemoteImageMessage(timestamp, incoming, partner, parts[4], parts[5], parts[6]));
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     * @return response (line by line)
     *
     * @throws IOException propagated from POST.
     */
    private String[] post(String endpoint, Map<String, String> params) throws IOException
    {
        URL url;
        try
        {
            url = new URL(endpoint);
        } catch (MalformedURLException e)
        {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext())
        {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            if (iterator.hasNext())
            {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        String response = "";
        try
        {
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200)
            {
                throw new IOException("Post failed with error code " + status);
            }
            InputStreamReader reader = new InputStreamReader(conn.getInputStream(), "utf-8");
            int c;
            while ((c = reader.read()) != -1)
            {
                response += (char) c;
            }
        } finally
        {
            if (conn != null)
            {
                conn.disconnect();
            }
        }

        return response.split("\n");
    }

    /**
     * Issue a POST request to the server. Sends parameters and a binary file.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     * @param fileKey key for the sent file
     * @param fileStream binary file as a stream
     * @param fileName name of the binary file
     *
     * @throws IOException propagated from POST.
     */
    private void post(String endpoint, Map<String, String> params, String fileKey, InputStream fileStream, String mimeType) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) new URL(endpoint).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        PrintWriter writer = null;
        try
        {
            OutputStream output = connection.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(output, CHARSET), true); // true = autoFlush, important!

            for (Entry<String, String> param : params.entrySet())
            {
                addParam(writer, param.getKey(), param.getValue());
            }

            // Send binary file.
            writer.append("--" + BOUNDARY).append(CRLF);
            writer.append("Content-Disposition: form-data; name=\"" + fileKey + "\"; filename=\"uploadedImage\"").append(CRLF);
            writer.append("Content-Type: " + mimeType).append(CRLF);
            writer.append("Content-Transfer-Encoding: binary").append(CRLF);
            writer.append(CRLF).flush();
            try
            {
                byte[] buffer = new byte[1024];
                for (int length = 0; (length = fileStream.read(buffer)) > 0;)
                {
                    output.write(buffer, 0, length);
                }
                output.flush(); // Important! Output cannot be closed. Close of writer will close output as well.
            } finally
            {
                if (fileStream != null)
                {
                    try
                    {
                        fileStream.close();
                    } catch (IOException logOrIgnore)
                    {
                    }
                }
            }
            writer.append(CRLF).flush(); // CRLF is important! It indicates end of binary boundary.

            // End of multipart/form-data.
            writer.append("--" + BOUNDARY + "--").append(CRLF);
        } finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
        connection.getResponseCode();
    }

    private void addParam(PrintWriter writer, String key, String value)
    {
        // Send normal param.
        writer.append("--" + BOUNDARY).append(CRLF);
        writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append(CRLF);
        writer.append("Content-Type: text/plain; charset=" + CHARSET).append(CRLF);
        writer.append(CRLF);
        writer.append(value).append(CRLF).flush();
    }

}
