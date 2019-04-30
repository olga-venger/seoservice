package com.DirectApi.Keywords;

import com.DirectApi.Info.OAuthInfo;

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
}


