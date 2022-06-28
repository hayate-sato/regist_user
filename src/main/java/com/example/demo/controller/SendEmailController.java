package com.example.demo.controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.RegistUser;
import com.example.demo.domain.SendEmail;
import com.example.demo.form.RegistUserForm;
import com.example.demo.form.SendEmailForm;
import com.example.demo.service.RegistUserService;
import com.example.demo.service.SendEmailService;

@Controller
@RequestMapping("/user")
public class SendEmailController {
	@Autowired
	private SendEmailService sendEmailService;

	@Autowired
	private RegistUserService registUserService;


	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private HttpSession session;

	@RequestMapping("")
	public String index(Model model) {
		return "email_submit";
	}

	@ModelAttribute
	public SendEmailForm setupForm() {
		return new SendEmailForm();
	}



	@RequestMapping("/create")
	public String create(@Validated SendEmailForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {

		if (result.hasErrors()) {
			return index(model);
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		SendEmail sendEmail = new SendEmail();

		String uniqueKey = UUID.randomUUID().toString();

		String email = form.getEmail();

		List<SendEmail> emailList = sendEmailService.findByEmail(email);
		if (emailList != null) {
			result.rejectValue("email", null, "24時間以内にURLを発行済みです");
			return "email_submit";
		} else {

		sendEmail.setEmail(email);
		sendEmail.setUniqueKey(uniqueKey);
		sendEmail.setRegisterDate(timestamp);
		sendEmail.setDelFrag(0);
		sendEmailService.insert(sendEmail);

		session.setAttribute("mail", email);

		SimpleMailMessage msg = new SimpleMailMessage();
		try {
			msg.setFrom("rakuraku.robot.202204@gmail.com");
			msg.setTo(email);
			msg.setSubject("ユーザ登録URLの送付");
			msg.setText("Hogehogeシステム、新規ユーザー登録依頼を受け付けました。以下のURLから本登録処理を行ってください。Hogehogeシステム、ユーザー登録URL "
					+ "http://localhost:8080/user/regist?key=" + uniqueKey + "※上記URLの有効期限は24時間以内です");

			mailSender.send(msg);
		} catch (MailException e) {
			e.printStackTrace();
		}

		return "redirect:/user/index2";
	}
}
	@RequestMapping("/index2")
	public String index2() {
		return "email_finished";
	}


	@ModelAttribute
	public RegistUserForm setupForm2() {

		return new RegistUserForm();
	}

	@RequestMapping("/regist")
	public String index2(String key) {

		session.setAttribute("uniqueKey", key);
		return "register_user";
	}


	@RequestMapping("/confirm")

	public String registerUsers(@Validated RegistUserForm form, BindingResult result) {
		System.out.println(form.getPassword());
		System.out.println(form.getConfirmPassword());
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "", "パスワードが一致していません");
		}

		if (result.hasErrors()) {
			return "register_user";
		}

		SendEmailForm sendEmailForm = new SendEmailForm();
		String email = sendEmailForm.getEmail();
		RegistUser registUser = new RegistUser();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		registUser.setName(form.getName());
		registUser.setKana(form.getKana());
		registUser.setEmail(String.valueOf(session.getAttribute("mail")));
		System.out.println(session.getAttribute("mail"));
		registUser.setPhone(form.getPhone());
		registUser.setZipCode(form.getZipCode());
		registUser.setAddress(form.getAddress());
		registUser.setPassword(form.getPassword());
		registUser.setRegistDate(timestamp);
		registUser.setRegistUser(1);
		registUser.setUpdateDate(timestamp);
		registUser.setUpdateUser(1);
		registUser.setDelFrag(1);
		

		registUserService.insert(registUser);
		
		return "redirect:/user/index3";
	}

	@RequestMapping("/index3")
	public String index3() {
		return "register_finished";
	}

}
