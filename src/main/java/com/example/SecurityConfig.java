package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ログイン認証用設定
 * 
 * @author shota.suzuki
 *
 */
@Configuration // 設定用のクラス
@EnableWebSecurity // SpringSecurityのウェブ用の機能を利用する
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	/**
	 * 特定のリクエストに対して「セキュリティ設定」の無視する設定など全体にかかわる設定をする.
	 * 具体的には静的リソースに対してセキュリティの設定を無効にする.
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
	 * 認可の設定やログイン／ログアウトに関する設定ができる.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests() // 認可に関する設定
		    .antMatchers( // 認証が不要なパスを記載する
		    		"/login",
		    		"/register",
		    		"/register/userRegister",
		    		"/",
		    		"/detail/")
		    .permitAll()
		    .anyRequest().authenticated(); //上記以外のパスは認証が必要
		
		http.formLogin() // ログインに関する設定
		    .loginPage("/login") // ログイン画面に遷移させるパス(ログイン認証が必要なパスを指定し、かつログインされていないと、このパスに遷移される)
		    .loginProcessingUrl("/login/userLogin") // ログインボタンを押した際に遷移させるパス(ここに遷移させれば自動的にログインが行われる)
		    .failureUrl("/login?error=true") // ログイン失敗に遷移させるパス
		    .defaultSuccessUrl("/", true) // 第1引数:デフォルトでログイン成功時に遷移させるパス, 第2引数:とりあえずtrueにする
		    .usernameParameter("email") // 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
		    .passwordParameter("password"); // 認証時に使用するパスワードのリクエストパラメータ名
		
		http.logout() //ログアウトに関する設定
		    .logoutRequestMatcher(new AntPathRequestMatcher("/logout**")) // ログアウトさせる際に遷移させるパス
		    .logoutSuccessUrl("/login") // ログアウト後に遷移させるパス(ここではログイン画面を指定)
		    .deleteCookies("JSESSIONID") // ログアウト後、Cookieに保存されているセッションIDを削除
		    .invalidateHttpSession(true); // true:ログアウト後、セッションを無効にする、false:セッションを無効にしない
	}
	
	
	/**
	 * 「認証」に関する設定
	 * 認証ユーザを取得する「UserDetailService」の設定やパスワード照合時に使う「PasswordEncoder」の設定
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		    .passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	/**
	 * bcryptアルゴリズムでハッシュ化する実装を返します.
	 * これを指定することでパスワードハッシュ化やマッチ確認する際に
	 * @Autowired
	 * private PasswordEncoder passwordEncoder;
	 * と記載するとDIされるようになります.
	 * 
	 * @return bcryptアルゴリズムでハッシュ化する実装オブジェクト
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
