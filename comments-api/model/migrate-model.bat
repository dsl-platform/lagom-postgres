@echo off
setlocal
pushd "%~dp0"

set PACKAGE=worldwonders
set API_TARGET=..\src\generated\java
set IMPL_TARGET=..\..\comments-impl\src\generated\java

echo Cleaning old compilation ...
if exist temp\compile rmdir /S /Q temp\compile
mkdir temp\compile
if not exist temp\compiler mkdir temp\compiler

echo Compiling model ...
java ^
  -jar lib\dsl-clc.jar ^
  compiler=temp\compiler\dsl-compiler.exe ^
  download ^
  dsl=dsl ^
  temp=temp\compile ^
  namespace=%PACKAGE% ^
  revenj.java source-only jackson ^
  "postgres=localhost:5432/comments_db?user=comments_user&password=comments_pass" ^
  sql=sql apply
IF ERRORLEVEL 1 goto :error

if exist temp\compile\dsl-compiler.exe move temp\compile\dsl-compiler.exe temp\compiler > NUL

:: Format SQL script and Java sources
echo Running code formatter ...
java ^
  -Dsql-clean.regex=lib\sql-clean.regex ^
  -jar lib\dsl-clc-formatter.jar ^
  sql ^
  temp\compile\REVENJ_JAVA
IF ERRORLEVEL 1 goto :error

:: Copy sources so that we can archive them
if exist %IMPL_TARGET% rmdir /S /Q %IMPL_TARGET%
mkdir %IMPL_TARGET%\%PACKAGE%
move temp\compile\REVENJ_JAVA\%PACKAGE%\Boot.java %IMPL_TARGET%\%PACKAGE% > NUL

if exist %API_TARGET% rmdir /S /Q %API_TARGET%
mkdir %API_TARGET%
move temp\compile\REVENJ_JAVA\%PACKAGE% %API_TARGET% > NUL

echo Done^!
:exit
popd
goto :EOF

:error
echo An error has occurred, aborting^!
goto :exit
