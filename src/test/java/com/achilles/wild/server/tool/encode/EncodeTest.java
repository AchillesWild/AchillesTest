package com.achilles.wild.server.tool.encode;

import java.nio.charset.Charset;

public class EncodeTest {

    public static void main(String[] args) throws Exception{

        System.out.println(System.getProperty("file.encoding"));
        System.out.println(Charset.defaultCharset());
        System.out.println("-------------------------------------");

        String str = "��";
        System.out.println(str);
        byte[] bytes =str.getBytes("GBK");
        String str2 =new String(bytes,"GBK");
        byte[] UTF8 = str.getBytes("UTF-8");
        byte[] UTF16 =str.getBytes("UTF-16");
        byte[] GBK =str.getBytes("GBK");
        byte[] GB2312 =str.getBytes("GB2312");
        byte[] ISO88591 =str.getBytes("ISO8859-1");

        System.out.println();
    }
}
