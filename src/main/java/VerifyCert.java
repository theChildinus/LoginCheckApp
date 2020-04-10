import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;
import java.io.FileInputStream;

public class VerifyCert {
    public static String VerifyCert(String username, String certPath) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 证书验证过程 ======");
        FileInputStream fis = new FileInputStream(certPath);
        String cert = Common.getFileContent(fis, "UTF-8");
        //Inserting key-value pairs into the json object
        jsonWrite.put("name", username);
        jsonWrite.put("certcontent", Base64.encode(cert.getBytes()));
        jsonWrite.put("type", "user");
        System.out.println("CHECK: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "user/verifyCert", jsonWrite.toJSONString(), "");
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "user/verifyCert", jsonWrite.toJSONString(), "");
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        String username = "zhao";
        VerifyCert(username, "./pairs/" + username + ".crt");
    }
}
