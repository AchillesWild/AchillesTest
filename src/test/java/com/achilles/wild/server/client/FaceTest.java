package com.achilles.wild.server.client;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;

public class FaceTest {


    @Test
    public void upload() throws Exception{
        String path = "C:\\Users\\Achilles\\Desktop\\1.png";
        FileInputStream inputStream = new FileInputStream(new File(path));
//        PutObjectResult result = obsClient.putObject(bucketname, key,inputStream);

        byte[] bytes = IOUtils.toByteArray(inputStream);

        String encoded = Base64.getEncoder().encodeToString(bytes);
        System.out.println(encoded);
    }
}
