FROM openjdk:17

MAINTAINER archer

VOLUME /tmp

ADD instagdm.jar app.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]