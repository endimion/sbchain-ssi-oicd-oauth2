// package com.example.sbchainssioicdoauth2.controller;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.ResolvableType;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.servlet.ModelAndView;

// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Controller
// public class LoginController {

//     private static String authorizationRequestBaseUri
//             = "oauth2/authorization";
//     Map<String, String> oauth2AuthenticationUrls
//             = new HashMap<>();

//     @Autowired
//     private ClientRegistrationRepository clientRegistrationRepository;

//     @GetMapping("/loginTest")
//     public ModelAndView getLoginPage(Model model) {
//         Iterable<ClientRegistration> clientRegistrations = null;
//         ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
//                 .as(Iterable.class);
//         if (type != ResolvableType.NONE
//                 && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
//             clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
//         }

//         clientRegistrations.forEach(registration
//                 -> oauth2AuthenticationUrls.put(registration.getClientName(),
//                         authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
//         model.addAttribute("urls", oauth2AuthenticationUrls);

//         log.info("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb oauth2AuthenticationUrls :{}", oauth2AuthenticationUrls);
//         log.info("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbb oauth2AuthenticationUrls get :{}", oauth2AuthenticationUrls.get("http://localhost:9090/auth/realms/personalinfo"));

//         return new ModelAndView("redirect:" + oauth2AuthenticationUrls.get("http://localhost:9090/auth/realms/personalinfo"));

// //        return "oauth_login";
//     }

//     @GetMapping("/logoutTest")
//     public ModelAndView logoutTest(ModelMap model){
//         return new ModelAndView("redirect: http://localhost:9090/auth/realms/personalinfo/protocol/openid-connect/logout" );
//     }

// }