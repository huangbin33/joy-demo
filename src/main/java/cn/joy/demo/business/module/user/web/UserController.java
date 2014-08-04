package cn.joy.demo.business.module.user.web;

import com.jfinal.core.Controller;

public class UserController extends Controller {
	public void index() {
		renderText("Hello User.");
	}
}
