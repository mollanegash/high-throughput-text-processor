# FedFeral Regulatory Insights Engine
**An analytical platform for quantifying administrative burden and regulatory complexity.**

## 📊 Project Overview
This engine is a sophisticated "RegTech" (Regulatory Technology) tool designed to process the Electronic Code of Federal Regulations (eCFR). Moving beyond simple data ingestion, it applies **Economic Theory** to quantify "Red Tape" by calculating the **Requirement Density (RD)** of various federal agencies.



## 🎓 The "Economics + CS" Logic
Drawing on my background in **Economics**, this tool identifies prescriptive legal language that creates compliance costs for businesses and individuals.

### The Requirement Density (RD) Algorithm
The engine uses an NLP-based heuristic to scan regulatory text for prescriptive keywords (e.g., *shall, must, prohibited, penalty, compliance*). 

$$RD = \frac{\text{Count of Prescriptive Keywords}}{\text{Total Word Count}}$$

**Key Features:**
* **Red Tape Flagging:** Automatically flags agencies with an RD > 5.0% as "High Burden."
* **Data Integrity:** Every agency dataset is processed through a **SHA-256 Checksum** generator to ensure data consistency and track version changes in federal law.

## 🛠 Technical Architecture
Designed with high-performance Java principles to handle large-scale federal datasets:

* **Java 17 & Spring Boot 3.4.2:** Modern backend stack.
* **Parallel Processing:** Uses **Java Parallel Streams** to fetch and analyze multiple agency endpoints concurrently, optimizing CPU usage and reducing sync time.
* **Persistence Layer:** **Spring Data JPA** with an **H2 In-Memory Database** for fast, ACID-compliant data management.
* **Frontend Dashboard:** A responsive, real-time UI built with **Thymeleaf** and **Tailwind CSS**.



## 🚀 Getting Started

### Prerequisites
* Java 17 or higher
* Maven 3.6+

### Installation
1. **Clone the repository:**
   ```bash
   git clone [https://github.com/mollanegash/federal-regulations-analyzer.git](https://github.com/mollanegash/federal-regulations-analyzer.git)
