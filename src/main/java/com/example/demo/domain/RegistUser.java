package com.example.demo.domain;

import java.sql.Timestamp;

public class RegistUser {
	private Integer id;
	private String name;
	private String kana;
	private String email;
	private String zipCode;
	private String address;
	private String phone;
	private String password;
	private Timestamp registDate;
	private Integer registUser;
	private Timestamp updateDate;
	private Integer updateUser;
	private Integer delFrag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Timestamp getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Timestamp registDate) {
		this.registDate = registDate;
	}

	public Integer getRegistUser() {
		return registUser;
	}

	public void setRegistUser(Integer registUser) {
		this.registUser = registUser;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getDelFrag() {
		return delFrag;
	}

	public void setDelFrag(Integer delFrag) {
		this.delFrag = delFrag;
	}

	@Override
	public String toString() {
		return "RegistUser [id=" + id + ", name=" + name + ", kana=" + kana + ", email=" + email + ", zipCode="
				+ zipCode + ", address=" + address + ", phone=" + phone + ", password=" + password + ", registDate="
				+ registDate + ", registUser=" + registUser + ", updateDate=" + updateDate + ", updateUser="
				+ updateUser + ", delFrag=" + delFrag + "]";
	}

}
