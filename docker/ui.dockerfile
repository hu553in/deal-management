FROM node:latest

RUN groupmod -g 1001 node && usermod -u 1001 -g 1001 node

RUN groupadd -g 1000 deal-management
RUN useradd -u 1000 -ms /bin/bash -g deal-management deal-management

RUN npm i -g npm
