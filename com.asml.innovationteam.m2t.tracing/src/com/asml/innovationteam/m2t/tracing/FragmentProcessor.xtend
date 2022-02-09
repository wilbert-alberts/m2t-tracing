package com.asml.innovationteam.m2t.tracing

import org.eclipse.xtend.lib.macro.AbstractMethodProcessor
import org.eclipse.xtend.lib.macro.CodeGenerationContext
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration
import org.eclipse.xtend.lib.macro.expression.Expression
import org.eclipse.xtext.xbase.XBlockExpression
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext

class FragmentProcessor extends AbstractMethodProcessor {

	override doTransform(MutableMethodDeclaration annotatedMethod, extension TransformationContext context) {
		val invocationName = annotatedMethod.declaringType.qualifiedName+'.'+annotatedMethod.simpleName
		val originalName = annotatedMethod.simpleName
		val newName = '_cg_' + annotatedMethod.simpleName

		val clz = annotatedMethod.declaringType

		clz.addMethod(originalName, [ m |
			m.returnType = annotatedMethod.returnType
			annotatedMethod.parameters.forEach [ p |
				m.addParameter(p.simpleName, p.type)
			]
			m.body = '''
				// This is the wrapper around «annotatedMethod.simpleName»
				com.asml.innovationteam.m2t.tracing.MetaData md = com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().push("«invocationName»");
				String orgResult = «newName»(«annotatedMethod.parameters.map[p|p.simpleName].join(', ')»); 
				return "["+md.getId()+"[" + orgResult + "]"+md.getId()+"]";
			'''
		])
		annotatedMethod.simpleName = newName

		super.doTransform(annotatedMethod, context)
	}
	
	override doRegisterGlobals(MethodDeclaration annotatedMethod, RegisterGlobalsContext context) {
		super.doRegisterGlobals(annotatedMethod, context)
	}
	
}
