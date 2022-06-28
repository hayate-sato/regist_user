package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.SendEmail;

@Repository
public class SendEmailRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<SendEmail> EMAIL_ROW_MAPPER = (rs, i) -> {
		SendEmail email = new SendEmail();
		email.setUniqueKey(rs.getString("unique_key"));
		email.setEmail(rs.getString("email"));
		email.setRegisterDate(rs.getTimestamp("register_date"));
		return email;

	};

	public void insert(SendEmail email) {
		String insertSql = "insert into regist_url(email, unique_key, register_date, del_frag) values(:email, :uniqueKey, :registerDate, :delFrag);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(email);
		template.update(insertSql, param);
	}

	public List<SendEmail> findByEmail(String userEmail) {
		String sql = "SELECT * FROM regist_url WHERE email=:email and register_date +cast('1 days' as interval)>now();";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", userEmail);
		List<SendEmail> sendEmail = template.query(sql, param, EMAIL_ROW_MAPPER);
		if (sendEmail.size() == 0) {
			return null;
		}
		return sendEmail;
	}

	public List<SendEmail> findByKey(String key) {
		String sql = "SELECT unique_key FROM regist_url WHERE email=:email and register_date +cast('1 days' as interval)>now();";
		SqlParameterSource param = new MapSqlParameterSource().addValue("unique_key", key);
		List<SendEmail> accessKey = template.query(sql, param, EMAIL_ROW_MAPPER);
		if (accessKey.size() == 0) {
			return null;
		}
		return accessKey;
	}
	
	
	
	// URLの有効期限が24時間のやつのメソッド(没)

//	public String findByKeyAndTime(String key, LocalDateTime before24Hour) {
//		System.out.println("before24Hour:" + before24Hour);
//		String sql = "select * from regist_url where unique_key=:unique_key and time_stamp>=:before24Hour";
//		SqlParameterSource param = new MapSqlParameterSource().addValue("uniqueKey", key).addValue("before24Hour",
//		before24Hour);
//		String email = null;
//		try {
//			email = template.queryForObject(sql, param, String.class);
//		} catch (EmptyResultDataAccessException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			email = null;
//		}
//		System.out.println(email);
//		return email;
//	}
}
