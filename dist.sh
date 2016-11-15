#!/bin/bash

RESOURCES_DIR="src/main/resources"
BUILD_DIR="target"
DIST_DIR="dist"

rm -Rf $DIST_DIR
mkdir $DIST_DIR

mvn clean install
cp $BUILD_DIR/zadanie-kursy-walut.jar $DIST_DIR
cp -R $BUILD_DIR/dependency-jars $DIST_DIR
cp $RESOURCES_DIR/log4j.properties $DIST_DIR
