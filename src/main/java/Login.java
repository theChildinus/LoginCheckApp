import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;

import java.security.PrivateKey;

public class Login {
    public static String Login(String username, String role, String secret, String token) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        if (token.isEmpty()) {
            JSONObject jsonWrite = new JSONObject();
            //Inserting key-value pairs into the json object
            jsonWrite.put("name", username);
            jsonWrite.put("role", role);
            jsonWrite.put("secret", secret);
            System.out.println("Login 1: " + jsonWrite.toJSONString());
            if (url.contains("https")) {
                resp = HttpsPost.doPost(Common.proUrlPrefix + "user/login", jsonWrite.toJSONString(), "");
            } else {
                resp = HttpPost.doPost(Common.devUrlPrefix + "user/login", jsonWrite.toJSONString(), "");
            }
            System.out.println("RESP 1: " + resp);
        } else {
            JSONObject jsonWrite = new JSONObject();
            jsonWrite.put("name", username);
            jsonWrite.put("role", role);
            System.out.println("Login 2: " + jsonWrite.toJSONString());
            if (url.contains("https")) {
                resp = HttpsPost.doPost(Common.proUrlPrefix + "user/login", jsonWrite.toJSONString(), token);
            } else {
                resp = HttpPost.doPost(Common.devUrlPrefix + "user/login", jsonWrite.toJSONString(), token);
            }
            System.out.println("RESP 2: " + resp);
            return resp;
        }
        return resp;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("====== JWT登录过程 第 1 次 ======");
        String privatePath = "./pairs/zhao.pem";
        PrivateKey privateKey = SignWithEC.getPrivateKey(privatePath, Common.opensslPath);
        String content = "123456";
        byte[] s = SignWithEC.sign(privateKey, content);
        String resp = Login("zhao", "技术部/培训部/经理", Base64.encode(s), "");

        System.out.println("====== JWT登录过程 第 n>1 次 ======");
        JSONObject jsonRead = JSONObject.parseObject(resp);
        String token = (String)jsonRead.get("token");
        Login("zhao", "技术部/培训部/经理", "", token);
    }
}
