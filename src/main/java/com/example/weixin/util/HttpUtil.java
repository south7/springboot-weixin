package com.example.weixin.util;

import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class HttpUtil {
    /**
     * 发送get请求
     * @return
     */
    public static String get(String url){
     String result="";
     InputStream inputStream=null;
        try {
            HttpURLConnection httpURLConnection=(HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setRequestProperty("accept","*/*");
            httpURLConnection.setRequestProperty("connection","Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.setRequestProperty("Accept","application/json");
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            inputStream=httpURLConnection.getInputStream();
            result = StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 发送post请求
     * @param url
     * @param paramStr
     * @return
     */
    public static String post(String url,String paramStr){
        String result="";
        InputStream inputStream=null;
        OutputStream outputStream=null;
        HttpURLConnection httpURLConnection= null;
        try {
            httpURLConnection = (HttpURLConnection)new URL(url).openConnection();
            httpURLConnection.setRequestProperty("accept","*/*");
            httpURLConnection.setRequestProperty("connection","Keep-Alive");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            outputStream=httpURLConnection.getOutputStream();
            if(StringUtils.hasText(paramStr)){
                outputStream.write(paramStr.getBytes("UTF-8"));
                outputStream.close();
            }
            inputStream=httpURLConnection.getInputStream();
            result=StreamUtils.copyToString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
