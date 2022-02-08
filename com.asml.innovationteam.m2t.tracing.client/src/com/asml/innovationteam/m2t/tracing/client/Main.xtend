package com.asml.innovationteam.m2t.tracing.client

class Main {
	def static void main(String[] args) {
		val mg = new MyGenerator
		val r = mg.generateMain
		System.out.println(r)
	}
}