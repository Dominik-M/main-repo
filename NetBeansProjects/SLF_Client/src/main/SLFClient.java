/**
 * Copyright (C) 2016 Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package main;

import client.Client;
import client.ClientListener;
import server.Constants;

/**
 *
 * @author Dominik Messerschmidt
 * <dominik.messerschmidt@continental-corporation.com> Created 16.09.2016
 */
public class SLFClient implements ClientListener {

    private final Client client;
    private String username;
    private final String[] keys;
    private boolean replyStatus;

    public SLFClient(String username, String[] keys) {
        this.username = username;
        this.client = new Client();
        this.keys = keys;
        init();
    }

    private void init() {
        client.addClientListener(this);
    }

    public boolean start(String address) {
        boolean ok = !client.isConnected();
        if (ok) {
            ok = client.connect(username, address, Constants.PORT);
        }
        return ok;
    }

    public boolean stop() {
        if (client.isConnected()) {
            client.disconnect();
            return true;
        }
        return false;
    }

    public boolean isRunning() {
        return client.isConnected();
    }

    public boolean isValid(String key, String value) {
        replyStatus = false;
        if (client.isConnected()) {
            client.sendAndWaitOnAck(client.assembleRequest(Constants.REQUEST_VALIDATE, key + ":" + value), 2000);
        }
        return replyStatus;
    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onUserlistChanged() {

    }

    @Override
    public void println(String txt) {
        if (txt.startsWith(Constants.COMMAND)) {
            if (txt.startsWith(Constants.REPLY_VALID)) {
                replyStatus = true;
            } else {
                replyStatus = false;
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (client.setUserName(username)) {
            this.username = username;
        }
    }

    public void addClientListener(ClientListener l) {
        client.addClientListener(l);
    }

    public void removeClientListener(ClientListener l) {
        client.removeClientListener(l);
    }
}
