package com.mycompany.app;

import org.graalvm.polyglot.Context;
import static org.junit.Assert.fail;
import org.junit.Assume;
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
        assertGraalVMOrJDK11();
        long graalJS = App.benchGraalPolyglotContext();
        if (nashorn < graalJS) {
            fail(String.format("Graal.js (%d ms) should be faster than Nashorn (%d ms).", graalJS, nashorn));
        }
    }

    @Test
    public void testGraalScriptEngineSpeed() throws Exception {
        assertGraalVMOrJDK11();
        long graalJS = App.benchGraalScriptEngine();
        if (nashorn < graalJS) {
            fail(String.format("Graal.js (%d ms) should be faster than Nashorn (%d ms).", graalJS, nashorn));
        }
    }

    private void assertGraalVMOrJDK11() {
        try {
            Context.create().close();
        } catch (IllegalStateException ex) {
            Assume.assumeNoException("Download GraalVM.org or use JDK11!", ex);
        }
    }
}
