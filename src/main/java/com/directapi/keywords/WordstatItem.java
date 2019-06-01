package com.directapi.keywords;

import com.google.gson.annotations.SerializedName;

public class WordstatItem {
    @SerializedName("Phrase")
    private String phrase;
    @SerializedName("Shows")
    private int shows;

    public WordstatItem(){}

    public WordstatItem(String phrase, Integer shows) {
        this.phrase = phrase;
        this.shows = shows;
    }

    public String getPhrase() {
        return phrase;
    }

    public int getShows() {
        return shows;
    }

    public String getWordstatItem(){
        return "Phrase: " + phrase + ", Shows: " + shows;
    }
}
