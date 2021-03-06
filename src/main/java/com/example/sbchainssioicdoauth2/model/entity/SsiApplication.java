package com.example.sbchainssioicdoauth2.model.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SsiApplication {

    @Id
    private String id;

    private String uuid;

    //personal info
    private String ssn;
    private String afm;
    private String surname;
    private String name;
    private String fatherName;
    private String motherName;
    private String latinSurname;
    private String latinName;
    private String latinFatherName;
    private String latinMotherName;

    //personal declaration
    private String hospitalized;
    private String hospitalizedSpecific;
    private String monk;
    private String luxury;

    //residence info
    private String street;
    private String streetNumber;
    private String po;
    private String municipality;
    private String prefecture;
    private String ownership;
    private String supplyType;
    private String meterNumber;

    //Fead
    private String participateFead;
    private String selectProvider;

    //demographic info
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String maritalStatus;
    private String disabilityStatus;
    private String levelOfEducation;

    //employment info
    private String employmentStatus;
    private String unemployed;
    private String oaedId;
    private String oaedDate;

    //contact info
    private String email;
    private String mobilePhone;
    private String landline;
    private String iban;
    private Map<String, String> mailAddress = new HashMap<>();

    //parenthood info
    private String parenthood;
    private String custody;
    private String additionalAdults;

    //financial info
    private String salariesR;
    private String pensionsR;
    private String farmingR;
    private String freelanceR;
    private String rentIncomeR;
    private String unemploymentBenefitR;
    private String otherBenefitsR;
    private String ekasR;
    private String otherIncomeR;
    private String ergomeR;
    private String depositInterestA;
    private String depositsA;
    private String domesticRealEstateA;
    private String foreignRealEstateA;
    private String vehicleValueA;
    private String investmentsA;
    
    //household composition
    private Map<String, String> householdComposition = new HashMap<>();

    //income guarantee
    private String monthlyGuarantee;
    private String totalIncome;
    private String monthlyIncome;
    private String monthlyAid;
    
}