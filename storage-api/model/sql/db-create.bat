@echo off
setlocal
pushd "%~dp0"

for /f %%e in ('psql -Upostgres -qtAc "SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname='storage_db')"') do if %%e==f goto :create

echo The "storage_db" database already exists^!
echo Do you wish to drop ^& recreate this database?
CHOICE /D n /T 10 /C:YN /M "Cancelling in 5 seconds, or press "
IF ERRORLEVEL 2 goto :exit

echo.
echo Applying script: 00-drop-database.sql
psql -AtUpostgres < 00-drop-database.sql
IF ERRORLEVEL 1 goto :exit

:create
echo.
echo Applying script: 10-create-database.sql
psql -AtUpostgres < 10-create-database.sql
IF ERRORLEVEL 1 goto :exit

set PGPASSWORD=storage_pass
for %%a in (20-*.sql) do (
  echo.
  echo Applying script: %%~na ...
  psql -AtUstorage_user storage_db < "%%~fa"
  IF ERRORLEVEL 1 goto :exit
)

echo Done^!
:exit
popd
goto :EOF
