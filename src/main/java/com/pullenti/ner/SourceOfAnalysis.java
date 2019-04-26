/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.ner;

/**
 * Анализируемый текст
 */
public class SourceOfAnalysis {

    private String _text;

    /**
     * [Get] Исходный плоский текст
     */
    public String getText() {
        return _text;
    }

    /**
     * [Set] Исходный плоский текст
     */
    public String setText(String value) {
        _text = value;
        return _text;
    }


    private Object _tag;

    /**
     * [Get] Используется произвольным образом
     */
    public Object getTag() {
        return _tag;
    }

    /**
     * [Set] Используется произвольным образом
     */
    public Object setTag(Object value) {
        _tag = value;
        return _tag;
    }


    public boolean clearDust = false;

    /**
     * Делать анализ переходов на новую строку, которые на самом деле таковыми не являются 
     *  (например, они форматируют абзац в PDF).
     * Создать контейнер на основе плоского текста. 
     *  При создании будут автоматически сделаны транслитеральные замены, если они будут найдены.
     * @param txt Текст
     */
    public SourceOfAnalysis(String txt) {
        if (com.pullenti.unisharp.Utils.isNullOrEmpty(txt)) {
            setText("");
            return;
        }
        setText(txt);
    }

    /**
     * Это анализ случаев принудительно отформатированного текста
     * @param txt 
     */
    private String doCrLfCorrection(String txt) {
        int i;
        int j;
        int cou = 0;
        int totalLen = 0;
        for (i = 0; i < txt.length(); i++) {
            char ch = txt.charAt(i);
            if (((int)ch) != 0xD && ((int)ch) != 0xA) 
                continue;
            int len = 0;
            char lastChar = ch;
            for (j = i + 1; j < txt.length(); j++) {
                ch = txt.charAt(j);
                if (((int)ch) == 0xD || ((int)ch) == 0xA) 
                    break;
                else if (((int)ch) == 0x9) 
                    len += 5;
                else {
                    lastChar = ch;
                    len++;
                }
            }
            if (j >= txt.length()) 
                break;
            if (len < 30) 
                continue;
            if (lastChar != '.' && lastChar != ':' && lastChar != ';') {
                boolean nextIsDig = false;
                for (int k = j + 1; k < txt.length(); k++) {
                    if (!com.pullenti.unisharp.Utils.isWhitespace(txt.charAt(k))) {
                        if (Character.isDigit(txt.charAt(k))) 
                            nextIsDig = true;
                        break;
                    }
                }
                if (!nextIsDig) {
                    cou++;
                    totalLen += len;
                }
            }
            i = j;
        }
        if (cou < 4) 
            return txt;
        totalLen /= cou;
        if ((totalLen < 50) || totalLen > 100) 
            return txt;
        StringBuilder tmp = new StringBuilder(txt);
        for (i = 0; i < tmp.length(); i++) {
            char ch = tmp.charAt(i);
            int jj;
            int len = 0;
            char lastChar = ch;
            for (j = i + 1; j < tmp.length(); j++) {
                ch = tmp.charAt(j);
                if (((int)ch) == 0xD || ((int)ch) == 0xA) 
                    break;
                else if (((int)ch) == 0x9) 
                    len += 5;
                else {
                    lastChar = ch;
                    len++;
                }
            }
            if (j >= tmp.length()) 
                break;
            for (jj = j - 1; jj >= 0; jj--) {
                if (!com.pullenti.unisharp.Utils.isWhitespace((lastChar = tmp.charAt(jj)))) 
                    break;
            }
            boolean notSingle = false;
            jj = j + 1;
            if ((jj < tmp.length()) && ((int)tmp.charAt(j)) == 0xD && ((int)tmp.charAt(jj)) == 0xA) 
                jj++;
            for (; jj < tmp.length(); jj++) {
                ch = tmp.charAt(jj);
                if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                    break;
                if (((int)ch) == 0xD || ((int)ch) == 0xA) {
                    notSingle = true;
                    break;
                }
            }
            if (((!notSingle && len > (totalLen - 20) && (len < (totalLen + 10))) && lastChar != '.' && lastChar != ':') && lastChar != ';') {
                tmp.setCharAt(j, ' ');
                crlfCorrectedCount++;
                if ((j + 1) < tmp.length()) {
                    ch = tmp.charAt(j + 1);
                    if (((int)ch) == 0xA) {
                        tmp.setCharAt(j + 1, ' ');
                        j++;
                    }
                }
            }
            i = j - 1;
        }
        return tmp.toString();
    }

