package com.achilles;

import com.obs.services.ObsClient;
import com.obs.services.model.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ObsTest {

    String endPoint = "obs.cn-north-4.myhuaweicloud.com";
    String ak = "JS2GFAVBZJCMNWZ2GVEL";
    String sk = "XfrCT0Ld0Xu0AKWTQbboMXhRIpywVKWtBTMR5VRa";
    ObsClient obsClient = new ObsClient(ak, sk, endPoint);
    String bucketname = "gamma-attendance1";
    String key = "a/a/z.jpg";

    @Test
    public void upload() throws Exception{
        String path = "C:\\Users\\Achilles\\Desktop\\z.jpg";
        FileInputStream inputStream = new FileInputStream(path);
        PutObjectResult result = obsClient.putObject(bucketname, key,inputStream);
        System.out.println();
    }

    @Test
    public void download() throws Exception{
        String path = "C:\\Users\\Achilles\\Desktop\\123.jpg";
        ObsObject obsObject = obsClient.getObject(bucketname, key);
        InputStream inputStream1 = obsObject.getObjectContent();
        File targetFile = new File(path);
        FileUtils.copyInputStreamToFile(inputStream1, targetFile);
        System.out.println();
    }

    @Test
    public void getUrl() throws Exception{
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // 替换您的过期时间，单位是秒
        long expireSeconds = 300L;

        // 替换成您对应的操作
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);

        // 替换为请求本次操作访问的桶名和对象名
        request.setBucketName(bucketname);
        request.setObjectKey(key);

        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        String url = response.getSignedUrl();
        // 成功返回预签名URL，如下打印URL信息
        System.out.println(url);
    }
}
