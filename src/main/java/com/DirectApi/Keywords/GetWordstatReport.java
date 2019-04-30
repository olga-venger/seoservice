package com.DirectApi.Keywords;

import com.DirectApi.Info.OAuthInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import tools.httpbuilder.HttpBuilder;

import java.io.IOException;
import java.util.*;

import static tools.httpbuilder.HttpBuilder.makePostJsonRequest;

public class GetWordstatReport extends WordstatReport{

    @SerializedName("data") transient
    public ArrayList<WordstatReportInfo> data;

    public GetWordstatReport(){
        super("GetWordstatReport");
    }

    public GetWordstatReport(Integer param){
        super("GetWordstatReport");
        setParam(param);
    }

    public static GetWordstatReport getWordstatReport(ArrayList<String> phrases, ArrayList<Integer> geoID) throws IOException {
        String request;
        String response;

        //Создание строителя json
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.excludeFieldsWithModifiers().setPrettyPrinting().create();

        //Создание тела запроса
        CreateNewWordstatReport report1 = new CreateNewWordstatReport(phrases, geoID);

        //Запись в строку и в файл  тела запроса

        request = createNewReportRequest(report1, gson);

        //Выполнение запроса
        response = HttpBuilder.makePostJsonRequest(request, OAuthInfo.sandboxURL_v4);
        //Запись ответа
        //toJson("RESPONSE_" + report1.getMethod(), response);
        //Получение данных из ответа
        report1 = gson.fromJson(response, CreateNewWordstatReport.class);

        System.out.println(report1.ID);

        //Integer reportID = 1297095;
        Integer reportID = report1.ID;

        //Сюда встроить проверку готовности отчёта
        //Создание тела запроса
        GetWordstatReportList reportList = new GetWordstatReportList();
        //Запись в строку и в файл тела запроса
        request = createNewReportRequest(reportList, gson);
        //toJson("REQUEST_" + reportList.getMethod(), request);

        //Запись исполнение запроса и получение идентификатора готовности отчёта
        System.out.println("Ожидание формирования отчёта...");

        String statusReport;
        String currentStatusReport;

        do {
            try {
                System.out.println("Ждите 5 секунд...");
                Thread.sleep(5000);
                statusReport = makePostJsonRequest(request, OAuthInfo.sandboxURL_v4);
                reportList = gson.fromJson(statusReport, GetWordstatReportList.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentStatusReport = reportList.getReportStatusInfo(reportID);
            System.out.println(currentStatusReport);
        }
        while (!currentStatusReport.equals("Done"));
        System.out.println("Отчёт сформирован");
        //Создание тела запроса
        GetWordstatReport report2 = new GetWordstatReport(reportID);

        //Запись в строку и в файл тела запроса
        request = createNewReportRequest(report2, gson);
        //toJson("REQUEST_" + report2.getMethod(), request);

        //Запись исполнение запроса и получение списка фраз и показов
        response = makePostJsonRequest(request, OAuthInfo.sandboxURL_v4);

        //Запись ответа
        //toJson("RESPONSE_" + report2.getMethod(), response);

        //Получение данных из ответа
        report2 = gson.fromJson(response, GetWordstatReport.class);

        return report2;
    }

    private static String createNewReportRequest(WordstatReport report, Gson gson) {
        //Запись в строку и в файл  тела запроса
        String request = gson.toJson(report);
        return request;
    }

    public ArrayList<WordstatReportInfo> getData() {
        return data;
    }

    /**
     * Shows statistics for all phrases in report (not sorted)
     * Показывает статистику по всем фразам в отчёте (не сортировано)
     * @return List<WordstatItem>
     */
    public List<WordstatItem> getAllSearchedWith(){
        List<WordstatItem> list = new ArrayList<>();
        for(int i = 0; i< this.getData().size(); i++){
            list.addAll(this.getData().get(i).getSearchedWith());
        }
        return list;
    }
    public List<WordstatItem> getAllSearchedWithSorted(boolean ascend){
        List<WordstatItem> list = getAllSearchedWith();
        Comparator<WordstatItem> descending = (WordstatItem o1, WordstatItem o2)-> o2.getShows() - o1.getShows();
        Comparator<WordstatItem> ascending = (WordstatItem o1, WordstatItem o2)-> o1.getShows() - o2.getShows();
        if(ascend){
            list.sort(ascending);
        }
        else {
            list.sort(descending);
        }
        return list;
    }
}
