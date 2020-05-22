import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import utils.Common;
import utils.HttpPost;
import utils.HttpsPost;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Check {
    // 每次身份认证后 md5sum 值均不一样
    public static String Check(String policyname, String sub, String obj, String act, String env,
                               String username, String md5sum) throws Exception {
        Common.init();
        String url = Common.proUrlPrefix;
        String resp;
        JSONObject jsonWrite = new JSONObject();
        System.out.println("====== 访问控制执行过程 ======");
        //Inserting key-value pairs into the json object
        jsonWrite.put("policyname", policyname);
        jsonWrite.put("policysub", sub);
        jsonWrite.put("policyobj", obj);
        jsonWrite.put("policyact", act);
        jsonWrite.put("policyenv", env);
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

    public static String CheckABAC(String policyname, String sub, String role, String obj, String act, String env,
                                   String username, String md5sum) throws Exception {
        SubAttr sa = new SubAttr();
        sa.setName(sub);
        sa.setRole(role);

        ObjAttr oa = new ObjAttr();
        oa.setName(obj);
        oa.setOwner(username);

        ActAttr aa = new ActAttr();
        aa.setName(act);

        EnvAttr ea = new EnvAttr();
        ea.setTime(env);

        String sastr = JSON.toJSONString(sa);
        String oastr = JSON.toJSONString(oa);
        String aastr = JSON.toJSONString(aa);
        String eastr = JSON.toJSONString(ea);

        String resp = Check(policyname, sastr, oastr, aastr, eastr, username, md5sum);
        return resp;
    }

    public static class SubAttr {
        private String name;
        private String role;

        public void setName(String name) {
            this.name = name;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getName() {
            return name;
        }

        public String getRole() {
            return role;
        }
    }
    public static class ObjAttr {
        private String name;
        private String owner;

        public void setName(String name) {
            this.name = name;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getName() {
            return name;
        }

        public String getOwner() {
            return owner;
        }
    }

    public static class ActAttr {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class EnvAttr {
        private String time;

        public void setTime(String time) {
            this.time = time;
        }

        public String getTime() {
            return time;
        }
    }

    public static void main(String[] args) throws Exception{
        String md5sum = "ce76602bd9e77f3c85130454a7e95e52";
        String username = "kong";
        Check("策略1","zhao", "物联网资源/态势图","read","",username, md5sum);
        CheckABAC("运控分系统访问控制策略集合", "zhao", "admin",
                "data1", "read","2020-05-22", username, md5sum);
    }
}
