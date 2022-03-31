//package com.weifuchow.jdk.learn.jvm;
//
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//
//public class FinalUnsafeFields {
//    private static final Unsafe unsafe;
//    static
//    {
//        try
//        {
//            Field field = Unsafe.class.getDeclaredField("theUnsafe");
//            field.setAccessible(true);
//            unsafe = (Unsafe)field.get(null);
//        }
//        catch (Exception e)
//        {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public static void main(String[] args) throws NoSuchFieldException {
//        updateFinal();
//        updateFinalStatic();
//    }
//
//    private static void updateFinalStatic() throws NoSuchFieldException {
//        System.out.println( "Original static final value = " + SafeKlass.getNotSoConstant() );
//        //we need a field to update
//        final Field fieldToUpdate = SafeKlass.class.getDeclaredField( "NOT_SO_CONSTANT" );
//        //this is a 'base'. Usually a Class object will be returned here.
//        final Object base = unsafe.staticFieldBase( fieldToUpdate );
//        //this is an 'offset'
//        final long offset = unsafe.staticFieldOffset( fieldToUpdate );
//        //actual update
//        unsafe.putObject( base, offset, 145 );
//        //ensure the value was updated
//        System.out.println( "Updated static final value = " + SafeKlass.getNotSoConstant() );
//    }
//
//    private static void updateFinal() throws NoSuchFieldException {
//        final JustFinal obj = new JustFinal( 10 );
//        //ensure we can see an original value
//        System.out.println( "Original value = " + obj.getField() );
//        //obtain an updated field
//        final Field fieldToUpdate = JustFinal.class.getDeclaredField( "m_field" );
//        //get unsafe offset to this field
//        final long offset = unsafe.objectFieldOffset( fieldToUpdate );
//        //actual update
//        unsafe.putInt( obj, offset, 20 );
//        //ensure update was successful
//        System.out.println( "Updated value = " + obj.getField() );
//    }
//
//    private static final class JustFinal
//    {
//        private final int m_field;
//
//        public JustFinal(int field) {
//            m_field = field;
//        }
//
//        public int getField() {
//            return m_field;
//        }
//    }
//}