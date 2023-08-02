package com.Ambition.config;

import com.Ambition.filter.JwtAuthenticationTokenFilter;
import com.Ambition.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    //获取AuthenticationManager（认证管理器），登录时认证使用
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    //配置过滤
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()//关闭csrf，跨站请求伪造，前后端分离没有这个问题
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//关闭session
                .and()
                .authorizeRequests()
                .antMatchers( "/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**").permitAll()
                .antMatchers("/tologin","/toLoginOut").permitAll()
                .antMatchers("/role/add","/role/delete","/role/update","/email").hasAuthority("用户管理")
                .antMatchers("/user/add","/user/beachDelete","/user/delete","/user/update").hasAuthority("用户管理")
                .antMatchers("/goods/add","/goods/delete","/goods/update").hasAuthority("商品管理")
                .antMatchers("/checkoutEdit","/checkoutDelete","/checkoutAdd").hasAuthority("出库管理")
                .antMatchers("/checkinEdit","/checkinDelete","/checkinAdd").hasAuthority("入库管理")
                .antMatchers("/order/add","/order/delete","/order/update").hasAuthority("订单管理")
                .anyRequest().authenticated()
                .and()
                //把token校验过滤器添加到过滤器链中
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                //配置自定义异常处理器
                .exceptionHandling()
                //认证失败
                .authenticationEntryPoint(authenticationEntryPoint)
                //权限不足
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .userDetailsService(userDetailsService)
                //允许跨域
                .cors()
                .and()
                .build();
    }

    //配置加密方式
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置跨源访问(CORS)
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

}