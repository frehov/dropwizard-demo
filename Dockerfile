FROM openjdk:8u171-alpine3.7
RUN apk --no-cache add curl
COPY target/dropwizard-demo*SHOT.jar dropwizard-demo.jar
COPY config.yml ./
CMD java ${JAVA_OPTS} -jar dropwizard-demo.jar server config.yml