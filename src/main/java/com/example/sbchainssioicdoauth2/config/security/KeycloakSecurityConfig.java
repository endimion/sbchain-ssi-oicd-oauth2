package com.example.sbchainssioicdoauth2.config.security;

import java.util.HashSet;
import java.util.Set;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@KeycloakConfiguration
public class KeycloakSecurityConfig  extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
      SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
      grantedAuthorityMapper.setPrefix("ROLE_");

      KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
      keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
      auth.authenticationProvider(keycloakAuthenticationProvider);
    } 

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    // @Bean
    // protected SecurityContextLogoutHandler securityContextLogoutHandler(){
    //   SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    //   log.info("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk logout handler");
    //   logoutHandler.setInvalidateHttpSession(false);
    //   return logoutHandler;
    // }

    // @Bean
    // public SessionAuthenticationStrategy sessionStrategy() {
    //   SessionFixationProtectionStrategy sessionStrategy = new SessionFixationProtectionStrategy();
    //     sessionStrategy.setMigrateSessionAttributes(true);
    //     log.info("pppppppppppppppppppppppppp session strategy");
    //     return sessionStrategy;
    // }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager() {
        return new HttpSessionManager();
    }

    // @Bean
    // public HttpSessionEventPublisher httpSessionEventPublisher() {
    //     return new HttpSessionEventPublisher();
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
            .authorizeRequests()
            .antMatchers("/multi/**").hasRole("user")
            // .antMatchers("/multi/disqualifyingCrit/*").hasRole("personal_declaration")
            // .antMatchers("/multi/residenceInfo/*").hasRole("user")
            //.antMatchers("/disqualifyingCrit/*").hasRole("user")
            .anyRequest().permitAll()
            .and()
            .logout()
            .invalidateHttpSession(false)
            .and()
            .sessionManagement()
            .sessionFixation().migrateSession()
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }

    //request debuger, remove
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

    private GrantedAuthoritiesMapper userAuthoritiesMapper() {
      log.info("mmmmmmmmmmmmmmmmmm userAuthorities mapper :{}");
      return (authorities) -> {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(
       
            authority -> {
              log.info("nnnnnnnnnnnnnnnnnnn oidcuserauthority :{}", authority);
              if (authority instanceof OidcUserAuthority) {
                OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
                
                OidcIdToken idToken = oidcUserAuthority.getIdToken();
                OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
  
              //   List<SimpleGrantedAuthority> groupAuthorities =
              //       userInfo.getClaimAsStringList("groups").stream()
              //           .map(g -> new SimpleGrantedAuthority("ROLE_" + g.toUpperCase()))
              //           .collect(Collectors.toList());
               mappedAuthorities.add(new SimpleGrantedAuthority("user"));
  
              // List<SimpleGrantedAuthority> groupAuthorities =
              //       userInfo.getClaimAsStringList("claims").stream()
              //           .map(g -> new SimpleGrantedAuthority("ROLE_" + g.toUpperCase()))
              //           .collect(Collectors.toList());
              // mappedAuthorities.addAll(groupAuthorities);
              }
            });
  
        return mappedAuthorities;
      };
    }

    // @Bean
    // public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
    //         KeycloakAuthenticationProcessingFilter filter) {
    //             log.info("11111111111111111111111 keycloakAuthenticationProcessingFilterRegistrationBean");
    //     FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    //     registrationBean.setEnabled(false);
    //     return registrationBean;
    // }

    // @Bean
    // public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
    //         KeycloakPreAuthActionsFilter filter) {
    //             log.info("2222222222222222222222222222 keycloakPreAuthActionsFilterRegistrationBean");
    //     FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    //     registrationBean.setEnabled(false);
    //     return registrationBean;
    // }

    // @Bean
    // public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
    //         KeycloakAuthenticatedActionsFilter filter) {
    //             log.info("3333333333333333333333333333333333 keycloakAuthenticatedActionsFilterBean");
    //     FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    //     registrationBean.setEnabled(false);
    //     return registrationBean;
    // }

    // @Bean
    // public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
    //     KeycloakSecurityContextRequestFilter filter) {
    //         log.info("444444444444444444444444444444444444 keycloakSecurityContextRequestFilterBean");
    //     FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
    //     registrationBean.setEnabled(false);
    //     return registrationBean;
    // }
    // @Bean
    // protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
    //   KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(authenticationManagerBean());
    //   filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy());
    //   return filter;
    // }
    
}