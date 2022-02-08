package com.asml.innovationteam.m2t.tracing.client;

import com.asml.innovationteam.m2t.tracing.CodeGeneration;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class MyGenerator {
  public String generateMain() {
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
  
  @CodeGeneration
  public String _cg_generateFunctionName(final int i) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("Foo_");
    _builder.append(i);
    return _builder.toString();
  }
  
  @CodeGeneration
  public String _cg_generateBody() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// This is Foo\'s body");
    _builder.newLine();
    _builder.append("skip();");
    _builder.newLine();
    _builder.append("return;");
    _builder.newLine();
    return _builder.toString();
  }
  
  public String generateFunctionName(final int i) {
    // This is the wrapper around _cg_generateFunctionName
    String orgResult = _cg_generateFunctionName(i); 
    return "[meta[" + orgResult + "]meta]";
  }
  
  public String generateBody() {
    // This is the wrapper around _cg_generateBody
    String orgResult = _cg_generateBody(); 
    return "[meta[" + orgResult + "]meta]";
  }
}
