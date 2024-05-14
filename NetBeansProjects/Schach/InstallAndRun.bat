"%JAVA_HOME%\javac" -d build -classpath lib\Platform.jar src\graphic\* src\chess\*
cd build
"%JAVA_HOME%\jar" cmf MANIFEST.MF ..\Chess.jar graphic\* chess\*
cd..
"%JAVA_HOME%\java" -jar Chess.jar