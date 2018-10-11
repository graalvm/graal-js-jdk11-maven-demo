package com.mycompany.app;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigourous Test :-)
     */
    @Test
    public void testApp()
    {
    	Assert.assertTrue(App.get42().isNumber());
    }
}
