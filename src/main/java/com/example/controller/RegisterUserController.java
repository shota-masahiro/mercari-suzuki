package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
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
	 * ユーザ登録画面を出力します.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String toRegister() {
		return "register";
	}


	/**
	 * ユーザ情報の登録処理をします.
	 * 
	 * @param form ユーザ登録情報
	 * @return     ログイン画面
	 */
	@RequestMapping("/userRegister")
	public String register(
			@Validated RegisterUserForm form,
			BindingResult result,
			Model model) {
		
		//メールアドレスの重複チェック
		User user = registerUserService.findByMailAddress(form);
		if (user != null) {
			result.rejectValue("mailAddress", "", "そのメールアドレスは既に登録されています");
		}
		
		//確認用パスワードチェック
		if (!form.getPassword().equals(form.getConfirmationPassword())) {
			result.rejectValue("password", "", "パスワードが一致していません");
		}
		
		//エラーチェック判定
		if (result.hasErrors()) {
			return toRegister();
		}
		
		registerUserService.insert(form);
		return "redirect:/login";
	}

}