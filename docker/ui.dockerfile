FROM node:latest

RUN groupmod -g 1001 node && usermod -u 1001 -g 1001 node

RUN groupadd -g 1000 app
RUN useradd -u 1000 -ms /bin/bash -g app app

RUN npm i -g npm

RUN apt-get update && apt-get -qy install libnss3 openjdk-8-jdk
ARG CHROME_VERSION=87.0.4280.66-1
RUN wget --no-check-certificate \
https://dl.google.com/linux/chrome/deb/pool/main/g/google-chrome-stable/google-chrome-stable_${CHROME_VERSION}_amd64.deb
RUN dpkg -i google-chrome-stable_${CHROME_VERSION}_amd64.deb || apt-get -fqy install
RUN rm -rf google-chrome-stable_${CHROME_VERSION}_amd64.deb
