package com.hbc.sms.common;

import okhttp3.OkHttpClient;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class APIClient extends OkHttpClient {

    private String accessToken  = null;
    private String baseUrl      = null;
    private String appKey       = null;
    private String appId        = null;
    private String userId       = null;
    private String userKey      = null;
    private Date today          = new Date();


    public APIClient(String accessToken, String baseUrl) {
        super();
        this.accessToken = accessToken;
        this.baseUrl     = baseUrl;
    }


    public APIClient() {
    }

    public APIClient(String baseUrl, String appKey, String appId, String userId, String userKey) {
        this.baseUrl = baseUrl;
        this.appKey = appKey;
        this.appId = appId;
        this.userId = userId;
        this.userKey = userKey;
    }

    public String getUrl(String resource) {
        return baseUrl + resource + "?access_token=" + accessToken;
    }

    public String getUrlAnd(String resource) {
        return baseUrl + resource + "&access_token=" + accessToken;
    }

    public String getUrlWithParams(String resource, Map<String,String> params) {
        return baseUrl + resource + "?access_token=" + accessToken + params.entrySet()
                .stream()
                .map(entry -> "&" + entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(""));
    }

    public String getMoodleUrl(String resource) {
        return baseUrl + "?wstoken=" + accessToken + "&moodlewsrestformat=json&wsfunction=" + resource;
    }

    public String getD2LURL (URI d2lUrl) {
        return d2lUrl.toString();
    }

    public String addD2LURLAndParams(URI d2lUrl, Map<String, String> params) {
        return d2lUrl.toString() + params.entrySet()
                .stream()
                .map(entry -> "&" + entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining(""));
    }

    public String getHarvestUrl(String resource) {
        return baseUrl + resource;
    }

    public String getAzureUrl(String resource, String azureAccountId) {
        return baseUrl + "/enrollments/" + azureAccountId + "/" +resource;
    }

    public String getPhoenixUrl(String resource) {
        return baseUrl + resource;
    }

    public String getMetabaseUrl(String resource) {
        return baseUrl + resource;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }
}

