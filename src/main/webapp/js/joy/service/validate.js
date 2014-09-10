/**
 * Created by liyy on 2014/8/26.
 */
joy.validator = joy.service("validate", function() {
	var regExs = {
		"ASCII" : /^[\x00-\xFF]+$/, // ASCII
		"ALPHA_DIGIT" : /^[a-zA-Z0-9]+$/, //字母或数字
		"CHINESE" : /^\u4E00-\u9FA5\uF900-\uFA2D$/, // 中文
		"INTEGER" : /^(-?[1-9]\d*|0)$/, // 整数
		"POSITIVE_INTEGER" : /^[1-9]\d*$/, // 正整数
		"NEGATIVE_INTEGER" : /^-[1-9]\d*$/, // 负整数
		"UNPOSITIVE_INTEGER" : /^(-[1-9]\d*|0)$/, // 非正整数
		"UNNEGATIVE_INTEGER" : /^([1-9]\d*|0)$/, // 非负整数
		"FLOAT" : /^-?([1-9]\d*|0)\.\d+$/, // 浮点数
		"POSITIVE_FLOAT" : /^([1-9]\d*\.\d+|0\.\d*[1-9]\d*)$/, // 正浮点数
		"NEGATIVE_FLOAT" : /^-([1-9]\d*\.\d+|0\.\d*[1-9]\d*)$/, // 负浮点数
		"UNPOSITIVE_FLOAT" : /^(-([1-9]\d*\.\d+|0\.\d*[1-9]\d*)|0\.0+)$/, // 非正浮点数
		"UNNEGATIVE_FLOAT" : /^([1-9]\d*\.\d+|0\.\d*[1-9]\d*|0\.0+)$/, // 非负浮点数
		"DOUBLE" : /^-?([1-9]\d*|0)(\.\d+)?([Ee][+-]?\d+)?$/, // 实数
		"NUMBER" : /^-?(?:\d+|\d{1,3}(?:,\d{3})+)?(?:\.\d+)?$/, // 数字，支持千分号
		"EMAIL" : /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/, // 电子邮箱
		"SIMPLE_EMAIL" : /^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i, // 电子邮箱
		"COMPLEX_EMAIL" : /^[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-zA-Z0-9!#$%&'*+\/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-]*[a-zA-Z0-9])?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$/, // 电子邮箱
		"URL" : /(https?|s?ftp):\/\/([\w-]+\.)+[\w-]+([\w-.\/?%&=]*)?$/, // 网址
		"SIMPLE_URL" : /^[a-zA-z]+:\/\/[^\s]*$/, // 网址
		"COMPLEX_URL" : /^(https?|s?ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i, // 网址
		"QQ" : /^[1-9]\d{4,9}$/, // QQ
		"IDCARD" : /^(\d{15}|\d{17}([0-9]|X))$/i, // 身份证
		"COMPLEX_IDCARD" : /^(([1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3})|([1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)))$/, // 身份证
		"PHONE" : /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/, // 电话
		"MOBILE" : /^(1)[0-9]{10}$/, // 手机
		"DATE" : /^((\d{4}|\d{2})(\-|\/|\.)\d{1,2}\3\d{1,2})|(\d{4}年\d{1,2}月\d{1,2}日)$/, // 日期
		"COMPLEX_DATE" : /((^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(10|12|0?[13578])([-\/\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(11|0?[469])([-\/\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\d{2})|([2-9]\d{3}))([-\/\._])(0?2)([-\/\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([3579][26]00)([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][0][48])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][2468][048])([-\/\._])(0?2)([-\/\._])(29)$)|(^([1][89][13579][26])([-\/\._])(0?2)([-\/\._])(29)$)|(^([2-9][0-9][13579][26])([-\/\._])(0?2)([-\/\._])(29)$))/, // 日期，判断闰年  yyyy-mm-dd,yyyy/mm/dd
		"CHINESE_DATE" : /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/, // 中文格式日期
		"TIME" : /^(?:(?:(?:(?:(?:(?:1[6-9]|[2-9]\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00)))(\/|-)(?:0?2\1(?:29)))|(?:(?:(?:1[6-9]|[2-9]\d)?\d{2})(\/|-)(?:(?:(?:0?[13578]|1[02])\2(?:31))|(?:(?:0?[1,3-9]|1[0-2])\2(29|30))|(?:(?:0?[1-9])|(?:1[0-2]))\2(?:0?[1-9]|1\d|2[0-8])))))\s(?:([0-1]\d|2[0-3]):[0-5]\d:[0-5]\d)$/, // 时间
		"ZIP" : /^[1-9]\d{5}$/, // 邮编
		"IPV4" : /^(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)\.(25[0-5]|2[0-4]\d|[0-1]\d{2}|[1-9]?\d)$/		//IPV4
	};
	return {
		isMatch : function(val, type) {
			var regEx = regExs[type];
			return !!regEx ? regEx.test(val) : false;
		},
		isChinese : function(val) {
			return regExs["CHINESE"].test(val);
		},
		isEmail : function(val) {
			return regExs["EMAIL"].test(val);
		},
		isIDCard : function(val) {
			return regExs["IDCARD"].test(val);
		},
		isStrictIDCard : function(val) {
			if(!regExs["IDCARD"].test(val))
				return false;
			//
			
			return true;
		},
		isMobile : function(val) {
			return regExs["MOBILE"].test(val);
		},
		isPhone : function(val) {
			return regExs["PHONE"].test(val);
		},
		isNickName : function(val, allowChinese, rangeLength){
			
		}
	}
});