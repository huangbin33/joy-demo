package cn.joy.demo.external.smarty4j;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.statement.Modifier;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

import cn.joy.framework.kits.StringKit;

public class $empty extends Modifier{   
	  
	@Override
	public Object execute(Object var, Context context, Object[] values) {
		return StringKit.isEmpty(var);
	}

	public ParameterCharacter[] getDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

} 