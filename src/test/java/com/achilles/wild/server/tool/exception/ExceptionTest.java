package com.achilles.wild.server.tool.exception;

import org.junit.Test;

public class ExceptionTest {

    @Test
    public void test(){
        ty();
    }


    private void ty(){

        try {
            Long.parseLong("e");
            System.out.println("9999999999");
        }catch (Exception e){
            //e.printStackTrace();
            System.out.println("Exception ==========");
            throw new RuntimeException(e);
        }finally {
            System.out.println("finally");
        }

        System.out.println("return ==========");
    }
}
