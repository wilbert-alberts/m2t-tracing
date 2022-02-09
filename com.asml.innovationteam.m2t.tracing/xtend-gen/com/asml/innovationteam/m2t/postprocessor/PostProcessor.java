package com.asml.innovationteam.m2t.postprocessor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PostProcessor {
  public static class MetaLocation {
    public MetaLocation(final int k) {
      this.key = k;
    }
    
    @Accessors(AccessorType.PUBLIC_GETTER)
    private int key;
    
    @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
    private int startLine;
    
    @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
    private int startCol;
    
    @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
    private int endLine;
    
    @Accessors({ AccessorType.PUBLIC_GETTER, AccessorType.PUBLIC_SETTER })
    private int endCol;
    
    @Pure
    public int getKey() {
      return this.key;
    }
    
    @Pure
    public int getStartLine() {
      return this.startLine;
    }
    
    public void setStartLine(final int startLine) {
      this.startLine = startLine;
    }
    
    @Pure
    public int getStartCol() {
      return this.startCol;
    }
    
    public void setStartCol(final int startCol) {
      this.startCol = startCol;
    }
    
    @Pure
    public int getEndLine() {
      return this.endLine;
    }
    
    public void setEndLine(final int endLine) {
      this.endLine = endLine;
    }
    
    @Pure
    public int getEndCol() {
      return this.endCol;
    }
    
    public void setEndCol(final int endCol) {
      this.endCol = endCol;
    }
  }
  
  private Map<Integer, PostProcessor.MetaLocation> metaData = new HashMap<Integer, PostProcessor.MetaLocation>();
  
  private static Pattern metaBeginRx = Pattern.compile("(\\[(\\d+)\\[)(.*)");
  
  private static Pattern metaEndRx = Pattern.compile("(\\](\\d+)\\])(.*)");
  
  private static Pattern eolRx = Pattern.compile("(\\n\\r|\\r\\n|\\r|\\n)");
  
  private static Pattern lineRx = Pattern.compile((("([^\\n\\r]*)" + PostProcessor.eolRx) + "?(.*)"), 
    (Pattern.MULTILINE | Pattern.DOTALL));
  
  public String extractEssentialText(final String src) {
    String _xblockexpression = null;
    {
      String buffer = src;
      int lineCounter = 1;
      final StringBuffer result = new StringBuffer();
      while ((buffer.length() > 0)) {
        {
          final Matcher m = PostProcessor.lineRx.matcher(buffer);
          boolean _matches = m.matches();
          if (_matches) {
            final String line = m.group(1);
            final String eol = m.group(2);
            final String rest = m.group(3);
            final String extractedLine = this.extractEssentialTextFromLine(line, lineCounter);
            result.append(extractedLine).append(eol);
            buffer = rest;
          } else {
            result.append(buffer);
            buffer = "";
          }
          lineCounter++;
        }
      }
      _xblockexpression = result.toString();
    }
    return _xblockexpression;
  }
  
  public String extractEssentialTextFromLine(final String line, final int lineNr) {
    String _xblockexpression = null;
    {
      String buffer = line;
      StringBuffer result = new StringBuffer();
      int colNr = 0;
      while ((buffer.length() > 0)) {
        boolean _matched = false;
        boolean _handleOpenMeta = this.handleOpenMeta(buffer);
        if (_handleOpenMeta) {
          _matched=true;
          final int openBrackPos = buffer.indexOf("[");
          result.append(buffer.subSequence(0, openBrackPos));
          buffer = buffer.substring(openBrackPos);
          int _colNr = colNr;
          colNr = (_colNr + openBrackPos);
          final Matcher m = PostProcessor.metaBeginRx.matcher(buffer);
          boolean _matches = m.matches();
          if (_matches) {
            final String meta = m.group(1);
            final int metaIdx = Integer.parseInt(m.group(2));
            final String restOfLine = m.group(3);
            final PostProcessor.MetaLocation md = new PostProcessor.MetaLocation(metaIdx);
            md.startLine = lineNr;
            md.startCol = colNr;
            this.metaData.put(Integer.valueOf(metaIdx), md);
            buffer = restOfLine;
          } else {
            result.append("[");
            buffer = buffer.substring(1);
            int _colNr_1 = colNr;
            colNr = (_colNr_1 + 1);
          }
        }
        if (!_matched) {
          boolean _handleCloseMeta = this.handleCloseMeta(buffer);
          if (_handleCloseMeta) {
            _matched=true;
            final int closeBrackPos = buffer.indexOf("]");
            result.append(buffer.subSequence(0, closeBrackPos));
            buffer = buffer.substring(closeBrackPos);
            int _colNr_2 = colNr;
            colNr = (_colNr_2 + closeBrackPos);
            final Matcher m_1 = PostProcessor.metaEndRx.matcher(buffer);
            boolean _matches_1 = m_1.matches();
            if (_matches_1) {
              final String meta_1 = m_1.group(1);
              final int metaIdx_1 = Integer.parseInt(m_1.group(2));
              final String restOfLine_1 = m_1.group(3);
              final PostProcessor.MetaLocation md_1 = this.metaData.get(Integer.valueOf(metaIdx_1));
              md_1.endLine = lineNr;
              md_1.endCol = colNr;
              buffer = restOfLine_1;
            } else {
              result.append("]");
              buffer = buffer.substring(1);
              int _colNr_3 = colNr;
              colNr = (_colNr_3 + 1);
            }
          }
        }
        if (!_matched) {
          {
            result.append(buffer);
            buffer = "";
          }
        }
      }
      _xblockexpression = result.toString();
    }
    return _xblockexpression;
  }
  
  public boolean handleOpenMeta(final String buffer) {
    final int openBrackPos = buffer.indexOf("[");
    final int closeBrackPos = buffer.indexOf("]");
    return ((openBrackPos >= 0) && ((closeBrackPos < 0) || (closeBrackPos > openBrackPos)));
  }
  
  public boolean handleCloseMeta(final String buffer) {
    final int openBrackPos = buffer.indexOf("[");
    final int closeBrackPos = buffer.indexOf("]");
    return ((closeBrackPos >= 0) && ((openBrackPos < 0) || (closeBrackPos < openBrackPos)));
  }
  
  public String metaAsString() {
    final Function1<Map.Entry<Integer, PostProcessor.MetaLocation>, Integer> _function = (Map.Entry<Integer, PostProcessor.MetaLocation> mde) -> {
      return mde.getKey();
    };
    final Function1<Map.Entry<Integer, PostProcessor.MetaLocation>, PostProcessor.MetaLocation> _function_1 = (Map.Entry<Integer, PostProcessor.MetaLocation> mde) -> {
      return mde.getValue();
    };
    final Function1<PostProcessor.MetaLocation, String> _function_2 = (PostProcessor.MetaLocation md) -> {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("<");
      _builder.append(md.key);
      _builder.append(", ");
      _builder.append(md.startLine);
      _builder.append(", ");
      _builder.append(md.startCol);
      _builder.append(", ");
      _builder.append(md.endLine);
      _builder.append(", ");
      _builder.append(md.endCol);
      _builder.append(">");
      return _builder.toString();
    };
    return IterableExtensions.join(ListExtensions.<PostProcessor.MetaLocation, String>map(ListExtensions.<Map.Entry<Integer, PostProcessor.MetaLocation>, PostProcessor.MetaLocation>map(IterableExtensions.<Map.Entry<Integer, PostProcessor.MetaLocation>, Integer>sortBy(this.metaData.entrySet(), _function), _function_1), _function_2), "\n");
  }
  
  public static void main(final String[] args) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("void [0[Foo_42]0] {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("[1[// This is [2[Foo_43]2]\'s body");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("skip();");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("return;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("]1]");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    final String testString = _builder.toString();
    final PostProcessor pp = new PostProcessor();
    final String cleanString = pp.extractEssentialText(testString);
    System.out.println(cleanString);
    String _metaAsString = pp.metaAsString();
    String _plus = (_metaAsString + "\n");
    System.err.println(_plus);
    System.err.println(testString);
  }
}
