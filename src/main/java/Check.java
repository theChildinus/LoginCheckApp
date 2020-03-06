import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;
import org.json.simple.JSONObject;

public class Check {
    // 每次身份认证后 md5sum 值均不一样
    public static String Check(String policyname, String sub, String obj, String act,
                               String username, String md5sum) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 访问控制执行过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("policyname", policyname);
        jsonWrite.put("policysub", sub);
        jsonWrite.put("policyobj", obj);
        jsonWrite.put("policyact", act);
        jsonWrite.put("username", username);
        jsonWrite.put("userhash", md5sum);
        System.out.println("CHECK: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "policy/check", jsonWrite.toJSONString(), "");
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "policy/check", jsonWrite.toJSONString(), "");
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        String md5sum = "9e9e341f7d0597a5490e6e0ddf03edd3";
        Check("策略1","zhao", "物联网资源/态势图","read", "zhao", md5sum);
    }
}
