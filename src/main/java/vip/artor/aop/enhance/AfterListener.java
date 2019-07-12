package vip.artor.aop.enhance;

/**
 * Func :后置监听器
 *
 * @author: leeton on 2019/7/11.
 */
public interface AfterListener {
    /**
     * 方法正常返回后的通知
     *
     * @param classLoader target类加载器
     * @param className   类名
     * @param methodName  方法名
     * @param methodDesc  方法描述
     * @param target      目标类实例对象,如果目标为静态方法,则为null
     * @param args        增强的方法参数
     * @param returnObj   目标方法返回值
     * @throws Throwable 通知执行过程中的异常
     */
    void afterReturning(ClassLoader classLoader, String className, String methodName, String methodDesc, Object target,
                        Object[] args, Object returnObj) throws Throwable;
}
