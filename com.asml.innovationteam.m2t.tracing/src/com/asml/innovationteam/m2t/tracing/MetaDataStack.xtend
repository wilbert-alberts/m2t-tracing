package com.asml.innovationteam.m2t.tracing

import java.util.ArrayList
import java.util.List

class MetaDataStack {
	public static MetaDataStack INSTANCE

	static def MetaDataStack getInstance() {
		if (INSTANCE === null)
			INSTANCE = new MetaDataStack
		INSTANCE
	}


	List<MetaData> metaDataStack = new ArrayList

	def void clear() {
		metaDataStack.clear
	}

	def MetaData push(String methodName) {
		val md = new MetaData(metaDataStack.length.toString, methodName)
		metaDataStack.add(md)
		return md
	}

	def void pop() {
//		val result= metaDataStack.head		
	}
	
	def void save() {
		System.err.println("Meta data stack: ")
		System.err.println(metaDataStack.map[md| md.toString].join('\n'))
	}
}
