#!/bin/bash
PACKAGE=worldwonders
MODULE=comments
API_SRC=temp/DSL-Platform/model/JAVA_POJO
IMPL_SRC=temp/DSL-Platform/model/REVENJ_JAVA

PWD=$(pwd)
cd lib

function error {
    echo 'An error has occurred, aborting!'
    exit 1
}

mkdir -p temp

echo Compiling model ...
java \
  -jar dsl-clc.jar \
  download \
  dependencies=temp \
  dsl=../dsl \
  namespace="$PACKAGE" \
  java_pojo=../../model-lib/${MODULE}-api-model.jar \
  revenj.java=../../../${MODULE}-impl/model-lib/${MODULE}-impl-model.jar \
  manual-json \
  "postgres=localhost:5432/${MODULE}_db?user=${MODULE}_user&password=${MODULE}_pass" \
  sql=../sql apply

if [ $? -eq 1 ]; then error ; fi

rm -rf $API_SRC/compile-java_pojo
rm -rf $IMPL_SRC/compile-revenj

# Format SQL script and Java sources
echo Running code formatter ...
java \
  -Dsql-clean.regex=sql-clean.regex \
  -jar dsl-clc-formatter.jar \
  ../sql \
  "$API_SRC" \
  "$IMPL_SRC"

if [ $? -eq 1 ]; then error ; fi

cd "$PWD"
