package com.wuxl.interview.exam.config;

import com.wuxl.interview.exam.security.CustomAccessDeniedHandler;
import com.wuxl.interview.exam.security.CustomAuthenticationFailureHandler;
import com.wuxl.interview.exam.security.CustomBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomBasicAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * HTTP请求拦截处理
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index").permitAll()  //主路径直接请求
                .antMatchers("/info/group/**").permitAll() // 特殊接口允许
                .antMatchers("/info/**").hasRole("ADMIN") // 需要admin权限
                .anyRequest().authenticated()    //请他请求都要验证
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler()) // 权限认证失败的自定义handler
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout().permitAll()   //允许注销
                .and()
                .formLogin()     //允许表单登录
                .failureHandler(customAuthenticationFailureHandler()); // 自定义认证失败返回
        http.csrf().disable();  //关闭csrf的认证
    }

    /**
     * 处理前端文件，拦截忽略
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/image/**");
    }


    /**
     * 设置内存中的用户admin
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(encoder.encode("123456"))
                .roles("ADMIN");
        auth.inMemoryAuthentication()
                .withUser("wuxl")
                .password(encoder.encode("123456"))
                .roles("USER");
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
