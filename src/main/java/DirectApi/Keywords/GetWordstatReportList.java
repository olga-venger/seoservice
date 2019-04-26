package DirectApi.Keywords;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetWordstatReportList extends WordstatReport {

    @SerializedName("data")
    transient
    private ArrayList<WordstatReportStatusInfo> reportStatusInfos;

    public GetWordstatReportList() {
        super("GetWordstatReportList");
    }

    public boolean reportIsReady(String statusInfo) {
        if(statusInfo.equals("Done")) return true;
        else return false;
    }

    public String getReportStatusInfo(Integer reportID) {
        String status = "Non status";

        for(int i = reportStatusInfos.size()-1; i >= 0; i--){
            int ID = reportStatusInfos.get(i).getReportID();
            if ( ID == reportID) {
                return reportStatusInfos.get(i).getStatusReport();
            } else status = "Non report";
        }

        return status;
    }

}

