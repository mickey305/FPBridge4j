#!/bin/sh

logname=`date +%Y-%m-%d_%H%M%S`_release_bintray.txt

# ---------- BINTRAY_USERNAME : bintray username
# ---------- BINTRAY_KEY : bintray secretkey
# ./gradlew clean
# ./gradlew build
./gradlew bintrayUpload -PbintrayUser=$BINTRAY_USERNAME -PbintrayKey=$BINTRAY_KEY -PdryRun=false > .log/$logname
