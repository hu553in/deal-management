.PHONY: all
all: build run

.PHONY: build
build:
	./gradlew clean assemble

.PHONY: run
run:
	docker-compose up --build

.PHONY: test
test:
	./gradlew test
