#!/bin/bash

function create {
    echo Applying script: 10-create-database.sql
    psql -AtUpostgres < 10-create-database.sql
    if [ $? -eq 1 ]; then
        echo "Error applying script"
        exit 1
    fi
}

function exists_drop {
    echo 'The "comments_db" database already exists!'
    echo 'Do you wish to drop & recreate this database?'
    read -t 10 -r -p 'Cancelling in 5 seconds... (Enter anything to continue)'
    if [ $? -ne 0 ]; then
	exit
    fi

    echo Applying script: 00-drop-database.sql
    psql -AtUpostgres < 00-drop-database.sql
    if [ $? -eq 1 ]; then
	echo "Error applying script"
	exit 1
    fi

}

CREATED=false
for e in $( psql -Upostgres -qtAc "SELECT EXISTS(SELECT 1 FROM pg_database WHERE datname='comments_db')" ); do 
    if [ "$e" == 'f' ]; then
        create
	CREATED=true
    fi
done

if [ $CREATED != true ]; then
    exists_drop
    create
fi

PGPASSWORD=comments_pass

for a in 20-*.sql
do
    echo "Applying script: $a ..."
    psql -AtUcomments_user comments_db < "$a"
    if [ $? -eq 1 ]; then
	echo "Error applying script $a"
	exit 1
    fi  
done

echo 'Done!'
