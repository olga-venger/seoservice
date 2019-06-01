package com.seoservice.naturaltextanalizer;

import com.pullenti.morph.MorphLang;
import com.pullenti.morph.MorphToken;
import com.pullenti.morph.MorphWordForm;
import com.pullenti.morph.Morphology;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class TextAnalizer {

    private String text;                        // текст в виде строки
    private List<WordInText> textAsListWords = new ArrayList<>();   // текст как список слов со свойствами
    private int numberOfSentences;              // общее количество предложений
    private int numberOfWord = 0;               // общее количество слов
    private int numberOfSyllables = 0;          // общее количество слогов
    private int numberOfCharsWithoutSpace;      // количество символов без пробелов
    private double percentTextNaturalZipf;          // процент естественности по Ципфу
    private double indexFlesh;                      // индекс удобочитаемости

    public TextAnalizer(){}

    public TextAnalizer(String text) throws Exception {
        this.text = text;
        //Вычисление списка неповторяющихся слов + процент естественности по Ципфу
        setTextAsListWords();

        //Определение количества предложений
        String sentences[] = text.split("[.!?\n\r]\\s*");
        numberOfSentences = sentences.length;

        StringTokenizer stringTokenizer = new StringTokenizer(text, " .-,!;:?\"<>[]\\\n\t\r»«—()…");

        //Количество символов без пробелов и количество слогов
        numberOfCharsWithoutSpace = 0;
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken();
            numberOfSyllables += WordInText.numberOfSyllables(token);
            numberOfCharsWithoutSpace += token.length();
        }
    }

    public List<WordInText> getTextAsListWords() {
        return textAsListWords;
    }

    public int getNumberOfSentences() {
        return numberOfSentences;
    }

    public int getNumberOfWord() {
        return numberOfWord;
    }

    public int getNumberOfSyllables() {
        return numberOfSyllables;
    }

    public int getNumberOfCharsWithoutSpace() {
        return numberOfCharsWithoutSpace;
    }

    public double getPercentTextNaturalZipf() {
        return new BigDecimal(percentTextNaturalZipf).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    private ArrayList<MorphToken> getTextToken(String text) throws Exception {
        Morphology morphology = new Morphology();
        morphology.initialize(MorphLang.RU);
        StringTokenizer stringTokenizer = new StringTokenizer(text, " .-,!;:?\"<>[]\\\n\t\r»«—()…");
        numberOfWord = 0;
        ArrayList<MorphToken> textToken = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            textToken.addAll(morphology.process(stringTokenizer.nextToken().toLowerCase(), MorphLang.RU, null));
            numberOfWord++;
        }
        return textToken;
    }

    public double getAverageSentenceLength() {
        return new BigDecimal((double) numberOfWord / numberOfSentences).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    public double getAverageWordLength() {
        return new BigDecimal((double) numberOfCharsWithoutSpace / numberOfWord).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    //недописан
    public Integer checkDuplicationWords() {
        StringTokenizer stringTokenizer = new StringTokenizer(text, " .-,!;:?\"<>[]\\\n\t\r»«—()…");

        String currentWord = "";
        String previousWord = "";
Integer counter = 0;
        while (stringTokenizer.hasMoreTokens()) {
            currentWord = stringTokenizer.nextToken();
            if (currentWord.equals(previousWord)) {
//                System.out.println(currentWord);
                counter++;
            }
            else {
                previousWord = currentWord;
            }
        }
        return counter;
    }

    public double getIndexFlesh(){
        Double result = 206.835 - 1.3 * ((double)numberOfWord/numberOfSentences) - 60.1 * ((double)numberOfSyllables/numberOfWord);
        return new BigDecimal(result).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    private void setTextAsListWords() throws Exception {
        HashMap<String, WordRankZipf> hashMap = new LinkedHashMap<>();
        for (MorphToken t : getTextToken(text)
        ) {
            String key = "";
            if (t.wordForms.size() > 0) {
                MorphWordForm form = t.wordForms.get(0);

                //"Отсеивание" лишних стоп-слов
                if (!form._getClass().isPreposition() && !form._getClass().isMisc()
                        && !form._getClass().isConjunction() && !form._getClass().isUndefined()) {
                    key = t.getLemma().toLowerCase();
                }
            }

            if (!key.isEmpty()) {
                if (hashMap.containsKey(key)) {
                    hashMap.put(key, new WordRankZipf(hashMap.get(key).getCurrentFrequency() + 1));
                } else {
                    hashMap.put(key, new WordRankZipf(1));
                }
            }
        }
        for (Map.Entry<String, WordRankZipf> entry : hashMap.entrySet()
        ) {
            textAsListWords.add(new WordInText(entry.getKey(), entry.getValue()));
        }

        Comparator<WordInText> descending =
                (WordInText o1, WordInText o2)
                        -> o2.getFrequency() - o1.getFrequency();
        textAsListWords.sort(descending);
        //текст без подсчёта по ципфу

        double C = textAsListWords.get(0).getFrequency();
        int rank = 1;
        double sum = 0;
        int sumUseWord = 0;

        for (WordInText word : textAsListWords
        ) {
            word.setWordRankZipf(new WordRankZipf(C, rank, word.getFrequency()));
            sum += word.getFrequency() * (word.getWordRankZipf().getPercent() / 100);
            sumUseWord += word.getFrequency();
            rank++;
        }
        percentTextNaturalZipf =  (sum / sumUseWord) * 100.0;
    }
}

