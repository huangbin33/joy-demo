package cn.joy.demo.external.smarty4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

public class $length extends org.lilystudio.smarty4j.statement.Modifier{   
	  
	@Override
	public Object execute(Object var, Context context, Object[] values) {
		System.out.println(values);
		if(var!=null){
			if (var instanceof Object[])
		      return ((Object[])var).length;
		    if (var instanceof List)
		      return ((List)var).size();
		    if (var instanceof Map)
		      return ((Map)var).size();
		    if (var instanceof byte[]) {
		      return ((byte[])var).length;
		    }
		}
		return 0;
	}

	public ParameterCharacter[] getDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

} 