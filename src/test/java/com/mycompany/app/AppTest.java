package com.mycompany.app;

import static org.junit.Assert.fail;
import org.junit.Test;

public class AppTest {
    @Test
    public void testSpeed() throws Exception {
        long nashorn = App.benchNashornScriptEngine();
        long graalJS = App.benchGraalPolyglotContext();
        if (nashorn < graalJS) {
            fail(String.format("Graal.js (%d ms) should be faster than Nashorn (%d ms).", graalJS, nashorn));
        }
    }
}
