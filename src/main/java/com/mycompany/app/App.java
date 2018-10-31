package com.mycompany.app;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.Invocable;
import java.io.IOException;

/**
 * Simple benchmark for Graal.js via GraalVM Polyglot Context and ScriptEngine.
 */
public class App {

    public static final int WARMUP = 10;
    public static final int ITERATIONS = 10;
    public static final String BENCHFILE = "src/bench.js";

    public static final String SOURCE = ""
            + "var N = 2000;\n"
            + "var EXPECTED = 17393;\n"
            + "\n"
            + "function Natural() {\n"
            + "    x = 2;\n"
            + "    return {\n"
            + "        'next' : function() { return x++; }\n"
            + "    };\n"
            + "}\n"
            + "\n"
            + "function Filter(number, filter) {\n"
            + "    var self = this;\n"
            + "    this.number = number;\n"
            + "    this.filter = filter;\n"
            + "    this.accept = function(n) {\n"
            + "      var filter = self;\n"
            + "      for (;;) {\n"
            + "          if (n % filter.number === 0) {\n"
            + "              return false;\n"
            + "          }\n"
            + "          filter = filter.filter;\n"
            + "          if (filter === null) {\n"
            + "              break;\n"
            + "          }\n"
            + "      }\n"
            + "      return true;\n"
            + "    };\n"
            + "    return this;\n"
            + "}\n"
            + "\n"
            + "function Primes(natural) {\n"
            + "    var self = this;\n"
            + "    this.natural = natural;\n"
            + "    this.filter = null;\n"
            + "\n"
            + "    this.next = function() {\n"
            + "        for (;;) {\n"
            + "            var n = self.natural.next();\n"
            + "            if (self.filter === null || self.filter.accept(n)) {\n"
            + "                self.filter = new Filter(n, self.filter);\n"
            + "                return n;\n"
            + "            }\n"
            + "        }\n"
            + "    };\n"
            + "}\n"
            + "\n"
            + "function primesMain() {\n"
            + "    var primes = new Primes(Natural());\n"
            + "    var primArray = [];\n"
            + "    for (var i=0;i<=N;i++) { primArray.push(primes.next()); }\n"
            + "    if (primArray[N] != EXPECTED) { throw new Error('wrong prime found: '+primArray[N]); }\n"
            + "}\n";

    public static void main(String[] args) throws Exception {
        benchGraalPolyglotContext();
        benchGraalScriptEngine();
        benchNashornScriptEngine();
    }

    static long benchGraalPolyglotContext() throws IOException {
        System.out.println("=== Graal.js via org.graalvm.polyglot.Context === ");
        long took = 0;
        try (Context context = Context.create()) {
            context.eval("js", SOURCE);
            Value primesMain = context.getBindings("js").getMember("primesMain");
            System.out.println("warming up ...");
            for (int i = 0; i < WARMUP; i++) {
                primesMain.execute();
            }
            System.out.println("warmup finished, now measuring");
            for (int i = 0; i < ITERATIONS; i++) {
                long start = System.currentTimeMillis();
                primesMain.execute();
                took = System.currentTimeMillis() - start;
                System.out.println("iteration: " + took);
            }
        } // context.close() is automatic
        return took;
    }

    static long benchNashornScriptEngine() throws IOException {
        System.out.println("=== Nashorn via javax.script.ScriptEngine ===");
        ScriptEngine nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");
        if (nashornEngine == null) {
            System.out.println("*** Nashorn not found ***");
            return 0;
        } else {
            return benchScriptEngineIntl(nashornEngine);
        }
    }

    private static long benchGraalScriptEngine() throws IOException {
        System.out.println("=== Graal.js via javax.script.ScriptEngine ===");
        ScriptEngine graaljsEngine = new ScriptEngineManager().getEngineByName("graal.js");
        if (graaljsEngine == null) {
            System.out.println("*** Graal.js not found ***");
            return 0;
        } else {
            return benchScriptEngineIntl(graaljsEngine);
        }
    }

    private static long benchScriptEngineIntl(ScriptEngine eng) throws IOException {
        long took = 0L;
        try {
            eng.eval(SOURCE);
            Invocable inv = (Invocable) eng;
            System.out.println("warming up ...");
            for (int i = 0; i < WARMUP; i++) {
                inv.invokeFunction("primesMain");
            }
            System.out.println("warmup finished, now measuring");
            for (int i = 0; i < ITERATIONS; i++) {
                long start = System.currentTimeMillis();
                inv.invokeFunction("primesMain");
                took = System.currentTimeMillis() - start;
                System.out.println("iteration: " + (took));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return took;
    }

}
