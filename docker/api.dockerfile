FROM openjdk:11-slim

ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /opt/
RUN chmod +x /opt/wait-for-it.sh
