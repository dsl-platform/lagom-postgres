@echo off
setlocal enabledelayedexpansion
pushd "%~dp0"

:: Get time and date independent of locale
for /F "usebackq tokens=1,2 delims==" %%i in (`wmic os get LocalDateTime /VALUE 2^>NUL`) do if '.%%i.'=='.LocalDateTime.' set ldt=%%j
set SNAPSHOT=%ldt:~0,8%-%ldt:~8,6%
set PACKAGE=worldwonders

set MODEL_JAR=%PACKAGE%-model-%SNAPSHOT%.jar
set MODEL_SRC=%PACKAGE%-model-%SNAPSHOT%-sources.jar

echo Cleaning old compilation ...
if exist temp\compile rmdir /S /Q temp\compile
mkdir temp\compile
if not exist temp\compiler mkdir temp\compiler

echo Compiling model ...
java -cp "*" ^
  com.dslplatform.compiler.client.Main ^
  compiler=temp\compiler\dsl-compiler.exe ^
  download ^
  dsl=dsl ^
  temp=temp\compile ^
  namespace=%PACKAGE% ^
  revenj.java source-only manual-json ^
  "postgres=localhost:5432/wondersdb?user=wondersuser&password=wonderspass" ^
  sql=sql apply
IF ERRORLEVEL 1 goto :error

if exist temp\compile\dsl-compiler.exe move temp\compile\dsl-compiler.exe temp\compiler > NUL

:: Format SQL script and Java sources
echo Running code formatter ...
java -cp "*" ^
  -Dsql-clean.regex=sql-clean.regex ^
  com.dslplatform.compiler.client.formatter.Main ^
  sql ^
  temp\compile\REVENJ_JAVA
IF ERRORLEVEL 1 goto :error

:: Copy sources so that we can archive them
if exist src\main\java rmdir /S /Q src\main\java
mkdir src\main\java
move temp\compile\REVENJ_JAVA\%PACKAGE% src\main\java > NUL

goto :EOF
