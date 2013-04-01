#! /bin/sh

JAVA_HOME=/usr/lib/jvm/java-6-sun/
if test -n "${JAVA_HOME}"; then
  if test -z "${JAVA_EXE}"; then
    JAVA_EXE=$JAVA_HOME/bin/java
  fi
fi  
RESIN_HOME=/home/kooer/download/resin/resin-3.1.1
#JVM="-Xms512m -Xmx1024m -XX:MaxNewSize=256m -XX:MaxPermSize=256m"
$JAVA_EXE -jar ${RESIN_HOME}/lib/resin.jar -conf ./conf/resin.linux.conf $JVM $*
