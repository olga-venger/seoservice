package com.seoservice.naturaltextanalizer;
/*
public class WordRankZipf {
    Integer currentFrequency = 0;
    Integer rank;
    Double percent;
    Integer frequency;


    public WordRankZipf(int currentFrequency){
        this.currentFrequency = currentFrequency;
    }

    public WordRankZipf(double C, Integer rank, int currentFrequency){
        this.rank = rank;
        this.frequency = (int)Math.ceil(C / rank);
        this.currentFrequency = currentFrequency;
        int denominator = (frequency > currentFrequency ? frequency : currentFrequency);
        this.percent = 100 - Math.ceil((Math.abs(currentFrequency - frequency) * 100) / denominator);
        if (percent == 0) {
            percent++;
        }
    }

    public Integer getCurrentFrequency() {
        return currentFrequency;
    }

    public Integer getRank() {
        return rank;
    }

    public Double getPercent() {
        return percent;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setCurrentFrequency(Integer currentFrequency) {
        this.currentFrequency = currentFrequency;
    }
}
*/

public class WordRankZipf {
    private Integer currentFrequency = 0;
    private Integer rank;
    private Double percent;
    private Integer frequency;

    public WordRankZipf(int currentFrequency){
        this.currentFrequency = currentFrequency;
    }

    public WordRankZipf(double C, Integer rank, int currentFrequency){
        this.rank = rank;
        this.frequency = (int)Math.ceil(C / rank);
        this.currentFrequency = currentFrequency;
        int denominator = (frequency > currentFrequency ? frequency : currentFrequency);
        this.percent = 100 - Math.ceil((Math.abs(currentFrequency - frequency) * 100) / denominator);
        if (percent == 0) {
            percent++;
        }
    }

    public Integer getCurrentFrequency() {
        return currentFrequency;
    }

    public Integer getRank() {
        return rank;
    }

    public Double getPercent() {
        return percent;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setCurrentFrequency(Integer currentFrequency) {
        this.currentFrequency = currentFrequency;
    }
}

