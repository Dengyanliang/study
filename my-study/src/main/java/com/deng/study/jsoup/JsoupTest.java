package com.deng.study.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class JsoupTest {

    public static void main(String[] args) throws IOException {
        String url = "https://time.geekbang.org/column/article/71806";
//        Document doc = (Document) Jsoup
//                .connect(url).get();
//        Element div1 = doc.getElementById("SlateRichContent_main_1Bj6H");
//
//        Document document = Jsoup.parse(new URL(url), 50000);
//        Element div2 = doc.getElementById("SlateRichContent_main_1Bj6H");

        Document document = getPageContent(url);
        Element element = document.getElementById("app");


        Elements select = element.select("div.SlateRichContent_main_1Bj6H");



    }
    private static Document getPageContent(String urlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            String html = convertStreamToString(connection.getInputStream());

            return Jsoup.parse(html);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertStreamToString(InputStream is) throws IOException {
        if(Objects.isNull(is)){
            return "";
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while((line = reader.readLine()) != null){
            sb.append(line);
        }
        reader.close();
        return sb.toString();
    }
}
