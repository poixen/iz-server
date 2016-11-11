FROM alpine:3.4
RUN apk --no-cache add openjdk8-jre

ENV INST_DIR /opt/iz-server

RUN mkdir -p ${INST_DIR}
COPY target/iz-server.jar ${INST_DIR}
COPY scripts/start.sh ${INST_DIR}

VOLUME ["${INST_DIR}"]
EXPOSE 8080
EXPOSE 5005

WORKDIR $INST_DIR
RUN adduser -S iz
USER iz
CMD ["./start.sh"]
