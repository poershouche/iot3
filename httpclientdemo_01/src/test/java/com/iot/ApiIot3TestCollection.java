package com.iot;

import com.alibaba.fastjson.JSONObject;
import com.iot.services.Iot3Api;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试iot3接口参数
 */
public class ApiIot3TestCollection {

    @Test
    public void test(){

        Iot3Api.testMutipDevices();
//        Iot3Api.testAlterClear();
//        Iot3Api.testShadowQuery();
//        Iot3Api.testDevCount();

    }

     @Test
    public void test02() throws IOException {
        //org.apache.http.impl.client
         HttpClientBuilder httpClient = HttpClients.custom();
         HttpGet httpGet = new HttpGet();
         RequestConfig build = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
         httpGet.setConfig(build);

         Header header = new BasicHeader("ContentType",ContentType.APPLICATION_JSON.toString());
         httpGet.addHeader(header);

         BasicNameValuePair basicNameValuePair = new BasicNameValuePair("name","wangwu");
         BasicNameValuePair basicNameValuePair2 = new BasicNameValuePair("age","23");

         List<BasicNameValuePair> list= new ArrayList<>();
         list.add(basicNameValuePair);
         list.add(basicNameValuePair2);
         StringEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list);

         String stringEntity =  EntityUtils.toString(urlEncodedFormEntity);

         StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append("?").append(stringEntity);

         CloseableHttpClient build1 = (CloseableHttpClient) httpClient.build().execute(httpGet);

     }

    @Test
    public void test03() throws IOException {
        FileReader fileReader = new FileReader(String.valueOf(Paths.get("src/main/resources/config.json")));
//        FileReader fileReader = new FileReader("src/main/resources/config.json");
        BufferedReader fr= new BufferedReader(fileReader);
        String line=null;
        StringBuffer stringBuffer=new StringBuffer();
        try {
            while ((line= fr.readLine())!=null){
                System.out.println(line);
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSONObject.parseObject(stringBuffer.toString());
        System.out.println(jsonObject.get("status"));
       /* FileOutputStream outputStream = new FileOutputStream("src/main/resources/config1.json");
        outputStream.write(jsonObject.toString().getBytes("utf-8"));
        outputStream.close();
        BufferedWriter bufferedReader = new BufferedWriter(new FileWriter("src/main/resources/config1.json"));
        bufferedReader.write("xxx");
        bufferedReader.newLine();
        bufferedReader.write("yyy");
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bufferedReader.close();*/


    }


}
