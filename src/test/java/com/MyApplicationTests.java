package com;

import com.achilles.MyApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MyApplication.class})
public class MyApplicationTests {

    public final String urlPrefix = "http://localhost:8080/achilles/";

}
