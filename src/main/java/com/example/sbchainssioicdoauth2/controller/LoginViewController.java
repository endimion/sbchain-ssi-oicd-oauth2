// package com.example.sbchainssioicdoauth2.controller;

// import java.util.Map;

// import javax.servlet.http.HttpServletRequest;

// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.security.oauth2.core.oidc.user.OidcUser;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.ModelMap;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.servlet.ModelAndView;

// @Controller
// @RequestMapping("/")
// public class LoginViewController {
    
//     //@Autowired
//     //WebClient webClient;

//     private final WebClient webClient;

//     LoginViewController(WebClient webClient){
//         this.webClient = webClient;
//     }

// 	@GetMapping
//     protected ModelAndView loginView(ModelMap model, HttpServletRequest request) {

// 		model.addAttribute("name","Mibu");

//         return new ModelAndView("basic");
//     }

//     @GetMapping("/user")
//     protected ModelAndView userView(ModelMap model, @AuthenticationPrincipal OidcUser oidcUser, HttpServletRequest request) {

// 		model.addAttribute("name", oidcUser.getAttribute("name"));

//         return new ModelAndView("basic");
//     }

//     @GetMapping("/claims")
//     protected ModelAndView getClaims(ModelMap model, HttpServletRequest request) {

//         model.addAttribute("name", webClient.get().uri("/claims").retrieve().bodyToMono(Map.class).block());
//         return new ModelAndView("basic");
//     }

//     @GetMapping("/something")
//     protected ModelAndView getSomething(ModelMap model, HttpServletRequest request) {

//         model.addAttribute("name", webClient.get().uri("/resource/something").retrieve().bodyToMono(String.class).block());
//         return new ModelAndView("basic");
//     }

//     @GetMapping("/email")
//     protected ModelAndView getEmail(ModelMap model, HttpServletRequest request) {

//         model.addAttribute("name", webClient.get().uri("/email").retrieve().bodyToMono(String.class).block());
//         return new ModelAndView("basic");
//     }

// }
