package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.SendEmail;
import com.example.demo.repository.SendEmailRepository;

@Service
@Transactional
public class SendEmailService {

	@Autowired
	private SendEmailRepository sendEmailRepository;

	public void insert(SendEmail send) {
		sendEmailRepository.insert(send);
	}


	@Autowired
	private JavaMailSender sender;

//	public String generateKey() {
//		// 指定の文字によるリストを作成
//		List<Character> letters = new ArrayList<>();
//		char lowercaseAlphabet = 'a';
//		for (int i = 1; i <= 26; i++) {// 理解できなければ参照：https://inarizuuuushi.hatenablog.com/entry/2017/05/31/173343
//			letters.add(lowercaseAlphabet++);
//		}
//		char uppercaseAlphabet = 'A';
//		for (int i = 1; i <= 26; i++) {
//			letters.add(uppercaseAlphabet++);
//		}
//		char number = '0';
//		for (int i = 1; i <= 10; i++) {
//			letters.add(number++);
//		}
//
//		// ランダムなkeyを生成
//		String key = "";
//		int digit = 20;// 桁数の指定
//		Random random = new Random();
//		for (int i = 1; i <= digit; i++) {
//			int randomNumber = random.nextInt(letters.size() - 1);
//			key += letters.get(randomNumber);
//		}
//		return key;
//	}

//	/**
//	 * メール送信メソッド HTMLのメールで断念したやつ
//	 * 
//	 * @param key
//	 * @param email
//	 */
//	public void sendHtmlMail(String url, String email) {
//		MimeMessage message = sender.createMimeMessage();
//		try {
//			MimeMessageHelper helper = new MimeMessageHelper(message, true);
//			helper.setFrom("rakuraku.robot.202204@gmail.com");
//			helper.setTo(email);
//			helper.setSubject("件名");
//			helper.setText("本文", "ここをクリック!<br>" + "<a href=\"" + url + "\">" + url + "</a>");
//			sender.send(message);
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * keyをURLの末尾に埋め込むメソッド
	 * 
	 * @param key ランダムな文字列
	 * @return
	 */
//	public String generateUrl(String key) {
//		String url = "http://localhost:8080/user/register" + "?key=" + key;
//		return url;
//	}

	public List<SendEmail> findByEmail(String email) {
		List<SendEmail> sendEmail = sendEmailRepository.findByEmail(email);
		return sendEmail;
	}

	public List<SendEmail> findByKey(String key) {
		List<SendEmail> accessKey = sendEmailRepository.findByKey(key);
		return accessKey;
	}
}


