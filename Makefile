POSTGRES_IMAGE := docker.io/library/postgres:17.0

.PHONY: check-deps clean dev-backend dev-frontend help

check-deps:
	@if ! command -v podman &>/dev/null; then \
		echo "Couldn't find Podman!"; \
		exit 1; \
	fi; \
	if ! command -v javac &>/dev/null; then \
		echo "Couldn't find JDK!"; \
		exit 1; \
	fi; \
	if ! command -v node &>/dev/null; then \
		echo "Couldn't find Node!"; \
		exit 1; \
	fi

clean: check-deps
	@cd ./backend; \
	./mvnw --quiet clean; \
	echo "Cleaned build artifacts for backend"; \
	cd ../frontend; \
	rm -rf node_modules dist; \
	echo "Removed build artifacts for frontend"

.dev-db: check-deps
	@podman container run \
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
	@trap 'podman container stop postgres_hexrite' EXIT; \
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
