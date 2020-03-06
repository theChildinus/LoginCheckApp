import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;

import java.security.PrivateKey;

public class Logout {
    public static String Logout(String username, String token) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        //Inserting key-value pairs into the json object
        jsonWrite.put("name", username);
        System.out.println("Logout: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "user/logout", jsonWrite.toJSONString(), token);
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "user/logout", jsonWrite.toJSONString(), token);
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("====== JWT登出过程 ======");
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1ODM0OTA2NzcsInN1YiI6InpoYW8ifQ.sDni4fNqAL8u0F_2jn7gGwpx09zgs3EcYGa6WTNq0wY";
        Logout("zhao", token);
    }
}
