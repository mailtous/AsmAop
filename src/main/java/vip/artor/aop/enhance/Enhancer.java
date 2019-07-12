package vip.artor.aop.enhance;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import static jdk.internal.org.objectweb.asm.ClassWriter.COMPUTE_FRAMES;
import static jdk.internal.org.objectweb.asm.ClassWriter.COMPUTE_MAXS;


/**
 * 增强者
 */
public abstract class Enhancer {

    private Class<?> targetClass;

    private AdviceListener adviceListener;

    private int adviceId;

    private String transferName;

    public static EnhancerClassLoader enhancerClassLoader;

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    public Enhancer() {
        enhancerClassLoader = new EnhancerClassLoader();
    }

    public void setAdviceListener(AdviceListener adviceListener) {
        this.adviceListener = adviceListener;
        this.adviceId = ID_GENERATOR.getAndIncrement();
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public String genTransferClassName() {
        if (transferName == null) {
            this.transferName = this.targetClass.getName().replace("/", ".");
        }
        return this.transferName;
    }

    /**
     * 真正要增强的内容
     */
    public abstract void aplyMyEnhance();

    public static final String adviced_flag = "ator_aop_flag$";

    /**
     * 增强点
     */
    public Object buildEnhanceObj() {
        try {
            if (isNotEnhanced(targetClass)) {
                ClassReader cr = new ClassReader(targetClass.getName());
                // 字节码增强
                final ClassWriter cw = new ClassWriter(cr, COMPUTE_FRAMES | COMPUTE_MAXS);
                // 标记已增强FLAG
                cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, adviced_flag, "I", null, new Integer(1)).visitEnd();
                cr.accept(new AdviceWeaver(adviceId, adviceListener, genTransferClassName(), targetClass, cw), ClassReader.EXPAND_FRAMES);
                byte[] enhanced = cw.toByteArray();

                //通过 EnhancerClassLoader 直接加载增强后的 class
                Class<?> enhancedClazz = enhancerClassLoader.defineClass(genTransferClassName(), enhanced);
                // 增强后的 class 写入物理硬盘
                saveToFile(targetClass, enhanced);
                return enhancedClazz.newInstance();

            } else {
                ClassReader cr = new ClassReader(targetClass.getName());
//                 字节码增强
                final ClassWriter cw = new ClassWriter(cr, COMPUTE_FRAMES | COMPUTE_MAXS);
                new AdviceWeaver(adviceId, adviceListener, genTransferClassName(), targetClass, cw);
                return targetClass;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isNotEnhanced(Class<?> targetClass ){
        Field field = null;
        try {
            field = targetClass.getDeclaredField(adviced_flag);
        } catch (NoSuchFieldException e) {
        }
        return (null == field);
    }

    private static java.security.ProtectionDomain DOMAIN;

    static {
        DOMAIN = (java.security.ProtectionDomain)
                java.security.AccessController.doPrivileged(
                        new java.security.PrivilegedAction() {
                            public Object run() {
                                return EnhancerClassLoader.class.getProtectionDomain();
                            }
                        });
    }

    public static class EnhancerClassLoader extends ClassLoader {
        public EnhancerClassLoader() {
            super(Thread.currentThread().getContextClassLoader());
        }

        public Class<?> defineClass(String clzName, byte[] b) {
            Class<?> c = super.defineClass(clzName, b, 0, b.length, DOMAIN);
            return c;
        }

    }

    public static <T> T instanceOf(ClassLoader loader, String clzName) {
        try {
            return (T)loader.loadClass(clzName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void saveToFile(Class<?> targetClass, byte[] bytes) {
        String targetClassName = targetClass.getName();
        StringBuffer targetFilePath = new StringBuffer(256);
        String simpleName = targetClassName.substring(targetClassName.lastIndexOf(".") + 1, targetClassName.length());
        targetFilePath.append(targetClass.getResource("").getPath()).append(simpleName).append(".class");
        File f = new File(targetFilePath.toString());
        targetFilePath = null;
        simpleName = null;
        saveFile(f, bytes);
    }
    private void saveFile(File file ,byte [] data){
        FileOutputStream fos = null;
        try {
            if(null != file && data != null){
                if(file.exists()){
                    file.delete();
                }
                fos = new FileOutputStream(file);
                fos.write(data,0,data.length);
                fos.flush();
            }
        } catch (IOException e) {
            try {
                fos.close();
            } catch (IOException ex) {
            }
            throw new RuntimeException(e);
        }finally {
            try {
                if (null != fos) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
