package com.mollanegash.regulator.repository;

import com.mollanegash.regulator.model.RegulatoryInsight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegulatoryInsightRepository extends JpaRepository<RegulatoryInsight, Long> {

    Optional<RegulatoryInsight> findByAgencyName(String agencyName);

    @Query(value = """
            SELECT *
            FROM regulatory_insight
            ORDER BY embedding_vector <=> CAST(:embedding AS vector)
            LIMIT 5
            """, nativeQuery = true)
    List<RegulatoryInsight> findTop5ByEmbeddingSimilarity(@Param("embedding") String embedding);
}