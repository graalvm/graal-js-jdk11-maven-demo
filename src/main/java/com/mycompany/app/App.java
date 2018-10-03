package com.mycompany.app;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {
        System.out.println("Hello Java!");
        Context context = Context.create();
        context.eval("js", "print('Hello JavaScript!');");
	}

	public static Value get42() {
            return Context.create().eval("js", "42");
	}
}
