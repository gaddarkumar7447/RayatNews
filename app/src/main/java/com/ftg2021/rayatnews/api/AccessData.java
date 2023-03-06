package com.ftg2021.rayatnews.api;

public class AccessData {
//   private final static String baseUrl="http://139.59.28.80";
//    private final static String apiUrl="http://139.59.28.80:5000/api/news/";

    // Currently running url
    /*private final static String baseUrl = "https://rnews.5techg.com";
    private final static String apiUrl = "https://rnews.5techg.com/api/news/";*/

    private final static String baseUrl = "https://rnewsnew.5techg.com/";
    private final static String apiUrl = "https://rnewsnew.5techg.com/api/news";

//    private final static String baseUrl="https://rnews.rayatnews.com";
//    private final static String apiUrl="https://rnews.rayatnews.com/api/news/";

    private final static String port = "5000";
    public static int badgeCount = 0;

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static String getPort() {
        return port;
    }

    public static String getApiUrl() {
        return apiUrl;
    }

}
