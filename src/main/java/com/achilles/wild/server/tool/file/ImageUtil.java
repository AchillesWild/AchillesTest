package com.achilles.wild.server.tool.file;

import com.achilles.wild.server.business.controller.demo.ImageController;
import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    private final static Logger log = LoggerFactory.getLogger(ImageController.class);

    static String srcPath = "C:\\Users\\Achilles\\Desktop\\photo\\619.jpg";
//    static String srcPath = "C:\\Users\\Achilles\\Desktop\\test2.jpg";
    static String destPath = "C:\\Users\\Achilles\\Desktop\\test3.jpg";

    static String format = "jpg";

    static String remoteUrl = "https://gamma-attendance1.obs.cn-north-4.myhuaweicloud.com:443/a/a/z.jpg?AccessKeyId=JS2GFAVBZJCMNWZ2GVEL&Expires=1621987461&Signature=kjb2sCGoZPJKtPU7XE6%2BVVKtodE%3D";

    public static void main(String[] args) {

//        trimByWidth(FileUtil.getInputStream(path),"jpg",50,desc);
//        trimByScale(FileUtil.getInputStream(path),"jpg",0.5,desc);

//        int size = trimByQuality(FileUtil.getInputStream(path).

//        trimByQuality(FileUtil.getInputStream(path),"jpg",0.1414213,1f,desc);

//        compressForScale(path,desc,220*1024L,0.8);

//        trimByWidthLimit(srcPath,destPath,500);

//        trimByWidthAndHeight( srcPath, destPath, 120,120);

        downloadPicture( remoteUrl,destPath);

        System.out.println();
    }

    public static void downloadPicture(String remoteUrl,String localPath) {
        URL url = null;
        try {
            url = new URL(remoteUrl);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            FileOutputStream fileOutputStream = new FileOutputStream(localPath);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void trimByWidthAndHeight(String srcPath,String destPath,int width,int height){

        File srcFile = new File(srcPath);

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inputStream = trimByWidthAndHeight(inputStream,width,height,format);
        FileUtil.toFile(inputStream,destPath);
    }

    /**
     * trimByWidthLimit
     *
     * @param inputStream
     * @param width
     * @param format
     * @return
     */
    public static InputStream trimByWidthAndHeight(InputStream inputStream,int width,int height,String format){

        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream can not be null !");
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(inputStream).width(width).height(height).outputQuality(1).outputFormat(format).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream trimInputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return trimInputStream;
    }

    public static void trimBySizeLimit(String srcPath,String destPath,int sizeLimit){

        File srcFile = new File(srcPath);
        int srcFileSize = (int)srcFile.length()/1024;
        if (srcFileSize <= sizeLimit) {
            return;
        }

        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(srcFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        inputStream = trimBySizeLimit(inputStream,sizeLimit,format);
        FileUtil.toFile(inputStream,destPath);
    }

    /**
     * trimByWidthSizeLimit
     *
     * @param inputStream
     * @param sizeLimit
     * @param format
     * @return
     */
    public static InputStream trimBySizeLimit(InputStream inputStream,int sizeLimit,String format){
        return trimBySizeLimit(inputStream,sizeLimit,format,0);
    }

    /**
     * trimBySizeAndCountLimit
     *
     * @param inputStream
     * @param sizeLimit
     * @return
     */
    public static InputStream trimBySizeLimit(InputStream inputStream,int sizeLimit,String format,int count){

        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream can not be null !");
        }

        int srcFileSize = 0;
        try {
            srcFileSize = inputStream.available()/1024;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (srcFileSize <= sizeLimit) {
            return inputStream;
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(inputStream).scale(0.9).outputFormat(format).toOutputStream(outputStream);
            count++;
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream trimInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        int  trimSrcFileSize = 0;
        try {
            trimSrcFileSize = trimInputStream.available()/1024;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (count >= 3 || trimSrcFileSize <= sizeLimit) {
            return trimInputStream;
        }

        return trimBySizeLimit(trimInputStream,sizeLimit,format,count);
    }

    /**
     * getWidthAndHeight
     *
     * @param desPath
     * @return
     */
    public static Map<String,Integer> getWidthAndHeight(String desPath) {

        BufferedImage bim = null;
        try {
            bim = ImageIO.read(new FileInputStream(srcPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = bim.getWidth();
        int height = bim.getHeight();

        Map<String,Integer> map = new HashMap<>();
        map.put("width",width);
        map.put("height",height);

        return map;
    }

    /**
     * trimByWidth
     *
     * @param inputStream
     * @param format
     * @param width
     * @param path
     */
    public static void trimByWidth(InputStream inputStream,String format, int width,String path){

        try {
            Thumbnails.of(inputStream).width(width).outputFormat(format).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void trimByScale(InputStream inputStream,String format,double scale,String path){

        try {
            Thumbnails.of(inputStream).scale(scale).outputFormat(format).toFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
