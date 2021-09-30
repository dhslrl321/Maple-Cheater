package com.maplecheater.domain.dto.response;

import com.maplecheater.domain.entity.CheaterDetail;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchCheaterResponseData {
    private String ingameNickname;
    private String ingameServer;
    private Integer reportCount;
    private List<CheaterDetail> cheaterDetails;
}
