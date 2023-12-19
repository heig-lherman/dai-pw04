# Practical Work 4 - API server for ...

## Dependencies

### Lombok

This project uses [Lombok](https://projectlombok.org/) to generate boilerplate code.
When opening this project in IntelliJ, annotation processing is recommended.
Maven will compile the project without any additional configuration.

## Building and running with Docker

Pre-built images are built using [Google Jib](https://github.com/GoogleContainerTools/jib) efficiently for each
commit on the `main` branch.
These images are available with the `dev` tag on the
[packages of this repository](https://github.com/heig-lherman/dai-pw04/pkgs/container/dai-pw04)

If one wants to build the images locally, the following command can be used that uses a multi-stage build to build the
JAR and then build the image:

```shell
docker build -t dai-pw04:dev .
```

The server can easily be started using docker compose:

```shell
docker-compose up
```

## Build and running without Docker

This project can also be packaged and run without using Docker.

### Packaging the JAR

To build the JAR without using the Docker image, run the following command from the root of the repository:

```shell
./mvnw clean package
```

### Running

To run the JAR, run the following command from the root of the repository:

```shell
java -jar target/pw-api-1.0.0-SNAPSHOT.jar <command>
```

## Usage

The CLI methods are documented in the form of usage messages, given using the `--help` flag.

```shell
java -jar target/pw-api-1.0.0-SNAPSHOT.jar --help
```

Giving

```text
[Input example here]
```
