# High-Throughput Text Processing Engine

Java 21 + Spring Boot 3 | AI-Powered Regulatory Intelligence (Spring AI + OpenAI) | PostgreSQL | Concurrent Processing | Docker | Terraform | Live on Render

Live URL:  
https://federal-regulations-analyzer-fahp.onrender.com

---

## Overview

A cloud-native backend system for ingesting, processing, and analyzing regulatory text data from public APIs.

The system combines:
- High-performance concurrent processing (Java Parallel Streams)
- AI-powered analysis using OpenAI via Spring AI
- Embedding-based similarity search (RAG-style retrieval)
- Persistent storage using PostgreSQL

It is designed for scalability, stateless execution, and production-grade AI integration.

---

## Architecture

Client → Spring Boot API → AI Processing Layer (Spring AI + OpenAI) → PostgreSQL

Key design principles:
- Stateless backend service enabling horizontal scalability
- No session dependency between requests
- AI-assisted regulatory analysis pipeline
- Embedding-based similarity retrieval (vector-style matching)

---

## AI / ML Processing Layer

This system integrates Spring AI with OpenAI models to provide:

- Regulatory text embedding generation
- Semantic similarity search across regulatory insights
- Context-aware LLM analysis using ChatClient
- AI-driven regulatory density scoring (0–100 scale)
- Retrieval-Augmented Generation (RAG-style pipeline)

---

## Engineering Highlights

- Concurrent data ingestion using Java Parallel Streams
- REST API integration with eCFR public data source
- SHA-256 checksum generation for data integrity validation
- AI-powered regulatory analysis (Spring AI + OpenAI)
- Embedding-based semantic search
- PostgreSQL persistence layer
- Stateless Spring Boot architecture
- JVM tuning for containerized environments
- Multi-stage Docker builds (380MB → 89MB)
- Terraform infrastructure configuration
- GitHub Actions CI/CD pipeline
- Live deployment on Render

---

## Performance & Load Testing

The system was evaluated using Apache JMeter under controlled load testing.

- Tool: Apache JMeter
- Deployment: Single-instance Render environment
- Concurrency: (define your thread count if needed)
- Test type: sustained load test

Results:
- Throughput: up to ~5,000 requests/sec
- p99 latency: ~118ms
- Stable processing under sustained concurrent load

Note:
Results reflect single-node deployment using Java Parallel Streams and PostgreSQL-backed persistence with AI enrichment.

---

## Data Storage

- Current: PostgreSQL (primary production database)
- Development fallback: H2 embedded database (optional local testing)
- Schema: Designed for AI analysis, embeddings, and regulatory insights storage

---

## Technology Stack

| Layer          | Technology |
|----------------|------------|
| Backend        | Java 21, Spring Boot 3 |
| AI Layer       | Spring AI, OpenAI GPT-4o-mini |
| Database       | PostgreSQL |
| Infrastructure | Docker, Terraform |
| CI/CD          | GitHub Actions |
| Deployment     | Render |

---

## Key Features

- High-concurrency data processing
- AI-powered regulatory intelligence engine
- Embedding-based semantic search (RAG architecture)
- External API ingestion (eCFR)
- Text analytics engine
- SHA-256 validation pipeline
- Stateless backend design
- Containerized deployment
- Infrastructure as Code (IaC)
- Automated CI/CD pipeline

---

## What This Project Demonstrates

- Concurrent programming in Java
- Production AI integration (Spring AI + OpenAI)
- Retrieval-Augmented Generation (RAG-style design)
- Backend system design fundamentals
- Performance optimization techniques
- REST API integration
- Cloud deployment workflows
- Containerization with Docker
- Infrastructure automation (Terraform)
- Load testing with JMeter

---

## How to Run Locally

```bash
docker build -t text-processor .
docker run -p 8080:8080 text-processor
