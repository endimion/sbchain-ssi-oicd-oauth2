package com.example.sbchainssioicdoauth2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.sbchainssioicdoauth2.model.RequestParams;
import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.service.ResourceService;
import com.example.sbchainssioicdoauth2.utils.FormType;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/multi/personalInfo")
public class PersonalInformationController {

    @Autowired
    CacheService cacheService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    PopulateInfoService infoService;

    private static final String SSI_REQUEST_PARAMS = "ssiInformation";

    @GetMapping("/view")
    protected ModelAndView personalInfo(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {

        model.addAttribute("uuid", uuid);
        return new ModelAndView("personalInfo");
    }

    @GetMapping("/results")
    protected ModelAndView personalInfoResults(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {
        
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        
        //String something = resourceService.getSomething(context);
        //log.info("ddddddddddddddddddddddddd something string :{}", something);
        log.info("zzzzzzzzzzzzzzzzzzzzzz user :{}",  oidcUser);
        log.info("eeeeeeeeeeeeeeeeeeeeeee otherclaims :{}", context.getIdToken().getOtherClaims());
       
        // log.info("gggggggggggggggggggg attributes :{}", oidcUser.getAttributes());
        // params.setSsiInfo(oidcUser.getAttributes());
        // model.addAttribute("ssiInfo", oidcUser.getAttributes());
        model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());
        model.addAttribute("uuid", uuid);

        return new ModelAndView("personalInfo");
    }

    @GetMapping("/save")
    protected ModelAndView personalInfoSubmit(@AuthenticationPrincipal OidcUser oidcUser, RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request, HttpSession session) {

        // model.addAttribute("userAttr", oidcUser.getAttributes());
        
        attr.addAttribute("uuid", uuid);
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        
        log.info("ppppppppppppppppppppppppppp uuid :{}", uuid);
        SsiApplication ssiApp = new SsiApplication();
        infoService.populateSsiApp(ssiApp, context, FormType.PERSONAL_INFO.value, uuid);
        cacheService.putInfo(ssiApp, uuid);

        
        try {
            request.logout();
            log.info("logged out ???");
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView("redirect:/multi/disqualifyingCrit/view");
    }

}