package com.mollanegash.regulator.repository;

import com.mollanegash.regulator.model.RegulatoryInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegulatoryInsightRepository extends JpaRepository<RegulatoryInsight, Long> {

    /**
     * Custom similarity lookup for pgvector-based embeddings.
     * TODO: replace with a native query using pgvector cosine similarity / ivfflat index.
     * Example native query:
     * SELECT * FROM regulatory_insight
     * ORDER BY embedding_vector <-> CAST(:embedding AS real[])
     * LIMIT 1
     */
    @Query(value = "SELECT * FROM regulatory_insight WHERE 1 = 1 LIMIT 1", nativeQuery = true)
    Optional<RegulatoryInsight> findTopByEmbeddingSimilarity(@Param("embedding") List<Float> embedding);
}
