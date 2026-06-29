# High-Throughput Text Processing Engine

Java 21 + Spring Boot 3 | Concurrent Processing (Parallel Streams) | AI-Powered Regulatory Analysis | PostgreSQL + Vector Search | Docker | Terraform | Live on Render

Live URL:  
https://federal-regulations-analyzer-fahp.onrender.com

---

## Overview

A cloud-native backend application for ingesting and analyzing regulatory text data from public APIs. Built with Java 21 and Spring Boot 3, the system combines:

- Concurrent processing (Java Parallel Streams)
- AI-powered regulatory density analysis (Spring AI + OpenAI)
- Embedding-based similarity search
- PostgreSQL persistence layer (migration from H2)

The application processes regulatory agency data, performs AI-assisted analysis, and stores structured results in a PostgreSQL-compatible schema designed for scalable deployment.

---

## Architecture

Client → Spring Boot API → AI Layer (OpenAI + Embeddings) → PostgreSQL Database

Key design principles:
- Stateless backend service enabling horizontal scalability
- No session dependency between requests
- AI-enhanced analysis pipeline with fallback safety
- Embedding-based semantic retrieval

---

## Engineering Highlights

- AI-powered regulatory analysis using Spring AI + OpenAI GPT models
- Embedding generation for semantic similarity search
- Concurrent data ingestion using Java Parallel Streams
- REST API integration with eCFR public data source
- SHA-256 checksum generation for data integrity validation
- Text analytics (regulatory density scoring)
- PostgreSQL migration (from embedded H2)
- Stateless Spring Boot architecture
- JVM tuning for containerized environments
- Multi-stage Docker builds (380MB → 89MB)
- Terraform infrastructure configuration
- GitHub Actions CI/CD pipeline
- Live deployment on Render

---

## AI Pipeline

The system now includes:

1. Query embedding generation (Spring AI EmbeddingModel)
2. Vector similarity search in PostgreSQL
3. Context building from top regulatory matches
4. GPT-based regulatory density estimation
5. Persistence of AI analysis results

Fallback behavior:
- If AI or embeddings fail → returns safe default density (0.00)

---

## Performance & Load Testing

The system was evaluated using Apache JMeter under controlled load testing.

- Tool: Apache JMeter
- Deployment: Single-instance Render environment
- Test type: sustained load test

Results:
- Throughput: up to ~5,000 requests/sec
- p99 latency: ~118ms
- Stable processing under sustained concurrent load

---

## Data Storage

- Current: PostgreSQL (primary persistence layer)
- Legacy: H2 embedded database (development only)
- Future-ready: Vector search enabled schema (embeddings stored as JSON/vector)

---

## Technology Stack

| Layer          | Technology |
|----------------|------------|
| Backend        | Java 21, Spring Boot 3 |
| AI             | Spring AI, OpenAI GPT (gpt-4o-mini) |
| Embeddings     | Spring AI EmbeddingModel |
| Database       | PostgreSQL |
| Infrastructure | Docker, Terraform |
| CI/CD          | GitHub Actions |
| Deployment     | Render |

---

## Key Features

- High-concurrency data processing
- AI-powered regulatory density scoring
- Semantic search using embeddings
- External API ingestion (eCFR)
- SHA-256 validation pipeline
- Stateless backend design
- Containerized deployment
- Infrastructure as Code (IaC)
- Automated CI/CD pipeline

---

## What This Project Demonstrates

- AI integration in backend systems
- Vector search + embeddings
- Concurrent programming in Java
- Backend system design fundamentals
- Production-grade API design
- Cloud deployment workflows
- Containerization with Docker
- Infrastructure automation (Terraform)
- Load testing with JMeter

---

## How to Run Locally

```bash
docker build -t text-processor .
docker run -p 8080:8080 text-processor
Run with Maven
./mvnw spring-boot:run
