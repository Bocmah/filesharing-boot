 #!/usr/bin/make

SHELL = /bin/sh

docker_compose_bin := $(shell command -v docker-compose 2> /dev/null)

.PHONY: docker-up docker-down docker-rebuild build build-frontend

# Docker-related tasks
docker-up:
	$(docker_compose_bin) up -d

docker-down:
	$(docker_compose_bin) down

docker-rebuild:
	$(docker_compose_bin) up -d --build

# Build-related tasks
build:
	mvn clean package

# Frontend-related tasks
build-frontend:
	npm run build