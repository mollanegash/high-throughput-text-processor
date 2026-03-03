package gov.usds.regulations.repository;

import gov.usds.regulations.model.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<Agency, String> {
}