package DirectApi.Keywords;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NewWordstatReportInfo {
    @SerializedName("Phrases")
    private ArrayList<String> phrases;

    @SerializedName("GeoID")
    private ArrayList<Integer> geoID;

    public NewWordstatReportInfo(ArrayList<String> phrases, ArrayList<Integer> geoID){
        this.phrases = phrases;
        this.geoID = geoID;
    }


}
