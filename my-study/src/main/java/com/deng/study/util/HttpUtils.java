package com.deng.study.util;

import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;

public class HttpUtils {

    // 连接池
    private PoolingHttpClientConnectionManager cm;

    public HttpUtils() {
        this.cm = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        this.cm.setMaxTotal(100);
        // 设置每个主机的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /*
     * 根据请求地址下载页面数据
     */
    public String doGetHtml(String url) {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        // 创建httpclient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        // 设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        // 使用httpClient发起请求，获取响应
        try {
            response = httpClient.execute(httpGet);
            // 解析响应，返回结果
            if(response.getStatusLine().getStatusCode() == 200){
                if(Objects.nonNull(response.getEntity())){
                    return EntityUtils.toString(response.getEntity(),"utf8");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(Objects.nonNull(response)){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 下载图片
     * @param url
     * @return
     */
    public String doGetImage(String url) {
        // 获取HttpClient对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();

        // 创建httpclient请求对象，设置url地址
        HttpGet httpGet = new HttpGet(url);
        // 设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        // 使用httpClient发起请求，获取响应
        try {
            response = httpClient.execute(httpGet);
            // 解析响应，返回结果
            if(response.getStatusLine().getStatusCode() == 200){
                if(Objects.nonNull(response.getEntity())){
                    // 下载图片
                    String extName = url.substring(url.lastIndexOf("."));

                    // 获取图片的路径，重命名图片
                    String picName = DateUtil.parseDateFormat(new Date(),DateUtil.YMD_HMS)+extName;

                    // 下载图片
                    OutputStream os = new FileOutputStream(new File("D:\\mine\\image\\"+picName));

                    response.getEntity().writeTo(os);
                    // 返回图片名称
                    return picName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(Objects.nonNull(response)){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 设置请求信息
     * @return
     */
    private RequestConfig getConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(1000)            // 创建连接的最大时间
                .setConnectionRequestTimeout(500)   // 获取连接的最大时间
                .setSocketTimeout(10000)            // 数据传输的最大时间
                .build();
    }

}
