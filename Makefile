.PHONY: check-deps clean backend frontend help

check-deps:
	@if ! command -v javac &>/dev/null; then \
		echo "Couldn't find JDK!"; \
		exit 1; \
	fi; \
	if ! command -v node &>/dev/null; then \
		echo "Couldn't find Node!"; \
		exit 1; \
	fi

clean: check-deps
	@cd ./backend && ./mvnw --quiet clean; \
	cd ../frontend && rm -rf node_modules dist

backend: check-deps
	@cd backend && ./mvnw clean install -DskipTests -DskipITs

frontend: check-deps
	@cd frontend && npm install && npm run build

all: check-deps backend frontend

help:
	@echo "Available targets:"
	@echo "  check-deps    - Check for required system dependencies"
	@echo "  clean         - Clean backend and frontend builds"
	@echo "  backend       - Build backend artifact"
	@echo "  frontend      - Build frontend assets"
	@echo "  help          - Show this help message"
