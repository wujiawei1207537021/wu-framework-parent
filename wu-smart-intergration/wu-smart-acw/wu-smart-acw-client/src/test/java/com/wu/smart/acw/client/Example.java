package com.wu.smart.acw.client;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class Example {

    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        // 获取目标方法的Method对象
        Method method = Example.class.getMethod("hello", String.class);

        // 获取方法的字节码
        InputStream inputStream = Example.class.getResourceAsStream("Example.class");
        assert inputStream != null;
        ClassReader reader = new ClassReader(inputStream);

        // 创建ClassWriter对象
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        // ClassReader接受ClassVisitor的访问
        reader.accept(new TestClassVisitor(Opcodes.ASM8, classWriter), ClassReader.EXPAND_FRAMES);


        // 获取修改后的字节码
        byte[] modifiedBytes = classWriter.toByteArray();

        // 将修改后的字节码写入文件
        try (OutputStream outputStream = new FileOutputStream("Example.class")) {
            outputStream.write(modifiedBytes);
        }


        // 调用目标方法
        Example example = new Example();
        method.invoke(example, "world");
    }

    public void hello(String name) {
        System.out.println("hello " + name);
    }
}

class TestClassVisitor extends ClassVisitor {

    /**
     * Constructs a new {@link ClassVisitor}.
     *
     * @param api          the ASM API version implemented by this visitor. Must be one of the {@code
     *                     ASM}<i>x</i> values in {@link Opcodes}.
     * @param classVisitor the class visitor to which this visitor must delegate method calls. May be
     *                     null.
     */
    protected TestClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        if (name.equals("hello")) {
            // 在test方法中插入一条打印语句
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Hello, World!");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
        return mv;
    }
}
