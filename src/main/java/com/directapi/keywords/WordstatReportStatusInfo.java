package com.directapi.keywords;

import com.google.gson.annotations.SerializedName;

public class WordstatReportStatusInfo {
    @SerializedName("ReportID")
    private Integer reportID;
    @SerializedName("StatusReport")
    private String statusReport;

    public WordstatReportStatusInfo(){}

    public Integer getReportID() {
        return reportID;
    }

    public String getStatusReport() {
        return statusReport;
    }
}
