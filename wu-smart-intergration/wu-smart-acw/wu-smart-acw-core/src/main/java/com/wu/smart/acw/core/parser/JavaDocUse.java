package com.wu.smart.acw.core.parser;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;

/**
 * @Author shaoyuanhu
 * @Date 2021/2/20 10:15 AM
 * @Desc
 */
public class JavaDocUse {

    private static RootDoc rootDoc;

    public static void show() {
        ClassDoc[] classes = rootDoc.classes();
        for (ClassDoc classDoc : classes) {
            System.out.println(classDoc.name() + "类的注释:" + classDoc.commentText());
            MethodDoc[] methodDocs = classDoc.methods();
            for (MethodDoc methodDoc : methodDocs) {
                // 打印出方法上的注释
                System.out.println("类" + classDoc.name() + "," + methodDoc.name() + "方法注释:" + methodDoc.commentText());
            }
            System.out.println("===============");
            FieldDoc[] fields = classDoc.fields();
            for (FieldDoc field : fields) {
                //打印属性的注释
                System.out.println(field.name() + "--" + field.type().simpleTypeName() + "--" + field.commentText());
            }

//            Field[] declaredFields = PayablePojo.class.getDeclaredFields();
//            int length = fields.length;
//            int length1 = declaredFields.length;
//            System.out.println(length + "--" + length1);
//            System.out.println("===============");
//            for (int i = 0; i < length; i++) {
//                System.out.println(declaredFields[i].getName() + "--" + declaredFields[i].getType().getSimpleName() + "--" +  fields[i].commentText());
//            }
//            System.out.println("===============");
        }
    }

    public static void main(String[] args) {
        com.sun.tools.javadoc.Main.execute(new String[]{"-doclet",
                Doclet.class.getName(),
                "-encoding", "utf-8", "-classpath",
                "/Users/shaoyuanhu/WordSpace/xxx/target/classes",
                "/Users/shaoyuanhu/WordSpace/xxx/src/main/java/xxx/test/DocTestDO.java"
        });
        show();
    }

    public static class Doclet {
        public static boolean start(RootDoc rootDoc) {
            JavaDocUse.rootDoc = rootDoc;
            return true;
        }
    }

}