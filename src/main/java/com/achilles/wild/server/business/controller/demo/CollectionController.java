package com.achilles.wild.server.business.controller.demo;


import com.achilles.wild.server.tool.generate.unique.GenerateUniqueUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/Collection", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class CollectionController {


    final Map<String,String> map = new HashMap<>();
    String key;

    @PostConstruct
    private void makeHashMap(){
        for (int i = 0; i < 500; i++) {
            String uuid = GenerateUniqueUtil.getUuId();
            if (i==0) {
                key=uuid;
            }
            map.put(uuid,uuid);
        }
    }

    @GetMapping(path = "/map/hashmap")
    public String hashmap(){

        String result  = map.get(key);

//        log.info("--hashmap------result : " + result);

        return result;
    }

}
