package cn.joy.demo.external.smarty4j;

import org.lilystudio.smarty4j.Context;
import org.lilystudio.smarty4j.expression.StringExpression;
import org.lilystudio.smarty4j.statement.Modifier;
import org.lilystudio.smarty4j.statement.ParameterCharacter;

import cn.joy.framework.kits.NumberKit;
import cn.joy.framework.kits.StringKit;

public class $util extends Modifier{   
	private static ParameterCharacter[] definitions = { new ParameterCharacter(8, new StringExpression("")),new ParameterCharacter(8, new StringExpression(""))};
	 
	@Override
	public Object execute(Object var, Context context, Object[] values) {
		String method = "";
		if(values!=null&&values.length>0)
			method = StringKit.getString( values[0] );
		if("".equals(method))
			return null;
		else if("getString".equals(method))
			return StringKit.getString(var);
		else if("isNull".equals(method))
			return var==null;
		else if("isNumber".equals(method))
			return NumberKit.isNumber(StringKit.getString(var));
		else if("noWrap".equals(method))
			return StringKit.getString(var).replace(System.getProperty("line.separator"), "");
		return null;
	}

	public ParameterCharacter[] getDefinitions() {
		return definitions;
	}

} 