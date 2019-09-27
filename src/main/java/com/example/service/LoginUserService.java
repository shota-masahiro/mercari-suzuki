package com.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ログイン情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
@Transactional
public class LoginUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByMailAddress(email);
		if (user == null) {
			throw new UsernameNotFoundException("そのメールアドレスは登録されていません");
		}
		//ユーザ権限付与の処理
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

		return new LoginUser(user, authorityList);
	}

}
