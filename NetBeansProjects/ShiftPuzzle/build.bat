"%JAVA_HOME%\javac" -d bin src\gui\*.java src\main\*
cd bin
"%JAVA_HOME%\jar" cmf manifest.mf ..\ShiftPuzzle.jar gui\* main\*
cd ..
pause
