package com.mycompany.app;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println(Context.newBuilder("js").build().eval("js", "var g = 0; "
				+ "function foo(){ "
				+ "  for (i = 0; i < 500000; i++) {"
				+ "    g = g+i;"
				+ "  }"
				+ "}"
				+ "for (j = 0; j < 1000; j++) { "
				+ "  foo();"
				+ "} "
				+ "g"));
		System.out.println("Hello World!");
	}
	
	public static Value test(String[] args) {
		return Context.newBuilder("js").build().eval("js", "42");
	}
}
