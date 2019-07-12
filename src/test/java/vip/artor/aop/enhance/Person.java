package vip.artor.aop.enhance;

import vip.artor.aop.anno.Aop;

/**
 * 用来测试enhancer的测试类
 */
@Aop
public class Person {

    @Aop.Before(listener = PersonListener.class,enhancer = PersonEnhancer.class)
    public void sayHello(String to) {
        System.out.println("hello:" + to);
    }
    public String getGender() {
        throw new UnsupportedOperationException("you should not know my gender.");
    }

}
