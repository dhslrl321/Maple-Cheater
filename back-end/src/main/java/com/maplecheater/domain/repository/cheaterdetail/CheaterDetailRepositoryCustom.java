package com.maplecheater.domain.repository.cheaterdetail;

import com.maplecheater.domain.dto.response.CheaterDetailResponseData;
import com.maplecheater.domain.entity.CheaterDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheaterDetailRepositoryCustom {
    List<CheaterDetailResponseData> findAllByCheaterNickname(String ingameNickname);
}
