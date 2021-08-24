package com.weifuchow.list;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/22 15:25
 */
public class ListTester {

    public static int counter = 1;
    public static int size = 10;

    public static void main(String[] args) {

        IQueue queue = new ArraysQueue(size);
        testQueue(queue);
        //
        IStack stack = new ArraysStack(size);
        testStack(stack);
    }

    public static void testQueue(IQueue queue){

        System.out.println("queue 开始push");
        // 第一次插入满
        for (int i = 0; i < size ; i++) {
            People people = generatePeople();
            queue.push(people);
            System.out.println("queue push = 》 " + people);
        }
        // 第一次漏去取一个
        System.out.println("queue 开始pop");
        for (int i = 0; i < size-1 ; i++) {
            System.out.println("queue pop = 》 " +queue.pop());
        }
        // 插满 取满
        for (int i = 0; i < size ; i++) {
            People people = generatePeople();
            queue.push(people);
            System.out.println("queue push = 》 " + people);
        }
        for (int i = 0; i < size + 1 ; i++) {
            People people = generatePeople();
            System.out.println("queue pop = 》 " +queue.pop());
        }
        //
        System.out.println("finish queue");
    }

    public static void testStack(IStack stack){
        System.out.println("stack 开始push");
        // 第一次插入满
        for (int i = 0; i < size ; i++) {
            People people = generatePeople();
            stack.push(people);
            System.out.println("stack push = 》 " + people);
        }
        // 第一次漏去取一个
        System.out.println("stack 开始pop");
        for (int i = 0; i < size-1 ; i++) {
            System.out.println("stack pop = 》 " +stack.pop());
        }
        // 插满 取满
        for (int i = 0; i < size ; i++) {
            People people = generatePeople();
            stack.push(people);
            System.out.println("stack push = 》 " + people);
        }
        for (int i = 0; i < size + 1 ; i++) {
            People people = generatePeople();
            System.out.println("stack pop = 》 " +stack.pop());
        }
        //
        System.out.println("stack queue");

    }


    public static People generatePeople(){
        return new People("test - " + counter++);
    }

    public static class People{

        private String name;

        public People(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "People{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
