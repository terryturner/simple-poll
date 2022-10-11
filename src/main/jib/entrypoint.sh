#!/bin/sh



echo "The application will start in ${DEMO_SLEEP}s..." && sleep ${DEMO_SLEEP}

exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.simple.poll.SimplePollApplication"  "$@"