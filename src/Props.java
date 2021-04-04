import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Props
{
    private static String sd = System.getenv("APPDATA") + "/RCSys";

    public static String GetValueByKey(String filePath, String key) {
        File f = new File(sd+filePath);
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(f));
        } catch (IOException e) {
        }
        return p.getProperty(key);
    }

    public static String GetValueByKey(String filePath, String key, String None) {
        File f = new File(sd+filePath);
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(f));
        } catch (IOException e) {
        }
        return p.getProperty(key, None);
    }

    public static Map<String, String> GetAllProperties(String filePath) {
        File f=new File(sd+filePath);
        if(!f.exists())
        {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Properties pps = new Properties();
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(sd+filePath));
            pps.load(in);
            Enumeration en = pps.propertyNames(); //得到配置文件的名字

            while (en.hasMoreElements()) {
                String strKey = (String) en.nextElement();
                String strValue = pps.getProperty(strKey);
                map.put(strKey,strValue);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return map;
    }

    public static void WriteProperties(String filePath, String pKey, String pValue) {
        File f=new File(sd+filePath);
        if(!f.exists())
        {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Properties pps = new Properties();
        try {
            InputStream in = new FileInputStream(sd+filePath);
            pps.load(in);
            OutputStream out = new FileOutputStream(sd+filePath);
            pps.setProperty(pKey, pValue);
            pps.store(out, "Update " + pKey + " name");
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
