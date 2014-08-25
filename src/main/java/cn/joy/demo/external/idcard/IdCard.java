package cn.joy.demo.external.idcard;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class IdCard {
	// 经过计算得出的指数数组，算法：２的ｎ-1次方求和，除以11取模
	// 如：2的0次方除以11取模=1,2的1次方除以11取模=2,2的2次方除以11取模=4
	static int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	// 校验位数组
	static char[] ai = { '1', '0', 'x', '9', '8', '7', '6', '5', '4', '3', '2' };

	public static void main(String args[]) {
		// IdCard ic = new IdCard();
		// System.currentTimeMillis();
		try {
			boolean flag = true;
			while (flag) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("15位身份证号:");
				String lowerid = reader.readLine();
				if (lowerid.equals("quit")) {
					System.out.println("bye~~");
					break;
				}
				System.out.println("8位出生日期(19791216):");
				String birth = reader.readLine();
				System.out.println("请输入性别");
				String sex = reader.readLine();
				System.out.println(checkIdNumber(lowerid, birth, sex));
				System.out.println("18位号：" + upperIdNumber(lowerid, birth));
				System.out.println(checkIdNumber(upperIdNumber(lowerid, birth), birth, sex));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * * 根据15位身份证号和出生日期计算得出18位身份证号 * *
	 * 
	 * @param lowerId，15位身份证号 *
	 * @param birthday出生日期，19810912 *
	 * @return upperId，返回18位身份证号
	 * 
	 */
	public static String upperIdNumber(String lowerId, String birthday) {
		if (lowerId.length() != 15) {
			return "请录入15位身份证号码。";
		} else {
			return lowerId.substring(0, 6) + birthday.substring(0, 2) + lowerId.substring(6) + ai[checkBit(lowerId, birthday)];
		}
		// return lowerId+ai[checkBit("372832198109126616")];
	}

	/**
	 * * 根据15位身份证号和出生日期，计算校验位 * *
	 * 
	 * @param lowerId，15位身份证号 *
	 * @param birthday出生日期，19810912 *
	 * @return mod，第18位校验位，用于从ai数组取数作为身份证号的最后一位，即ai[mod]
	 * 
	 */
	public static int checkBit(String lowerId, String birthday) {
		if (lowerId.length() != 15)// 请录入15位身份证号码
			return -1;
		lowerId = lowerId.substring(0, 6) + birthday.substring(0, 2) + lowerId.substring(6);
		int sum = 0;
		// 计算校验位，前 17位加权求和，然后除以11取模
		for (int i = 1; i < lowerId.length() + 1; i++) {
			sum = sum + wi[i - 1] * (Integer.parseInt(lowerId.substring(i - 1, i)));
		}
		// System.out.println("sum = " + sum);
		// 计算校验位end
		int mod = sum % 11;
		return mod;
	}

	/**
	 * * 根据传入的１８位身份证号，计算校验位 * *
	 * 
	 * @param ｉd，１８位身份证号 *
	 * @return mod，返回校验位，用于从ai数组取数作为身份证号的最后一位，即ai[mod]
	 * 
	 */
	public static int checkBit(String id) {
		String lowerId = id.substring(0, 17);
		int sum = 0;
		for (int i = 1; i < lowerId.length() + 1; i++) {
			sum = sum + wi[i - 1] * (Integer.parseInt(lowerId.substring(i - 1, i)));
		}
		int mod = sum % 11;
		return mod;
	}

	/**
	 * * 校验身份证号 * *
	 * 
	 * @param id
	 *            身份证号，包括15位和18位 *
	 * @param birthday出生日期8位(20071207) *
	 * @param sex，性别,男为奇数，女为偶数 *
	 * @return result，返回身份证号是否正确
	 * 
	 */

	public static String checkIdNumber(String id, String birthday, String sex) {
		String result = "";
		int len = id.length();
		if (len == 15) {
			// 调用15位身份证号校验方法
			result = checkId_15(id, birthday, sex);
			// System.out.println("15位---------------");
		} else if (len == 18) {
			// 调用18位身份证号校验方法
			result = checkId_18(id, birthday, sex);
			// System.out.println("18位---------------");
		} else {
			result = "false|身份证号长度错误，只能是15位或18位。";
		}
		return result;
	}

	/**
	 * * 校验15位身份证号 * *
	 * 
	 * @param id
	 *            15位身份证号 *
	 * @param birthday出生日期8位(20071207) *
	 * @param sex，性别,男为奇数，女为偶数 *
	 * @return result
	 * 
	 */

	public static String checkId_15(String id, String birthday, String sex) {
		String result = "";
		String birth_id = id.substring(6, 12);// 6位日期
		String birth = birthday.substring(2);
		if (birth_id.equals(birth)) {
			// 检验日期，出生日期与身份证中的出生日期相符，然后检验性别
			if (sex.equals("男")) {
				// 性别为男，最后一位是奇数
				String temp = id.substring(14);// 最后一位代表性别
				int isex = Integer.parseInt(temp);
				if (isex % 2 == 1) {// 除2余数为1
					result = "true|身份证号通过验证。";
				} else {
					result = "false|身份证号与性别不相符。";
				}
			} else if (sex.equals("女")) {
				// 性别为女，最后一位是偶数
				String temp = id.substring(14);// 最后一位代表性别
				int isex = Integer.parseInt(temp);
				if (isex % 2 == 0) {// 除2余数为0
					result = "true|身份证号通过验证。";
				} else {
					result = "false|身份证号与性别不相符。";
				}
			} else {
				result = "false|性别录入错误。";
			}
		} else {
			// 出生日期与身份证中的出生日期不相符
			result = "false|出生日期与身份证中的出生日期不相符。";
		}
		return result;
	}

	/**
	 * * 校验18位身份证号 * *
	 * 
	 * @param id
	 *            18位身份证号 *
	 * @param birthday出生日期8位(20071207) *
	 * @param sex，性别,男为奇数，女为偶数 *
	 * @return result
	 * 
	 */
	public static String checkId_18(String id, String birthday, String sex) {
		String result = "";
		String birth_id = id.substring(6, 14);// 8位日期
		if (birth_id.equals(birthday)) {
			// 检验日期，出生日期与身份证中的出生日期相符，然后检验性别
			if (sex.equals("男")) {
				// 性别为男，最后一位是奇数
				String temp = id.substring(16, 17);// 倒数第二位代表性别
				int isex = Integer.parseInt(temp);
				if (isex % 2 == 1) {// 除2余数为1
					result = "true|身份证号通过验证。";
				} else {
					result = "false|身份证号与性别不相符。";
				}
			} else if (sex.equals("女")) {
				// 性别为女，最后一位是偶数
				String temp = id.substring(16, 17);// 倒数第二位代表性别
				int isex = Integer.parseInt(temp);
				if (isex % 2 == 0) {// 除2余数为0
					result = "true|身份证号通过验证。";
				} else {
					result = "false|身份证号与性别不相符。";
				}
			} else {
				result = "false|性别录入错误。";
			}
		} else {
			// 出生日期与身份证中的出生日期不相符
			result = "false|出生日期与身份证中的出生日期不相符。";
		}
		return result;
	}
}
