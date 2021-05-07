package com;

import com.achilles.MyApplication;
import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MyApplication.class})
public class MyApplicationTests {

    public final String urlPrefix = "http://127.0.0.1:8080/achilles";

    public Map<String, Object> getHeaderMap(String keyWords){
        String traceId = GenerateUniqueUtil.getTraceId(keyWords);
        Map<String, Object> map = new HashMap<>();
        map.put("traceId",traceId);
        return map;
    }

}
