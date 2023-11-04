"%JAVA_HOME%\javac" -d bin src\gui\* src\main\*
cd bin
"%JAVA_HOME%\jar" cmf MANIFEST.MF ..\ImageIO.jar *
cd ..
ImageIO.bat %1