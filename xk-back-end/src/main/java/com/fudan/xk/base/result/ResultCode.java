package com.fudan.xk.base.result;

public enum ResultCode {


	SUCCESS(200, "成功"),
	FAIL(500, "失败"),
	USER_LOGIN_ERROR(500201, "登录失败，用户名或密码出错，请重新输入"),
	UNAVAILABLE_TOKEN(500202, "token已过期"),
	COURSE_NO_SELECT(500204, "退课错误!"),
	COURSE_ALREADY_SELECTED(500205, "无法重复选课"),
	COURSE_NO_STOCK(500203, "课程余量不足，选课失败"),
	COURSE_TIME_CONFLICT(500206, "课程时间冲突"),
	CLASSROOM_CAPACITY_FULL(500208, "教室容量已满，无法申请!"),
    CLASSROOM_CAPACITY_FULL2(500212, "教室容量已满，无法通过申请!"),
	COURSE_NOT_FOUND(500209, "所申请的课程不存在，检查课程id!"),
	APPLICATION_EXISTS(500210, "无法重复申请!"),
	COURSE_SELECTABLE(500211, "课程有余量，请选课!");

	private Integer code;
	
	private String message;
	
	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
