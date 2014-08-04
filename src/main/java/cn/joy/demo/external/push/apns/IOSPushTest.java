package cn.joy.demo.external.push.apns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javapns.Push;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;
import cn.joy.framework.kits.JsonKit;

public class IOSPushTest {
	private static int badge = 1;
	private final static String PASSWORD = "";
	private final static String CERTIFICATE_PATH = IOSPushTest.class
			.getResource("/").getPath()
			+ "/cn/joy/demo/external/push/apns/push_cer_product.p12";
	/**
	 * 
	 * 
	 * @param tokens
	 *            iphone手机获取的token
	 * @param path
	 *            这里是一个.p12格式的文件路径，需要去apple官网申请一个
	 * @param password
	 *            p12的密码 此处注意导出的证书密码不能为空因为空密码会报错
	 * @param message
	 *            推送消息的内容
	 * @param count
	 *            应用图标上小红圈上的数值
	 * @param sendCount
	 *            单发还是群发 true：单发 false：群发
	 */
	public static void sendpush(List<String> tokens, String path,
			String password, String message, Integer count, boolean sendCount) {

		try {

			PushNotificationPayload payLoad = PushNotificationPayload
					.fromJSON(message);

			// payLoad.addAlert(message); // 消息内容

			payLoad.addBadge(count); // iphone应用图标上小红圈上的数值

			payLoad.addSound("default"); // 铃音 默认

			PushNotificationManager pushManager = new PushNotificationManager();

			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务

			pushManager
					.initializeConnection(new AppleNotificationServerBasicImpl(
							path, password, true));

			List<PushedNotification> notifications = new ArrayList<PushedNotification>();

			// 发送push消息

			if (sendCount) {

				System.out.println("--------------------------apple 推送 单-------");

				Device device = new BasicDevice();

				device.setToken(tokens.get(0));

				PushedNotification notification = pushManager.sendNotification(
						device, payLoad, true);

				notifications.add(notification);

			} else {

				System.out.println("--------------------------apple 推送 群-------");

				List<Device> device = new ArrayList<Device>();

				for (String token : tokens) {

					device.add(new BasicDevice(token));

				}

				notifications = pushManager.sendNotifications(payLoad, device);

			}

			List<PushedNotification> failedNotifications = PushedNotification
					.findFailedNotifications(notifications);

			List<PushedNotification> successfulNotifications = PushedNotification
					.findSuccessfulNotifications(notifications);

			int failed = failedNotifications.size();

			int successful = successfulNotifications.size();

			if (successful > 0 && failed == 0) {

				System.out.println("-----All notifications pushed 成功 ("
						+ successfulNotifications.size() + "):");

			} else if (successful == 0 && failed > 0) {

				System.out.println("-----All notifications 失败 ("
						+ failedNotifications.size() + "):");

			} else if (successful == 0 && failed == 0) {

				System.out.println("No notifications could be sent, probably because of a critical error");

			} else {

				System.out.println("------Some notifications 失败 ("
						+ failedNotifications.size() + "):");

				System.out.println("------Others 成功 ("
						+ successfulNotifications.size() + "):");

			}

			// pushManager.stopConnection();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
	
	public static void main(String[] args) throws Exception{
		Map msgInfo = new HashMap();
		msgInfo.put("alert", "312");
		
		Map apns = new HashMap();
		//apns.put("aps", msgInfo);
		
		Map m = new HashMap();
		m.put("type", "1");
		m.put("alert", msgInfo.get("alert"));
		apns.put("aps", m);
			
		String message = JsonKit.object2Json(apns);
		message = "{\"aps\":{\"type\":\"1\",\"alert\":\"CU42 发来消息\"}}";
		List<String> tokens = new ArrayList();
		tokens.add("e8fd8ad066d154e3605f45b090845dce6a7283dbe412554016295b9f054f2ca5");
		sendpush(tokens, CERTIFICATE_PATH, PASSWORD, message, badge, false);
		//PushedNotifications pns = Push.combined(message, badge, "default", CERTIFICATE_PATH, PASSWORD, true, "e8fd8ad066d154e3605f45b090845dce6a7283dbe412554016295b9f054f2ca5");
		
	}
}
