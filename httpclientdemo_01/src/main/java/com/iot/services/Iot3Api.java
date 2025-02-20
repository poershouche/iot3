package com.iot.services;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iot.untils.ApiClient;
import com.iot.untils.Config;

import java.util.*;
import java.util.logging.Logger;

public class Iot3Api {

    private static final Map<String, String> token;
    private static final Logger logger = Logger.getLogger(Iot3Api.class.getName());
    private static final String host_url;

    static {

        host_url = Config.getBaseUrl();
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/x-www-form-urlencoded");
        headersMap.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("grant_type", Config.getGrantType());
        paramsMap.put("client_id", Config.getClientId()); //大屏应用接入账号
        paramsMap.put("client_secret", Config.getClientSecret());

        JSONObject jsonObject = mapToJson(paramsMap);

        String uri = "/oauth2/token";
        String url = host_url + uri;


        Map<String, String> get_token = ApiClient.getToken(url, jsonObject.toString(), headersMap);
        token = get_token;


//        System.out.println(token.get("x-access-token"));
    }

    /**
     * 4.2 查设备的实时数据
     */
    public static void testMutipDevices() {
        logger.info("4.2 查设备的实时数据" + "  URI:>>>" + "/dashboard/_multi");
        JSONArray params = new JSONArray();
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("dashboard", "device");
        jsonBody.put("measurement", "properties");
        jsonBody.put("dimension", "history");
        jsonBody.put("object", "0713007");
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("deviceId", "012010102200010714002QNM3");
        jsonObject1.put("history", 1);

        jsonBody.put("params", jsonObject1);
        params.add(jsonBody);
        String s = params.toString();
        String url = host_url + "/dashboard/_multi";
        Map<String, String> headers = new HashMap<>(); //请求头
        headers.put("Content-Type", "application/json");
        headers.put("x-access-token", token.get("x-access-token"));
        JSONObject jsonObject = ApiClient.doPostJson(url, s, headers);
        logger.info(jsonObject.getString("status"));
        System.out.println(jsonObject);
    }

    /**
     * 4.25 手动解除告警
     */
    public static void testAlterClear() {
        logger.info("4.25 手动解除告警" + "  URI:>>>" + "/api/v1/manualAlertClear");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("deviceId", "012020102000010103001REP3");
        jsonBody.put("alarmConfigId", "1864860078520524800");
        jsonBody.put("describe", "zdr execute");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-access-token", token.get("x-access-token"));

        String url = host_url + "/api/v1/manualAlertClear";

        JSONObject jsonObject = ApiClient.doPostJson(url, jsonBody.toString(), headers);
        logger.info(jsonObject.getString("status"));
        logger.info(jsonObject.toString());


    }

    /**
     * 4.27 设备影子数据查询
     */
    public static void testShadowQuery() {
        logger.info("4.27 设备影子数据查询" + "  URI:>>>" + "/api/v1/shadow/query");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageIndex", 0);
        jsonBody.put("pageSize", 10);
        jsonBody.put("orgId", "012");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-access-token", token.get("x-access-token"));

        String url = host_url + "/api/v1/shadow/query";

        JSONObject jsonObject = ApiClient.doPostJson(url, jsonBody.toString(), headers);
        logger.info(jsonObject.getString("status"));
        System.out.println(jsonObject.toJSONString());

    }

    /**
     * 5.11 获取项目下设备统计信息
     */
    public static void testDevCount() {
        logger.info("5.11 获取项目下设备统计信息" + "  URI:>>>" + "/ResourceServer/api/v1/getdevicecount");
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("ret_type", "devlist");
        int k = new Random().nextInt(1000);
        String valueOf = String.valueOf(k);
        if (valueOf.length() == 1) {
            valueOf = "00" + valueOf;
        }else if(valueOf.length()==2){
            valueOf = "0"+valueOf;
        }
        jsonBody.put("project_no", valueOf);


        jsonBody.put("special_class", "0");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("x-access-token", token.get("x-access-token"));

        String url = host_url + "/ResourceServer/api/v1/getdevicecount";

        JSONObject jsonObject = ApiClient.doPostJson(url, jsonBody.toString(), headers);
        logger.info(jsonObject.getString("result"));
        if (JSONArray.parseArray(jsonObject.getString("devlist")).size() < 1) {
            logger.info("项目id:>>>" + valueOf);
        } else {
            System.out.println(jsonObject.toJSONString());
        }


    }


    /**
     *
     * @param mapK keyset
     * @return JSONObject
     */
    public static JSONObject mapToJson(Map<String, String> mapK) {
        JSONObject jsonObject = new JSONObject();
        for (String key : mapK.keySet()) {
            jsonObject.put(key, mapK.get(key));
        }
        return jsonObject;
    }
}
