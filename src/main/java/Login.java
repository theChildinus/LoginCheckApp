import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.security.PrivateKey;

public class Login {

    public static String Login(String username, String privateKeyPath) throws Exception {
        String url = Common.proUrlPrefix;
        String resp1;
        String resp2;
        System.out.println("====== 登录过程 ======");
        JSONObject jsonWrite = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", username);
        System.out.println("STEP 1: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp1 = HttpsPost.doPost(Common.proUrlPrefix + "user/login", jsonWrite.toJSONString());
        } else {
            resp1 = HttpPost.doPost(Common.devUrlPrefix + "user/login", jsonWrite.toJSONString());
        }
        System.out.println("RESP 1: " + resp1);

        JSONObject jsonRead = (JSONObject) new JSONParser().parse(resp1);
        Long code = (Long)jsonRead.get("code");
        System.out.println(code);

        PrivateKey privateKey = SignWithEC.getPrivateKey(privateKeyPath, Common.opensslPath);
        byte[] s = SignWithEC.sign(privateKey, String.valueOf(code));
        System.out.println("签名："+ Base64.encode(s));

        jsonWrite.put("userrand", code);
        jsonWrite.put("usersign", Base64.encode(s));
        System.out.println("STEP 2: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp2 = HttpsPost.doPost(Common.proUrlPrefix + "user/login", jsonWrite.toJSONString());
        } else {
            resp2 = HttpPost.doPost(Common.devUrlPrefix + "user/login", jsonWrite.toJSONString());
        }
        System.out.println("RESP 2: " + resp2);
        return resp2;
    }

    public static void main(String[] args) throws Exception{
        Login("zhao", "./pairs/zhao.pem");
    }
}
