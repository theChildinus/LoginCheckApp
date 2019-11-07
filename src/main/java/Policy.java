import org.json.simple.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Policy {

    public static String getPolicies(String username) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 策略获取过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("search_sub", username);
        System.out.println("POLICIES: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "policy/list", jsonWrite.toJSONString());
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "policy/list", jsonWrite.toJSONString());
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception {
        getPolicies("zhao");
    }
}
