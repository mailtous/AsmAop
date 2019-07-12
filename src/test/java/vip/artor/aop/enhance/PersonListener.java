package vip.artor.aop.enhance;

/**
 * 用于测试的AdviceListener
 */
public class PersonListener implements BeforeListener, AfterListener,AfterThrowingListener {

    @Override
    public void before(ClassLoader classLoader, String className, String methodName, String methodDesc, Object target,
                       Object[] args) throws Throwable {
        System.out.println("PersonListener before ->:  current method name:" + methodName +", val=" +args[0]);
    }

    @Override
    public void afterReturning(ClassLoader classLoader, String className, String methodName, String methodDesc,
                               Object target, Object[] args, Object returnObj) throws Throwable {
        return;
//        System.out.println("PersonListener afterReturning- >: current method return val:" + returnObj);
    }

    @Override
    public void afterThrowing(ClassLoader loader, String className, String methodName, String methodDesc, Object target,
                              Object[] args, Throwable throwable) throws Throwable {
        return;
//        System.out.println("PersonListener afterThrowing->: current method throw ex:" + throwable);
    }
}
