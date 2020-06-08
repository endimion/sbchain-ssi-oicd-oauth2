package com.example.sbchainssioicdoauth2.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.example.sbchainssioicdoauth2.model.entity.SsiApplication;
import com.example.sbchainssioicdoauth2.utils.FormType;

import org.keycloak.KeycloakSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PopulateInfoService {

    public void populateFetchInfo(ModelMap model, HttpServletRequest request, String uuid){

        KeycloakSecurityContext context = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        model.addAttribute("ssiInfo", context.getIdToken().getOtherClaims());
        model.addAttribute("uuid", uuid);
    }

    public void populateSsiApp(SsiApplication ssiApp, KeycloakSecurityContext context, String formType, String uuid){

        ssiApp.setUuid(uuid);

        if(formType.equals(FormType.PERSONAL_INFO.value)){
            ssiApp.setSsn(String.valueOf(context.getIdToken().getOtherClaims().get("ssn")));
            ssiApp.setAfm(String.valueOf(context.getIdToken().getOtherClaims().get("afm")));
            ssiApp.setSurname(context.getIdToken().getFamilyName());
            ssiApp.setName(context.getIdToken().getGivenName());
            ssiApp.setFatherName(String.valueOf(context.getIdToken().getOtherClaims().get("fatherName")));
            ssiApp.setMotherName(String.valueOf(context.getIdToken().getOtherClaims().get("motherName")));
            ssiApp.setLatinSurname(String.valueOf(context.getIdToken().getOtherClaims().get("latinSurname")));
            ssiApp.setLatinName(String.valueOf(context.getIdToken().getOtherClaims().get("latinName")));
            ssiApp.setLatinFatherName(String.valueOf(context.getIdToken().getOtherClaims().get("latinFatherName")));
            ssiApp.setLatinMotherName(String.valueOf(context.getIdToken().getOtherClaims().get("latinMothername")));
            ssiApp.setDateOfBirth(String.valueOf(context.getIdToken().getOtherClaims().get("dateOfBirth")));
            ssiApp.setGender(String.valueOf(context.getIdToken().getOtherClaims().get("gender")));
            ssiApp.setNationality(String.valueOf(context.getIdToken().getOtherClaims().get("nationality")));
            ssiApp.setMaritalStatus(String.valueOf(context.getIdToken().getOtherClaims().get("maritalStatus")));
            ssiApp.setDisabilityStatus(String.valueOf(context.getIdToken().getOtherClaims().get("disabilityStatus")));
            ssiApp.setLevelOfEducation(String.valueOf(context.getIdToken().getOtherClaims().get("levelOfEducation")));
        }
        if(formType.equals(FormType.PERSONAL_DECLARATION.value)){
            ssiApp.setHospitalized(String.valueOf(context.getIdToken().getOtherClaims().get("hospitalized")));
            ssiApp.setHospitalizedSpecific(String.valueOf(context.getIdToken().getOtherClaims().get("hospitalizedSpecific")));
            ssiApp.setMonk(String.valueOf(context.getIdToken().getOtherClaims().get("monk")));
            ssiApp.setLuxury(String.valueOf(context.getIdToken().getOtherClaims().get("luxury")));
        }
        if(formType.equals(FormType.RESIDENCE_INFO.value)){
            ssiApp.setStreet(String.valueOf(context.getIdToken().getOtherClaims().get("street")));
            ssiApp.setStreetNumber(String.valueOf(context.getIdToken().getOtherClaims().get("streetNumber")));
            ssiApp.setPo(String.valueOf(context.getIdToken().getOtherClaims().get("po")));
            ssiApp.setMunicipality(String.valueOf(context.getIdToken().getOtherClaims().get("municipality")));
            ssiApp.setPrefecture(String.valueOf(context.getIdToken().getOtherClaims().get("prefecture")));
            
        }
        if(formType.equals(FormType.FEAD.value)){
            ssiApp.setParticipateFead(String.valueOf(context.getIdToken().getOtherClaims().get("participateFead")));
            ssiApp.setSelectProvider(String.valueOf(context.getIdToken().getOtherClaims().get("selectProvider")));
        }
        if(formType.equals(FormType.ELECTRICITY_BILL_INFO.value)){
            ssiApp.setOwnership(String.valueOf(context.getIdToken().getOtherClaims().get("ownership")));
            ssiApp.setSupplyType(String.valueOf(context.getIdToken().getOtherClaims().get("supplyType")));
            ssiApp.setMeterNumber(String.valueOf(context.getIdToken().getOtherClaims().get("meterNumber")));
        }
        if(formType.equals(FormType.EMPLOYMENT_INFO.value)){
            ssiApp.setEmploymentStatus(String.valueOf(context.getIdToken().getOtherClaims().get("employmentStatus")));
            ssiApp.setUnemployed(String.valueOf(context.getIdToken().getOtherClaims().get("unemployed")));
            ssiApp.setOaedId(String.valueOf(context.getIdToken().getOtherClaims().get("oaedId")));
            ssiApp.setOaedDate(String.valueOf(context.getIdToken().getOtherClaims().get("oaedDate")));
        }
        if(formType.equals(FormType.CONTACT_INFO.value)){
            ssiApp.setEmail(String.valueOf(context.getIdToken().getEmail()));
            ssiApp.setMobilePhone(String.valueOf(context.getIdToken().getOtherClaims().get("mobilePhone")));
            ssiApp.setLandline(String.valueOf(context.getIdToken().getOtherClaims().get("landline")));
            ssiApp.setIban(String.valueOf(context.getIdToken().getOtherClaims().get("iban")));
            Map<String, String> mailAddress = new HashMap<>();
            mailAddress.put("street", String.valueOf(context.getIdToken().getOtherClaims().get("street")));
            mailAddress.put("streetNumber", String.valueOf(context.getIdToken().getOtherClaims().get("streetNumber")));
            mailAddress.put("PO", String.valueOf(context.getIdToken().getOtherClaims().get("PO")));
            mailAddress.put("municipality", String.valueOf(context.getIdToken().getOtherClaims().get("municipality")));
            mailAddress.put("prefecture", String.valueOf(context.getIdToken().getOtherClaims().get("prefecture")));
            ssiApp.setMailAddress(mailAddress);
        }
        if(formType.equals(FormType.PARENTHOOD_INFO.value)){
            ssiApp.setParenthood(String.valueOf(context.getIdToken().getOtherClaims().get("parenthood")));
            ssiApp.setCustody(String.valueOf(context.getIdToken().getOtherClaims().get("custody")));
            ssiApp.setAdditionalAdults(String.valueOf(context.getIdToken().getOtherClaims().get("additionalAdults")));
        }
        if(formType.equals(FormType.FINANCIAL_INFO.value)){
            ssiApp.setSalariesR(String.valueOf(context.getIdToken().getOtherClaims().get("salariesR")));
            ssiApp.setPensionsR(String.valueOf(context.getIdToken().getOtherClaims().get("pensionsR")));
            ssiApp.setFarmingR(String.valueOf(context.getIdToken().getOtherClaims().get("farmingR")));
            ssiApp.setFreelanceR(String.valueOf(context.getIdToken().getOtherClaims().get("freelanceR")));
            ssiApp.setRentIncomeR(String.valueOf(context.getIdToken().getOtherClaims().get("rentIncomeR")));
            ssiApp.setUnemploymentBenefitR(String.valueOf(context.getIdToken().getOtherClaims().get("unemploymentBenefitR")));
            ssiApp.setOtherBenefitsR(String.valueOf(context.getIdToken().getOtherClaims().get("otherBenefitsR")));
            ssiApp.setEkasR(String.valueOf(context.getIdToken().getOtherClaims().get("ekasR")));
            ssiApp.setOtherIncomeR(String.valueOf(context.getIdToken().getOtherClaims().get("otherIncomeR")));
            ssiApp.setErgomeR(String.valueOf(context.getIdToken().getOtherClaims().get("ergomeR")));
            
        }

        if(formType.equals(FormType.ASSET_INFO.value)){
            ssiApp.setDepositInterestA(String.valueOf(context.getIdToken().getOtherClaims().get("depositInterestA")));
            ssiApp.setDepositsA(String.valueOf(context.getIdToken().getOtherClaims().get("depositsA")));
            ssiApp.setDomesticRealEstateA(String.valueOf(context.getIdToken().getOtherClaims().get("domesticRealEstateA")));
            ssiApp.setForeignRealEstateA(String.valueOf(context.getIdToken().getOtherClaims().get("foreignRealEstateA")));
            ssiApp.setVehicleValueA(String.valueOf(context.getIdToken().getOtherClaims().get("vehicleValueA")));
            ssiApp.setInvestmentsA(String.valueOf(context.getIdToken().getOtherClaims().get("investmentsA")));
        }

        if(formType.equals(FormType.HOUSEHOLD_COMPOSITION.value)){
            Map<String, String> householdComposition = new HashMap<>();
            householdComposition.put(String.valueOf(context.getIdToken().getOtherClaims().get("member")), String.valueOf(context.getIdToken().getOtherClaims().get("relation")));
            ssiApp.setHouseholdComposition(householdComposition);
        }
        if(formType.equals(FormType.INCOME_GUARANTEE.value)){
            ssiApp.setMonthlyGuarantee(String.valueOf(context.getIdToken().getOtherClaims().get("monthlyGuarantee")));
            ssiApp.setTotalIncome(String.valueOf(context.getIdToken().getOtherClaims().get("totalIncome")));
            ssiApp.setMonthlyIncome(String.valueOf(context.getIdToken().getOtherClaims().get("monthlyIncome")));
            ssiApp.setMonthlyAid(String.valueOf(context.getIdToken().getOtherClaims().get("monthlyAid")));
        }
    }
    
}