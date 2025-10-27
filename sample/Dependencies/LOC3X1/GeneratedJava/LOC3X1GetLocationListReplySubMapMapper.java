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
 * LOC3X1GetLocationListReplySubMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1GetLocationListReplySubMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: Location:Location ()
 * Target: Location:Location ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1GetLocationListReplySubMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1GetLocationListReplySubMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for Location:Location
     */
    public static class Location:Location {
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

        private String LocationInformation/LocationClassificationCode;

        public String getLocationInformation/LocationClassificationCode() {
            return LocationInformation/LocationClassificationCode;
        }

        public void setLocationInformation/LocationClassificationCode(String LocationInformation/LocationClassificationCode) {
            this.LocationInformation/LocationClassificationCode = LocationInformation/LocationClassificationCode;
        }

        private String LocationInformation/FireDistrictCode;

        public String getLocationInformation/FireDistrictCode() {
            return LocationInformation/FireDistrictCode;
        }

        public void setLocationInformation/FireDistrictCode(String LocationInformation/FireDistrictCode) {
            this.LocationInformation/FireDistrictCode = LocationInformation/FireDistrictCode;
        }

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

        private String HazardousArea;

        public String getHazardousArea() {
            return HazardousArea;
        }

        public void setHazardousArea(String HazardousArea) {
            this.HazardousArea = HazardousArea;
        }

    }

    /**
     * Target model class for Location:Location
     */
    public static class Location:Location {
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

        private String LocationInformation/LocationClassificationCode;

        public String getLocationInformation/LocationClassificationCode() {
            return LocationInformation/LocationClassificationCode;
        }

        public void setLocationInformation/LocationClassificationCode(String LocationInformation/LocationClassificationCode) {
            this.LocationInformation/LocationClassificationCode = LocationInformation/LocationClassificationCode;
        }

        private String LocationInformation/FireDistrictCode;

        public String getLocationInformation/FireDistrictCode() {
            return LocationInformation/FireDistrictCode;
        }

        public void setLocationInformation/FireDistrictCode(String LocationInformation/FireDistrictCode) {
            this.LocationInformation/FireDistrictCode = LocationInformation/FireDistrictCode;
        }

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

        private String HazardousArea;

        public String getHazardousArea() {
            return HazardousArea;
        }

        public void setHazardousArea(String HazardousArea) {
            this.HazardousArea = HazardousArea;
        }

    }

    /**
     * Main mapping method to transform Location:Location to Location:Location
     */
    public Location:Location mapLocation:LocationToLocation:Location(Location:Location source) {
        try {
            logger.info("Starting mapping from Location:Location to Location:Location");
            
            // Create output Location:Location object
            Location:Location target = new Location:Location();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from Location:Location to Location:Location");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map Location:Location to Location:Location", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(Location:Location source, Location:Location target) {
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
        executeMapping15(source, target);
    }

    /**
     * Execute mapping 1: Move mapping from LocationInformation/CityCode to LocationInformation/CityCode
     * Source: LocationInformation/CityCode ()
     * Target: LocationInformation/CityCode ()
     */
    private void executeMapping1(Object source, Object target) {
        try {
            // Move mapping: LocationInformation/CityCode -> LocationInformation/CityCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof LocationInformation/CityCode && target instanceof LocationInformation/CityCode) {
                LocationInformation/CityCode sourceObj = (LocationInformation/CityCode) source;
                LocationInformation/CityCode targetObj = (LocationInformation/CityCode) target;

                // Map LocationInformation/CityCode to LocationInformation/CityCode
                if (sourceObj.getLocationInformation/CityCode() != null) {
                    targetObj.setLocationInformation/CityCode(sourceObj.getLocationInformation/CityCode());
                    logger.debug("Moved {0} -> {1}: {2}", "LocationInformation/CityCode", "LocationInformation/CityCode", sourceObj.getLocationInformation/CityCode());
                } else {
                    logger.debug("Source property LocationInformation/CityCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "LocationInformation/CityCode", "LocationInformation/CityCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 1: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 2: Move mapping from LocationInformation/CountyCode to LocationInformation/CountyCode
     * Source: LocationInformation/CountyCode ()
     * Target: LocationInformation/CountyCode ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: LocationInformation/CountyCode -> LocationInformation/CountyCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof LocationInformation/CountyCode && target instanceof LocationInformation/CountyCode) {
                LocationInformation/CountyCode sourceObj = (LocationInformation/CountyCode) source;
                LocationInformation/CountyCode targetObj = (LocationInformation/CountyCode) target;

                // Map LocationInformation/CountyCode to LocationInformation/CountyCode
                if (sourceObj.getLocationInformation/CountyCode() != null) {
                    targetObj.setLocationInformation/CountyCode(sourceObj.getLocationInformation/CountyCode());
                    logger.debug("Moved {0} -> {1}: {2}", "LocationInformation/CountyCode", "LocationInformation/CountyCode", sourceObj.getLocationInformation/CountyCode());
                } else {
                    logger.debug("Source property LocationInformation/CountyCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "LocationInformation/CountyCode", "LocationInformation/CountyCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Move mapping from LocationInformation/LocationClassificationCode to LocationInformation/LocationClassificationCode
     * Source: LocationInformation/LocationClassificationCode ()
     * Target: LocationInformation/LocationClassificationCode ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Move mapping: LocationInformation/LocationClassificationCode -> LocationInformation/LocationClassificationCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof LocationInformation/LocationClassificationCode && target instanceof LocationInformation/LocationClassificationCode) {
                LocationInformation/LocationClassificationCode sourceObj = (LocationInformation/LocationClassificationCode) source;
                LocationInformation/LocationClassificationCode targetObj = (LocationInformation/LocationClassificationCode) target;

                // Map LocationInformation/LocationClassificationCode to LocationInformation/LocationClassificationCode
                if (sourceObj.getLocationInformation/LocationClassificationCode() != null) {
                    targetObj.setLocationInformation/LocationClassificationCode(sourceObj.getLocationInformation/LocationClassificationCode());
                    logger.debug("Moved {0} -> {1}: {2}", "LocationInformation/LocationClassificationCode", "LocationInformation/LocationClassificationCode", sourceObj.getLocationInformation/LocationClassificationCode());
                } else {
                    logger.debug("Source property LocationInformation/LocationClassificationCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "LocationInformation/LocationClassificationCode", "LocationInformation/LocationClassificationCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: Move mapping from LocationInformation/FireDistrictCode to LocationInformation/FireDistrictCode
     * Source: LocationInformation/FireDistrictCode ()
     * Target: LocationInformation/FireDistrictCode ()
     */
    private void executeMapping4(Object source, Object target) {
        try {
            // Move mapping: LocationInformation/FireDistrictCode -> LocationInformation/FireDistrictCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof LocationInformation/FireDistrictCode && target instanceof LocationInformation/FireDistrictCode) {
                LocationInformation/FireDistrictCode sourceObj = (LocationInformation/FireDistrictCode) source;
                LocationInformation/FireDistrictCode targetObj = (LocationInformation/FireDistrictCode) target;

                // Map LocationInformation/FireDistrictCode to LocationInformation/FireDistrictCode
                if (sourceObj.getLocationInformation/FireDistrictCode() != null) {
                    targetObj.setLocationInformation/FireDistrictCode(sourceObj.getLocationInformation/FireDistrictCode());
                    logger.debug("Moved {0} -> {1}: {2}", "LocationInformation/FireDistrictCode", "LocationInformation/FireDistrictCode", sourceObj.getLocationInformation/FireDistrictCode());
                } else {
                    logger.debug("Source property LocationInformation/FireDistrictCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "LocationInformation/FireDistrictCode", "LocationInformation/FireDistrictCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 4: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 5: Move mapping from StandardizedAddress/CityName to StandardizedAddress/CityName
     * Source: StandardizedAddress/CityName ()
     * Target: StandardizedAddress/CityName ()
     */
    private void executeMapping5(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/CityName -> StandardizedAddress/CityName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/CityName && target instanceof StandardizedAddress/CityName) {
                StandardizedAddress/CityName sourceObj = (StandardizedAddress/CityName) source;
                StandardizedAddress/CityName targetObj = (StandardizedAddress/CityName) target;

                // Map StandardizedAddress/CityName to StandardizedAddress/CityName
                if (sourceObj.getStandardizedAddress/CityName() != null) {
                    targetObj.setStandardizedAddress/CityName(sourceObj.getStandardizedAddress/CityName());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/CityName", "StandardizedAddress/CityName", sourceObj.getStandardizedAddress/CityName());
                } else {
                    logger.debug("Source property StandardizedAddress/CityName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/CityName", "StandardizedAddress/CityName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 5: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 6: Move mapping from StandardizedAddress/CountyName to StandardizedAddress/CountyName
     * Source: StandardizedAddress/CountyName ()
     * Target: StandardizedAddress/CountyName ()
     */
    private void executeMapping6(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/CountyName -> StandardizedAddress/CountyName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/CountyName && target instanceof StandardizedAddress/CountyName) {
                StandardizedAddress/CountyName sourceObj = (StandardizedAddress/CountyName) source;
                StandardizedAddress/CountyName targetObj = (StandardizedAddress/CountyName) target;

                // Map StandardizedAddress/CountyName to StandardizedAddress/CountyName
                if (sourceObj.getStandardizedAddress/CountyName() != null) {
                    targetObj.setStandardizedAddress/CountyName(sourceObj.getStandardizedAddress/CountyName());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/CountyName", "StandardizedAddress/CountyName", sourceObj.getStandardizedAddress/CountyName());
                } else {
                    logger.debug("Source property StandardizedAddress/CountyName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/CountyName", "StandardizedAddress/CountyName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 6: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 7: Move mapping from StandardizedAddress/StateOrProvinceCode to StandardizedAddress/StateOrProvinceCode
     * Source: StandardizedAddress/StateOrProvinceCode ()
     * Target: StandardizedAddress/StateOrProvinceCode ()
     */
    private void executeMapping7(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/StateOrProvinceCode -> StandardizedAddress/StateOrProvinceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/StateOrProvinceCode && target instanceof StandardizedAddress/StateOrProvinceCode) {
                StandardizedAddress/StateOrProvinceCode sourceObj = (StandardizedAddress/StateOrProvinceCode) source;
                StandardizedAddress/StateOrProvinceCode targetObj = (StandardizedAddress/StateOrProvinceCode) target;

                // Map StandardizedAddress/StateOrProvinceCode to StandardizedAddress/StateOrProvinceCode
                if (sourceObj.getStandardizedAddress/StateOrProvinceCode() != null) {
                    targetObj.setStandardizedAddress/StateOrProvinceCode(sourceObj.getStandardizedAddress/StateOrProvinceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/StateOrProvinceCode", "StandardizedAddress/StateOrProvinceCode", sourceObj.getStandardizedAddress/StateOrProvinceCode());
                } else {
                    logger.debug("Source property StandardizedAddress/StateOrProvinceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/StateOrProvinceCode", "StandardizedAddress/StateOrProvinceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 7: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 8: Move mapping from StandardizedAddress/StateOrProvinceName to StandardizedAddress/StateOrProvinceName
     * Source: StandardizedAddress/StateOrProvinceName ()
     * Target: StandardizedAddress/StateOrProvinceName ()
     */
    private void executeMapping8(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/StateOrProvinceName -> StandardizedAddress/StateOrProvinceName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/StateOrProvinceName && target instanceof StandardizedAddress/StateOrProvinceName) {
                StandardizedAddress/StateOrProvinceName sourceObj = (StandardizedAddress/StateOrProvinceName) source;
                StandardizedAddress/StateOrProvinceName targetObj = (StandardizedAddress/StateOrProvinceName) target;

                // Map StandardizedAddress/StateOrProvinceName to StandardizedAddress/StateOrProvinceName
                if (sourceObj.getStandardizedAddress/StateOrProvinceName() != null) {
                    targetObj.setStandardizedAddress/StateOrProvinceName(sourceObj.getStandardizedAddress/StateOrProvinceName());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/StateOrProvinceName", "StandardizedAddress/StateOrProvinceName", sourceObj.getStandardizedAddress/StateOrProvinceName());
                } else {
                    logger.debug("Source property StandardizedAddress/StateOrProvinceName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/StateOrProvinceName", "StandardizedAddress/StateOrProvinceName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 8: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 9: Move mapping from StandardizedAddress/PostalStateAbbreviation to StandardizedAddress/PostalStateAbbreviation
     * Source: StandardizedAddress/PostalStateAbbreviation ()
     * Target: StandardizedAddress/PostalStateAbbreviation ()
     */
    private void executeMapping9(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/PostalStateAbbreviation -> StandardizedAddress/PostalStateAbbreviation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/PostalStateAbbreviation && target instanceof StandardizedAddress/PostalStateAbbreviation) {
                StandardizedAddress/PostalStateAbbreviation sourceObj = (StandardizedAddress/PostalStateAbbreviation) source;
                StandardizedAddress/PostalStateAbbreviation targetObj = (StandardizedAddress/PostalStateAbbreviation) target;

                // Map StandardizedAddress/PostalStateAbbreviation to StandardizedAddress/PostalStateAbbreviation
                if (sourceObj.getStandardizedAddress/PostalStateAbbreviation() != null) {
                    targetObj.setStandardizedAddress/PostalStateAbbreviation(sourceObj.getStandardizedAddress/PostalStateAbbreviation());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/PostalStateAbbreviation", "StandardizedAddress/PostalStateAbbreviation", sourceObj.getStandardizedAddress/PostalStateAbbreviation());
                } else {
                    logger.debug("Source property StandardizedAddress/PostalStateAbbreviation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/PostalStateAbbreviation", "StandardizedAddress/PostalStateAbbreviation");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 9: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 10: Move mapping from StandardizedAddress/PostalCode to StandardizedAddress/PostalCode
     * Source: StandardizedAddress/PostalCode ()
     * Target: StandardizedAddress/PostalCode ()
     */
    private void executeMapping10(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/PostalCode -> StandardizedAddress/PostalCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/PostalCode && target instanceof StandardizedAddress/PostalCode) {
                StandardizedAddress/PostalCode sourceObj = (StandardizedAddress/PostalCode) source;
                StandardizedAddress/PostalCode targetObj = (StandardizedAddress/PostalCode) target;

                // Map StandardizedAddress/PostalCode to StandardizedAddress/PostalCode
                if (sourceObj.getStandardizedAddress/PostalCode() != null) {
                    targetObj.setStandardizedAddress/PostalCode(sourceObj.getStandardizedAddress/PostalCode());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/PostalCode", "StandardizedAddress/PostalCode", sourceObj.getStandardizedAddress/PostalCode());
                } else {
                    logger.debug("Source property StandardizedAddress/PostalCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/PostalCode", "StandardizedAddress/PostalCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 10: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 11: Move mapping from StandardizedAddress/CountryCode to StandardizedAddress/CountryCode
     * Source: StandardizedAddress/CountryCode ()
     * Target: StandardizedAddress/CountryCode ()
     */
    private void executeMapping11(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/CountryCode -> StandardizedAddress/CountryCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/CountryCode && target instanceof StandardizedAddress/CountryCode) {
                StandardizedAddress/CountryCode sourceObj = (StandardizedAddress/CountryCode) source;
                StandardizedAddress/CountryCode targetObj = (StandardizedAddress/CountryCode) target;

                // Map StandardizedAddress/CountryCode to StandardizedAddress/CountryCode
                if (sourceObj.getStandardizedAddress/CountryCode() != null) {
                    targetObj.setStandardizedAddress/CountryCode(sourceObj.getStandardizedAddress/CountryCode());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/CountryCode", "StandardizedAddress/CountryCode", sourceObj.getStandardizedAddress/CountryCode());
                } else {
                    logger.debug("Source property StandardizedAddress/CountryCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/CountryCode", "StandardizedAddress/CountryCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 11: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 12: Move mapping from StandardizedAddress/CountryName to StandardizedAddress/CountryName
     * Source: StandardizedAddress/CountryName ()
     * Target: StandardizedAddress/CountryName ()
     */
    private void executeMapping12(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/CountryName -> StandardizedAddress/CountryName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/CountryName && target instanceof StandardizedAddress/CountryName) {
                StandardizedAddress/CountryName sourceObj = (StandardizedAddress/CountryName) source;
                StandardizedAddress/CountryName targetObj = (StandardizedAddress/CountryName) target;

                // Map StandardizedAddress/CountryName to StandardizedAddress/CountryName
                if (sourceObj.getStandardizedAddress/CountryName() != null) {
                    targetObj.setStandardizedAddress/CountryName(sourceObj.getStandardizedAddress/CountryName());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/CountryName", "StandardizedAddress/CountryName", sourceObj.getStandardizedAddress/CountryName());
                } else {
                    logger.debug("Source property StandardizedAddress/CountryName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/CountryName", "StandardizedAddress/CountryName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 12: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 13: Move mapping from StandardizedAddress/LocationPlaceCode to StandardizedAddress/LocationPlaceCode
     * Source: StandardizedAddress/LocationPlaceCode ()
     * Target: StandardizedAddress/LocationPlaceCode ()
     */
    private void executeMapping13(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/LocationPlaceCode -> StandardizedAddress/LocationPlaceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/LocationPlaceCode && target instanceof StandardizedAddress/LocationPlaceCode) {
                StandardizedAddress/LocationPlaceCode sourceObj = (StandardizedAddress/LocationPlaceCode) source;
                StandardizedAddress/LocationPlaceCode targetObj = (StandardizedAddress/LocationPlaceCode) target;

                // Map StandardizedAddress/LocationPlaceCode to StandardizedAddress/LocationPlaceCode
                if (sourceObj.getStandardizedAddress/LocationPlaceCode() != null) {
                    targetObj.setStandardizedAddress/LocationPlaceCode(sourceObj.getStandardizedAddress/LocationPlaceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/LocationPlaceCode", "StandardizedAddress/LocationPlaceCode", sourceObj.getStandardizedAddress/LocationPlaceCode());
                } else {
                    logger.debug("Source property StandardizedAddress/LocationPlaceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/LocationPlaceCode", "StandardizedAddress/LocationPlaceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 13: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 14: Move mapping from StandardizedAddress/POBoxIndicator to StandardizedAddress/POBoxIndicator
     * Source: StandardizedAddress/POBoxIndicator ()
     * Target: StandardizedAddress/POBoxIndicator ()
     */
    private void executeMapping14(Object source, Object target) {
        try {
            // Move mapping: StandardizedAddress/POBoxIndicator -> StandardizedAddress/POBoxIndicator
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StandardizedAddress/POBoxIndicator && target instanceof StandardizedAddress/POBoxIndicator) {
                StandardizedAddress/POBoxIndicator sourceObj = (StandardizedAddress/POBoxIndicator) source;
                StandardizedAddress/POBoxIndicator targetObj = (StandardizedAddress/POBoxIndicator) target;

                // Map StandardizedAddress/POBoxIndicator to StandardizedAddress/POBoxIndicator
                if (sourceObj.getStandardizedAddress/POBoxIndicator() != null) {
                    targetObj.setStandardizedAddress/POBoxIndicator(sourceObj.getStandardizedAddress/POBoxIndicator());
                    logger.debug("Moved {0} -> {1}: {2}", "StandardizedAddress/POBoxIndicator", "StandardizedAddress/POBoxIndicator", sourceObj.getStandardizedAddress/POBoxIndicator());
                } else {
                    logger.debug("Source property StandardizedAddress/POBoxIndicator is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StandardizedAddress/POBoxIndicator", "StandardizedAddress/POBoxIndicator");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 14: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 15: Move mapping from HazardousArea to HazardousArea
     * Source: HazardousArea ()
     * Target: HazardousArea ()
     */
    private void executeMapping15(Object source, Object target) {
        try {
            // Move mapping: HazardousArea -> HazardousArea
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof HazardousArea && target instanceof HazardousArea) {
                HazardousArea sourceObj = (HazardousArea) source;
                HazardousArea targetObj = (HazardousArea) target;

                // Map HazardousArea to HazardousArea
                if (sourceObj.getHazardousArea() != null) {
                    targetObj.setHazardousArea(sourceObj.getHazardousArea());
                    logger.debug("Moved {0} -> {1}: {2}", "HazardousArea", "HazardousArea", sourceObj.getHazardousArea());
                } else {
                    logger.debug("Source property HazardousArea is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "HazardousArea", "HazardousArea");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 15: {0}", e.getMessage());
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
