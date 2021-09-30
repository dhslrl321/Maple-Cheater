package com.maplecheater.domain.type;

import lombok.Getter;

@Getter
public enum ReportStatus {
    PENDING("보류중"),
    REJECTED("거절"),
    ACCEPTED("완료");

    private String status;

    ReportStatus(String status){
        this.status = status;
    }
}
