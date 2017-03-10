package com.ericsson.scala.AnnotationDemo;

public class JavaCaller {
	public static void main(String[] args) {
		Sum t = new Sum();
		System.out.println(t.sum(1, 2, 3));

		System.out.println(new GetAllLines().getAllLineFromFile());
	}
}