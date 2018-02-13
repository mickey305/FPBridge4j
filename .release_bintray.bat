@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  release command script for Windows
@rem
@rem ##########################################################################

set DT=%date%
set TM=%time: =0%
set LOGNAME=%DT:~-10,4%-%DT:~-5,2%-%DT:~-2,2%_%TM:~0,2%%TM:~3,2%%TM:~6,2%_release_bintray.txt

echo "logfile name: %LOGNAME%"

@rem -------------- BINTRAY_USERNAME : bintray username
@rem -------------- BINTRAY_KEY : bintray secretkey
@rem gradlew.bat clean
@rem gradlew.bat build
gradlew.bat bintrayUpload "-PbintrayUser=%BINTRAY_USERNAME%" "-PbintrayKey=%BINTRAY_KEY%" -PdryRun=false > .log\%LOGNAME%
