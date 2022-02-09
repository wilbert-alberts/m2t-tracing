package com.asml.innovationteam.m2t.tracing;

import java.util.function.Consumer;
import org.eclipse.xtend.lib.macro.AbstractMethodProcessor;
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeDeclaration;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class FragmentProcessor extends AbstractMethodProcessor {
  @Override
  public void doTransform(final MutableMethodDeclaration annotatedMethod, @Extension final TransformationContext context) {
    String _qualifiedName = annotatedMethod.getDeclaringType().getQualifiedName();
    String _plus = (_qualifiedName + ".");
    String _simpleName = annotatedMethod.getSimpleName();
    final String invocationName = (_plus + _simpleName);
    final String originalName = annotatedMethod.getSimpleName();
    String _simpleName_1 = annotatedMethod.getSimpleName();
    final String newName = ("_cg_" + _simpleName_1);
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
          _builder.append("// This is the wrapper around ");
          String _simpleName = annotatedMethod.getSimpleName();
          _builder.append(_simpleName);
          _builder.newLineIfNotEmpty();
          _builder.append("com.asml.innovationteam.m2t.tracing.MetaData md = com.asml.innovationteam.m2t.tracing.MetaDataStack.getInstance().push(\"");
          _builder.append(invocationName);
          _builder.append("\");");
          _builder.newLineIfNotEmpty();
          _builder.append("String orgResult = ");
          _builder.append(newName);
          _builder.append("(");
          final Function1<MutableParameterDeclaration, String> _function = (MutableParameterDeclaration p) -> {
            return p.getSimpleName();
          };
          String _join = IterableExtensions.join(IterableExtensions.map(annotatedMethod.getParameters(), _function), ", ");
          _builder.append(_join);
          _builder.append("); ");
          _builder.newLineIfNotEmpty();
          _builder.append("return \"[\"+md.getId()+\"[\" + orgResult + \"]\"+md.getId()+\"]\";");
          _builder.newLine();
        }
      };
      m.setBody(_client);
    };
    clz.addMethod(originalName, _function);
    annotatedMethod.setSimpleName(newName);
    super.doTransform(annotatedMethod, context);
  }
  
  @Override
  public void doRegisterGlobals(final MethodDeclaration annotatedMethod, final RegisterGlobalsContext context) {
    super.doRegisterGlobals(annotatedMethod, context);
  }
}
