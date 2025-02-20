package com.iot.untils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiClient {

    /**
     * 获取header中token参数
     * @param url
     * @param params
     * @param headParams
     * @return
     */
    public static @NotNull Map<String,String> getToken(String url, String params, Map<String,String> headParams){
        JSONObject token = doPost(url, params, headParams);
        Map<String, String> header = new HashMap<>();
        header.put("x-access-token",token.getString("access_token"));
        return header;
    }


    /**
     * get请求
     * @param url
     * @param params
     * @param headParams
     * @return
     */
    public static JSONObject doHttpGet(String url, Map<String,String> params,Map<String,String> headParams) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String paramStr = null;
        JSONObject responseJson = null;
        try {
            List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();

            for (String key : params.keySet()) {
                paramsList.add(new BasicNameValuePair(key,params.get(key)));
            }
            paramStr = EntityUtils.toString(new UrlEncodedFormEntity(paramsList));
            //拼接参数
            StringBuffer sb = new StringBuffer();
            sb.append(url)
                    .append("?")
                    .append(paramStr);

            HttpGet httpGet = new HttpGet(sb.toString());
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000)
                    .setConnectTimeout(2000)
                    .build();
            httpGet.setConfig(requestConfig);
//            httpGet.addHeader("content-type","text/xml");
            for (String head : headParams.keySet()) {
                httpGet.addHeader(head,headParams.get(head));
            }
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity);
                responseJson = JSONObject.parseObject(result);
            }
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseJson;
    }

    /**
     * post请求-params传参
     * @param url
     * @param params 表单传参
     * @param headParams 请求头
     * @return
     */
    public static JSONObject doPost(String url, String params,Map<String,String> headParams) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject jsonObject=null;
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
            httpPost.setConfig(requestConfig);

            JSONObject parseObject = JSONObject.parseObject(params);
            List<BasicNameValuePair> paramsList = new ArrayList<>();
            for (String key : parseObject.keySet()) {
                paramsList.add(new BasicNameValuePair(key,parseObject.getString(key)));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramsList, Charset.defaultCharset().toString());
            httpPost.setEntity(urlEncodedFormEntity);
            for (String head : headParams.keySet()) {
                httpPost.addHeader(head,headParams.get(head));
            }

            response = httpClient.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject;
    }


    /**
     * post请求-body传参 json
     * @param url
     * @param params
     * @param headParams 传入一个map转换的字符串
     * @return
     */
    public static JSONObject doPostJson(String url, String params, Map<String,String> headParams) {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        JSONObject responseJson=null;
        try {
            HttpPost httpPost = new HttpPost(url);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
            httpPost.setConfig(requestConfig);

            httpPost.setEntity(new StringEntity(params, ContentType.APPLICATION_JSON));

            for (String head : headParams.keySet()) {
                httpPost.addHeader(head,headParams.get(head));
            }

            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK != statusCode) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode+">>"+ response.getEntity().toString());
            }
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                result = EntityUtils.toString(entity, "UTF-8");
                responseJson = JSONObject.parseObject(result);
            }
            EntityUtils.consume(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseJson;
    }

}
