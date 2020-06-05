package com.example.sbchainssioicdoauth2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
import com.example.sbchainssioicdoauth2.utils.FormType;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/multi/residenceInfo")
public class ResidenceInfoController {

    private static final String SSI_REQUEST_PARAMS = "ssiInformation";

    @Autowired
    CacheService cacheService;
    
    @Autowired
    PopulateInfoService infoService;
    
    @GetMapping("/view")
    protected ModelAndView residenceInfo(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request, HttpSession session){

        log.info("kkkkkkkkkkkkkkkkkkkkk disqCriteria after in residence info :{}", request.getSession().getAttribute("disqCriteria"));
        log.info("kkkkkkkkkkkkkkkkkkkkk disqCriteria session after logout residence info :{}", session.getAttribute("disqCriteria"));
        model.addAttribute("uuid", uuid);
        return new ModelAndView("residenceInfo");
    }
    
    @GetMapping("/results")
    protected ModelAndView residenceInfoResults(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){
        

       
        
        // KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        // log.info("rrrrrrrrrrrrrrrrrrrrrrrrr otherclaims residence :{}", context.getIdToken().getOtherClaims());
        // log.info("rrrrrrrrrrrrrrrrrrrrrrrrr name residence:{}", context.getIdToken().getName());
        // log.info("zzzzzzzzzzzzzzzzzzzzzz context :{}", context);
        //log.info("zzzzzzzzzzzzzzzzzzzzzz oidcUser :{}", oidcUser.getAttributes());
        // log.info("zzzzzzzzzzzzzzzzzzzzzz token :{}", context.getIdToken());
        // log.info("zzzzzzzzzzzzzzzzzzzzzz name :{}", context.getIdToken().getOtherClaims());
        // params.setSsiInfo(context.getIdToken(). getOtherClaims());
        // model.addAttribute("userAttr", context.getIdToken().getOtherClaims());

        //request.getSession().setAttribute("residenceInformation", residenceInfo);
        //params.setSsiInfo(oidcUser.getAttributes());
        //params.setSsiInfo(context.getIdToken().getOtherClaims());
        //params.setSsiInfo(oidcUser.getAttributes());
        // KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        // model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());
        // model.addAttribute("uuid", uuid);
        infoService.populateFetchInfo(model, request, uuid);
        log.info("dddddddddddddddddddddd after populate :{}", model.getAttribute("uuid"));

        return new ModelAndView("residenceInfo");
    
    }

    @GetMapping("/save")
    protected ModelAndView residenceInfoSave(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, RedirectAttributes attr, ModelMap model, HttpServletRequest request){
        
        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, context, FormType.RESIDENCE_INFO.value, uuid);
        cacheService.putInfo(ssiApp, uuid);
        attr.addAttribute("uuid", uuid);

        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView("redirect:/multi/electricityBill/view");
    
    }

    // private ResidenceInfo fillResidenceInfo(OidcUser user, RequestParams params){
    //     ResidenceInfo residenceInfo = new ResidenceInfo();

    //     residenceInfo.setStreet(user.getAttribute("street"));
    //     residenceInfo.setStreetNumber(user.getAttribute("streetNumber"));
    //     residenceInfo.setPo(user.getAttribute("po"));
    //     residenceInfo.setMunicipality(user.getAttribute("municipality"));
    //     residenceInfo.setPrefecture(user.getAttribute("prefecture"));
    //     residenceInfo.setOwnership(user.getAttribute("ownership"));
    //     residenceInfo.setSupplyType(String.valueOf(params.getSsiInfo().get("supplyType")));
    //     residenceInfo.setMeterNumber(String.valueOf(params.getSsiInfo().get("meterNumber")));

    //     return residenceInfo;
    // }

    private void fillResidenceInfo(SsiApplication ssiApp, KeycloakSecurityContext context){
        
        ssiApp.setStreet(String.valueOf(context.getIdToken().getOtherClaims().get("street")));
        ssiApp.setStreetNumber(String.valueOf(context.getIdToken().getOtherClaims().get("streetNumber")));
        ssiApp.setPo(String.valueOf(context.getIdToken().getOtherClaims().get("po")));
        ssiApp.setMunicipality(String.valueOf(context.getIdToken().getOtherClaims().get("municipality")));
        ssiApp.setPrefecture(String.valueOf(context.getIdToken().getOtherClaims().get("prefecture")));
        ssiApp.setOwnership(String.valueOf(context.getIdToken().getOtherClaims().get("ownership")));
        ssiApp.setSupplyType(String.valueOf(context.getIdToken().getOtherClaims().get("supplyType")));
        ssiApp.setMeterNumber(String.valueOf(context.getIdToken().getOtherClaims().get("meterNumber")));

    }
    
}