package com.maplecheater.service;

import com.maplecheater.domain.dto.request.AddReportRequestData;
import com.maplecheater.domain.dto.request.UpdateReportStatusRequestData;
import com.maplecheater.domain.dto.response.*;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
                .registeredAt(LocalDateTime.now())
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

    /**
     * 관리자 권한으로 모든 신고서를 확인한다.
     *
     * @return Pageable List
     */
    public Page<ReportPreviewResponseData> getReports(Pageable pageable) {
        return reportRepository.findAllDTO(pageable);
    }

    /**
     * 관리자 권한으로 신고서 단건을 조회한다.
     *
     * @param id : 조회하려는 page 의 id
     * @return report entity
     */
    public ReportDetailResponseData getReport(Long id) {
        ReportDetailResponseData response = reportRepository.findByIdDTO(id)
                .orElseThrow(() -> new IllegalDataException("[" + id + "] 는 존재하지 않는 신고서입니다."));
        return response;
    }


    /**
     * accept boolean 을 받아 report 의 상태를 update 한다.
     *
     * @param request : boolean accept 만 존재하는 dto
     * @return update 된 report
     */
    public UpdateReportStatusResponseData updateStatus(UpdateReportStatusRequestData request, Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalDataException("[" + reportId + "]" + " 는 존재하지 않는 report 입니다."));

        if(request.getAccepted()) {
            report.accept();
        } else if (!request.getAccepted()) {
            report.reject();
        }

        Report savedReport = reportRepository.save(report);
        return UpdateReportStatusResponseData.builder()
                .reportId(savedReport.getId())
                .reporterEmail(savedReport.getUser().getEmail())
                .cheaterIngameNickname(savedReport.getIngameNickname())
                .status(savedReport.getStatus())
                .build();
    }
}
