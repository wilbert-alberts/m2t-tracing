package com.asml.innovationteam.m2t.tracing.client

import com.asml.innovationteam.m2t.tracing.CodeGeneration

class MyGenerator {

	def String generateMain() {
		val result = '''
			void «generateFunctionName(42)» {
				«generateBody()»
			}
		'''
		return result
	}

	@CodeGeneration
	def String generateFunctionName(int i) {
		'''Foo_«i»'''
	}

	@CodeGeneration
	def String generateBody() {
		'''
			// This is Foo's body
			skip();
			return;
		'''
	}
}
