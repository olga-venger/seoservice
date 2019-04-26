package com.pullenti;

import com.pullenti.morph.MorphLang;
import com.pullenti.ner.*;
import com.pullenti.ner.keyword.KeywordAnalyzer;
import com.pullenti.ner.keyword.KeywordReferent;
import com.pullenti.unisharp.Stopwatch;
import com.pullenti.unisharp.Utils;

import java.util.LinkedHashSet;


public class PulentiSDK {

    public static void Initialization() throws Exception {
        Stopwatch sw = Utils.startNewStopwatch();
        // инициализация - необходимо проводить один раз до обработки текстов
        System.out.print("Initializing ... ");
        // инициализируются движок и все имеющиеся анализаторы
        Sdk.initialize(MorphLang.ooBitor(MorphLang.RU, MorphLang.EN));
        sw.stop();
        System.out.println("OK (by " + ((int)sw.getElapsedMilliseconds()) + " ms), version " + ProcessorService.getVersion());
    }

    public static void getEntity(String txt) {
        try (Processor proc = ProcessorService.createProcessor()) {

            // анализируем текст
            AnalysisResult ar = proc.process(new SourceOfAnalysis(txt), null, null);

            // результирующие сущности
            System.out.println("\r\n==========================================\r\nEntities: ");
            for (Referent e : ar.getEntities()) {
                System.out.println(e.getTypeName() + ": " + e.toString());
                for (Slot s : e.getSlots()) {
                    System.out.println("   " + s.getTypeName() + ": " + s.getValue());
                }
            }
        }
    }

    public static LinkedHashSet<String> getKeywords(String txt) {
        LinkedHashSet<String> keywords = new LinkedHashSet();
        try (Processor proc = ProcessorService.createSpecificProcessor(KeywordAnalyzer.ANALYZER_NAME)) {
            AnalysisResult ar = proc.process(new SourceOfAnalysis(txt), null, null);
            for (Token t = ar.firstToken; t != null; t = t.getNext()) {
                if (t instanceof ReferentToken) {
                    KeywordReferent kw = Utils.cast(t.getReferent(), KeywordReferent.class);
                    if (kw == null)
                        continue;
                    keywords.add(kw.toString());
                }
            }
        }
        return keywords;
    }
}
