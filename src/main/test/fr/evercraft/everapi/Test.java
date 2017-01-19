package fr.evercraft.everapi;

import junit.framework.TestCase;

public class Test extends TestCase {

    public void testApply() throws Exception {
    	int x = 56;
    	int z = 23;
    	long to = toLong(x, z);
    	
    	int mainIdx = (int) (to & 255);
    	int outerIdx = (int) ((to >> 32) & 255);
        System.out.println("test : " + to);
        System.out.println("x : " + msw(to));
        System.out.println("z : " + lsw(to));
        System.out.println("mainIdx : " + mainIdx);
        System.out.println("outerIdx : " + outerIdx);
    }
    
    public static long toLong(int msw, int lsw) {
        return ((long) msw << 32) + lsw - Integer.MIN_VALUE;
    }
    
    public static int msw(long l) {
        return (int) (l >> 32);
    }

    public static int lsw(long l) {
        return (int) (l & 0xFFFFFFFF) + Integer.MIN_VALUE;
    }
    
}