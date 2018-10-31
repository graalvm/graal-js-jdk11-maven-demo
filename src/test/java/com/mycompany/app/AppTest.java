package com.mycompany.app;

import static org.junit.Assert.fail;
import org.junit.Test;

public class AppTest {
    @Test
    public void testSpeed() throws Exception {
        long nashorn = App.benchNashornScriptEngine();
        long graalJS = App.benchGraalPolyglotContext();
        if (nashorn < graalJS) {
            fail("Graal.js should be five times faster. Nashorn " + nashorn + " ms. Graal.js " + graalJS + " ms");
        }
    }
}
