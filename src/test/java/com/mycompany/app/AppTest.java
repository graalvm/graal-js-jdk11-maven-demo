package com.mycompany.app;

import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;

public class AppTest {
    private static long nashorn;

    @BeforeClass
    public static void nashornBenchmark() throws Exception {
        nashorn = App.benchNashornScriptEngine();
    }

    @Test
    public void testGraalPolyglotSpeed() throws Exception {
        long graalJS = App.benchGraalPolyglotContext();
        if (nashorn < graalJS) {
            fail(String.format("Graal.js (%d ms) should be faster than Nashorn (%d ms).", graalJS, nashorn));
        }
    }

    @Test
    public void testGraalScriptEngineSpeed() throws Exception {
        long graalJS = App.benchGraalScriptEngine();
        if (nashorn < graalJS) {
            fail(String.format("Graal.js (%d ms) should be faster than Nashorn (%d ms).", graalJS, nashorn));
        }
    }
}
