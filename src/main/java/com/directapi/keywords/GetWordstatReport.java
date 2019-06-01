package com.directapi.keywords;

import com.google.gson.annotations.SerializedName;

import java.util.*;

public class GetWordstatReport extends WordstatReport{

    @SerializedName("data") transient
    public ArrayList<WordstatReportInfo> data;

    public GetWordstatReport(){
        super("GetWordstatReport");
    }

    public GetWordstatReport(Integer param){
        super("GetWordstatReport");
        setParam(param);
    }

    public ArrayList<WordstatReportInfo> getData() {
        return data;
    }

    /**
     * Shows statistics for all phrases in report (not sorted)
     * Показывает статистику по всем фразам в отчёте (не сортировано)
     * @return List<WordstatItem>
     */
    public List<WordstatItem> getAllSearchedWith(){
        List<WordstatItem> list = new ArrayList<>();
        for(int i = 0; i< this.getData().size(); i++){
            list.addAll(this.getData().get(i).getSearchedWith());
        }
        return list;
    }
    public List<WordstatItem> getAllSearchedWithSorted(boolean ascend){
        List<WordstatItem> list = getAllSearchedWith();
        Comparator<WordstatItem> descending = (WordstatItem o1, WordstatItem o2)-> o2.getShows() - o1.getShows();
        Comparator<WordstatItem> ascending = (WordstatItem o1, WordstatItem o2)-> o1.getShows() - o2.getShows();
        if(ascend){
            list.sort(ascending);
        }
        else {
            list.sort(descending);
        }
        return list;
    }
}
