package com.maplecheater.service;

import com.maplecheater.domain.dto.response.EvidenceImageResponseData;
import com.maplecheater.domain.entity.Report;
import com.maplecheater.domain.repository.evidence.EvidenceRepository;
import com.maplecheater.domain.repository.report.ReportRepository;
import com.maplecheater.exception.IllegalDataException;
import com.maplecheater.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class EvidenceService {
    private final EvidenceRepository evidenceRepository;
    private final ReportRepository reportRepository;
    private final S3Uploader s3Uploader;

    /**
     * reportId 를 받아 해당 id 에 존재하는 모든 image url 을 반환한다.
     *
     * @param reportId 조회하려는 report 의 id
     * @return 이미지 url 리스트
     */
    public List<EvidenceImageResponseData> getImageUrl(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow(
                () -> new IllegalDataException("신고 번호 [" + reportId + "] 가 존재하지 않습니다."));

        return evidenceRepository.findAllByReportId(report.getId());
    }

    /**
     * Multipart Image list 를 받아 이미지를 업로드한다.
     *
     * @param images : 이미지 리스트
     * @return 업로드된 이미지 리스트
     */
    public List<EvidenceImageResponseData> uploadToS3(List<MultipartFile> images) throws IOException {

        if(images.size() == 0) {
            throw new IllegalDataException("업로드할 이미지가 존재하지 않습니다");
        }

        List<String> urls = s3Uploader.upload(images, "static");
        List<EvidenceImageResponseData> result = new ArrayList<>();
        for (String url : urls) {
            result.add(new EvidenceImageResponseData(url));
        }
        return result;
    }
}
