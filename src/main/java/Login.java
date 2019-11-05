import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.security.PrivateKey;

public class Login {
    // 开发环境url
    private static final String devUrlPrefix = "http://localhost:9090/";
    // 正式环境url
    private static final String proUrlPrefix = "https://localhost/api/";
    private static final String opensslPath = "C:\\Program Files\\Git\\usr\\bin\\openssl";
    private static final String privateKeyPath = "./pairs/zhao.pem";

    public static void main(String[] args) throws Exception{
        String url = proUrlPrefix;
        String resp1;
        String resp2;
        JSONObject jsonWrite = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", "zhao");
        System.out.println("STEP 1: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp1 = HttpsPost.doPost(proUrlPrefix + "user/login", jsonWrite.toJSONString());
        } else {
            resp1 = HttpPost.doPost(devUrlPrefix + "user/login", jsonWrite.toJSONString());
        }
        System.out.println(resp1);

        JSONObject jsonRead = (JSONObject) new JSONParser().parse(resp1);
        Long code = (Long)jsonRead.get("code");
        System.out.println(code);
        PrivateKey privateKey = SignWithEC.getPrivateKey(privateKeyPath, opensslPath);
        byte[] s = SignWithEC.sign(privateKey, String.valueOf(code));
        System.out.println("签名："+ HexBin.encode(s));

        jsonWrite.put("userrand", code);
        jsonWrite.put("usersign", Base64.encode(s));
        System.out.println("STEP 2: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp2 = HttpsPost.doPost(proUrlPrefix + "user/login", jsonWrite.toJSONString());
        } else {
            resp2 = HttpPost.doPost(devUrlPrefix + "user/login", jsonWrite.toJSONString());
        }
        System.out.println(resp2);
    }
}
