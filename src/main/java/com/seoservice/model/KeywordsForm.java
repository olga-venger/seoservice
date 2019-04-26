package com.seoservice.model;


import java.util.ArrayList;

public class KeywordsForm {

    private ArrayList<String> keywords;
    private String textString;

    public KeywordsForm(){}

    public KeywordsForm(String textString) {this.textString = textString;}

    public ArrayList<String> getKeywords() {
        return keywords;
    }

    public String getTextString() {
        return textString;
    }

    public void setTextString(String textString) {
        this.textString = textString;
    }

    public void setKeywords(ArrayList<String> keywords) {
        this.keywords = keywords;
    }

    public ArrayList<String> toArrayList(){
        ArrayList<String> arrayList = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (char ch: textString.toCharArray()
        ) {
            if(ch != '\r' && ch != '\n'){
                str.append(ch);
            } else {
                arrayList.add(str.toString());
                str = new StringBuilder();
            }
        }
        arrayList.add(str.toString());
        return arrayList;
    }
}
