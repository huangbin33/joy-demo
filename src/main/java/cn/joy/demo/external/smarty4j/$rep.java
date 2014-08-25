package cn.joy.demo.external.smarty4j;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.expression.StringExpression;
import org.lilystudio.smarty4j.statement.Modifier;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

import cn.joy.framework.kits.StringKit;

public class $rep extends Modifier{   
	private static ParameterCharacter[] definitions = { new ParameterCharacter(8, new StringExpression("")),new ParameterCharacter(8, new StringExpression(""))};
	  
	@Override
	public Object execute(Object var, Context context, Object[] values) {
		if(var!=null&&values!=null&&values.length==2)
			return StringKit.getString(var).replace(StringKit.getString(values[0]), StringKit.getString(StringKit.getString(values[1])));
		return "";
	}

	public ParameterCharacter[] getDefinitions() {
		return definitions;
	}

} 