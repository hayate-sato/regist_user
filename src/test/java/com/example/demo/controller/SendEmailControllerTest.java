package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.example.demo.util.XlsDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = XlsDataSetLoader.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや@ExpectedDatabaseなどを使えるように指定
})
class SendEmailControllerTest {
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@DisplayName("画面表示")

	@Transactional
	void testToDisplay() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user")).andExpect(view().name("email_submit")).andReturn();

	}

	@Test
	@DisplayName("メールアドレス空白")

	@Transactional
	void testToErrorMail() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/create").param("email", ""))
				.andExpect(view().name("email_submit")).andReturn();

	}

	@Test
	@DisplayName("メールアドレス形式不正")

	@Transactional
	void testToErrorMail2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/create").param("email", "zenzenntigau"))
				.andExpect(view().name("email_submit")).andReturn();

	}


	@Test
	@DisplayName("会員登録していないユーザーに対してメールが送られるか")
	@DatabaseSetup("classpath:send_email_01.xlsx") // テスト実行前に初期データを投入
	@ExpectedDatabase("classpath:expected_send_email_01.xlsx")
	void testToSendEmail() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/create").param("email", "samplenomail@sample.com"))
				.andExpect(view().name("redirect:/user/index2")).andReturn();

	}

	@Test
	@DisplayName("２４時間以内に送ったメールに対してエラーが出るか")
	@DatabaseSetup("classpath:send_email_02.xlsx") // テスト実行前に初期データを投入
	void testToSendEmailError() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/create").param("email", "testsamplebefore24@test.com"))
				.andExpect(view().name("email_submit")).andReturn();

	}

	@Test
	@DisplayName("使われているメールアドレスがすでに会員登録されていた場合")
	@DatabaseSetup("classpath:regist_user_01.xlsx")
	void testToSendEmailAlready() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/create").param("email", "testsamplebefore24@test.com"))
				.andExpect(view().name("email_submit")).andReturn();
	}

	@Test
	@DisplayName("本登録後、リダイレクトからの画面表示")

	@Transactional
	void testToRegistDisplayRedirect2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/index2")).andExpect(view().name("email_finished"))
				.andReturn();

	}


	@Test
	@DisplayName("ユーザー登録画面の表示")
	@DatabaseSetup("classpath:send_email_01.xlsx")
	@Transactional
	void testToRegistDisplay() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/regist")).andExpect(view().name("register_user")).andReturn();

	}

	@Test
	@DisplayName("URLのリンクが切れた時の画面表示")
	@DatabaseSetup("classpath:send_email_02.xlsx")
	@Transactional
	void testToRegistDisplayOut() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/regist")).andExpect(view().name("out")).andReturn();

	}

	@Test
	@DisplayName("ユーザー本登録、正常系")
	@DatabaseSetup("classpath:regist_user_01.xlsx") // テスト実行前に初期データを投入
	//@ExpectedDatabase("classpath:expected_regist_user_01.xlsx")
	void testToRegistUser() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/user/confirm").param("name", "菅原光").param("kana", "すがわらひかる")
						.param("zipCode", "123-4598").param("address", "東京都港区赤坂").param("phone", "000-1234-1234")
						.param("password", "morimori")
						.param("confirmPassword", "morimori"))
				.andExpect(view().name("redirect:/user/index3")).andReturn();
	}
	
	@Test
	@DisplayName("本登録後、リダイレクトからの画面表示")

	@Transactional
	void testToRegistDisplayRedirect3() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/user/index3")).andExpect(view().name("register_finished"))
				.andReturn();

	}

	@Test
	@DisplayName("ユーザー本登録、異常系：パスワードが違う場合")
	@DatabaseSetup("classpath:regist_user_01.xlsx") // テスト実行前に初期データを投入
	void testToErrorPassword() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/user/confirm").param("name", "菅原光").param("kana", "すがわらひかる")
						.param("zipCode", "123-4598").param("address", "東京都港区赤坂").param("phone", "000-1234-1234")
						.param("password", "morimori").param("confirmPassword", "aribobo"))
				.andExpect(view().name("register_user")).andReturn();
	}

	@Test
	@DisplayName("ユーザー本登録、異常系：入力値に何も入れていない状態の時")
	@DatabaseSetup("classpath:regist_user_01.xlsx") // テスト実行前に初期データを投入
	void testToRegistNull() throws Exception {
		MvcResult mvcResult = mockMvc
				.perform(post("/user/confirm").param("name", "").param("kana", "").param("zipCode", "")
						.param("address", "").param("phone", "").param("password", "").param("confirmPassword", ""))
				.andExpect(view().name("register_user")).andReturn();
	}
}
