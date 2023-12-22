package com.nguyenkien.mms.config;


import com.nguyenkien.mms.service.impl.CustomerServiceImpl;
import com.nguyenkien.mms.service.impl.RestaurantServiceImpl;
import com.nguyenkien.mms.service.impl.UserDetailsServiceImpl;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

 
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl customerDetailsService;
		
	@Autowired
//	private DataSource dataSource;
	
	AppConfig appConfig = new AppConfig();

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		// Sét đặt dịch vụ để tìm kiếm User trong Database.
		// Và sét đặt PasswordEncoder.
		auth.userDetailsService(customerDetailsService).passwordEncoder(appConfig.passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Các trang không yêu cầu login
		http.authorizeRequests().antMatchers("/", "/home", "/login", "/logout", "/register").permitAll();

		// Trang /userInfo yêu cầu phải login với vai trò ROLE_USER hoặc ROLE_ADMIN.
		// Nếu chưa login, nó sẽ redirect tới trang /login.
		http.authorizeRequests().antMatchers("/customer/**").access("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')");
		
		http.authorizeRequests().antMatchers("/restaurant/**").access("hasAnyRole('ROLE_RESTAURANT', 'ROLE_ADMIN')");
		
		http.authorizeRequests().antMatchers("/shipper/**").access("hasAnyRole('ROLE_SHIPPER', 'ROLE_ADMIN')");

		// Trang chỉ dành cho ADMIN
		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");	

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		
		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//
				// Submit URL của trang login
				.loginPage("/login")//
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.defaultSuccessUrl("/",true)//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// Cấu hình cho Logout Page.
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

		// Cấu hình Remember Me.
//		http.authorizeRequests().and() //
//				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
//				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

	}


}
