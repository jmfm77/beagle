FROM alpine:3.9
COPY target/machine-1.0.0.RELEASE.jar /opt/machine/machine-1.0.0.RELEASE.jar
RUN chmod 444 /opt/machine/machine-1.0.0.RELEASE.jar \
	&& apk add --no-cache openjdk8-jre=8.212.04-r0
USER nobody
ENTRYPOINT ["java", "-jar", "/opt/machine/machine-1.0.0.RELEASE.jar"]
