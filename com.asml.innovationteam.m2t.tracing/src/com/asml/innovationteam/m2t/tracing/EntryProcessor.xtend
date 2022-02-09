package com.asml.innovationteam.m2t.tracing

import org.eclipse.xtend.lib.macro.AbstractMethodProcessor
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration

class EntryProcessor extends AbstractMethodProcessor {

	override doTransform(MutableMethodDeclaration annotatedMethod, extension TransformationContext context) {
		val before = '''
			// This is the before part
			com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().clear();
		'''
		val after = '''
			// This is the after part
			com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().save();
		'''
		wrapMethod(before, annotatedMethod, after)		
		super.doTransform(annotatedMethod, context)
	}
	
	protected def void wrapMethod(String before, MutableMethodDeclaration annotatedMethod, String after) {
		val oldName = annotatedMethod.simpleName
		val clz = annotatedMethod.declaringType
		clz.addMethod(annotatedMethod.simpleName, [m|
			m.returnType = annotatedMethod.returnType
			annotatedMethod.parameters.forEach[p|m.addParameter(p.simpleName, p.type)]
			m.body = '''
			«before»
			«m.returnType.name» result = _cg_«oldName»(«annotatedMethod.parameters.map[p|p.simpleName].join(', ')»);
			«after»
			return result;
			'''
		])
		val newName = '_cg_' + annotatedMethod.simpleName
		annotatedMethod.simpleName = newName
	}
	
	
}
