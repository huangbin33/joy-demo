package cn.joy.demo.external.smarty4j;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.expression.StringExpression;
import org.lilystudio.smarty4j.statement.Modifier;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

import cn.joy.framework.kits.StringKit;

public class $cond extends Modifier{   
	private static ParameterCharacter[] definitions = { new ParameterCharacter(8, new StringExpression("")),new ParameterCharacter(8, new StringExpression(""))};
	
	@Override
	public Object execute(Object var, Context context, Object[] values) {
		System.out.println(values);
		if(values!=null&&values.length==2){
			return "true".equals(var)?StringKit.getString(values[0]):StringKit.getString(values[1]);
		}
		return "";
	}

	public ParameterCharacter[] getDefinitions() {
		return definitions;
	}

} 