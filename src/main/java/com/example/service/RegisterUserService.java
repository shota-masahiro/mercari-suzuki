package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;

/**
 * ログイン情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	/**
	 * 検索処理をします.
	 * 
	 * @param form ユーザ登録情報
	 * @return     userオブジェクト
	 */
	public User findByMailAddress(RegisterUserForm form) {
		return userRepository.findByMailAddress(form.getEmail());
	}


	/**
	 * 登録処理をします.
	 * 
	 * @param form ユーザ登録情報
	 */
	public void insert(RegisterUserForm form) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println("user:"+user);
		userRepository.insert(user);
	}

}