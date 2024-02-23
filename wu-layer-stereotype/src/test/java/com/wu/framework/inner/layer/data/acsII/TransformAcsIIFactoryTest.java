package com.wu.framework.inner.layer.data.acsII;


import com.wu.framework.inner.layer.util.DataTransformUntil;

class TransformAcsIIFactoryTest {

    public static void main(String[] args) {

        String st = "你好";
        Long i = AcsIIFactory.acsII(st);
        System.out.println("你好转换成acsII  i = " + i);

        Long l = 12345L;
        Long i1 = AcsIIFactory.acsII(l);
        System.out.println("12345L i1 = " + i1);


        while (true) {
            try {
                String simulationBean = DataTransformUntil.simulationBean(String.class);
                Long i2 = AcsIIFactory.acsII(simulationBean);
                System.out.println(simulationBean + "i2 = " + i2);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}