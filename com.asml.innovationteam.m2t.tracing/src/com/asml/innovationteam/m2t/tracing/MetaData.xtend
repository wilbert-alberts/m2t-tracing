package com.asml.innovationteam.m2t.tracing

import org.eclipse.xtend.lib.annotations.Accessors

class MetaData {	
	@Accessors(PUBLIC_GETTER) String methodName
	@Accessors(PUBLIC_GETTER) String id;
	
	new (String id, String mName) {
		this.id = id;
		methodName = mName;
	}
	
	override String toString() {
		'''<«id», «methodName»>'''
	} 
}