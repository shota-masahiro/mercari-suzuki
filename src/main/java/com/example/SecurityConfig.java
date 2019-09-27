package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration // 設定用のクラス
@EnableWebSecurity // SpringSecurityのウェブ用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	/**
	 *
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		   .antMatchers(
				   "/css/**",
				   "/js/**",
				   "/fonts/**");
	}
	
	
	/**
	 *
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 認可に関する設定
		    .antMatchers( // 認証が不要なパスを記載する
		    		"/")
		    .permitAll()
		    .anyRequest().authenticated(); //上記以外のパスは認証が必要
		
		http.formLogin() // ログインに関する設定
		    .loginPage("") // ログイン画面に遷移させるパス
		    .loginProcessingUrl("") // ログインボタンを押した際に遷移させるパス 
		    .failureUrl("") // ログイン失敗に遷移させるパス
		    .defaultSuccessUrl("") // 第1引数:デフォルトでログイン成功時に遷移させるパス, 第2引数:とりあえずtrueにする
		    .usernameParameter("email") // 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
		    .passwordParameter("password"); // 認証時に使用するパスワードのリクエストパラメータ名
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
