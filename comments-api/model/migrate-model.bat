@echo off
setlocal
pushd "%~dp0\lib"

set PACKAGE=worldwonders
set MODULE=comments

echo Compiling model ...
java ^
  -jar dsl-clc.jar ^
  download ^
  dsl=..\dsl ^
  namespace=%PACKAGE% ^
  java_pojo=..\..\lib\%MODULE%-api-model.jar ^
  revenj.java=..\..\..\%MODULE%-impl\lib\%MODULE%-impl-model.jar ^
  jackson ^
  "postgres=localhost:5432/%MODULE%_db?user=%MODULE%_user&password=%MODULE%_pass" ^
  sql=..\sql apply
IF ERRORLEVEL 1 goto :error

set API_SRC=%TEMP%\DSL-Platform\model\JAVA_POJO
set IMPL_SRC=%TEMP%\DSL-Platform\model\REVENJ_JAVA

rmdir /S /Q "%API_SRC%\compile-java_pojo"
rmdir /S /Q "%IMPL_SRC%\compile-revenj"

:: Format SQL script and Java sources
echo Running code formatter ...
java ^
  -Dsql-clean.regex=lib\sql-clean.regex ^
  -jar dsl-clc-formatter.jar ^
  sql ^
  "%API_SRC%" ^
  "%IMPL_SRC%
IF ERRORLEVEL 1 goto :error

echo Packaging api sources ...
jar cfM ..\..\lib\%MODULE%-api-model-sources.jar -C "%API_SRC%\%PACKAGE%" .
IF ERRORLEVEL 1 goto :error

echo Archiving impl sources ...
jar cfM ..\..\..\%MODULE%-impl\lib\%MODULE%-impl-model-sources.jar -C "%IMPL_SRC%\%PACKAGE%" .
IF ERRORLEVEL 1 goto :error

echo Done^!
:exit
popd
pause
goto :EOF

:error
echo An error has occurred, aborting^!
goto :exit
