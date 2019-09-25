package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン画面を操作するコントローラ
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/login")
public class LoginUserController {
	
	
	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLogin() {
		return "login";
	}
	
	//Spring securityの実装するまでの仮メソッド
	@RequestMapping("/pre")
	public String pre(Model model) {
		return "list";
	}
}
