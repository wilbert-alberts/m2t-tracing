package com.asml.innovationteam.m2t.tracing.client

import com.asml.innovationteam.m2t.tracing.GeneratedFragment
import com.asml.innovationteam.m2t.tracing.GeneratorEntry

class MyGenerator {

	@GeneratorEntry
	def String generateMain() {
		val result = '''
			void «generateFunctionName(42)» {
				«generateBody()»
			}
		'''
		return result
	}

	@GeneratedFragment
	def String generateFunctionName(int i) {
		'''Foo_«i»'''
	}

	@GeneratedFragment
	def String generateBody() {
		'''
			// This is «generateFunctionName(43)»'s body
			skip();
			return;
		'''
	}
}
