package fr.evercraft.everapi;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
        
        Set<String> set1 = ImmutableSet.of("string1", "string2", "string3");
        Set<String> set2 = ImmutableSet.of("string3", "string4", "string5");
        Set<String> set3 = Sets.intersection(set2, set1);
        System.out.println("out1 : " + set3);
        set3 = Sets.intersection(set1, set2);
        System.out.println("out2 : " + set3);
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