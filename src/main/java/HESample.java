import com.alibaba.fastjson.JSONObject;

public class HESample {

    public static final String heServer = "http://localhost:55344";

    public static void Init() {

    }

    public static String NewPublishKeyStore(String username) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 生成发布者公私钥过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", username);
        System.out.println("Decrypt: " + jsonWrite.toJSONString());
        resp = HttpPost.doPost(heServer + "/newpks", jsonWrite.toJSONString());
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String NewSubscribeKeyStore(String username, String publishSkFile) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 生成订阅者密钥、交换密钥 过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", username);
        jsonWrite.put("publish_sk_file", publishSkFile);
        System.out.println("Decrypt: " + jsonWrite.toJSONString());
        resp = HttpPost.doPost(heServer + "/newsks", jsonWrite.toJSONString());
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String Encrypt(String publishPkFile, String plaintext) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 加密过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("publish_pk_file", publishPkFile);
        jsonWrite.put("plaintext", plaintext);
        System.out.println("Encrypt: " + jsonWrite.toJSONString());
        resp = HttpPost.doPost(heServer + "/encrypt", jsonWrite.toJSONString());
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String Decrypt(String subscribeSkFile, String subscribeSwkFile, String ciphertext) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 解密过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("subscribe_sk_file", subscribeSkFile);
        jsonWrite.put("subscribe_swk_file", subscribeSwkFile);
        jsonWrite.put("ciphertext", ciphertext);
        System.out.println("Decrypt: " + jsonWrite.toJSONString());
        resp = HttpPost.doPost(heServer + "/decrypt", jsonWrite.toJSONString());
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        String pubname = "pub1";
        String subname = "sub1";
        String plaintext = "HELLO HE";
        NewPublishKeyStore(pubname);
        NewSubscribeKeyStore(subname, pubname+".sk");
        String ctJson = Encrypt(pubname + ".pk", plaintext);
        JSONObject jsonReader = JSONObject.parseObject(ctJson);
        String ciphertext = (String)jsonReader.get("ciphertext");
        System.out.println(ciphertext);
        String ptJson = Decrypt(subname + ".sk", subname + ".swk", ciphertext);
        jsonReader = JSONObject.parseObject(ptJson);
        System.out.println((String)jsonReader.get("plaintext"));
    }
}
