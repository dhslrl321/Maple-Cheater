package com.maplecheater.domain.repository.cheaterdetail;

import com.maplecheater.domain.entity.CheaterDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheaterDetailRepository extends JpaRepository<CheaterDetail, Long>, CheaterDetailRepositoryCustom {

}
