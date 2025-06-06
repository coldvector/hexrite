# Hexrite

Chat with your LLMs.

## Running dev mode

You can run the application in dev mode that enables live coding using below. Dev UI should be accessible at [http://localhost:3005/q/dev-ui/](http://localhost:3005/q/dev-ui/).

```shell
make dev-backend
```

## Packaging and running JAR

1. Build the JAR

   ```shell
   ./mvnw package
   ```

2. Run the JAR

   ```shell
   java -jar ./target/hexrite-1.0.0.jar
   ```

## Packaging and running native executable

1. Build native executable

   ```shell
   ./mvnw package -Dnative
   ```

2. Run the executable

   ```shell
   ./target/hexrite-1.0.0
   ```
