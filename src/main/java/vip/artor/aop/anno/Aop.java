package vip.artor.aop.anno;

import vip.artor.aop.scanner.AnnotationDetector;
import vip.artor.aop.util.PropUtil;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

/**
 * Func :
 *
 * @author: leeton on 2019/7/2.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SuppressWarnings("unchecked")
public @interface Aop {
    public String value() default "";

    enum Scan {
        inst;
        public List<Method> aop_method_list;

        Scan() {
            try {
                Properties prop = PropUtil.load("aop.properties");
                String scanPath = prop.getProperty("aop.scan.path");
                // scan
                aop_method_list = AnnotationDetector.scanClassPath(scanPath) // or: scanFiles(File... files)
                        .forAnnotations(Aop.class) // one or more annotations
                        .forAnnotations(Before.class) // one or more annotations
                        .on(ElementType.METHOD) // optional, default ElementType.TYPE. One ore more element types
//                        .filter((File dir, String name) -> !dir.getName().startsWith("com.artlongs.amq.tools")) // optional, default all *.class files
                        .collect(s -> s.getMethod());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Before {
        public Class listener() ;
        public Class enhancer() ;

    }


}
