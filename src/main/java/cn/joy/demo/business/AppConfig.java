package cn.joy.demo.business;

import cn.joy.demo.business.module.user.web.UserController;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.render.ViewType;

public class AppConfig extends JFinalConfig {
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.JSP);
	}

	public void configRoute(Routes me) {
		me.add("/hello", UserController.class);
	}

	public void configPlugin(Plugins me) {
		/*
		 * C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost/db_name",
		 * "userName", "password"); me.add(cp); ActiveRecordPlugin arp = new
		 * ActiveRecordPlugin(cp); me.add(arp); arp.addMapping("user",
		 * User.class);
		 */
		// arp.addMapping("article", "article_id", Article.class);
	}

	public void configInterceptor(Interceptors me) {
	}

	public void configHandler(Handlers me) {
	}

	public void afterJFinalStart() {
		System.out.println("Start!");
	}

}