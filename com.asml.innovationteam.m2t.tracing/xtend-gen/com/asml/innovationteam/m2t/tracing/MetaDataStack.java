package com.asml.innovationteam.m2t.tracing;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class MetaDataStack {
  public static MetaDataStack INSTANCE;
  
  public static MetaDataStack getInstance() {
    MetaDataStack _xblockexpression = null;
    {
      if ((MetaDataStack.INSTANCE == null)) {
        MetaDataStack _metaDataStack = new MetaDataStack();
        MetaDataStack.INSTANCE = _metaDataStack;
      }
      _xblockexpression = MetaDataStack.INSTANCE;
    }
    return _xblockexpression;
  }
  
  private List<MetaData> metaDataStack = new ArrayList<MetaData>();
  
  public void clear() {
    this.metaDataStack.clear();
  }
  
  public MetaData push(final String methodName) {
    String _string = Integer.valueOf((((Object[])Conversions.unwrapArray(this.metaDataStack, Object.class)).length)).toString();
    final MetaData md = new MetaData(_string, methodName);
    this.metaDataStack.add(md);
    return md;
  }
  
  public void pop() {
  }
  
  public void save() {
    System.err.println("Meta data stack: ");
    final Function1<MetaData, String> _function = (MetaData md) -> {
      return md.toString();
    };
    System.err.println(IterableExtensions.join(ListExtensions.<MetaData, String>map(this.metaDataStack, _function), "\n"));
  }
}
