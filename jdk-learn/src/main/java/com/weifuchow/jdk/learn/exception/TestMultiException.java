package com.weifuchow.jdk.learn.exception;

import java.util.Random;

/**
 *
 * @author: weifuchow
 * @date: 2021/6/29 11:21
 */
public class TestMultiException {

    public static void doWork() throws L1Exception, L2Exception, L3Exception {
        int random = new Random().nextInt(5);
        System.out.println(random);
        switch (random){
            case 1:
                throw new L1Exception("happen l1 exception");
            case 2:
                throw new L2Exception("happen l2 exception");
            case 3:
                throw new L3Exception("happen l3 exception");
            case 4:
                throw new L4RuntimeException("happen l4 exception");
            default:
                // 运行时异常，java 编译器不需要catch 也可以编译通过
                // 非运行时异常需要手动的catch,或者抛出去
                throw new RuntimeException("happen another exception");
        }
    }

    // 当异常处理器所能处理的异常类型与方法抛出的异常类型相符时，即为合适 的异常处理器。
    // 运行时系统从发生异常的方法开始，依次回查调用栈中的方法，直至找到含有合适异常处理器的方法并执行。
    // 只要找到合适的便处理完成。找到一个异常处理类即可。
    public static void catchMethod(){
        try {
            doWork();
        }
        catch (L1Exception e) {
            System.out.println("l1");
            e.printStackTrace();
        } catch (L2Exception e) {
            System.out.println("l2");
            e.printStackTrace();
        } catch (L3Exception e) {
            System.out.println("l3");
            e.printStackTrace();
        } catch (L4RuntimeException e){
            System.out.println("l4");
            e.printStackTrace();
        } catch (RuntimeException e){
            System.out.println("runtime");
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("another ");
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        catchMethod();
    }

}
