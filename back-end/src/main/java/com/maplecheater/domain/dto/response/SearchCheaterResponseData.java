package com.maplecheater.domain.dto.response;

import com.maplecheater.domain.entity.CheaterDetail;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCheaterResponseData {
    private String cheaterNickname;
    private String cheaterServer;
    private Integer reportCount;
    private List<CheaterDetailResponseData> cheaterReportHistories;
}
