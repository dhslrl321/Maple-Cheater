package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddCheaterRequestData;
import com.maplecheater.domain.dto.response.CheaterDetailResponseData;
import com.maplecheater.domain.dto.response.SearchCheaterResponseData;
import com.maplecheater.domain.entity.Cheater;
import com.maplecheater.domain.entity.CheaterDetail;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.repository.cheater.CheaterRepository;
import com.maplecheater.domain.repository.cheaterdetail.CheaterDetailRepository;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.exception.CheaterNotFoundException;
import com.maplecheater.exception.IllegalDataException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        List<CheaterDetailResponseData> details = cheaterDetailRepository
                .findAllByCheaterNickname(nickname);

        return SearchCheaterResponseData.builder()
                .cheaterNickname(cheater.getIngameNickname())
                .cheaterServer(cheater.getIngameServer().getServer())
                .reportCount(details.size())
                .cheaterReportHistories(details)
                .build();
    }

    /**
     * cheater request data 를 받아 치터를 저장한다.
     * 만약 기존에 치터가 존재한다면 CheaterDetail 에만 저장하고,
     * 존재하지 않는다면 Cheater 추가 + CheaterDetail 추가
     * @param request : AddCheaterRequestData
     */
    public CheaterDetail addCheater(AddCheaterRequestData request) {
        IngameServer ingameServer = ingameServerRepository.findById(request.getIngameServer())
                .orElseThrow(() -> new IllegalDataException(request.getIngameServer() + " 서버는 존재하지 않습니다."));

        CheatingType cheatingType = cheatingTypeRepository.findById(request.getCheatingType())
                .orElseThrow(() -> new IllegalDataException(request.getCheatingType() + " 치팅 타입은 존재하지 않습니다."));

        Optional<Cheater> optionalCheater = cheaterRepository.findByIngameNickname(request.getIngameNickname());

        if(optionalCheater.isPresent()) { // 치터가 존재한다면 cheaterDetail 에 저장해야함
            if(!ingameServer.getId().equals(request.getIngameServer())) {
                throw new IllegalDataException("현재 신고하려는 치터의 서버와 저장된 치터의 서버가 일치하지 않습니다");
            }
            return cheaterDetailRepository.save(CheaterDetail.builder()
                    .cheater(optionalCheater.get())
                    .cheatingType(cheatingType)
                    .cheatingDatetime(request.getCheatingDatetime())
                    .situation(request.getSituation())
                    .build());
        }

        Cheater savedCheater = cheaterRepository.save(Cheater.builder()
                .registeredAt(LocalDateTime.now())
                .ingameServer(ingameServer)
                .ingameNickname(request.getIngameNickname())
                .build());

        return cheaterDetailRepository.save(CheaterDetail.builder()
                .cheater(savedCheater)
                .cheatingType(cheatingType)
                .situation(request.getSituation())
                .cheatingDatetime(request.getCheatingDatetime())
                .cheatingType(cheatingType)
                .build());
    }
}
