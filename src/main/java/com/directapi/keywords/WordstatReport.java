package com.directapi.keywords;

import com.directapi.info.OAuthInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tools.httpbuilder.HttpBuilder;

import java.io.IOException;

public class WordstatReport {
    private String token = OAuthInfo.TOKEN;
    private String method;
    private Object param = null;


    public WordstatReport(String method){
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){return token;}

    public void setParam(Object param){ this.param = param; }

    public static WordstatReport create(WordstatReport ws) throws IOException {
        //Создание строителя json
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.excludeFieldsWithModifiers().setPrettyPrinting().create();
        //
        String request = gson.toJson(ws);
        String response = HttpBuilder.makePostJsonRequest(request, OAuthInfo.sandboxURL_v4);
        return gson.fromJson(response, ws.getClass());
    }

}