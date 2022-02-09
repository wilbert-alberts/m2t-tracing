package com.asml.innovationteam.m2t.tracing.client;

import com.asml.innovationteam.m2t.tracing.GeneratedFragment;
import com.asml.innovationteam.m2t.tracing.GeneratorEntry;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class MyGenerator {
  @GeneratorEntry
  public String _cg_generateMain() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void ");
    String _generateFunctionName = this.generateFunctionName(42);
    _builder.append(_generateFunctionName);
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    String _generateBody = this.generateBody();
    _builder.append(_generateBody, "\t");
    _builder.newLineIfNotEmpty();
    _builder.append("}");
    _builder.newLine();
    final String result = _builder.toString();
    return result;
  }
  
  @GeneratedFragment
  public String _cg_generateFunctionName(final int i) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Foo_");
    _builder.append(i);
    return _builder.toString();
  }
  
  @GeneratedFragment
  public String _cg_generateBody() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// This is ");
    String _generateFunctionName = this.generateFunctionName(43);
    _builder.append(_generateFunctionName);
    _builder.append("\'s body");
    _builder.newLineIfNotEmpty();
    _builder.append("skip();");
    _builder.newLine();
    _builder.append("return;");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateMain() {
    // This is the before part
    com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().clear();
    java.lang.String result = _cg_generateMain();
    // This is the after part
    com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().save();
    return result;
  }
  
  public String generateFunctionName(final int i) {
    // This is the wrapper around _cg_generateFunctionName
    com.asml.innovationteam.m2t.tracing.MetaData md = com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().push("com.asml.innovationteam.m2t.tracing.client.MyGenerator.generateFunctionName");
    String orgResult = _cg_generateFunctionName(i); 
    return "["+md.getId()+"[" + orgResult + "]"+md.getId()+"]";
  }
  
  public String generateBody() {
    // This is the wrapper around _cg_generateBody
    com.asml.innovationteam.m2t.tracing.MetaData md = com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().push("com.asml.innovationteam.m2t.tracing.client.MyGenerator.generateBody");
    String orgResult = _cg_generateBody(); 
    return "["+md.getId()+"[" + orgResult + "]"+md.getId()+"]";
  }
}
