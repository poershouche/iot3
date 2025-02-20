import com.alibaba.fastjson.JSONObject;
import com.iot.untils.ApiClient;

import java.io.InputStream;
import java.util.*;


import org.yaml.snakeyaml.Yaml;

/**
 * 重置所有子设备
 */
public class test02 {
    public static void main(String[] args) {
        Map<String, Object> yaml = getYamlData();
        ArrayList source = (ArrayList) yaml.get("source");
        for (Object obj : source) {
            System.out.println(obj);
            loopRequest(String.valueOf(obj));
        }

    }

    /**
     * 重置子设备
     * @param devId
     */
    public static  void loopRequest(String devId){
        String url = "https://unilink.crland.com.cn/business/iotInit/destroyIotDevice";
        HashMap<String, String> stringHeader = new HashMap<>();
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3Mzk0MzU0NTcsInVzZXJJZCI6IjU2NWUyN2IxLTlhODAtNDliNC04MjBmLTkxOGE4NTEyMTU1NGMxMzY4ZTQwLTQ4NzktNDA5OS04YTlkLWExODJhZWU1OWVkMSJ9.KfTLN-eCHZJMprfy5jQmo4NAJ4sTJ_3GfWqPfZxHOXk";
        stringHeader.put("Authorization", token);
        stringHeader.put("Content-Type", "application/json");

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("devId",devId);
        jsonObject1.put("type",4); //4代表视频网关子设备

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("data",jsonObject1);
//        System.out.println(jsonObject2.toJSONString());
        String toString = jsonObject2.toString();
        JSONObject jsonObject = ApiClient.doPostJson(url, toString, stringHeader);
        System.out.println(jsonObject);
    }

    public static Map<String, Object> getYamlData() {
        Yaml yaml = new Yaml();
        InputStream inputStream = test02.class.getClassLoader().getResourceAsStream("sourceIot.yml");
        Map<String, Object> dataSource = yaml.load(inputStream);
//        System.out.println(config);
        return dataSource;

    }

}

