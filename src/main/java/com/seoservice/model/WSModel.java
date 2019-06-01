package com.seoservice.model;
import com.directapi.keywords.WordstatItem;

import java.util.ArrayList;
import java.util.List;

public class WSModel {

    private List<WordstatItem> wordstatItems;
    private ArrayList<Integer> checkedItems;

    public WSModel(){}

    public ArrayList<Integer> getCheckedItems() {
        return checkedItems;
    }

    public void setCheckedItems(ArrayList<Integer> checkedItems) {
        this.checkedItems = checkedItems;
    }

    public List<WordstatItem> getWordstatItems() {
        return wordstatItems;
    }

    public void setWordstatItems(List<WordstatItem> items) {
        this.wordstatItems = items;
    }
}
