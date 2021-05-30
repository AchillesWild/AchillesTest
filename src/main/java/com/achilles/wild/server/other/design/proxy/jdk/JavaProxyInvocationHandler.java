package com.achilles.wild.server.other.design.proxy.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JavaProxyInvocationHandler  implements InvocationHandler {

    private final static Logger LOG = LoggerFactory.getLogger(JavaProxyInvocationHandler.class);

    /**
     * 中间类持有委托类对象的引用,这里会构成一种静态代理关系
     */
    private Object obj ;

    /**
     * 有参构造器,传入委托类的对象
     * @param obj 委托类的对象
     */
    public JavaProxyInvocationHandler(Object obj){
        this.obj = obj;

    }

    /**
     * 动态生成代理类对象,Proxy.newProxyInstance
     * @return 返回代理类的实例
     */
    public Object newProxyInstance() {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    /**
     * @param proxy 代理对象
     * @param method 代理方法
     * @param args 方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOG.info("proxy---JDK---------------------------------- invoke -------------------------before");
        Object result = method.invoke(obj, args);
        LOG.info("proxy----JDK--------------------------------- invoke -------------------------after");
        return result;
    }
}
