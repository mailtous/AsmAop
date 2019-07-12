package vip.artor.aop.enhance;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Func :
 *
 * @author: leeton on 2019/7/11.
 */
public class PersonEnhancer extends Enhancer {
    @Override
    public void aplyMyEnhance() {

        try {
            Object object = this.buildEnhanceObj();
            Method[] methods = object.getClass().getDeclaredMethods();
            //用反射传值
            Method method = object.getClass().getDeclaredMethod("sayHello", String.class);
            method.invoke(object, "jack and luxi");
//            System.out.println(method.invoke(object, name+" and luxi"));

//            Parameter[] parameterNames = method.getParameters();

/*            Method method1 = object.getClass().getMethod("getName");
            System.out.println(method1.invoke(object));*/

/*            Method method2 = object.getClass().getMethod("getGender");
            System.out.println(method2.invoke(object));*/
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


}
