.PHONY: dev

dev:
	@echo "Loading .env and starting Quarkus..."
	set -a; source .env; set +a; ./mvnw quarkus:dev
	@echo "Quarkus started. You can access it at http://localhost:8080"
	@echo "To stop the server, press Ctrl+C"