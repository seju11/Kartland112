#!/usr/bin/env bash
# Ensure script stops on error
set -o errexit

# Install Java 17 if not available
apt-get update && apt-get install -y openjdk-17-jdk

# Make mvnw executable and build the JAR
chmod +x mvnw
./mvnw clean package -DskipTests
