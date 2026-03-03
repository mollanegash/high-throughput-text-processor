package com.mollanegash.regulator.repository;

import com.mollanegash.regulator.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, String> {
}