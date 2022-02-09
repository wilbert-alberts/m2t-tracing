package com.asml.innovationteam.m2t.tracing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import org.eclipse.xtend.lib.macro.Active;

@Target(ElementType.METHOD)
@Active(EntryProcessor.class)
@SuppressWarnings("all")
public @interface GeneratorEntry {
}
