package com.weifuchow.jdk.learn.jvm;


public class ObjectStroageTest {

    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(10000);
        System.out.println("new ");
        // 400000000 byte = > 4byte
//        Object[] obj = new Object[100000000];

        // the same of Object
        Person[] p1 = new Person[100000000];


        // 2812179576 - 29535848
        // 2782643728
        //  100000000
        // 27 个字节。
        for (int i = 0; i < p1.length ; i++) {
            p1[i] = new Person();
        }
        System.out.println("finished ");
        Thread.sleep(10000);
    }

    static class Person {
        private String name;
        private Byte aByte;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Byte getaByte() {
            return aByte;
        }

        public void setaByte(Byte aByte) {
            this.aByte = aByte;
        }
    }


}
