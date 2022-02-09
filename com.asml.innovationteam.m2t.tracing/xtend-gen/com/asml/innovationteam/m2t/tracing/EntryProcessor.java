package com.asml.innovationteam.m2t.tracing;

import java.util.function.Consumer;
import org.eclipse.xtend.lib.macro.AbstractMethodProcessor;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeDeclaration;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class EntryProcessor extends AbstractMethodProcessor {
  @Override
  public void doTransform(final MutableMethodDeclaration annotatedMethod, @Extension final TransformationContext context) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("// This is the before part");
    _builder.newLine();
    _builder.append("com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().clear();");
    _builder.newLine();
    final String before = _builder.toString();
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("// This is the after part");
    _builder_1.newLine();
    _builder_1.append("com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().save();");
    _builder_1.newLine();
    final String after = _builder_1.toString();
    this.wrapMethod(before, annotatedMethod, after);
    super.doTransform(annotatedMethod, context);
  }
  
  protected void wrapMethod(final String before, final MutableMethodDeclaration annotatedMethod, final String after) {
    final String oldName = annotatedMethod.getSimpleName();
    final MutableTypeDeclaration clz = annotatedMethod.getDeclaringType();
    final Procedure1<MutableMethodDeclaration> _function = (MutableMethodDeclaration m) -> {
      m.setReturnType(annotatedMethod.getReturnType());
      final Consumer<MutableParameterDeclaration> _function_1 = (MutableParameterDeclaration p) -> {
        m.addParameter(p.getSimpleName(), p.getType());
      };
      annotatedMethod.getParameters().forEach(_function_1);
      StringConcatenationClient _client = new StringConcatenationClient() {
        @Override
        protected void appendTo(StringConcatenationClient.TargetStringConcatenation _builder) {
          _builder.append(before);
          _builder.newLineIfNotEmpty();
          String _name = m.getReturnType().getName();
          _builder.append(_name);
          _builder.append(" result = _cg_");
          _builder.append(oldName);
          _builder.append("(");
          final Function1<MutableParameterDeclaration, String> _function = (MutableParameterDeclaration p) -> {
            return p.getSimpleName();
          };
          String _join = IterableExtensions.join(IterableExtensions.map(annotatedMethod.getParameters(), _function), ", ");
          _builder.append(_join);
          _builder.append(");");
          _builder.newLineIfNotEmpty();
          _builder.append(after);
          _builder.newLineIfNotEmpty();
          _builder.append("return result;");
          _builder.newLine();
        }
      };
      m.setBody(_client);
    };
    clz.addMethod(annotatedMethod.getSimpleName(), _function);
    String _simpleName = annotatedMethod.getSimpleName();
    final String newName = ("_cg_" + _simpleName);
    annotatedMethod.setSimpleName(newName);
  }
}
