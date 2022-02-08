package com.asml.innovationteam.m2t.tracing

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target

@Target(METHOD)
@Active(CodeGenerationProcessor)
annotation CodeGeneration {
	
}