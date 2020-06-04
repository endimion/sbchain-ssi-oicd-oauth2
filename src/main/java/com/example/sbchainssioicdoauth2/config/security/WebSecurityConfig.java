// package com.example.sbchainssioicdoauth2.config.security;

// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
// import org.springframework.security.oauth2.core.oidc.OidcIdToken;
// import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
// import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
// import org.springframework.web.client.RestTemplate;

// @Configuration
// class WebSecurityConfig {

// 	@Bean
// 	public WebSecurityConfigurerAdapter webSecurityConfigurer( //
// 			@Value("${kc.realm}") String realm, KeycloakLogoutHandler keycloakLogoutHandler ) {
// 		return new WebSecurityConfigurerAdapter() {
// 			@Override
// 			public void configure(HttpSecurity http) throws Exception {

// 				http
// 						// Configure session management to your needs.
// 						// I need this as a basis for a classic, server side rendered application
// 						.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
// 						// Depends on your taste. You can configure single paths here
// 						// or allow everything a I did and then use method based security
//                         // like in the controller below
//                         .authorizeRequests()
// 						.antMatchers("/personalInfo/*").hasAuthority("user")
//                         .antMatchers("/disqualifyingCrit/*").hasAuthority("personal_declaration")
//                         .antMatchers("/residenceInfo/*").hasAuthority("residence")
//                         .antMatchers("/**").permitAll()
//                         .and()
// 						// Propagate logouts via /logout to Keycloak
// 						.logout().addLogoutHandler(keycloakLogoutHandler).and()
// 						// This is the point where OAuth2 login of Spring 5 gets enabled
// 						.oauth2Client()
//                         .and()
//                         .oauth2Login()
//                         //                .successHandler(new RefererAuthenticationSuccessHandler())

//                         .userInfoEndpoint()
//                         .userAuthoritiesMapper(userAuthoritiesMapper());
// 						// I don't want a page with different clients as login options
// 						// So i use the constant from OAuth2AuthorizationRequestRedirectFilter
// 						// plus the configured realm as immediate redirect to Keycloak
// 						//.loginPage(DEFAULT_AUTHORIZATION_REQUEST_BASE_URI + "/" + realm);
// 			}
// 		};
// 	}


// 	@Bean
// 	KeycloakLogoutHandler keycloakLogoutHandler() {
// 		return new KeycloakLogoutHandler(new RestTemplate());
//     }
    
//     private GrantedAuthoritiesMapper userAuthoritiesMapper() {
//                 return (authorities) -> {
//                     Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        
//                     authorities.forEach(
//                             authority -> {
//                                 if (authority instanceof OidcUserAuthority) {
//                                     OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
        
//                                     OidcIdToken idToken = oidcUserAuthority.getIdToken();
//                                     OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
        
//         //                            List<SimpleGrantedAuthority> groupAuthorities
//         //                            = userInfo.getClaimAsStringList("groups").stream()
//         //                                    .map(g -> new SimpleGrantedAuthority("ROLE_" + g.toUpperCase()))
//         //                                    .collect(Collectors.toList());
//         //                            SimpleGrantedAuthority auth = ;
//                                     mappedAuthorities.add(new SimpleGrantedAuthority("user"));
//                                     mappedAuthorities.add(new SimpleGrantedAuthority("personal_declaration"));
//                                     mappedAuthorities.add(new SimpleGrantedAuthority("residence"));
//                                 }
//                             });
        
//                     return mappedAuthorities;
//                 };
//             }
// }