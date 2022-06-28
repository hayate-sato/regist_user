package com.example.demo.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class SendEmailForm {
	@Size(min = 1, max = 127, message = "メールアドレスは1文字以上127文字以内で入力してください")
	@Email(message = "メールアドレスの形式が不正です")
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "sendEmail [email=" + email + "]";
	}
}
