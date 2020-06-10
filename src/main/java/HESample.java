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
        System.out.println("Req: " + jsonWrite.toJSONString());
        resp = utils.HttpPost.doPost(heServer + "/newpks", jsonWrite.toJSONString(), "");
        System.out.println("RESP: " + resp);
        return resp;
}

    public static String NewSubscribeKeyStore(String username) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 生成订阅者公私钥过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("username", username);
        //jsonWrite.put("publish_sk_file", publishSkFile);
        System.out.println("Req: " + jsonWrite.toJSONString());
        resp = utils.HttpPost.doPost(heServer + "/newsks", jsonWrite.toJSONString(), "");
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String Encrypt(String publishKeyFile, String plaintext) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 加密过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("publish_key_file", publishKeyFile);
        jsonWrite.put("plaintext", plaintext);
        System.out.println("Encrypt: " + jsonWrite.toJSONString());
        resp = utils.HttpPost.doPost(heServer + "/encrypt", jsonWrite.toJSONString(), "");
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String ReEncrypt(String publishKeyFile, String subscribeKeyFile, String ciphertext) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 重加密过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("publish_key_file", publishKeyFile);
        jsonWrite.put("subscribe_key_file", subscribeKeyFile);
        jsonWrite.put("ciphertext", ciphertext);
        System.out.println("ReEncrypt: " + jsonWrite.toJSONString());
        resp = utils.HttpPost.doPost(heServer + "/reencrypt", jsonWrite.toJSONString(), "");
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static String Decrypt(String subscribeKeyFile, String ciphertext) throws Exception {
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 解密过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("subscribe_key_file", subscribeKeyFile);
        jsonWrite.put("ciphertext", ciphertext);
        System.out.println("Decrypt: " + jsonWrite.toJSONString());
        resp = utils.HttpPost.doPost(heServer + "/decrypt", jsonWrite.toJSONString(), "");
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception{
        String pubname = "pub1";
        String subname = "sub1";
        String plaintext = "helloworld";

        NewPublishKeyStore(pubname);
        NewSubscribeKeyStore(subname);

        String ctJson = Encrypt(pubname + ".pk", plaintext);
        JSONObject jsonReader = JSONObject.parseObject(ctJson);
        String ciphertext = (String)jsonReader.get("ciphertext");
        System.out.println(ciphertext);

        ctJson = ReEncrypt(pubname + ".sk", subname + ".sk", ciphertext);
        jsonReader = JSONObject.parseObject(ctJson);
        String newciphertext = (String)jsonReader.get("ciphertext");

        String ptJson = Decrypt(subname + ".sk", newciphertext);
        jsonReader = JSONObject.parseObject(ptJson);
        System.out.println((String)jsonReader.get("plaintext"));
    }
}
