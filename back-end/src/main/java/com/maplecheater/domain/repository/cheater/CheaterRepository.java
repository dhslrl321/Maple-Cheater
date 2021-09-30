package com.maplecheater.domain.repository.cheater;

import com.maplecheater.domain.entity.Cheater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheaterRepository extends JpaRepository<Cheater, Long>, CheaterRepositoryCustom {
}
