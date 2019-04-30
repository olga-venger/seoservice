package com.DirectApi.Keywords;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class WordstatReportInfo {
    @SerializedName("phrase")
    private String phrase;

    @SerializedName("GeoID")
    private int[] GeoID;

    @SerializedName("SearchedWith")
    private ArrayList<WordstatItem> searchedWith;

    @SerializedName("SearchedAlso")
    private ArrayList<WordstatItem> searchedAlso;

    WordstatReportInfo(){}

    public ArrayList<WordstatItem> getSearchedWith() {
        return searchedWith;
    }

    public ArrayList<WordstatItem> getSearchedAlso() {
        return searchedAlso;
    }

    public LinkedHashMap toHashMap(ArrayList<WordstatItem> wsItem){
        LinkedHashMap<String, Integer> hashMapWordstatItem = new LinkedHashMap<>();
        for (WordstatItem item: wsItem) {
            hashMapWordstatItem.put(item.getPhrase(), item.getShows());
        }
        return hashMapWordstatItem;
    }


}
