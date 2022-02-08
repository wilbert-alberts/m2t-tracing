package com.asml.innovationteam.m2t.tracing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.eclipse.xtend.lib.macro.AbstractMethodProcessor;
import org.eclipse.xtend.lib.macro.CodeGenerationContext;
import org.eclipse.xtend.lib.macro.TransformationContext;
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableMethodDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableParameterDeclaration;
import org.eclipse.xtend.lib.macro.declaration.MutableTypeDeclaration;
import org.eclipse.xtend.lib.macro.expression.Expression;
import org.eclipse.xtend2.lib.StringConcatenationClient;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class CodeGenerationProcessor extends AbstractMethodProcessor {
  @Override
  public void doTransform(final MutableMethodDeclaration annotatedMethod, @Extension final TransformationContext context) {
    final String originalName = annotatedMethod.getSimpleName();
    String _simpleName = annotatedMethod.getSimpleName();
    final String newName = ("_cg_" + _simpleName);
    annotatedMethod.setSimpleName(newName);
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
          _builder.append("return \"[meta[\" + orgResult + \"]meta]\";");
          _builder.newLine();
        }
      };
      m.setBody(_client);
    };
    clz.addMethod(originalName, _function);
    System.err.println("doTransform");
    super.doTransform(annotatedMethod, context);
  }
  
  protected Iterable<Expression> _findReturnExpressions(final Expression expression) {
    List<Expression> _xblockexpression = null;
    {
      System.err.println("findReturnExpressions");
      _xblockexpression = Collections.<Expression>unmodifiableList(CollectionLiterals.<Expression>newArrayList());
    }
    return _xblockexpression;
  }
  
  protected Iterable<Expression> _findReturnExpressions(final XBlockExpression ex) {
    List<Expression> _xblockexpression = null;
    {
      System.err.println("findReturnExpressions(XBlockExpression)");
      _xblockexpression = Collections.<Expression>unmodifiableList(CollectionLiterals.<Expression>newArrayList());
    }
    return _xblockexpression;
  }
  
  @Override
  public void doGenerateCode(final MethodDeclaration annotatedMethod, @Extension final CodeGenerationContext context) {
    System.err.println("doGenerateCode");
    super.doGenerateCode(annotatedMethod, context);
  }
  
  public Iterable<Expression> findReturnExpressions(final Object ex) {
    if (ex instanceof XBlockExpression) {
      return _findReturnExpressions((XBlockExpression)ex);
    } else if (ex instanceof Expression) {
      return _findReturnExpressions((Expression)ex);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(ex).toString());
    }
  }
}
