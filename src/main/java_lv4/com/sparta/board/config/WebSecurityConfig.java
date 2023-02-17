package com.sparta.board.config;

import com.sparta.board.jwt.JwtAuthFilter;
import com.sparta.board.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //  Spring Security 필터 체인을 구성하는 인터페이스다.
        http.csrf().disable(); // CSRF 방어 기능을 비활성화한다.

        // 기본 설정인 Session 방식은 사용하지 않고 JWT 방식을 사용하기 위한 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests() //  요청에 대한 보안 검사를 구성한다.
                .antMatchers("/**").permitAll()
//                .antMatchers("/api/search").permitAll()
//                .antMatchers("/api/shop").permitAll()
                .anyRequest().authenticated() // 나머지 URL에 대한 접근 권한을 설정합니다. 인증된 사용자만 접근할 수 있다.
                // JWT 인증/인가를 사용하기 위한 설정
                // JwtAuthFilter를 UsernamePasswordAuthenticationFilter 이전에 실행되도록 설정한다. JwtAuthFilter는 JWT 토큰을 검증하고 인증/인가를 처리한다.
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

//
//        http.formLogin().loginPage("/api/user/login-page").permitAll(); //로그인 페이지를 설정한다
//
//        http.exceptionHandling().accessDeniedPage("/api/user/forbidden");

        return http.build();
    }

}