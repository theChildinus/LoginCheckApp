import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.json.simple.JSONObject;
import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;
import java.io.*;

public class UploadFile {

    public static String uploadFile(String epcstr) throws Exception {
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 上传EPC文件 ======");
        jsonWrite.put("epc_ctx", Base64.encode(epcstr.getBytes()));
        System.out.println("UploadReq: " + jsonWrite.toJSONString());
        if (url.contains("https")) {
            resp = HttpsPost.doPost(Common.proUrlPrefix + "policy/executable", jsonWrite.toJSONString(), "");
        } else {
            resp = HttpPost.doPost(Common.devUrlPrefix + "policy/executable", jsonWrite.toJSONString(), "");
        }
        System.out.println("RESP: " + resp);
        return resp;
    }

    public static void main(String[] args) throws Exception {
        // 上传文件测试
        FileInputStream fis = new FileInputStream("./pairs/tmp.epml");
        String epc_context = Common.getFileContent(fis, "UTF-8");
        uploadFile(epc_context);
    }

}
