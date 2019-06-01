package com.seoservice.controllers;

import com.directapi.keywords.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tools.ExelWorker;
import com.seoservice.model.KeywordsForm;
import com.seoservice.model.WSModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

//получение статистики от Я.Вордстат
@Controller
public class WordstatController {
    List<WordstatItem> listWS = new ArrayList<>();
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/wordstat", method = RequestMethod.GET)
    public String showOwnKeywordsToWordstat(Model model) {
        model.addAttribute("keywordsForm", new KeywordsForm());
        return "wordstat";
    }

    @RequestMapping(value = "/getStatisticFromWordstat", method = RequestMethod.GET)
    public String sendOwnKeywordsToWordstat(Model model) {
        model.addAttribute("keywordsForm", new KeywordsForm());
        return "showKeywords";
    }

    @RequestMapping(value = "/getStatisticFromWordstat", method = RequestMethod.POST)
    public String sendKeywordsToWordstat(@ModelAttribute("keywordsForm") KeywordsForm keywordsForm, Model model) throws IOException {

        log.info("Формирование отчёта о статистике поисковых запросов.");
        //если КС как текст, то преобразовать в массив КС
        if (keywordsForm.getKeywords() == null && keywordsForm.getTextString() != null) {
            keywordsForm.setKeywords(keywordsForm.toArrayList());
        }

        listWS.clear();

        CreateNewWordstatReport report = (CreateNewWordstatReport) WordstatReport.create(new CreateNewWordstatReport(keywordsForm.getKeywords(), null));
        String currentStatusReport = "";
        do {
            try {
                log.info("Ожидание 5 секунд...");
//                System.out.println("Ждите 5 секунд...");
                Thread.sleep(5000);

                GetWordstatReportList reportList = (GetWordstatReportList) WordstatReport.create(new GetWordstatReportList());
                currentStatusReport = reportList.getReportStatusInfo(report.ID);
                log.info("Текущий статус отчёта: " + currentStatusReport);
//                System.out.println(currentStatusReport);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        while (!currentStatusReport.equals("Done"));
log.info("Отчёт сформирован!");
//        System.out.println("Отчёт сформирован");

        GetWordstatReport finalReport = (GetWordstatReport)WordstatReport.create(new GetWordstatReport(report.ID));
        listWS = finalReport.getAllSearchedWithSorted(false);

//        listWS.add(new WordstatItem("Яблоко", 100));
//        listWS.add(new WordstatItem("Груша", 132));
//        listWS.add(new WordstatItem("Фунчоза", 123));
//        listWS.add(new WordstatItem("Дыня", 12234323));
//        listWS.add(new WordstatItem("Лимон", 32423223));
//        listWS.add(new WordstatItem("Банан", 345234));

        model.addAttribute("wsModel", new WSModel());
        model.addAttribute("wordstatStatistic", listWS);
        return "showKeywords";
    }

    @RequestMapping(value = "/savefile", method = RequestMethod.POST)
    public String saveFilePOST(@ModelAttribute("wsModel") WSModel wsModel, HttpServletResponse response, Model model) throws IOException {
        // что-то происходит
        System.out.println(wsModel.getCheckedItems());
        //создать новый список с выбранными словами
        List<WordstatItem> list = new ArrayList<>();
        for (Integer index : wsModel.getCheckedItems()
        ) {
            list.add(listWS.get(index));
            System.out.println(listWS.get(index).getPhrase() + " " + listWS.get(index).getShows());
        }
        //Записать в файл
        ExelWorker myStatistic = new ExelWorker();
        myStatistic.addSheet("statistics", list);

        try (OutputStream out = response.getOutputStream()){
            //response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "inline; filename=\"" + "statistic.xls" + "\"");
            myStatistic.getWorkbook().write(out);
            out.flush();
            out.close();
            System.out.println("Excel файл успешно создан!");
        }
        return "download";
    }
}
