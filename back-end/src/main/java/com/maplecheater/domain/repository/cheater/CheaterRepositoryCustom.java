package com.maplecheater.domain.repository.cheater;

import com.maplecheater.domain.entity.Cheater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheaterRepositoryCustom {
    Optional<Cheater> findByIngameNickname(String nickname);
}