    public int crlfCorrectedCount = 0;

    public boolean doWordCorrectionByMorph = false;

    public boolean doWordsMergingByMorph = true;

    public java.util.HashMap<String, String> correctionDict = null;

    /**
     * Произвести транслитеральную коррекцию
     * @param txt корректируемый текст
     * @param info информация о замене (может быть null)
     * @return количество замен
     */
    private static int doTransliteralCorrection(StringBuilder txt, StringBuilder info) {
        int i;
        int j;
        int k;
        int stat = 0;
        boolean prefRusWord = false;
        for (i = 0; i < txt.length(); i++) {
            if (Character.isLetter(txt.charAt(i))) {
                int rus = 0;
                int pureLat = 0;
                int unknown = 0;
                for (j = i; j < txt.length(); j++) {
                    char ch = txt.charAt(j);
                    if (!Character.isLetter(ch)) 
                        break;
                    int code = (int)ch;
                    if (code >= 0x400 && (code < 0x500)) 
                        rus++;
                    else if (m_LatChars.indexOf(ch) >= 0) 
                        unknown++;
                    else 
                        pureLat++;
                }
                if (((unknown > 0 && rus > 0)) || ((unknown > 0 && pureLat == 0 && prefRusWord))) {
                    if (info != null) {
                        if (info.length() > 0) 
                            info.append("\r\n");
                        for (k = i; k < j; k++) {
                            info.append(txt.charAt(k));
                        }
                        info.append(": ");
                    }
                    for (k = i; k < j; k++) {
                        int ii = m_LatChars.indexOf(txt.charAt(k));
                        if (ii >= 0) {
                            if (info != null) 
                                info.append(txt.charAt(k)).append("->").append(m_RusChars.charAt(ii)).append(" ");
                            txt.setCharAt(k, m_RusChars.charAt(ii));
                        }
                    }
                    stat += unknown;
                    prefRusWord = true;
                }
                else 
                    prefRusWord = rus > 0;
                i = j;
            }
        }
        return stat;
    }

    private static String m_LatChars = "ABEKMHOPCTYXaekmopctyx";

    private static String m_RusChars = "АВЕКМНОРСТУХаекморстух";

    private static int calcTransliteralStatistics(String txt, StringBuilder info) {
        if (txt == null) 
            return 0;
        StringBuilder tmp = new StringBuilder(txt);
        return doTransliteralCorrection(tmp, info);
    }

    private int m_TotalTransliteralSubstitutions;

    private int getTotalTransliteralSubstitutions() {
        return m_TotalTransliteralSubstitutions;
    }


    /**
     * Извлечь фрагмент из исходного текста
     * @param position 
     * @param length 
     * @return 
     */
    public String substring(int position, int length) {
        if (length < 0) 
            length = getText().length() - position;
        if ((position + length) <= getText().length() && length > 0) {
            String res = getText().substring(position, position + length);
            if (res.indexOf("\r\n") >= 0) 
                res = res.replace("\r\n", " ");
            if (res.indexOf('\n') >= 0) 
                res = res.replace("\n", " ");
            return res;
        }
        return "Position + Length > Text.Length";
    }

    /**
     * Вычислить расстояние в символах между соседними элементами
     * @param indFrom 
     * @param indTo 
     * @return 
     */
    public int calcWhitespaceDistanceBetweenPositions(int posFrom, int posTo) {
        if (posFrom == (posTo + 1)) 
            return 0;
        if (posFrom > posTo || (posFrom < 0) || posTo >= getText().length()) 
            return -1;
        int res = 0;
        for (int i = posFrom; i <= posTo; i++) {
            char ch = this.getText().charAt(i);
            if (!com.pullenti.unisharp.Utils.isWhitespace(ch)) 
                return -1;
            if (ch == '\r' || ch == '\n') 
                res += 10;
            else if (ch == '\t') 
                res += 5;
            else 
                res++;
        }
        return res;
    }

    public void serialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        com.pullenti.ner.core.internal.SerializerHelper.serializeString(stream, this.getText());
    }

    public void deserialize(com.pullenti.unisharp.Stream stream) throws java.io.IOException {
        setText(com.pullenti.ner.core.internal.SerializerHelper.deserializeString(stream));
    }
    public SourceOfAnalysis() {
    }
}
