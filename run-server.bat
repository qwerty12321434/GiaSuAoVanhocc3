@echo off
@REM echo Setting up Java 21 environment...

set JAVA_HOME=F:\for school\app gia su van hoc\GiaSuAoVanhocc3\jdk-21.0.8
set PATH=F:\for school\app gia su van hoc\GiaSuAoVanhocc3\jdk-21.0.8\bin;%PATH%

@REM echo Java version:
@REM java -version

@REM echo.
@REM echo Starting Spring Boot server from root project...
.\gradlew.bat :server:bootRun
