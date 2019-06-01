package com.seoservice;

import com.pullenti.PulentiSDK;
import com.pullenti.morph.MorphLang;
import com.pullenti.ner.ProcessorService;
import com.pullenti.ner.Sdk;
import com.pullenti.unisharp.Stopwatch;
import com.pullenti.unisharp.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootWebApplication {
    private static Logger log = LoggerFactory.getLogger(SpringBootWebApplication.class.getName());

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);
        Stopwatch sw = Utils.startNewStopwatch();
        // инициализация - необходимо проводить один раз до обработки текстов
        log.info("Initializing PullEnti SDK ... ");
        // инициализируются движок и все имеющиеся анализаторы
        Sdk.initialize(MorphLang.ooBitor(MorphLang.RU, MorphLang.EN));
        sw.stop();
        log.info("OK (by " + ((int)sw.getElapsedMilliseconds()) + " ms), version " + ProcessorService.getVersion());
    }
}