package com.asml.innovationteam.m2t.tracing

import org.eclipse.xtend.lib.macro.AbstractMethodProcessor
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration
import org.eclipse.xtend.lib.macro.expression.Expression
import org.eclipse.xtext.xbase.XBlockExpression

class CodeGenerationProcessor extends AbstractMethodProcessor {

	override doTransform(MutableMethodDeclaration annotatedMethod, extension TransformationContext context) {
		val originalName = annotatedMethod.simpleName
		val newName = '_cg_' + annotatedMethod.simpleName
		annotatedMethod.simpleName = newName
		val clz = annotatedMethod.declaringType
		clz.addMethod(originalName, [ m |
			m.returnType = annotatedMethod.returnType
			annotatedMethod.parameters.forEach [ p |
				m.addParameter(p.simpleName, p.type)
			]
			m.body = '''
				// This is the wrapper around «annotatedMethod.simpleName»
				String orgResult = «newName»(«annotatedMethod.parameters.map[p|p.simpleName].join(', ')»); 
				return "[meta[" + orgResult + "]meta]";
			'''
		])
		System.err.println("doTransform")
		super.doTransform(annotatedMethod, context)
	}
}
