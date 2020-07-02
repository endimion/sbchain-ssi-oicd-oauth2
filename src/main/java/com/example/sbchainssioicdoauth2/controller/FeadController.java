package com.example.sbchainssioicdoauth2.controller;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.utils.FormType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/multi/fead")
public class FeadController {

    @Autowired
    CacheService cacheService;

    @Autowired
    PopulateInfoService infoService;

    @GetMapping("/view")
    protected ModelAndView feadView(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {
        model.addAttribute("uuid", uuid);
        infoService.populateFetchInfo(model, request, uuid);
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, request, FormType.PERSONAL_DECLARATION.value, uuid);
        cacheService.putInfo(ssiApp, uuid);
        return new ModelAndView("fead");
    }

    @GetMapping("/results")
    protected ModelAndView feadResults(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {

        infoService.populateFetchInfo(model, request, uuid);
        return new ModelAndView("fead");

    }

    @GetMapping("/save")
    protected ModelAndView feadSave(@RequestParam(value = "uuid", required = true) String uuid, RedirectAttributes attr, ModelMap model, HttpServletRequest request) {

//        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
//        SsiApplication ssiApp = cacheService.get(uuid);
//        infoService.populateSsiApp(ssiApp, context, FormType.FEAD.value, uuid);
//        cacheService.putInfo(ssiApp, uuid);
//        attr.addAttribute("uuid", uuid);
        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView("redirect:/multi/employment/view");

    }
}
