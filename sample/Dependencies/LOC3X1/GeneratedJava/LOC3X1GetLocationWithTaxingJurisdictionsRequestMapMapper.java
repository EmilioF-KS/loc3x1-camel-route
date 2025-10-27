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
 * LOC3X1GetLocationWithTaxingJurisdictionsRequestMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1GetLocationWithTaxingJurisdictionsRequestMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg ()
 * Target: GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1GetLocationWithTaxingJurisdictionsRequestMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1GetLocationWithTaxingJurisdictionsRequestMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg
     */
    public static class GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg {
        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        private String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;

        public String getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate() {
            return GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

        public void setGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate(String GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) {
            this.GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate = GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

    }

    /**
     * Target model class for GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg
     */
    public static class GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg {
        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode;
        }

        private String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;

        public String getGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate() {
            return GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

        public void setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate(String GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) {
            this.GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate = GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate;
        }

    }

    /**
     * Main mapping method to transform GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg to GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg
     */
    public GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg mapGetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsgToGetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg(GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg source) {
        try {
            logger.info("Starting mapping from GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg to GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg");
            
            // Create output GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg object
            GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg target = new GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg to GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg to GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(GetLocationWithTaxingJurisdictionsRequestMsg:GetLocationWithTaxingJurisdictionsRequestMsg source, GetLocationWithTaxingJurisdictions3X1BRequestMsg:GetLocationWithTaxingJurisdictions3X1BRequestMsg target) {
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
    }

    /**
     * Execute mapping 1: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 ()
     */
    private void executeMapping1(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1 is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine1", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine1");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 1: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 2: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2 is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AddressLine2", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AddressLine2");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CityName", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CityName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode ()
     */
    private void executeMapping4(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/StateOrProvinceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 4: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 5: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation ()
     */
    private void executeMapping5(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalStateAbbreviation");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 5: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 6: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode ()
     */
    private void executeMapping6(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/PostalCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/PostalCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 6: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 7: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode ()
     */
    private void executeMapping7(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 7: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 8: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation ()
     */
    private void executeMapping8(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/CountryAbbreviation");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 8: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 9: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode ()
     */
    private void executeMapping9(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/LocationPlaceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 9: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 10: Move mapping from GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate
     * Source: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate ()
     * Target: GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate ()
     */
    private void executeMapping10(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate -> GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate && target instanceof GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) {
                GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate sourceObj = (GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) source;
                GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate targetObj = (GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate) target;

                // Map GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate to GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate
                if (sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate(sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate", sourceObj.getGetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions/GetLocationWithTaxingJurisdictionsRequest/AsOfDate", "GetLocationWithTaxingJurisdictions3X1B/GetLocationWithTaxingJurisdictionsRequest/AsOfDate");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 10: {0}", e.getMessage());
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
