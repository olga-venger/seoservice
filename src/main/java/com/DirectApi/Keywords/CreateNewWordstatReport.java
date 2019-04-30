package com.DirectApi.Keywords;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CreateNewWordstatReport extends WordstatReport{

    @SerializedName("data") transient
    public Integer ID;

    public CreateNewWordstatReport(){
        super("CreateNewWordstatReport");
    }

    public CreateNewWordstatReport(ArrayList<String> phrases, ArrayList<Integer> geoID){
        super("CreateNewWordstatReport");
        setParam(new NewWordstatReportInfo(phrases, geoID));
    }
}
