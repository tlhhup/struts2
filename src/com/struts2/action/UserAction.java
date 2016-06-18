package com.struts2.action;

import com.opensymphony.xwork2.ActionContext;
import com.struts2.entity.User;

public class UserAction extends BaseAction<User> {

	private static final long serialVersionUID = 1L;

	public String login(){
		ActionContext context = ActionContext.getContext();
		context.getApplication().put("servletContent", "我是Servletcontent中的数据");
		context.put("request", "我是request中的数据");
		context.getSession().put("session", "我是session中的数据");
		System.out.println("UserAction--->login");
		System.out.println(this.model);
		return "main";
	}
	
	public void validateLogin() {
		if(!this.model.getUserName().equals("admin")){
			this.addFieldError("nameError", "用户名错误");
		}
	}
	
}
