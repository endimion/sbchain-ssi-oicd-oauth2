package com.example.sbchainssioicdoauth2.controller;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.service.SubmitService;
import com.example.sbchainssioicdoauth2.utils.FormType;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
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
@RequestMapping("/multi/householdInfo")
public class HouseholdController {

    @Autowired
    CacheService cacheService;

    @Autowired
    PopulateInfoService infoService;

    @Autowired
    private SubmitService submitService;

    @GetMapping("/view")
    protected ModelAndView householdInfo(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) throws IllegalArgumentException, InvocationTargetException, IntrospectionException, IllegalAccessException, JsonProcessingException {
        model.addAttribute("uuid", uuid);
        infoService.populateFetchInfo(model, request, uuid);
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, request, FormType.PERSONAL_DECLARATION.value, uuid);
        infoService.mergeModelFromCache(ssiApp, model);
        cacheService.putInfo(ssiApp, uuid);

        Map<String, String>[] householdComposition = ssiApp.getHouseholdComposition();
        model.addAttribute("houseHoldInfo", householdComposition);
        return new ModelAndView("householdInfo");
    }

//    @GetMapping("/results")
//    protected ModelAndView householdInfoResults(@RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
//        Map<String, String>[]
//        ] householdComposition = new HashMap<>();
//
//        householdComposition.put(String.valueOf(context.getIdToken().getOtherClaims().get("member")), String.valueOf(context.getIdToken().getOtherClaims().get("relation")));
//        model.addAttribute("houseHoldInfo", householdComposition);
//        model.addAttribute("uuid", uuid);
//
//        infoService.populateFetchInfo(model, request, uuid);
//        return new ModelAndView("householdInfo");
//
//    }
    @GetMapping("/save")
    protected ModelAndView householdInfoSubmit(RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request) {
//        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
//        SsiApplication ssiApp = cacheService.get(uuid);
//        infoService.populateSsiApp(ssiApp, context, FormType.HOUSEHOLD_COMPOSITION.value, uuid);
//        // cacheService.putInfo(ssiApp, uuid);
//        // attr.addAttribute("uuid", uuid);
//
//        submitService.submit(ssiApp);
//        cacheService.evictSsiAppCache(uuid);
//        model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());
        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        //return new ModelAndView("redirect:/multi/notifications/view");
        return new ModelAndView("redirect:/");
    }

}
