package com.example.sbchainssioicdoauth2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.service.ResourceService;
import com.example.sbchainssioicdoauth2.utils.FormType;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PreAuthorize("hasAuthority('personal_info')")
    @GetMapping("/view")
    protected ModelAndView personalInfo(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {

        model.addAttribute("uuid", uuid);
        return new ModelAndView("personalInfo");
    }

    @PreAuthorize("hasAuthority('personal_info')")
    @GetMapping("/results")
    protected ModelAndView personalInfoResults(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {
        infoService.populateFetchInfo(model, request, uuid);
        //KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        
        // String something = resourceService.getSomething(context);
        // Map<String, Object> personalInfo = resourceService.getPersonalInfo(context);
        // Map<String, Object> claims = resourceService.getClaims(context);
        
        // model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());
        // model.addAttribute("uuid", uuid);

        return new ModelAndView("personalInfo");
    }

    @PreAuthorize("hasAuthority('personal_info')")
    @GetMapping("/save")
    protected ModelAndView personalInfoSubmit(RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request, HttpSession session) {

        
        attr.addAttribute("uuid", uuid);
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        SsiApplication ssiApp = new SsiApplication();
        infoService.populateSsiApp(ssiApp, context, FormType.PERSONAL_INFO.value, uuid);
        cacheService.putInfo(ssiApp, uuid);

        
        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView("redirect:/multi/disqualifyingCrit/view");
    }

}