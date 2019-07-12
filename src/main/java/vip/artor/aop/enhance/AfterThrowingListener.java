package vip.artor.aop.enhance;

/**
 * Func :异常监听器
 *
 * @author: leeton on 2019/7/11.
 */
public interface AfterThrowingListener extends AdviceListener {
    /**
     * 方法抛出异常后的通知
     *
     * @param loader     target类加载器
     * @param className  类名
     * @param methodName 方法名
     * @param methodDesc 方法描述
     * @param target     目标类实例对象,如果目标为静态方法,则为null
     * @param args       增强的方法参数
     * @param throwable  目标方法返回值
     * @throws Throwable 通知执行过程中的异常
     */
    void afterThrowing(ClassLoader loader, String className, String methodName, String methodDesc, Object target,
                       Object[] args, Throwable throwable) throws Throwable;

}
