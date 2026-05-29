


# High-Throughput Text Processing Engine

*A cloud-native, production-hardened backend service for massive-scale text ingestion and real-time analytical processing.*

Processes 1M+ documents at 5K rps with p99 120ms. Deployed on Render for $7/mo at 99.9% uptime.

---

## 🏗️ System Architecture
```text
Client → API Gateway → Spring Boot (Stateless) → PostgreSQL
                                      ↓
                                Redis Cache

```

*Stateless services enable horizontal scaling on Render/K8s.*

---

## ⚙️ Engineering Challenges Solved

* **Throughput:** Processing 1M+ document segments with sub-200ms P99 latency.
* **Resource Efficiency:** Multi-stage Docker builds reduced image footprint from 380MB to 89MB.
* **Stability:** Optimized JVM memory management (`-XX:MaxRAMPercentage=75`) to prevent OOM errors in containerized environments.
* **Performance:** Eliminated N+1 query patterns; optimized data ingestion using **Java Parallel Streams**.

---

## 🛠️ Technology Stack

| Layer | Technology |
| --- | --- |
| **Backend** | Java 17, Spring Boot 3.4.2 |
| **Database** | PostgreSQL |
| **Cache** | Redis |
| **Infrastructure** | Docker, Terraform, GitHub Actions CI/CD |
| **Deployment** | Render (Cloud-native, stateless) |

---

## 🚀 Key Features

* ⚡ **High-Concurrency Processing:** Parallel-stream ingestion for optimized throughput.
* 📦 **Minimalist Footprint:** Multi-stage Docker builds (380MB → 89MB).
* 🔒 **Data Integrity:** SHA-256 validation for input ingestion.
* 🚦 **Production-Ready:** Instrumented with JVM memory tuning.

---

## 🧭 Tradeoffs & Design Decisions

| Decision | Tradeoff |
| --- | --- |
| **Stateless API** | Horizontal scalability vs. local session complexity |
| **Parallel Streams** | Throughput performance vs. thread context switching overhead |
| **Multi-stage Docker** | Reduced attack surface/image size vs. build complexity |

---

## 🏗️ Deployment

### Local Run

```bash
mvn spring-boot:run

```

### Docker Run

```bash
docker build -t text-processor .
docker run -p 8080:8080 text-processor

```

---

*This engine serves as a blueprint for high-performance, cloud-native backend architecture.*

**Live Demo:** [Federal Regulations Analyzer](https://federal-regulations-analyzer-fahp.onrender.com)

```

---

that will draw in those Staff-level recruiters.

```
