package com.example.demo.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistUserForm {

	@NotBlank(message = "名前を入力してください")
	private String name;
	@NotBlank(message = "ふりがなを入力してください")
	private String kana;
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号形式にしてください")
	private String zipCode;
	@NotBlank(message = "住所を入力してください")
	private String address;
	@Pattern(regexp = "^[0-9]{3}-[0-9]{4}-[0-9]{4}$", message = "携帯電話番号形式にしてください")
	private String phone;
	@Size(min = 8, max = 100, message = "パスワードは8文字以上100文字以内で入力してください")
	private String password;
	@Size(min = 8, max = 100, message = "パスワードは8文字以上100文字以内で入力してください")
	private String confirmPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKana() {
		return kana;
	}

	public void setKana(String kana) {
		this.kana = kana;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "RegistUserForm [name=" + name + ", kana=" + kana + ", zipCode=" + zipCode + ", address=" + address
				+ ", phone=" + phone + ", password=" + password + ", confirmPassword=" + confirmPassword + "]";
	}

}
