package vip.artor.aop.enhance;

import jdk.internal.org.objectweb.asm.commons.Method;

import static vip.artor.aop.enhance.AsmMethods.MethodFinder.getAsmMethod;

/**
 * Asm methods 常量
 */
public interface AsmMethods {

    class MethodFinder {

        private MethodFinder() {

        }

        static Method getAsmMethod(final Class<?> clazz, final String methodName, final Class<?>... parameterTypes) {
            return Method.getMethod(getJavaMethodUnsafe(clazz, methodName, parameterTypes));
        }

        static java.lang.reflect.Method getJavaMethodUnsafe(final Class<?> clazz, final String methodName,
                                                            final Class<?>... parameterTypes) {
            try {
                return clazz.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * @link {cn.david.scrat.profiler.buildEnhanceObj.AdviceWeaver#methodOnBegin(cn.david.scrat.profiler.buildEnhanceObj.AdviceListener,
     * java.lang.ClassLoader, java.lang.String, java.lang.String, java.lang.String, java.lang.Object,
     * java.lang.Object[])}
     */
    Method AdviceWeaver_methodOnBegin = getAsmMethod(
        AdviceWeaver.class,
        "methodOnBegin",
        int.class,
        ClassLoader.class,
        String.class,
        String.class,
        String.class,
        Object.class,
        Object[].class);

    /**
     * @link {cn.david.scrat.profiler.buildEnhanceObj.AdviceWeaver#methodOnReturning(java.lang.Object)}
     */
    Method AdviceWeaver_methodOnReturning = getAsmMethod(
        AdviceWeaver.class,
        "methodOnReturning",
        Object.class
    );

    /**
     * @link {cn.david.scrat.profiler.buildEnhanceObj.AdviceWeaver#methodOnThrowing(java.lang.Throwable)}
     */
    Method AdviceWeaver_methodOnThrowing = getAsmMethod(
        AdviceWeaver.class,
        "methodOnThrowing",
        Throwable.class
    );

    Method Class_forName = getAsmMethod(Class.class, "forName", String.class);

    Method OBJECT_getClass = getAsmMethod(Object.class, "getClass");

    Method Class_getClassLoader = getAsmMethod(Class.class, "getClassLoader");

}
