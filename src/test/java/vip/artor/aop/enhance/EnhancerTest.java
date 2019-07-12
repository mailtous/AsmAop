package vip.artor.aop.enhance;

import org.junit.Test;
import vip.artor.aop.anno.Aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 */
public class EnhancerTest {

    @Test
    public void testOfAop(){

        List<Method> classList = Aop.Scan.inst.aop_method_list;
        if (null != classList) {
            for (Method method : classList) {
                Class<?> targetClass = method.getDeclaringClass();
                ClassLoader loader = targetClass.getClassLoader();
                Annotation[] annos = method.getDeclaredAnnotations();
                for (Annotation anno : annos) {
                    if (anno instanceof Aop.Before) {
                        Aop.Before before = (Aop.Before) anno;
                        Enhancer enhancer = Enhancer.instanceOf(loader, before.enhancer().getName());
                        AdviceListener listener = Enhancer.instanceOf(loader, before.listener().getName());
                        enhancer.setTargetClass(targetClass);
                        enhancer.setAdviceListener(listener);
                        enhancer.aplyMyEnhance();

                    }
                }

            }
        }
        ////
        Person person = new Person();
        person.sayHello("leeton");

    }



    @Test
    public void testOfManual() throws NoSuchMethodException, InvocationTargetException {
        PersonEnhancer enhancer = new PersonEnhancer();
        enhancer.setTargetClass(Person.class);
        enhancer.setAdviceListener(new PersonListener());
    }

    private Map<String, Object> argValsToMap(Object[] vars, String[] names) {
        Map<String, Object> argsMap = new HashMap<>();
        for (int i = 0; i < vars.length; i++) {
            argsMap.put(names[i], vars[i]);
        }
        System.err.println(argsMap.toString());
        return argsMap;
    }

}
