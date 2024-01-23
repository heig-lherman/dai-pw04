# DAI - Practical Work 4 - API server for managing VMs

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
### Ansible

The application is deployed by connecting to the server using SSH and pulling from the git repository.

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

### Manual deployment

- `sudo apt update` to update the package list
- `sudo apt upgrade` to upgrade the packages
- Create the `./ssh/authorized_keys` file and copy the content of the ./ansible/authorized_keys file into it. (You must have 
the ssh folder in the machine)
```shell
  mkdir -p ~/.ssh
  chmod 0700 ~/.ssh
  cp ./ansible/authorized_keys ~/.ssh/authorized_keys
  chmod 0600 ~/.ssh/authorized_keys
  ```
- Install apache2-utils and remove old versions of docker
```shell
sudo apt install apache2-utils
sudo apt remove docker.io docker-doc docker-compose docker-compose-v2 podman-docker
sudo apt install ca-certificates curl gnupg
  ```
- Add Docker’s official GPG key
```shell
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
```

- Add the Docker repository to APT sources
```shell
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```

- Install Docker Engine and dependencies
```shell
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

- Create the docker group and add your user
```shell
sudo groupadd docker
sudo usermod -aG docker $USER
```

- Restart the service and enabling it
```shell
sudo systemctl restart docker
sudo systemctl enable docker
sudo systemctl enable containerd
```

- Clone the repositories
```shell
rm -rf ~/heig-vd-dai-course-code-examples
rm -rf ~/dai-pw04
git clone https://github.com/heig-vd-dai-course/heig-vd-dai-course-code-examples.git ~/heig-vd-dai-course-code-examples
git clone https://github.com/heig-lherman/dai-pw04.git ~/dai-pw04
```

- You must configure the followig files:
  - ~/heig-vd-dai-course-code-examples/23-practical-work-4/traefik-secure/.env
  - ~/heig-vd-dai-course-code-examples/23-practical-work-4/whoami-with-traefik-secure/.env

- Create ~/heig-vd-dai-course-code-examples/23-practical-work-4/traefik-secure/secrets and configure the username and password
```shell
mkdir ~/heig-vd-dai-course-code-examples/23-practical-work-4/traefik-secure/secrets
htpasswd -c secrets/auth-users.txt admin
```


- Run all the containers
```shell
cd ~/heig-vd-dai-course-code-examples/23-practical-work-4/traefik-secure/
docker-compose up -d
cd ~/

cd ~/heig-vd-dai-course-code-examples/23-practical-work-4/whoami-with-traefik-secure/
docker-compose up -d
cd ~/

cd ~/dai-pw04/
docker-compose up -d
cd ~/
```

## DNS configuration

We use terraform for the configuration of our DNS rules on the cloudflare platform, the configuration is
available in the `terraform` folder, which anyone can use to configure their own DNS rules for the app,
provided they change the secrets to point to their own respective accounts.

As for the actual configuration itself, the application requires a simple A record pointing to the server's IP address,
defined as below (for the `api.dai.heig.lutonite.dev` domain):

```
api.dai.heig.lutonite.dev. 300 IN A 185.144.38.63
```

In cloudflare, we disabled the proxy since we are using traefik to handle the SSL certificates, as
well as the redirection to the proper services, and we didn't want cloudflare to intervene in the TLS chain.

We have added additional domain names all pointing to the same IP address for access to the whoami application
as well as the traefik dashboard.

