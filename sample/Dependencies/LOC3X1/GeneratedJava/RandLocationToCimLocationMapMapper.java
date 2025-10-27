package com.crm3services.location.mapper;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dto.rand.chubb.com:Location;

/**
 * RandLocationToCimLocationMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from RandLocationToCimLocationMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: dto.rand.chubb.com:Location ()
 * Target: Location:Location ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class RandLocationToCimLocationMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(RandLocationToCimLocationMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for dto.rand.chubb.com:Location
     */
    public static class dto.rand.chubb.com:Location {
        private String cityName;

        public String getcityName() {
            return cityName;
        }

        public void setcityName(String cityName) {
            this.cityName = cityName;
        }

        private String countyName;

        public String getcountyName() {
            return countyName;
        }

        public void setcountyName(String countyName) {
            this.countyName = countyName;
        }

        private String stateOrProvinceCode;

        public String getstateOrProvinceCode() {
            return stateOrProvinceCode;
        }

        public void setstateOrProvinceCode(String stateOrProvinceCode) {
            this.stateOrProvinceCode = stateOrProvinceCode;
        }

        private String stateOrProvinceName;

        public String getstateOrProvinceName() {
            return stateOrProvinceName;
        }

        public void setstateOrProvinceName(String stateOrProvinceName) {
            this.stateOrProvinceName = stateOrProvinceName;
        }

        private String postalStateAbbreviation;

        public String getpostalStateAbbreviation() {
            return postalStateAbbreviation;
        }

        public void setpostalStateAbbreviation(String postalStateAbbreviation) {
            this.postalStateAbbreviation = postalStateAbbreviation;
        }

        private String postalCode;

        public String getpostalCode() {
            return postalCode;
        }

        public void setpostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        private String countryCode;

        public String getcountryCode() {
            return countryCode;
        }

        public void setcountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        private String countryName;

        public String getcountryName() {
            return countryName;
        }

        public void setcountryName(String countryName) {
            this.countryName = countryName;
        }

        private String locationPlaceCode;

        public String getlocationPlaceCode() {
            return locationPlaceCode;
        }

        public void setlocationPlaceCode(String locationPlaceCode) {
            this.locationPlaceCode = locationPlaceCode;
        }

        private String poBoxIndicator;

        public String getpoBoxIndicator() {
            return poBoxIndicator;
        }

        public void setpoBoxIndicator(String poBoxIndicator) {
            this.poBoxIndicator = poBoxIndicator;
        }

        private String cityCode;

        public String getcityCode() {
            return cityCode;
        }

        public void setcityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        private String countyCode;

        public String getcountyCode() {
            return countyCode;
        }

        public void setcountyCode(String countyCode) {
            this.countyCode = countyCode;
        }

        private String fireDistrictCode;

        public String getfireDistrictCode() {
            return fireDistrictCode;
        }

        public void setfireDistrictCode(String fireDistrictCode) {
            this.fireDistrictCode = fireDistrictCode;
        }

        private String taxInfo/licenseCode;

        public String gettaxInfo/licenseCode() {
            return taxInfo/licenseCode;
        }

        public void settaxInfo/licenseCode(String taxInfo/licenseCode) {
            this.taxInfo/licenseCode = taxInfo/licenseCode;
        }

    }

    /**
     * Target model class for Location:Location
     */
    public static class Location:Location {
        private String StandardizedAddress/CityName;

        public String getStandardizedAddress/CityName() {
            return StandardizedAddress/CityName;
        }

        public void setStandardizedAddress/CityName(String StandardizedAddress/CityName) {
            this.StandardizedAddress/CityName = StandardizedAddress/CityName;
        }

        private String StandardizedAddress/CountyName;

        public String getStandardizedAddress/CountyName() {
            return StandardizedAddress/CountyName;
        }

        public void setStandardizedAddress/CountyName(String StandardizedAddress/CountyName) {
            this.StandardizedAddress/CountyName = StandardizedAddress/CountyName;
        }

        private String StandardizedAddress/StateOrProvinceCode;

        public String getStandardizedAddress/StateOrProvinceCode() {
            return StandardizedAddress/StateOrProvinceCode;
        }

        public void setStandardizedAddress/StateOrProvinceCode(String StandardizedAddress/StateOrProvinceCode) {
            this.StandardizedAddress/StateOrProvinceCode = StandardizedAddress/StateOrProvinceCode;
        }

        private String StandardizedAddress/StateOrProvinceName;

        public String getStandardizedAddress/StateOrProvinceName() {
            return StandardizedAddress/StateOrProvinceName;
        }

        public void setStandardizedAddress/StateOrProvinceName(String StandardizedAddress/StateOrProvinceName) {
            this.StandardizedAddress/StateOrProvinceName = StandardizedAddress/StateOrProvinceName;
        }

        private String StandardizedAddress/PostalStateAbbreviation;

        public String getStandardizedAddress/PostalStateAbbreviation() {
            return StandardizedAddress/PostalStateAbbreviation;
        }

        public void setStandardizedAddress/PostalStateAbbreviation(String StandardizedAddress/PostalStateAbbreviation) {
            this.StandardizedAddress/PostalStateAbbreviation = StandardizedAddress/PostalStateAbbreviation;
        }

        private String StandardizedAddress/PostalCode;

        public String getStandardizedAddress/PostalCode() {
            return StandardizedAddress/PostalCode;
        }

        public void setStandardizedAddress/PostalCode(String StandardizedAddress/PostalCode) {
            this.StandardizedAddress/PostalCode = StandardizedAddress/PostalCode;
        }

        private String StandardizedAddress/CountryCode;

        public String getStandardizedAddress/CountryCode() {
            return StandardizedAddress/CountryCode;
        }

        public void setStandardizedAddress/CountryCode(String StandardizedAddress/CountryCode) {
            this.StandardizedAddress/CountryCode = StandardizedAddress/CountryCode;
        }

        private String StandardizedAddress/CountryName;

        public String getStandardizedAddress/CountryName() {
            return StandardizedAddress/CountryName;
        }

        public void setStandardizedAddress/CountryName(String StandardizedAddress/CountryName) {
            this.StandardizedAddress/CountryName = StandardizedAddress/CountryName;
        }

        private String StandardizedAddress/LocationPlaceCode;

        public String getStandardizedAddress/LocationPlaceCode() {
            return StandardizedAddress/LocationPlaceCode;
        }

        public void setStandardizedAddress/LocationPlaceCode(String StandardizedAddress/LocationPlaceCode) {
            this.StandardizedAddress/LocationPlaceCode = StandardizedAddress/LocationPlaceCode;
        }

        private String StandardizedAddress/POBoxIndicator;

        public String getStandardizedAddress/POBoxIndicator() {
            return StandardizedAddress/POBoxIndicator;
        }

        public void setStandardizedAddress/POBoxIndicator(String StandardizedAddress/POBoxIndicator) {
            this.StandardizedAddress/POBoxIndicator = StandardizedAddress/POBoxIndicator;
        }

        private String LocationInformation/CityCode;

        public String getLocationInformation/CityCode() {
            return LocationInformation/CityCode;
        }

        public void setLocationInformation/CityCode(String LocationInformation/CityCode) {
            this.LocationInformation/CityCode = LocationInformation/CityCode;
        }

        private String LocationInformation/CountyCode;

        public String getLocationInformation/CountyCode() {
            return LocationInformation/CountyCode;
        }

        public void setLocationInformation/CountyCode(String LocationInformation/CountyCode) {
            this.LocationInformation/CountyCode = LocationInformation/CountyCode;
        }

        private String LocationInformation/FireDistrictCode;

        public String getLocationInformation/FireDistrictCode() {
            return LocationInformation/FireDistrictCode;
        }

        public void setLocationInformation/FireDistrictCode(String LocationInformation/FireDistrictCode) {
            this.LocationInformation/FireDistrictCode = LocationInformation/FireDistrictCode;
        }

        private String LocationInformation/LicenseCode;

        public String getLocationInformation/LicenseCode() {
            return LocationInformation/LicenseCode;
        }

        public void setLocationInformation/LicenseCode(String LocationInformation/LicenseCode) {
            this.LocationInformation/LicenseCode = LocationInformation/LicenseCode;
        }

    }

    /**
     * Main mapping method to transform dto.rand.chubb.com:Location to Location:Location
     */
    public Location:Location mapdto.rand.chubb.com:LocationToLocation:Location(dto.rand.chubb.com:Location source) {
        try {
            logger.info("Starting mapping from dto.rand.chubb.com:Location to Location:Location");
            
            // Create output Location:Location object
            Location:Location target = new Location:Location();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from dto.rand.chubb.com:Location to Location:Location");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map dto.rand.chubb.com:Location to Location:Location", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(dto.rand.chubb.com:Location source, Location:Location target) {
        // Execute mappings in order
        executeMapping1(source, target);
        executeMapping2(source, target);
        executeMapping3(source, target);
        executeMapping4(source, target);
        executeMapping5(source, target);
        executeMapping6(source, target);
        executeMapping7(source, target);
        executeMapping8(source, target);
        executeMapping9(source, target);
        executeMapping10(source, target);
        executeMapping11(source, target);
        executeMapping12(source, target);
        executeMapping13(source, target);
        executeMapping14(source, target);
    }

    /**
     * Execute mapping 1: Custom mapping from cityName to StandardizedAddress/CityName with Java logic
     * Source: cityName ()
     * Target: StandardizedAddress/CityName ()
     */
    private void executeMapping1(Object source, Object target) {
        try {
            // Custom mapping: cityName -> StandardizedAddress/CityName
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 1");

            // Custom Java code from MAP file:
            java.lang.String __Location_cityName = (java.lang.String)Location_cityName;
            java.lang.String __Location_1_StandardizedAddress_CityName = (java.lang.String)Location_1_StandardizedAddress_CityName;
            boolean __result__1 = __Location_cityName != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_cityName.trim();
            __Location_1_StandardizedAddress_CityName = __result__5;
            Location_1_StandardizedAddress_CityName = __Location_1_StandardizedAddress_CityName;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_cityName" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_cityName != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_cityName" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CityName" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 1: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 2: Custom mapping from countyName to StandardizedAddress/CountyName with Java logic
     * Source: countyName ()
     * Target: StandardizedAddress/CountyName ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Custom mapping: countyName -> StandardizedAddress/CountyName
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 2");

            // Custom Java code from MAP file:
            java.lang.String __Location_countyName = (java.lang.String)Location_countyName;
            java.lang.String __Location_1_StandardizedAddress_CountyName = (java.lang.String)Location_1_StandardizedAddress_CountyName;
            boolean __result__1 = __Location_countyName != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_countyName.trim();
            __Location_1_StandardizedAddress_CountyName = __result__5;
            Location_1_StandardizedAddress_CountyName = __Location_1_StandardizedAddress_CountyName;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_countyName" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countyName != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countyName" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountyName" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Custom mapping from stateOrProvinceCode to StandardizedAddress/StateOrProvinceCode with Java logic
     * Source: stateOrProvinceCode ()
     * Target: StandardizedAddress/StateOrProvinceCode ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Custom mapping: stateOrProvinceCode -> StandardizedAddress/StateOrProvinceCode
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 3");

            // Custom Java code from MAP file:
            java.lang.String __Location_stateOrProvinceCode = (java.lang.String)Location_stateOrProvinceCode;
            java.lang.String __Location_1_StandardizedAddress_StateOrProvinceCode = (java.lang.String)Location_1_StandardizedAddress_StateOrProvinceCode;
            boolean __result__1 = __Location_stateOrProvinceCode != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_stateOrProvinceCode.trim();
            __Location_1_StandardizedAddress_StateOrProvinceCode = __result__5;
            Location_1_StandardizedAddress_StateOrProvinceCode = __Location_1_StandardizedAddress_StateOrProvinceCode;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_stateOrProvinceCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceCode" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_StateOrProvinceCode" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: Custom mapping from stateOrProvinceName to StandardizedAddress/StateOrProvinceName with Java logic
     * Source: stateOrProvinceName ()
     * Target: StandardizedAddress/StateOrProvinceName ()
     */
    private void executeMapping4(Object source, Object target) {
        try {
            // Custom mapping: stateOrProvinceName -> StandardizedAddress/StateOrProvinceName
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 4");

            // Custom Java code from MAP file:
            java.lang.String __Location_stateOrProvinceName = (java.lang.String)Location_stateOrProvinceName;
            java.lang.String __Location_1_StandardizedAddress_StateOrProvinceName = (java.lang.String)Location_1_StandardizedAddress_StateOrProvinceName;
            boolean __result__1 = __Location_stateOrProvinceName != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_stateOrProvinceName.trim();
            __Location_1_StandardizedAddress_StateOrProvinceName = __result__5;
            Location_1_StandardizedAddress_StateOrProvinceName = __Location_1_StandardizedAddress_StateOrProvinceName;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_stateOrProvinceName" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_StateOrProvinceName" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 4: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 5: Move mapping from postalStateAbbreviation to StandardizedAddress/PostalStateAbbreviation
     * Source: postalStateAbbreviation ()
     * Target: StandardizedAddress/PostalStateAbbreviation ()
     */
    private void executeMapping5(Object source, Object target) {
        try {
            // Move mapping: postalStateAbbreviation -> StandardizedAddress/PostalStateAbbreviation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof postalStateAbbreviation && target instanceof StandardizedAddress/PostalStateAbbreviation) {
                postalStateAbbreviation sourceObj = (postalStateAbbreviation) source;
                StandardizedAddress/PostalStateAbbreviation targetObj = (StandardizedAddress/PostalStateAbbreviation) target;

                // Map postalStateAbbreviation to StandardizedAddress/PostalStateAbbreviation
                if (sourceObj.getpostalStateAbbreviation() != null) {
                    targetObj.setStandardizedAddress/PostalStateAbbreviation(sourceObj.getpostalStateAbbreviation());
                    logger.debug("Moved {0} -> {1}: {2}", "postalStateAbbreviation", "StandardizedAddress/PostalStateAbbreviation", sourceObj.getpostalStateAbbreviation());
                } else {
                    logger.debug("Source property postalStateAbbreviation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "postalStateAbbreviation", "StandardizedAddress/PostalStateAbbreviation");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 5: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 6: Custom mapping from postalCode to StandardizedAddress/PostalCode with Java logic
     * Source: postalCode ()
     * Target: StandardizedAddress/PostalCode ()
     */
    private void executeMapping6(Object source, Object target) {
        try {
            // Custom mapping: postalCode -> StandardizedAddress/PostalCode
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 6");

            // Custom Java code from MAP file:
            java.lang.String __Location_postalCode = (java.lang.String)Location_postalCode;
            java.lang.String __Location_1_StandardizedAddress_PostalCode = (java.lang.String)Location_1_StandardizedAddress_PostalCode;
            boolean __result__1 = __Location_postalCode != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_postalCode.trim();
            __Location_1_StandardizedAddress_PostalCode = __result__5;
            Location_1_StandardizedAddress_PostalCode = __Location_1_StandardizedAddress_PostalCode;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_postalCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_postalCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_postalCode" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_PostalCode" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 6: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 7: Custom mapping from countryCode to StandardizedAddress/CountryCode with Java logic
     * Source: countryCode ()
     * Target: StandardizedAddress/CountryCode ()
     */
    private void executeMapping7(Object source, Object target) {
        try {
            // Custom mapping: countryCode -> StandardizedAddress/CountryCode
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 7");

            // Custom Java code from MAP file:
            java.lang.String __Location_countryCode = (java.lang.String)Location_countryCode;
            java.lang.String __Location_1_StandardizedAddress_CountryCode = (java.lang.String)Location_1_StandardizedAddress_CountryCode;
            boolean __result__1 = __Location_countryCode != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_countryCode.trim();
            __Location_1_StandardizedAddress_CountryCode = __result__5;
            Location_1_StandardizedAddress_CountryCode = __Location_1_StandardizedAddress_CountryCode;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_countryCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryCode" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountryCode" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 7: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 8: Custom mapping from countryName to StandardizedAddress/CountryName with Java logic
     * Source: countryName ()
     * Target: StandardizedAddress/CountryName ()
     */
    private void executeMapping8(Object source, Object target) {
        try {
            // Custom mapping: countryName -> StandardizedAddress/CountryName
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 8");

            // Custom Java code from MAP file:
            java.lang.String __Location_countryName = (java.lang.String)Location_countryName;
            java.lang.String __Location_1_StandardizedAddress_CountryName = (java.lang.String)Location_1_StandardizedAddress_CountryName;
            boolean __result__1 = __Location_countryName != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_countryName.trim();
            __Location_1_StandardizedAddress_CountryName = __result__5;
            Location_1_StandardizedAddress_CountryName = __Location_1_StandardizedAddress_CountryName;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_countryName" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryName != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryName" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountryName" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 8: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 9: Move mapping from locationPlaceCode to StandardizedAddress/LocationPlaceCode
     * Source: locationPlaceCode ()
     * Target: StandardizedAddress/LocationPlaceCode ()
     */
    private void executeMapping9(Object source, Object target) {
        try {
            // Move mapping: locationPlaceCode -> StandardizedAddress/LocationPlaceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof locationPlaceCode && target instanceof StandardizedAddress/LocationPlaceCode) {
                locationPlaceCode sourceObj = (locationPlaceCode) source;
                StandardizedAddress/LocationPlaceCode targetObj = (StandardizedAddress/LocationPlaceCode) target;

                // Map locationPlaceCode to StandardizedAddress/LocationPlaceCode
                if (sourceObj.getlocationPlaceCode() != null) {
                    targetObj.setStandardizedAddress/LocationPlaceCode(sourceObj.getlocationPlaceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "locationPlaceCode", "StandardizedAddress/LocationPlaceCode", sourceObj.getlocationPlaceCode());
                } else {
                    logger.debug("Source property locationPlaceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "locationPlaceCode", "StandardizedAddress/LocationPlaceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 9: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 10: Custom mapping from poBoxIndicator to StandardizedAddress/POBoxIndicator with Java logic
     * Source: poBoxIndicator ()
     * Target: StandardizedAddress/POBoxIndicator ()
     */
    private void executeMapping10(Object source, Object target) {
        try {
            // Custom mapping: poBoxIndicator -> StandardizedAddress/POBoxIndicator
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 10");

            // Custom Java code from MAP file:
            java.lang.String __Location_poBoxIndicator = (java.lang.String)Location_poBoxIndicator;
            java.lang.Boolean __Location_1_StandardizedAddress_POBoxIndicator = (java.lang.Boolean)Location_1_StandardizedAddress_POBoxIndicator;
            java.lang.String source = __Location_poBoxIndicator;
            boolean __result__3 = "Y".equalsIgnoreCase(source);
            __Location_1_StandardizedAddress_POBoxIndicator = new java.lang.Boolean(__result__3);
            Location_1_StandardizedAddress_POBoxIndicator = __Location_1_StandardizedAddress_POBoxIndicator;
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_poBoxIndicator" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_poBoxIndicator" variable="true" assignable="false" input="true">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.0/@dataOutputs.0" value="source" localVariable="//@localVariables.0" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;Y&quot;.equalsIgnoreCase( source)" assignable="false">
            //    <dataOutputs target="//@executableElements.3"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.2/@dataOutputs.0" value="Location_1_StandardizedAddress_POBoxIndicator" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="boolean" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <localVariables name="source">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </localVariables>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //  <executableGroups executableElements="//@executableElements.2 //@executableElements.3"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!2:3,1
            //!SMAP!3:4,1
            //!SMAP!4:5,2
            //!SMAP!1000000:44,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 10: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 11: Move mapping from cityCode to LocationInformation/CityCode
     * Source: cityCode ()
     * Target: LocationInformation/CityCode ()
     */
    private void executeMapping11(Object source, Object target) {
        try {
            // Move mapping: cityCode -> LocationInformation/CityCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof cityCode && target instanceof LocationInformation/CityCode) {
                cityCode sourceObj = (cityCode) source;
                LocationInformation/CityCode targetObj = (LocationInformation/CityCode) target;

                // Map cityCode to LocationInformation/CityCode
                if (sourceObj.getcityCode() != null) {
                    targetObj.setLocationInformation/CityCode(sourceObj.getcityCode());
                    logger.debug("Moved {0} -> {1}: {2}", "cityCode", "LocationInformation/CityCode", sourceObj.getcityCode());
                } else {
                    logger.debug("Source property cityCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "cityCode", "LocationInformation/CityCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 11: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 12: Move mapping from countyCode to LocationInformation/CountyCode
     * Source: countyCode ()
     * Target: LocationInformation/CountyCode ()
     */
    private void executeMapping12(Object source, Object target) {
        try {
            // Move mapping: countyCode -> LocationInformation/CountyCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof countyCode && target instanceof LocationInformation/CountyCode) {
                countyCode sourceObj = (countyCode) source;
                LocationInformation/CountyCode targetObj = (LocationInformation/CountyCode) target;

                // Map countyCode to LocationInformation/CountyCode
                if (sourceObj.getcountyCode() != null) {
                    targetObj.setLocationInformation/CountyCode(sourceObj.getcountyCode());
                    logger.debug("Moved {0} -> {1}: {2}", "countyCode", "LocationInformation/CountyCode", sourceObj.getcountyCode());
                } else {
                    logger.debug("Source property countyCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "countyCode", "LocationInformation/CountyCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 12: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 13: Custom mapping from fireDistrictCode to LocationInformation/FireDistrictCode with Java logic
     * Source: fireDistrictCode ()
     * Target: LocationInformation/FireDistrictCode ()
     */
    private void executeMapping13(Object source, Object target) {
        try {
            // Custom mapping: fireDistrictCode -> LocationInformation/FireDistrictCode
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 13");

            // Custom Java code from MAP file:
            java.lang.String __Location_fireDistrictCode = (java.lang.String)Location_fireDistrictCode;
            java.lang.String __Location_1_LocationInformation_FireDistrictCode = (java.lang.String)Location_1_LocationInformation_FireDistrictCode;
            boolean __result__1 = __Location_fireDistrictCode != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_fireDistrictCode.trim();
            __Location_1_LocationInformation_FireDistrictCode = __result__5;
            Location_1_LocationInformation_FireDistrictCode = __Location_1_LocationInformation_FireDistrictCode;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_fireDistrictCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_fireDistrictCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_fireDistrictCode" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_LocationInformation_FireDistrictCode" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 13: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 14: Custom mapping from taxInfo/licenseCode to LocationInformation/LicenseCode with Java logic
     * Source: taxInfo/licenseCode ()
     * Target: LocationInformation/LicenseCode ()
     */
    private void executeMapping14(Object source, Object target) {
        try {
            // Custom mapping: taxInfo/licenseCode -> LocationInformation/LicenseCode
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 14");

            // Custom Java code from MAP file:
            java.lang.String __Location_taxInfo_licenseCode = (java.lang.String)Location_taxInfo_licenseCode;
            java.lang.String __Location_1_LocationInformation_LicenseCode = (java.lang.String)Location_1_LocationInformation_LicenseCode;
            boolean __result__1 = __Location_taxInfo_licenseCode != null;
            if (__result__1){
            java.lang.String __result__5 = __Location_taxInfo_licenseCode.trim();
            __Location_1_LocationInformation_LicenseCode = __result__5;
            Location_1_LocationInformation_LicenseCode = __Location_1_LocationInformation_LicenseCode;
            }
            else{
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="Location_taxInfo_licenseCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_licenseCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.0/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_licenseCode" variable="true" assignable="false" input="true">
            //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_LocationInformation_LicenseCode" variable="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableGroups executableElements="//@executableElements.0 //@executableElements.1"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!1:3,1
            //!SMAP!2:4,1
            //!SMAP!5:5,1
            //!SMAP!6:6,2
            //!SMAP!1000000:60,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 14: {0}", e.getMessage());
        }
    }

    /**
     * Method 1: JavaCode_1
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_1() {
        java.lang.String __Location_cityName = (java.lang.String)Location_cityName;
        java.lang.String __Location_1_StandardizedAddress_CityName = (java.lang.String)Location_1_StandardizedAddress_CityName;
        boolean __result__1 = __Location_cityName != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_cityName.trim();
        __Location_1_StandardizedAddress_CityName = __result__5;
        Location_1_StandardizedAddress_CityName = __Location_1_StandardizedAddress_CityName;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_cityName" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CityName" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 2: JavaCode_2
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_2() {
        java.lang.String __Location_countyName = (java.lang.String)Location_countyName;
        java.lang.String __Location_1_StandardizedAddress_CountyName = (java.lang.String)Location_1_StandardizedAddress_CountyName;
        boolean __result__1 = __Location_countyName != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_countyName.trim();
        __Location_1_StandardizedAddress_CountyName = __result__5;
        Location_1_StandardizedAddress_CountyName = __Location_1_StandardizedAddress_CountyName;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countyName" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountyName" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 3: JavaCode_3
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_3() {
        java.lang.String __Location_stateOrProvinceCode = (java.lang.String)Location_stateOrProvinceCode;
        java.lang.String __Location_1_StandardizedAddress_StateOrProvinceCode = (java.lang.String)Location_1_StandardizedAddress_StateOrProvinceCode;
        boolean __result__1 = __Location_stateOrProvinceCode != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_stateOrProvinceCode.trim();
        __Location_1_StandardizedAddress_StateOrProvinceCode = __result__5;
        Location_1_StandardizedAddress_StateOrProvinceCode = __Location_1_StandardizedAddress_StateOrProvinceCode;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceCode" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_StateOrProvinceCode" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 4: JavaCode_4
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_4() {
        java.lang.String __Location_stateOrProvinceName = (java.lang.String)Location_stateOrProvinceName;
        java.lang.String __Location_1_StandardizedAddress_StateOrProvinceName = (java.lang.String)Location_1_StandardizedAddress_StateOrProvinceName;
        boolean __result__1 = __Location_stateOrProvinceName != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_stateOrProvinceName.trim();
        __Location_1_StandardizedAddress_StateOrProvinceName = __result__5;
        Location_1_StandardizedAddress_StateOrProvinceName = __Location_1_StandardizedAddress_StateOrProvinceName;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_StateOrProvinceName" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 5: JavaCode_5
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_5() {
        java.lang.String __Location_postalCode = (java.lang.String)Location_postalCode;
        java.lang.String __Location_1_StandardizedAddress_PostalCode = (java.lang.String)Location_1_StandardizedAddress_PostalCode;
        boolean __result__1 = __Location_postalCode != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_postalCode.trim();
        __Location_1_StandardizedAddress_PostalCode = __result__5;
        Location_1_StandardizedAddress_PostalCode = __Location_1_StandardizedAddress_PostalCode;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_postalCode" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_PostalCode" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 6: JavaCode_6
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_6() {
        java.lang.String __Location_countryCode = (java.lang.String)Location_countryCode;
        java.lang.String __Location_1_StandardizedAddress_CountryCode = (java.lang.String)Location_1_StandardizedAddress_CountryCode;
        boolean __result__1 = __Location_countryCode != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_countryCode.trim();
        __Location_1_StandardizedAddress_CountryCode = __result__5;
        Location_1_StandardizedAddress_CountryCode = __Location_1_StandardizedAddress_CountryCode;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryCode" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountryCode" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 7: JavaCode_7
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_7() {
        java.lang.String __Location_countryName = (java.lang.String)Location_countryName;
        java.lang.String __Location_1_StandardizedAddress_CountryName = (java.lang.String)Location_1_StandardizedAddress_CountryName;
        boolean __result__1 = __Location_countryName != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_countryName.trim();
        __Location_1_StandardizedAddress_CountryName = __result__5;
        Location_1_StandardizedAddress_CountryName = __Location_1_StandardizedAddress_CountryName;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_countryName" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_StandardizedAddress_CountryName" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 8: JavaCode_8
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_8() {
        java.lang.String __Location_poBoxIndicator = (java.lang.String)Location_poBoxIndicator;
        java.lang.Boolean __Location_1_StandardizedAddress_POBoxIndicator = (java.lang.Boolean)Location_1_StandardizedAddress_POBoxIndicator;
        java.lang.String source = __Location_poBoxIndicator;
        boolean __result__3 = "Y".equalsIgnoreCase(source);
        __Location_1_StandardizedAddress_POBoxIndicator = new java.lang.Boolean(__result__3);
        Location_1_StandardizedAddress_POBoxIndicator = __Location_1_StandardizedAddress_POBoxIndicator;
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <dataOutputs target="//@executableElements.3"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="boolean" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
    }

    /**
     * Method 9: JavaCode_9
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_9() {
        java.lang.String __Location_fireDistrictCode = (java.lang.String)Location_fireDistrictCode;
        java.lang.String __Location_1_LocationInformation_FireDistrictCode = (java.lang.String)Location_1_LocationInformation_FireDistrictCode;
        boolean __result__1 = __Location_fireDistrictCode != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_fireDistrictCode.trim();
        __Location_1_LocationInformation_FireDistrictCode = __result__5;
        Location_1_LocationInformation_FireDistrictCode = __Location_1_LocationInformation_FireDistrictCode;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_fireDistrictCode" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_LocationInformation_FireDistrictCode" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Method 10: JavaCode_10
     * Context: MAP Custom Mapping
     */
    private void processMapping_JavaCode_10() {
        java.lang.String __Location_taxInfo_licenseCode = (java.lang.String)Location_taxInfo_licenseCode;
        java.lang.String __Location_1_LocationInformation_LicenseCode = (java.lang.String)Location_1_LocationInformation_LicenseCode;
        boolean __result__1 = __Location_taxInfo_licenseCode != null;
        if (__result__1){
        java.lang.String __result__5 = __Location_taxInfo_licenseCode.trim();
        __Location_1_LocationInformation_LicenseCode = __result__5;
        Location_1_LocationInformation_LicenseCode = __Location_1_LocationInformation_LicenseCode;
        }
        else{
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <dataOutputs target="//@executableElements.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_licenseCode" variable="true" assignable="false" input="true">
        //        <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="Location_1_LocationInformation_LicenseCode" variable="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.1/@conditionalActivities.0/@executableElements.0 //@executableElements.1/@conditionalActivities.0/@executableElements.1 //@executableElements.1/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
    }

    /**
     * Utility method to map properties using reflection
     * Used when POJO types are not directly available
     */
    private void mapPropertyByReflection(Object source, Object target, String sourceProperty, String targetProperty) {
        try {
            // Get source property value using reflection
            var sourceMethod = source.getClass().getMethod("get" + sourceProperty.substring(0, 1).toUpperCase() + sourceProperty.substring(1));
            var sourceValue = sourceMethod.invoke(source);

            if (sourceValue != null) {
                // Set target property value using reflection
                var targetMethod = target.getClass().getMethod("set" + targetProperty.substring(0, 1).toUpperCase() + targetProperty.substring(1), sourceValue.getClass());
                targetMethod.invoke(target, sourceValue);
                logger.debug("Mapped property {} -> {}: {}", sourceProperty, targetProperty, sourceValue);
            } else {
                logger.debug("Source property {} is null, skipping mapping", sourceProperty);
            }
        } catch (Exception e) {
            logger.warn("Error in reflection-based mapping {} -> {}: {}", sourceProperty, targetProperty, e.getMessage());
        }
    }

    /**
     * Utility method to get property value safely
     */
    private Object getPropertyValue(Object obj, String propertyName) {
        try {
            var method = obj.getClass().getMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            return method.invoke(obj);
        } catch (Exception e) {
            logger.warn("Error getting property {}: {}", propertyName, e.getMessage());
            return null;
        }
    }

    /**
     * Utility method to set property value safely
     */
    private void setPropertyValue(Object obj, String propertyName, Object value) {
        try {
            if (value != null) {
                var method = obj.getClass().getMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), value.getClass());
                targetMethod.invoke(obj, value);
            }
        } catch (Exception e) {
            logger.warn("Error setting property {}: {}", propertyName, e.getMessage());
        }
    }

    /**
     * Utility method to check if property exists
     */
    private boolean hasProperty(Object obj, String propertyName) {
        try {
            var getterName = "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
            obj.getClass().getMethod(getterName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
