package utils;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

public class Common {
    // 开发环境url
    public static String devUrlPrefix = "";
    // 正式环境url
    public static String proUrlPrefix = "";
    public static String opensslPath = "";

    public static void init() {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            //读取属性文件config.properties
            in = new BufferedInputStream(new FileInputStream("resources/config.properties"));
            prop.load(new InputStreamReader(in, "utf-8"));
            Iterator<String> it = prop.stringPropertyNames().iterator();
            while (it.hasNext()) {
                String key = it.next();
                System.out.println(key + ":  " + prop.getProperty(key));
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        devUrlPrefix = prop.getProperty("devUrlPrefix");
        proUrlPrefix = prop.getProperty("proUrlPrefix");
        opensslPath = prop.getProperty("opensslPath");
    }
    public static String getFileContent(FileInputStream fis, String encoding) {
        StringBuilder sb = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding));
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
