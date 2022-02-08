package com.asml.innovationteam.m2t.tracing.client;

@SuppressWarnings("all")
public class Main {
  public static void main(final String[] args) {
    final MyGenerator mg = new MyGenerator();
    final String r = mg.generateMain();
    System.out.println(r);
  }
}
