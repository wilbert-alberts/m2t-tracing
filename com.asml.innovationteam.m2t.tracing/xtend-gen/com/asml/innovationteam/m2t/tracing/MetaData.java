package com.asml.innovationteam.m2t.tracing;

import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class MetaData {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private String methodName;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private String id;
  
  public MetaData(final String id, final String mName) {
    this.id = id;
    this.methodName = mName;
  }
  
  @Override
  public String toString() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<");
    _builder.append(this.id);
    _builder.append(", ");
    _builder.append(this.methodName);
    _builder.append(">");
    return _builder.toString();
  }
  
  @Pure
  public String getMethodName() {
    return this.methodName;
  }
  
  @Pure
  public String getId() {
    return this.id;
  }
}
