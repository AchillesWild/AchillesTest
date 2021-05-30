package com.achilles.wild.server.other.design.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CglibInterceptor implements MethodInterceptor {

    private final static Logger log = LoggerFactory.getLogger(CglibInterceptor.class);

    /**
     * CGLIB 增强类对象，代理类对象是由 Enhancer 类创建的，
     * Enhancer 是 CGLIB 的字节码增强器，可以很方便的对类进行拓展
     */
    private Enhancer enhancer = new Enhancer();

    /**
     * 执行
     *
     * @param obj  被代理的对象
     * @param method 代理的方法
     * @param args 方法的参数
     * @param proxy CGLIB方法代理对象
     * @return  cglib生成用来代替Method对象的一个对象，使用MethodProxy比调用JDK自身的Method直接执行方法效率会有提升
     * @throws Throwable
     */
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        log.info("proxy---CGLIB---------------------------------- invoke -------------------------before");
        Object o = proxy.invokeSuper(obj, args);
        log.info("proxy---CGLIB---------------------------------- invoke -------------------------after");
        return o;
    }

    /**
     * 使用动态代理创建一个代理对象
     *
     * @param c
     * @return
     */
    public  Object newProxyInstance(Class<?> c) {

        enhancer.setSuperclass(c);

        enhancer.setCallback(this);

        return enhancer.create();
    }
}
