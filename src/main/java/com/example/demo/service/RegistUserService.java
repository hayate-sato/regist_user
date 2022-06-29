package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.RegistUser;
import com.example.demo.repository.RegistUserRepository;

@Service
public class RegistUserService {

//	public void insert(String key, String email) {}

	@Autowired
	private RegistUserRepository registUserRepository;

	public void insert(RegistUser registUser) {
		registUserRepository.insert(registUser);
	}

	public List<RegistUser> findByEmail(String email) {
		List<RegistUser> registUser = registUserRepository.findByEmail(email);
		return registUser;
	}


}
