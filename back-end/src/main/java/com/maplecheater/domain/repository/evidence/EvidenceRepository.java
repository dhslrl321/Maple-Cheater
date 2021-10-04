package com.maplecheater.domain.repository.evidence;

import com.maplecheater.domain.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenceRepository extends JpaRepository<Evidence, Long>, EvidenceRepositoryCustom {
}
