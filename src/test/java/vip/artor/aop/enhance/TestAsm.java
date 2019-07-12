package vip.artor.aop.enhance;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Func :
 *
 * @author: leeton on 2019/7/12.
 */
public class TestAsm {

    public static void main(String[] args) throws IOException {

        String filename = "C:\\generated.class";

// Generate class


        Person.class.getResource("").getPath();

        System.out.println("Generating MyClass");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        cw.newClass("MyClass");
        cw.newField("owner", "value", "I");


// Write the generated class to a file

        ClassReader cr = new ClassReader(new FileInputStream("C:\\generated.class"));

// Print the class

        System.out.println("Read");
        cr.accept(cw, 0);

        System.out.println("Writing "+filename);

     FileOutputStream os = new FileOutputStream(filename);
        os.write(cw.toByteArray());
        os.close();

// Read the file back

        System.out.println("Reading "+filename);
}
}
