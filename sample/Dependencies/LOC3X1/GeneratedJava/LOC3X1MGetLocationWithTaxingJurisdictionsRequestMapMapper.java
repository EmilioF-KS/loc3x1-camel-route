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


/**
 * LOC3X1MGetLocationWithTaxingJurisdictionsRequestMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1MGetLocationWithTaxingJurisdictionsRequestMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg ()
 * Target: getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1MGetLocationWithTaxingJurisdictionsRequestMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1MGetLocationWithTaxingJurisdictionsRequestMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg
     */
    public static class GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg {
        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;

        public String getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate() {
            return GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

        public void setGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate(String GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) {
            this.GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate = GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

    }

    /**
     * Target model class for getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest
     */
    public static class getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest {
        private String getLocationWithTaxInfo/address/addressLine1;

        public String getgetLocationWithTaxInfo/address/addressLine1() {
            return getLocationWithTaxInfo/address/addressLine1;
        }

        public void setgetLocationWithTaxInfo/address/addressLine1(String getLocationWithTaxInfo/address/addressLine1) {
            this.getLocationWithTaxInfo/address/addressLine1 = getLocationWithTaxInfo/address/addressLine1;
        }

        private String getLocationWithTaxInfo/address/cityName;

        public String getgetLocationWithTaxInfo/address/cityName() {
            return getLocationWithTaxInfo/address/cityName;
        }

        public void setgetLocationWithTaxInfo/address/cityName(String getLocationWithTaxInfo/address/cityName) {
            this.getLocationWithTaxInfo/address/cityName = getLocationWithTaxInfo/address/cityName;
        }

        private String getLocationWithTaxInfo/address/stateOrProvinceCode;

        public String getgetLocationWithTaxInfo/address/stateOrProvinceCode() {
            return getLocationWithTaxInfo/address/stateOrProvinceCode;
        }

        public void setgetLocationWithTaxInfo/address/stateOrProvinceCode(String getLocationWithTaxInfo/address/stateOrProvinceCode) {
            this.getLocationWithTaxInfo/address/stateOrProvinceCode = getLocationWithTaxInfo/address/stateOrProvinceCode;
        }

        private String getLocationWithTaxInfo/address/countryCode;

        public String getgetLocationWithTaxInfo/address/countryCode() {
            return getLocationWithTaxInfo/address/countryCode;
        }

        public void setgetLocationWithTaxInfo/address/countryCode(String getLocationWithTaxInfo/address/countryCode) {
            this.getLocationWithTaxInfo/address/countryCode = getLocationWithTaxInfo/address/countryCode;
        }

        private String getLocationWithTaxInfo/address/postalCode;

        public String getgetLocationWithTaxInfo/address/postalCode() {
            return getLocationWithTaxInfo/address/postalCode;
        }

        public void setgetLocationWithTaxInfo/address/postalCode(String getLocationWithTaxInfo/address/postalCode) {
            this.getLocationWithTaxInfo/address/postalCode = getLocationWithTaxInfo/address/postalCode;
        }

        private String getLocationWithTaxInfo/address/locationPlaceCode;

        public String getgetLocationWithTaxInfo/address/locationPlaceCode() {
            return getLocationWithTaxInfo/address/locationPlaceCode;
        }

        public void setgetLocationWithTaxInfo/address/locationPlaceCode(String getLocationWithTaxInfo/address/locationPlaceCode) {
            this.getLocationWithTaxInfo/address/locationPlaceCode = getLocationWithTaxInfo/address/locationPlaceCode;
        }

        private String getLocationWithTaxInfo/asOfDate;

        public String getgetLocationWithTaxInfo/asOfDate() {
            return getLocationWithTaxInfo/asOfDate;
        }

        public void setgetLocationWithTaxInfo/asOfDate(String getLocationWithTaxInfo/asOfDate) {
            this.getLocationWithTaxInfo/asOfDate = getLocationWithTaxInfo/asOfDate;
        }

    }

    /**
     * Main mapping method to transform GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg to getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest
     */
    public getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest mapGetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsgTogetLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest(GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg source) {
        try {
            logger.info("Starting mapping from GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg to getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest");
            
            // Create output getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest object
            getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest target = new getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg to getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg to getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(GetLocationWithTaxingJurisdictions3X1MRequestMsg:GetLocationWithTaxingJurisdictions3X1MRequestMsg source, getLocationWithTaxInfoRequest:getLocationWithTaxInfoRequest target) {
        // Execute mappings in order
        executeMapping1(source, target);
        executeMapping2(source, target);
        executeMapping3(source, target);
        executeMapping4(source, target);
        executeMapping5(source, target);
        executeMapping6(source, target);
        executeMapping7(source, target);
    }

    /**
     * Execute mapping 1: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 to getLocationWithTaxInfo/address/addressLine1
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 ()
     * Target: getLocationWithTaxInfo/address/addressLine1 ()
     */
    private void executeMapping1(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 -> getLocationWithTaxInfo/address/addressLine1
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 && target instanceof getLocationWithTaxInfo/address/addressLine1) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) source;
                getLocationWithTaxInfo/address/addressLine1 targetObj = (getLocationWithTaxInfo/address/addressLine1) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 to getLocationWithTaxInfo/address/addressLine1
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/addressLine1(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1", "getLocationWithTaxInfo/address/addressLine1", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AddressLine1", "getLocationWithTaxInfo/address/addressLine1");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 1: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 2: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName to getLocationWithTaxInfo/address/cityName
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName ()
     * Target: getLocationWithTaxInfo/address/cityName ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName -> getLocationWithTaxInfo/address/cityName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName && target instanceof getLocationWithTaxInfo/address/cityName) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName) source;
                getLocationWithTaxInfo/address/cityName targetObj = (getLocationWithTaxInfo/address/cityName) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName to getLocationWithTaxInfo/address/cityName
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/cityName(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName", "getLocationWithTaxInfo/address/cityName", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CityName", "getLocationWithTaxInfo/address/cityName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode to getLocationWithTaxInfo/address/stateOrProvinceCode
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode ()
     * Target: getLocationWithTaxInfo/address/stateOrProvinceCode ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode -> getLocationWithTaxInfo/address/stateOrProvinceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode && target instanceof getLocationWithTaxInfo/address/stateOrProvinceCode) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) source;
                getLocationWithTaxInfo/address/stateOrProvinceCode targetObj = (getLocationWithTaxInfo/address/stateOrProvinceCode) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode to getLocationWithTaxInfo/address/stateOrProvinceCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/stateOrProvinceCode(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode", "getLocationWithTaxInfo/address/stateOrProvinceCode", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode", "getLocationWithTaxInfo/address/stateOrProvinceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode to getLocationWithTaxInfo/address/countryCode
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode ()
     * Target: getLocationWithTaxInfo/address/countryCode ()
     */
    private void executeMapping4(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode -> getLocationWithTaxInfo/address/countryCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode && target instanceof getLocationWithTaxInfo/address/countryCode) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode) source;
                getLocationWithTaxInfo/address/countryCode targetObj = (getLocationWithTaxInfo/address/countryCode) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode to getLocationWithTaxInfo/address/countryCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/countryCode(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode", "getLocationWithTaxInfo/address/countryCode", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/CountryCode", "getLocationWithTaxInfo/address/countryCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 4: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 5: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode to getLocationWithTaxInfo/address/postalCode
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode ()
     * Target: getLocationWithTaxInfo/address/postalCode ()
     */
    private void executeMapping5(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode -> getLocationWithTaxInfo/address/postalCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode && target instanceof getLocationWithTaxInfo/address/postalCode) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode) source;
                getLocationWithTaxInfo/address/postalCode targetObj = (getLocationWithTaxInfo/address/postalCode) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode to getLocationWithTaxInfo/address/postalCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/postalCode(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode", "getLocationWithTaxInfo/address/postalCode", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/PostalCode", "getLocationWithTaxInfo/address/postalCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 5: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 6: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode to getLocationWithTaxInfo/address/locationPlaceCode
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode ()
     * Target: getLocationWithTaxInfo/address/locationPlaceCode ()
     */
    private void executeMapping6(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode -> getLocationWithTaxInfo/address/locationPlaceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode && target instanceof getLocationWithTaxInfo/address/locationPlaceCode) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) source;
                getLocationWithTaxInfo/address/locationPlaceCode targetObj = (getLocationWithTaxInfo/address/locationPlaceCode) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode to getLocationWithTaxInfo/address/locationPlaceCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode() != null) {
                    targetObj.setgetLocationWithTaxInfo/address/locationPlaceCode(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode", "getLocationWithTaxInfo/address/locationPlaceCode", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode", "getLocationWithTaxInfo/address/locationPlaceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 6: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 7: Move mapping from GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate to getLocationWithTaxInfo/asOfDate
     * Source: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate ()
     * Target: getLocationWithTaxInfo/asOfDate ()
     */
    private void executeMapping7(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate -> getLocationWithTaxInfo/asOfDate
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate && target instanceof getLocationWithTaxInfo/asOfDate) {
                GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate sourceObj = (GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) source;
                getLocationWithTaxInfo/asOfDate targetObj = (getLocationWithTaxInfo/asOfDate) target;

                // Map GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate to getLocationWithTaxInfo/asOfDate
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate() != null) {
                    targetObj.setgetLocationWithTaxInfo/asOfDate(sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate", "getLocationWithTaxInfo/asOfDate", sourceObj.getGetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1M/GetLocationWithTaxingJurisdictionsRequest/AsOfDate", "getLocationWithTaxInfo/asOfDate");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 7: {0}", e.getMessage());
        }
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
