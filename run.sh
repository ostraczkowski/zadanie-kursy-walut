#!/bin/bash

DIST_DIR="dist"

if [ ! -d $DIST_DIR ]; then
  dist.sh
fi

cd $DIST_DIR
java -Dlog4j.configuration=file:log4j.properties -jar zadanie-kursy-walut.jar $1 $2 $3
