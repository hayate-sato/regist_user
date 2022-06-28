package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.RegistUser;

@Repository
public class RegistUserRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	private static final RowMapper<RegistUser>USER_ROW_MAPPER = (rs,i)->{
	RegistUser registUser= new RegistUser();
	registUser.setEmail(rs.getString("email"));
	registUser.setName(rs.getString("name"));
	registUser.setKana(rs.getString("kana"));
	registUser.setZipCode(rs.getString("zip_code"));
	registUser.setPhone(rs.getString("phone"));
	registUser.setPassword(rs.getString("password"));	
		return registUser;
	};

	public void insert(RegistUser registUser) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		registUser.setPassword(passwordEncoder.encode(registUser.getPassword()));
		String insertSql = "insert into users(name, kana, email, zip_code, address, phone, password, regist_date, regist_user, update_date, update_user, del_frag) values(:name, :kana,  :email, :zipCode, :address, :phone, :password, :registDate, :registUser, :updateDate, :updateUser, :delFrag);";
		SqlParameterSource param = new BeanPropertySqlParameterSource(registUser);
		template.update(insertSql, param);

	}

	public List<RegistUser> findByEmail(String userEmail) {
		String sql = "SELECT * FROM regist_url WHERE email=:email and register_date +cast('1 days' as interval)>now();";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", userEmail);
		List<RegistUser> registUser = template.query(sql, param, USER_ROW_MAPPER);
		if (registUser.size() == 0) {
			return null;
		}
		return registUser;
	}
}
