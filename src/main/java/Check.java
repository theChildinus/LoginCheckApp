import org.json.simple.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Check {
    // 每次登录后randNum值均不一样
    public static String Check(String username, String randNum, String sub, String obj, String act) throws Exception{
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 策略验证过程 ======");
        String str = username + randNum;
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 计算md5函数
        md.update(str.getBytes());
        String md5sum = new BigInteger(1, md.digest()).toString(16);
        System.out.println(md5sum);
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", username);
        jsonWrite.put("userhash", md5sum);
        jsonWrite.put("policysub", sub);
        jsonWrite.put("policyobj", obj);
        jsonWrite.put("policyact", act);
        System.out.println("CHECK: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "policy/check", jsonWrite.toJSONString());
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "policy/check", jsonWrite.toJSONString());
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        String randNum = "2338498362660772719";
        Check("zhao", randNum,"zhao", "data4", "exec");
    }
}
