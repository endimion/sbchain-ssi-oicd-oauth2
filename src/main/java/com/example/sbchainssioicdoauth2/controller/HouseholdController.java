package com.example.sbchainssioicdoauth2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.service.SubmitService;
import com.example.sbchainssioicdoauth2.utils.FormType;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/multi/householdInfo")
public class HouseholdController {

    @Autowired
    CacheService cacheService;
    
    @Autowired
    PopulateInfoService infoService;

    @Autowired
    private SubmitService submitService;
    
    @GetMapping("/view")
    protected ModelAndView householdInfo(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){
        model.addAttribute("uuid", uuid);
        return new ModelAndView("householdInfo");
    }
    
    @GetMapping("/results")
    protected ModelAndView householdInfoResults(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){
        
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        Map<String, String> householdComposition = new HashMap<>();
        householdComposition.put(String.valueOf(context.getIdToken().getOtherClaims().get("member")), String.valueOf(context.getIdToken().getOtherClaims().get("relation")));
        model.addAttribute("ssiInfo", householdComposition);
        model.addAttribute("uuid", uuid);

        infoService.populateFetchInfo(model, request, uuid);
        return new ModelAndView("householdInfo");
        
    }

    @GetMapping("/save")
    protected ModelAndView householdInfoSubmit(RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){

        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, context, FormType.HOUSEHOLD_COMPOSITION.value, uuid);
        // cacheService.putInfo(ssiApp, uuid);
        // attr.addAttribute("uuid", uuid);

        submitService.submit(ssiApp);
        cacheService.evictSsiAppCache(uuid);
        model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());

        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        //return new ModelAndView("redirect:/multi/notifications/view");
        return new ModelAndView("redirect:/");
    }

}