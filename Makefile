.PHONY: all
all: build run

.PHONY: build
build:
	./gradlew clean assemble

.PHONY: run
run:
	docker-compose up --build

.PHONY: testBackend
testBackend:
	./gradlew test

.PHONY: testFrontend
testFrontend:
	rm -rf ./gui/allure-results
	docker-compose exec ui sh -c 'yarn testMocks'

.PHONY: testEndToEnd
testEndToEnd:
	rm -rf ./gui/allure-results
	docker-compose exec ui sh -c '                          \
	  /opt/http-reverse-proxy :9920 http://server:9920 &    \
	  yarn test                                             \
	'
