package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザ情報登録画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {
	
	@Autowired
	private RegisterUserService registerUserService;
	
	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}
	
	/**
	 * ユーザ情報の登録処理をします.
	 * 
	 * @param form ユーザ登録情報
	 * @return     ログイン画面
	 */
	@RequestMapping("")
	public String register(RegisterUserForm form) {
		form.setName("鈴木将大");
		form.setMailAddress("s@gmail.com");
		form.setPassword("123");
		registerUserService.insert(form);
		return "";
	}
	
}
