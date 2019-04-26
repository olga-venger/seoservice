/*
 * Copyright (c) 2013, Pullenti. All rights reserved. Non-Commercial Freeware.
 * This class is generated using the converter UniSharping (www.unisharping.ru) from Pullenti C#.NET project (www.pullenti.ru).
 * See www.pullenti.ru/downloadpage.aspx.
 */

package com.pullenti.morph.internal;

public class NextModelHelper {

    public static java.util.ArrayList<NextModelItem> ITEMS;

    private static java.util.HashMap<String, NextModelItem> m_HashBySpel;

    public static void initialize() {
        if (ITEMS != null) 
            return;
        ITEMS = new java.util.ArrayList<NextModelItem>();
        ITEMS.add(new NextModelItem("", com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.UNDEFINED));
        ITEMS.add(new NextModelItem("", com.pullenti.morph.MorphCase.DATIVE, null, NextModelQuestion.UNDEFINED));
        ITEMS.add(new NextModelItem("", com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.UNDEFINED));
        ITEMS.add(new NextModelItem("", com.pullenti.morph.MorphCase.INSTRUMENTAL, null, NextModelQuestion.UNDEFINED));
        ITEMS.add(new NextModelItem("", com.pullenti.morph.MorphCase.PREPOSITIONAL, null, NextModelQuestion.UNDEFINED));
        for (String s : new String[] {"ИЗ", "ОТ", "С", "ИЗНУТРИ"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHEREFROM));
        }
        ITEMS.add(new NextModelItem("В", com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.WHERETO));
        ITEMS.add(new NextModelItem("НА", com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.WHERETO));
        ITEMS.add(new NextModelItem("ПО", com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.WHERETO));
        ITEMS.add(new NextModelItem("К", com.pullenti.morph.MorphCase.DATIVE, null, NextModelQuestion.WHERETO));
        ITEMS.add(new NextModelItem("НАВСТРЕЧУ", com.pullenti.morph.MorphCase.DATIVE, null, NextModelQuestion.WHERETO));
        ITEMS.add(new NextModelItem("ДО", com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHERETO));
        for (String s : new String[] {"У", "ОКОЛО", "ВОКРУГ", "ВОЗЛЕ", "ВБЛИЗИ", "МИМО", "ПОЗАДИ", "ВПЕРЕДИ", "ВГЛУБЬ", "ВДОЛЬ", "ВНЕ", "КРОМЕ", "МЕЖДУ", "НАПРОТИВ", "ПОВЕРХ", "ПОДЛЕ", "ПОПЕРЕК", "ПОСЕРЕДИНЕ", "СВЕРХ", "СРЕДИ", "СНАРУЖИ", "ВНУТРИ"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHERE));
        }
        for (String s : new String[] {"ПАРАЛЛЕЛЬНО"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.DATIVE, null, NextModelQuestion.WHERE));
        }
        for (String s : new String[] {"СКВОЗЬ", "ЧЕРЕЗ", "ПОД"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.WHERE));
        }
        for (String s : new String[] {"МЕЖДУ", "НАД", "ПОД", "ПЕРЕД", "ЗА"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.INSTRUMENTAL, null, NextModelQuestion.WHERE));
        }
        for (String s : new String[] {"В", "НА", "ПРИ"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.PREPOSITIONAL, null, NextModelQuestion.WHERE));
        }
        ITEMS.add(new NextModelItem("ПРЕЖДЕ", com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHEN));
        ITEMS.add(new NextModelItem("ПОСЛЕ", com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHEN));
        ITEMS.add(new NextModelItem("НАКАНУНЕ", com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.WHEN));
        ITEMS.add(new NextModelItem("СПУСТЯ", com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.WHEN));
        for (String s : new String[] {"БЕЗ", "ДЛЯ", "РАДИ", "ИЗЗА", "ВВИДУ", "ВЗАМЕН", "ВМЕСТО", "ПРОТИВ", "СВЫШЕ", "ВСЛЕДСТВИЕ", "ПОМИМО"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.GENITIVE, null, NextModelQuestion.UNDEFINED));
        }
        for (String s : new String[] {"ПО", "ПОДОБНО", "СОГЛАСНО", "СООТВЕТСТВЕННО", "СОРАЗМЕРНО", "ВОПРЕКИ"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.DATIVE, null, NextModelQuestion.UNDEFINED));
        }
        for (String s : new String[] {"ПРО", "О", "ЗА", "ВКЛЮЧАЯ", "С"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.ACCUSATIVE, null, NextModelQuestion.UNDEFINED));
        }
        for (String s : new String[] {"С"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.INSTRUMENTAL, null, NextModelQuestion.UNDEFINED));
        }
        for (String s : new String[] {"О", "ПО"}) {
            ITEMS.add(new NextModelItem(s, com.pullenti.morph.MorphCase.PREPOSITIONAL, null, NextModelQuestion.UNDEFINED));
        }
        java.util.Collections.sort(ITEMS);
        m_HashBySpel = new java.util.HashMap<String, NextModelItem>();
        for (NextModelItem it : ITEMS) {
            m_HashBySpel.put(it.spelling, it);
        }
    }

    public static NextModelItem findBySpel(String spel) {
        NextModelItem res;
        com.pullenti.unisharp.Outargwrapper<NextModelItem> wrapres39 = new com.pullenti.unisharp.Outargwrapper<NextModelItem>();
        boolean inoutres40 = com.pullenti.unisharp.Utils.tryGetValue(m_HashBySpel, spel, wrapres39);
        res = wrapres39.value;
        if (!inoutres40) 
            return null;
        return res;
    }
    public NextModelHelper() {
    }
}
