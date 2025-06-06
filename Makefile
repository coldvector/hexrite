CONTAINER_ENGINE := $(shell if command -v podman &>/dev/null; then echo podman; else echo docker; fi)
DEPENDENCIES := $(CONTAINER_ENGINE) javac node
POSTGRES_IMAGE := docker.io/library/postgres:17.0

.PHONY: check-deps clean dev-backend dev-frontend help

check-deps:
	@for cmd in $(DEPENDENCIES); do \
		if ! command -v $$cmd &>/dev/null; then \
			echo "Couldn't find $$cmd!"; \
			exit 1; \
		fi; \
	done

clean: check-deps
	@cd ./backend; \
	./mvnw --quiet clean; \
	echo "Cleaned build artifacts for backend"; \
	cd ../frontend; \
	rm -rf node_modules dist; \
	echo "Removed build artifacts for frontend"

.dev-db: check-deps
	@$(CONTAINER_ENGINE) container run \
		--quiet \
		--detach \
		--rm \
		--name postgres_hexrite \
		--publish 5432:5432 \
		--env POSTGRES_DB=hexrite \
		--env POSTGRES_USER=hexrite \
		--env POSTGRES_PASSWORD=hexrite \
		--cpus="0.50" \
		--memory="512M" \
		$(POSTGRES_IMAGE)

dev-backend: check-deps .dev-db
	@trap '$(CONTAINER_ENGINE) container stop postgres_hexrite' EXIT; \
	cd backend && ./mvnw quarkus:dev

dev-frontend: check-deps
	@cd frontend && npm install && npm run dev

help:
	@echo "Available targets:"
	@echo "  check-deps      - Check for required system dependencies"
	@echo "  clean           - Clean backend and frontend builds"
	@echo "  dev-backend     - Start backend in development mode"
	@echo "  dev-frontend    - Start frontend in development mode"
	@echo "  help            - Show this help message"
