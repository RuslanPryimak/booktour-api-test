# use container maven with taf 3.6.3-jdk-11-openj9:
FROM maven:3.6.3-jdk-11-openj9 AS MAVEN_TOOL_CHAIN

# copy source files and maven profile to tmp directory
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.7.3/wait /wait
RUN chmod +x /wait
CMD /wait