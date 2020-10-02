package com.example.sbchainssioicdoauth2.controller;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.utils.FormType;
import com.example.sbchainssioicdoauth2.utils.LogoutUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
@RequestMapping("/multi/disqualifyingCrit")
public class DisqualifyingController {

    @Autowired
    CacheService cacheService;

    @Autowired
    PopulateInfoService infoService;

    @GetMapping("/view")
    protected ModelAndView disqualifyingInfo(@RequestParam(value = "uuid", required = true) String uuid,
                                             ModelMap model, HttpServletRequest request) throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, IntrospectionException, JsonProcessingException {
        model.addAttribute("uuid", uuid);
        infoService.populateFetchInfo(model, request, uuid);
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, request, FormType.PERSONAL_DECLARATION.value, uuid);
        ssiApp = infoService.updateModelfromCacheMergeDB(ssiApp, model, request, uuid);
        cacheService.putInfo(ssiApp, uuid);
        return new ModelAndView("disqCriteria");
    }


    @GetMapping("/continue")
    protected ModelAndView feadSave(@RequestParam(value = "uuid", required = true) String uuid, RedirectAttributes attr, ModelMap model, HttpServletRequest request) {
        SsiApplication ssiApp = cacheService.get(uuid);
        LogoutUtils.forceRelogIfNotCondition(request, ssiApp.getDepositsA());
        return new ModelAndView("redirect:/multi/financialInfo/view?uuid=" + uuid);

    }

    @GetMapping("/nextCompleted")
    protected ModelAndView nextComplete(RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid,
                                        ModelMap model, HttpServletRequest request, HttpSession session) {
        return new ModelAndView("redirect:/multi/financialInfo/view?uuid=" + uuid);
    }


    @GetMapping("/back")
    protected ModelAndView back(RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid,
                                ModelMap model, HttpServletRequest request, HttpSession session) {
        return new ModelAndView("redirect:/multi/personalInfo/view?uuid=" + uuid);
    }

}
