all: build

build:
ifeq ($(OS), Linux)
	./mvnw clean compile package
else
	mvnw clean compile package
endif

run: build
	java -Djava.library.path=src/main/java/com/example/calculator/models -jar target/calculator-1.0-SNAPSHOT.jar