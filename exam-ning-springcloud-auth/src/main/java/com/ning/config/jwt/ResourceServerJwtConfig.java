package com.ning.config.jwt;

import com.ning.security.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;


/**
 * 资源服务器配置
 * 配置资源服务器的访问控制
 *
 */
@Configuration
@EnableResourceServer
@Order(3)
public class ResourceServerJwtConfig extends ResourceServerConfigurerAdapter {

    @Resource
    @Qualifier(value = "jwtTokenStore")
    TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/oauth/user").authenticated(); // 配置oauth访问控制，必须认证后才可以访问
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);

        // 处理 Invalid access token 方面异常
        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
    }

}
