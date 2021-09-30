package com.maplecheater.service;

import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.cheaterdetail.CheaterDetailRepository;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.exception.CheaterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class CheaterService {
    private final CheaterRepository cheaterRepository;
    private final CheaterDetailRepository cheaterDetailRepository;
    private final IngameServerRepository ingameServerRepository;
    private final CheatingTypeRepository cheatingTypeRepository;

    /**
     * cheater 의 ingame nickname 을 받아 모든 cheating 정보를 확인한다.
     *
     * @param nickname
     * @return cheating type 에 따른 count 와 각각 cheating 에 대한 situation DTO 를 반환
     */
    public SearchCheaterResponseData getCheater(String nickname) {
        Cheater cheater = cheaterRepository.findByIngameNickname(nickname)
                .orElseThrow(() -> new CheaterNotFoundException(nickname));

        List<CheaterDetail> details = cheaterDetailRepository
                .findAllByCheaterNickname(nickname);

        return SearchCheaterResponseData.builder()
                .ingameNickname(cheater.getIngameNickname())
                .ingameServer(cheater.getIngameServer().getServer())
                .reportCount(details.size())
                .cheaterDetails(details)
                .build();
    }

}
