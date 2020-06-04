package com.example.sbchainssioicdoauth2.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.service.CacheService;
import com.example.sbchainssioicdoauth2.service.PopulateInfoService;
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
@RequestMapping("/householdInfo")
public class HouseholdController {

    // @Autowired
    // CacheService cacheService;

    @Autowired
    CacheService cacheService;
    
    @Autowired
    PopulateInfoService infoService;

    private static final String SSI_REQUEST_PARAMS = "ssiInformation";
    
    @GetMapping("/view")
    protected ModelAndView householdInfo(@AuthenticationPrincipal OidcUser oidcUser, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){
        infoService.populateFetchInfo(model, request, uuid);
        return new ModelAndView("householdInfo");
    }
    
    @GetMapping("/save")
    protected ModelAndView householdInfoSubmit(@AuthenticationPrincipal OidcUser oidcUser, RedirectAttributes attr, @RequestParam(value = "uuid", required = true) String uuid, ModelMap model, HttpServletRequest request){

        //model.addAttribute("userAttr", oidcUser.getAttributes());
        // FinancialInfo financialInfo = fillFinancialInfo(oidcUser);

        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        SsiApplication ssiApp = cacheService.get(uuid);
        infoService.populateSsiApp(ssiApp, context, FormType.HOUSEHOLD_COMPOSITION.value, uuid);
        cacheService.putInfo(ssiApp, uuid);
        attr.addAttribute("uuid", uuid);

        try {
            request.logout();
        } catch (ServletException e) {
            log.error(e.getMessage());
        }

        return new ModelAndView("redirect:/notifications/view");
    }

    // private FinancialInfo fillFinancialInfo(OidcUser user){
        
    //     FinancialInfo financialInfo = new FinancialInfo();
    //     financialInfo.setSalariesR(user.getAttribute("salariesR"));
    //     financialInfo.setPensionsR(user.getAttribute("pensionsR"));
    //     financialInfo.setFarmingR(user.getAttribute("farmingR"));
    //     financialInfo.setFreelanceR(user.getAttribute("freelanceR"));
    //     financialInfo.setRentIncomeR(user.getAttribute("rentIncomeR"));
    //     financialInfo.setUnemploymentBenefitR(user.getAttribute("unemploymentBenefitR"));
    //     financialInfo.setOtherBenefitsR(user.getAttribute("otherBenefitsR"));
    //     financialInfo.setEkasR(user.getAttribute("ekasR"));
    //     financialInfo.setOtherIncomeR(user.getAttribute("otherIncomeR"));
    //     financialInfo.setErgomeR(user.getAttribute("ergomeR"));
    //     financialInfo.setDepositInterestA(user.getAttribute("depositInterestA"));
    //     financialInfo.setDepositsA(user.getAttribute("depositsA"));
    //     financialInfo.setDomesticRealEstateA(user.getAttribute("domesticRealEstateA"));
    //     financialInfo.setForeignRealEstateA(user.getAttribute("foreignRealEstateA"));
    //     financialInfo.setVehicleValueA(user.getAttribute("vehicleValueA"));
    //     financialInfo.setInvestmentsA(user.getAttribute("investmentsA"));

    //     return financialInfo;
    // }

}