import com.alibaba.fastjson.JSONObject;
import com.iot.untils.ApiClient;

import java.io.InputStream;
import java.util.*;


import org.yaml.snakeyaml.Yaml;

/**
 * 重置所有子设备
 */
public class test03 {
    public static void main(String[] args) {
        Map<String, Object> yaml = getYamlData();
        ArrayList source = (ArrayList) yaml.get("source");
//        System.out.println(source);
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
        String url = "https://unilink.crland.com.cn/iot-api/device-instance/"+devId+"/deploy";
        HashMap<String, String> stringHeader = new HashMap<>();
        String token = "a3c6a4fa24e05ef602dafdf14aa668d8";
        stringHeader.put("X-Access-Token", token);
        stringHeader.put("Content-Type", "application/json");

        JSONObject jsonObject1 = new JSONObject();
//        jsonObject1.put("devId",devId);
//        jsonObject1.put("type",4); //4代表视频网关子设备
//
//        JSONObject jsonObject2 = new JSONObject();
//        jsonObject2.put("data",jsonObject1);
////        System.out.println(jsonObject2.toJSONString());
//        String toString = jsonObject2.toString();
        JSONObject jsonObject = ApiClient.doPostJson(url, jsonObject1.toString(), stringHeader);
        System.out.println(jsonObject);
    }

    public static Map<String, Object> getYamlData() {
        Yaml yaml = new Yaml();
        InputStream inputStream = test03.class.getClassLoader().getResourceAsStream("sourceIot.yml");
        Map<String, Object> dataSource = yaml.load(inputStream);
//        System.out.println(config);
        return dataSource;

    }

}

