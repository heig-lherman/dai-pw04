# Practical Work 4 - API server for managing VMs

## Group members
- Massimo Stefani
- Loïc Herman
- Kevin Farine
- Olin Bourquin

## API documentation

API documentation can be found in the `API.md` file or at the following URL: https://api.dai.heig.lutonite.dev/

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

To start the server, run the following command:

```shell
java -jar target/pw-api-1.0.0-SNAPSHOT.jar server
```

## Deployment

The application is deployed by connecting to the server using SSH and pulling from the git repository.

Upon pulling the repository, the docker image must be rebuilt and run once again.

If you want to set up a virtual machine, install all the dependencies, and automatically launch the application, 
you can use Ansible from this repository and execute the following command:

You must have `Ansible` (2.10.8) and `Python` (3.10.4) installed on your machine.

The following environment variables must be set:
- `TRAEFIK_ACME_EMAIL`: the email address used to register the Let's Encrypt account
- `TRAEFIK_FULLY_QUALIFIED_DOMAIN_NAME`: the domain name used to access the application
- `TRAEFIK_USERNAME`: the username used to access Traefik
- `TRAEFIK_PASSWORD`: the password used to access Traefik
- `WHOAMI_FULLY_QUALIFIED_DOMAIN_NAME`: the domain name used to access the Whoami application

```shell
ansible-playbook -i ./ ansible/hosts ./ansible/playbook.yml -kK
```

Docker, Docker composes and the other dependencies will be installed, the authorized_keys will be copied and the 
application will be launched.

Note: `-k` will ask you for the password of the user you want to connect to the VM with. `-K` will ask you for the 
password of the sudo user (can be the same as the previous one). If you have a ssh key already set up, you can remove it.

You also need to change the IP address of the VM in the `ansible/hosts` file.

