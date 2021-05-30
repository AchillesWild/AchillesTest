package com.achilles.wild.server.tool.generate.encrypt;

import org.springframework.util.Base64Utils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * Base64
 */
public class Base64Util {

    /**
     * encodeToString
     *
     * @param src
     * @return
     */
    public static String encodeToString(byte[] src){
        String base64 = Base64Utils.encodeToString(src);
        return base64;
    }

    /**
     * decodeFromString
     *
     * @param str
     * @return
     */
    public static byte[] decodeFromString(String str){
        byte[] base64 = Base64Utils.decodeFromString(str);
        return base64;
    }

    public static String getEncodeMsg(String str) {  
        byte[] b = null;  
        String s = null;  
        try {  
            b = str.getBytes("utf-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        if (b != null) {  
            s = new BASE64Encoder().encode(b);  
        }  
        return s;  
    }  
  

    public static String getDecodeMsg(String s) {  
        byte[] b = null;  
        String result = null;  
        if (s != null) {  
            BASE64Decoder decoder = new BASE64Decoder();  
            try {  
                b = decoder.decodeBuffer(s);
                result = new String(b, "utf-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    } 
}
