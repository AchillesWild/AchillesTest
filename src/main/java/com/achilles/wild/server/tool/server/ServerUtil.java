package com.achilles.wild.server.tool.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerUtil {

    public static void main(String[] args) throws Exception {
        System.out.println(ServerUtil.getV4IP());

        System.out.println(ServerUtil.getMyIPLocal());
    }

    private static String getMyIPLocal() throws IOException {
        InetAddress ia = InetAddress.getLocalHost();
    return ia.getHostAddress();
    }
    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com/";

        String inputLine = "";
        String read = "";
        try {
            URL url = new URL(chinaz);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((read = in.readLine()) != null) {
                inputLine += read;
            }
//            System.out.println(inputLine);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //正则匹配标签，注意\\
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine);
        if(m.find()){
            ip = m.group(1);
        }
        return ip;
    }

}
