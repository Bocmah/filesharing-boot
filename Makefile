 #!/usr/bin/make

SHELL = /bin/sh

docker_compose_bin := $(shell command -v docker-compose 2> /dev/null)

.PHONY: docker-up docker-down docker-rebuild

# Docker-related tasks
docker-up:
	$(docker_compose_bin) up -d

docker-down:
	$(docker_compose_bin) down

docker-rebuild:
	$(docker_compose_bin) up -d --build