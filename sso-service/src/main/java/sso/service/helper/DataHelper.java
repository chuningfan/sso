package sso.service.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class DataHelper {
    
    private String properiesName = "data.properties";
    
    private static volatile Properties p;
    
    private static final String BASIC_PATH = DataHelper.class.getClassLoader().getResource("").getPath();
    
    public void reload() {
    	Properties temp = getProperties();
    	p = temp;
    }
    
    public String readProperty(String key) {
        if (p != null) {
        	return p.getProperty(key);
        } else {
        	reload();
        	if (p != null) {
        		return p.getProperty(key);
        	} else {
        		throw new RuntimeException("No config data found!");
        	}
        }
    }

    public Properties getProperties() {
        Properties p = new Properties();
        InputStream is = null;
        try {
            is = DataHelper.class.getClassLoader().getResourceAsStream(properiesName);
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public boolean writeProperty(String key, String value) {
        InputStream is = null;
        OutputStream os = null;
        Properties p = new Properties();
        try {
            is = new FileInputStream(BASIC_PATH + properiesName);
            p.load(is);
            os = new FileOutputStream(BASIC_PATH + properiesName);
            p.setProperty(key, value);
            p.store(os, key);
            os.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != is)
                    is.close();
                if (null != os)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            reload();
        }

    }

}
