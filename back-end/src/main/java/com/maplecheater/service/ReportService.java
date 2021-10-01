package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.response.AddReportResponseData;
import com.maplecheater.domain.entity.CheatingType;
import com.maplecheater.domain.entity.IngameServer;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.entity.User;
import com.maplecheater.domain.repository.cheatingtype.CheatingTypeRepository;
import com.maplecheater.domain.repository.ingameserver.IngameServerRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.domain.repository.user.UserRepository;
import com.maplecheater.domain.type.ReportStatus;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService {
    private final CheatingTypeRepository cheatingTypeRepository;
    private final IngameServerRepository ingameServerRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;

    /**
     * 치터 신고서를 생성한다.
     * server, 신고하는 user, cheatingType 에 대한 검증을 수행한다.
     *
     * @param request : AddReport DTO
     * @return AddReportResponseData : server, nickname, cheatingType
     */
    public AddReportResponseData addReport(AddReportRequestData request, Long tokenUserId) {

        if(request.getUserId() != tokenUserId) { // 요청하는 user 와 실제 user 가 다른 경우
            throw new UnauthorizedException();
        }

        CheatingType cheatingType = cheatingTypeRepository.findById(request.getCheatingType())
                .orElseThrow(() -> new IllegalDataException("존재하지 않는 신고 분류입니다."));
        IngameServer ingameServer = ingameServerRepository.findById(request.getIngameServer())
                .orElseThrow(() -> new IllegalDataException("존재하지 않는 서버입니다."));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalDataException("존재하지 않는 사용자입니다."));

        Report savedReport = reportRepository.save(Report.builder()
                .ingameNickname(request.getIngameNickname())
                .status(ReportStatus.PENDING)
                .situation(request.getSituation())
                .cheatingDatetime(request.getCheatingDatetime())
                .user(user)
                .cheatingType(cheatingType)
                .ingameServer(ingameServer)
                .build());

        return AddReportResponseData.builder()
                .ingameNickname(savedReport.getIngameNickname())
                .ingameServer(savedReport.getIngameServer().getServer())
                .cheatingType(savedReport.getCheatingType().getType())
                .build();
    }
}
