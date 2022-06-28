package com.example.demo.domain;

import java.sql.Timestamp;

public class SendEmail {
	private Integer id;
	private String email;
	private String uniqueKey;
	private Timestamp registerDate;
	private Integer delFrag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUniqueKey() {
		return uniqueKey;
	}

	public void setUniqueKey(String uniqueKey) {
		this.uniqueKey = uniqueKey;
	}

	public Timestamp getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Timestamp registerDate) {
		this.registerDate = registerDate;
	}

	public Integer getDelFrag() {
		return delFrag;
	}

	public void setDelFrag(Integer delFrag) {
		this.delFrag = delFrag;
	}

	@Override
	public String toString() {
		return "SendEmail [id=" + id + ", email=" + email + ", uniqueKey=" + uniqueKey + ", registerDate="
				+ registerDate + ", delFrag=" + delFrag + "]";
	}

}