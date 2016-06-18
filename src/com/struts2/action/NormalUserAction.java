package com.struts2.action;

import java.util.Date;

import com.opensymphony.xwork2.ActionContext;

public class NormalUserAction {

	private String userName;
	private String password;
	private Date date;

	// set方法用户注入数据，get方法用户在jsp界面中通过struts标签获取数据
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String login() {
		ActionContext context = ActionContext.getContext();
		context.getApplication().put("servletContent", "我是Servletcontent中的数据");
		context.put("request", "我是request中的数据");
		context.getSession().put("session", "我是session中的数据");
		System.out.println("normalrUserAction--->login");
		System.out.println("用户名：" + userName + " 密码：" + password);
		System.out.println("日期："+date);
		return "main";
	}

}
