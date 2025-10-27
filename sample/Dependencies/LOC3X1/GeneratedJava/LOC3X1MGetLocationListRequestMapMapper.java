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
 * LOC3X1MGetLocationListRequestMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1MGetLocationListRequestMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg ()
 * Target: getLocationsRequest:getLocationsRequest ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1MGetLocationListRequestMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1MGetLocationListRequestMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg
     */
    public static class GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg {
        private String GetLocationList3X1M/GetLocationListRequest/AddressLine1;

        public String getGetLocationList3X1M/GetLocationListRequest/AddressLine1() {
            return GetLocationList3X1M/GetLocationListRequest/AddressLine1;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/AddressLine1(String GetLocationList3X1M/GetLocationListRequest/AddressLine1) {
            this.GetLocationList3X1M/GetLocationListRequest/AddressLine1 = GetLocationList3X1M/GetLocationListRequest/AddressLine1;
        }

        private String GetLocationList3X1M/GetLocationListRequest/CityName;

        public String getGetLocationList3X1M/GetLocationListRequest/CityName() {
            return GetLocationList3X1M/GetLocationListRequest/CityName;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/CityName(String GetLocationList3X1M/GetLocationListRequest/CityName) {
            this.GetLocationList3X1M/GetLocationListRequest/CityName = GetLocationList3X1M/GetLocationListRequest/CityName;
        }

        private String GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode;

        public String getGetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode() {
            return GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode(String GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode) {
            this.GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode = GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode;
        }

        private String GetLocationList3X1M/GetLocationListRequest/CountryCode;

        public String getGetLocationList3X1M/GetLocationListRequest/CountryCode() {
            return GetLocationList3X1M/GetLocationListRequest/CountryCode;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/CountryCode(String GetLocationList3X1M/GetLocationListRequest/CountryCode) {
            this.GetLocationList3X1M/GetLocationListRequest/CountryCode = GetLocationList3X1M/GetLocationListRequest/CountryCode;
        }

        private String GetLocationList3X1M/GetLocationListRequest/PostalCode;

        public String getGetLocationList3X1M/GetLocationListRequest/PostalCode() {
            return GetLocationList3X1M/GetLocationListRequest/PostalCode;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/PostalCode(String GetLocationList3X1M/GetLocationListRequest/PostalCode) {
            this.GetLocationList3X1M/GetLocationListRequest/PostalCode = GetLocationList3X1M/GetLocationListRequest/PostalCode;
        }

        private String GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode;

        public String getGetLocationList3X1M/GetLocationListRequest/LocationPlaceCode() {
            return GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode;
        }

        public void setGetLocationList3X1M/GetLocationListRequest/LocationPlaceCode(String GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode) {
            this.GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode = GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode;
        }

    }

    /**
     * Target model class for getLocationsRequest:getLocationsRequest
     */
    public static class getLocationsRequest:getLocationsRequest {
        private String getLocations/address/addressLine1;

        public String getgetLocations/address/addressLine1() {
            return getLocations/address/addressLine1;
        }

        public void setgetLocations/address/addressLine1(String getLocations/address/addressLine1) {
            this.getLocations/address/addressLine1 = getLocations/address/addressLine1;
        }

        private String getLocations/address/cityName;

        public String getgetLocations/address/cityName() {
            return getLocations/address/cityName;
        }

        public void setgetLocations/address/cityName(String getLocations/address/cityName) {
            this.getLocations/address/cityName = getLocations/address/cityName;
        }

        private String getLocations/address/stateOrProvinceCode;

        public String getgetLocations/address/stateOrProvinceCode() {
            return getLocations/address/stateOrProvinceCode;
        }

        public void setgetLocations/address/stateOrProvinceCode(String getLocations/address/stateOrProvinceCode) {
            this.getLocations/address/stateOrProvinceCode = getLocations/address/stateOrProvinceCode;
        }

        private String getLocations/address/countryCode;

        public String getgetLocations/address/countryCode() {
            return getLocations/address/countryCode;
        }

        public void setgetLocations/address/countryCode(String getLocations/address/countryCode) {
            this.getLocations/address/countryCode = getLocations/address/countryCode;
        }

        private String getLocations/address/postalCode;

        public String getgetLocations/address/postalCode() {
            return getLocations/address/postalCode;
        }

        public void setgetLocations/address/postalCode(String getLocations/address/postalCode) {
            this.getLocations/address/postalCode = getLocations/address/postalCode;
        }

        private String getLocations/address/locationPlaceCode;

        public String getgetLocations/address/locationPlaceCode() {
            return getLocations/address/locationPlaceCode;
        }

        public void setgetLocations/address/locationPlaceCode(String getLocations/address/locationPlaceCode) {
            this.getLocations/address/locationPlaceCode = getLocations/address/locationPlaceCode;
        }

    }

    /**
     * Main mapping method to transform GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg to getLocationsRequest:getLocationsRequest
     */
    public getLocationsRequest:getLocationsRequest mapGetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsgTogetLocationsRequest:getLocationsRequest(GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg source) {
        try {
            logger.info("Starting mapping from GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg to getLocationsRequest:getLocationsRequest");
            
            // Create output getLocationsRequest:getLocationsRequest object
            getLocationsRequest:getLocationsRequest target = new getLocationsRequest:getLocationsRequest();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg to getLocationsRequest:getLocationsRequest");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg to getLocationsRequest:getLocationsRequest", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(GetLocationList3X1MRequestMsg:GetLocationList3X1MRequestMsg source, getLocationsRequest:getLocationsRequest target) {
        // Execute mappings in order
        executeMapping1(source, target);
        executeMapping2(source, target);
        executeMapping3(source, target);
        executeMapping4(source, target);
        executeMapping5(source, target);
        executeMapping6(source, target);
    }

    /**
     * Execute mapping 1: Move mapping from GetLocationList3X1M/GetLocationListRequest/AddressLine1 to getLocations/address/addressLine1
     * Source: GetLocationList3X1M/GetLocationListRequest/AddressLine1 ()
     * Target: getLocations/address/addressLine1 ()
     */
    private void executeMapping1(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/AddressLine1 -> getLocations/address/addressLine1
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/AddressLine1 && target instanceof getLocations/address/addressLine1) {
                GetLocationList3X1M/GetLocationListRequest/AddressLine1 sourceObj = (GetLocationList3X1M/GetLocationListRequest/AddressLine1) source;
                getLocations/address/addressLine1 targetObj = (getLocations/address/addressLine1) target;

                // Map GetLocationList3X1M/GetLocationListRequest/AddressLine1 to getLocations/address/addressLine1
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/AddressLine1() != null) {
                    targetObj.setgetLocations/address/addressLine1(sourceObj.getGetLocationList3X1M/GetLocationListRequest/AddressLine1());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/AddressLine1", "getLocations/address/addressLine1", sourceObj.getGetLocationList3X1M/GetLocationListRequest/AddressLine1());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/AddressLine1 is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/AddressLine1", "getLocations/address/addressLine1");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 1: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 2: Move mapping from GetLocationList3X1M/GetLocationListRequest/CityName to getLocations/address/cityName
     * Source: GetLocationList3X1M/GetLocationListRequest/CityName ()
     * Target: getLocations/address/cityName ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/CityName -> getLocations/address/cityName
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/CityName && target instanceof getLocations/address/cityName) {
                GetLocationList3X1M/GetLocationListRequest/CityName sourceObj = (GetLocationList3X1M/GetLocationListRequest/CityName) source;
                getLocations/address/cityName targetObj = (getLocations/address/cityName) target;

                // Map GetLocationList3X1M/GetLocationListRequest/CityName to getLocations/address/cityName
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/CityName() != null) {
                    targetObj.setgetLocations/address/cityName(sourceObj.getGetLocationList3X1M/GetLocationListRequest/CityName());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/CityName", "getLocations/address/cityName", sourceObj.getGetLocationList3X1M/GetLocationListRequest/CityName());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/CityName is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/CityName", "getLocations/address/cityName");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Move mapping from GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode to getLocations/address/stateOrProvinceCode
     * Source: GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode ()
     * Target: getLocations/address/stateOrProvinceCode ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode -> getLocations/address/stateOrProvinceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode && target instanceof getLocations/address/stateOrProvinceCode) {
                GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode sourceObj = (GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode) source;
                getLocations/address/stateOrProvinceCode targetObj = (getLocations/address/stateOrProvinceCode) target;

                // Map GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode to getLocations/address/stateOrProvinceCode
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode() != null) {
                    targetObj.setgetLocations/address/stateOrProvinceCode(sourceObj.getGetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode", "getLocations/address/stateOrProvinceCode", sourceObj.getGetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/StateOrProvinceCode", "getLocations/address/stateOrProvinceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: Move mapping from GetLocationList3X1M/GetLocationListRequest/CountryCode to getLocations/address/countryCode
     * Source: GetLocationList3X1M/GetLocationListRequest/CountryCode ()
     * Target: getLocations/address/countryCode ()
     */
    private void executeMapping4(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/CountryCode -> getLocations/address/countryCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/CountryCode && target instanceof getLocations/address/countryCode) {
                GetLocationList3X1M/GetLocationListRequest/CountryCode sourceObj = (GetLocationList3X1M/GetLocationListRequest/CountryCode) source;
                getLocations/address/countryCode targetObj = (getLocations/address/countryCode) target;

                // Map GetLocationList3X1M/GetLocationListRequest/CountryCode to getLocations/address/countryCode
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/CountryCode() != null) {
                    targetObj.setgetLocations/address/countryCode(sourceObj.getGetLocationList3X1M/GetLocationListRequest/CountryCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/CountryCode", "getLocations/address/countryCode", sourceObj.getGetLocationList3X1M/GetLocationListRequest/CountryCode());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/CountryCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/CountryCode", "getLocations/address/countryCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 4: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 5: Move mapping from GetLocationList3X1M/GetLocationListRequest/PostalCode to getLocations/address/postalCode
     * Source: GetLocationList3X1M/GetLocationListRequest/PostalCode ()
     * Target: getLocations/address/postalCode ()
     */
    private void executeMapping5(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/PostalCode -> getLocations/address/postalCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/PostalCode && target instanceof getLocations/address/postalCode) {
                GetLocationList3X1M/GetLocationListRequest/PostalCode sourceObj = (GetLocationList3X1M/GetLocationListRequest/PostalCode) source;
                getLocations/address/postalCode targetObj = (getLocations/address/postalCode) target;

                // Map GetLocationList3X1M/GetLocationListRequest/PostalCode to getLocations/address/postalCode
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/PostalCode() != null) {
                    targetObj.setgetLocations/address/postalCode(sourceObj.getGetLocationList3X1M/GetLocationListRequest/PostalCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/PostalCode", "getLocations/address/postalCode", sourceObj.getGetLocationList3X1M/GetLocationListRequest/PostalCode());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/PostalCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/PostalCode", "getLocations/address/postalCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 5: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 6: Move mapping from GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode to getLocations/address/locationPlaceCode
     * Source: GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode ()
     * Target: getLocations/address/locationPlaceCode ()
     */
    private void executeMapping6(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode -> getLocations/address/locationPlaceCode
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode && target instanceof getLocations/address/locationPlaceCode) {
                GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode sourceObj = (GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode) source;
                getLocations/address/locationPlaceCode targetObj = (getLocations/address/locationPlaceCode) target;

                // Map GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode to getLocations/address/locationPlaceCode
                if (sourceObj.getGetLocationList3X1M/GetLocationListRequest/LocationPlaceCode() != null) {
                    targetObj.setgetLocations/address/locationPlaceCode(sourceObj.getGetLocationList3X1M/GetLocationListRequest/LocationPlaceCode());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode", "getLocations/address/locationPlaceCode", sourceObj.getGetLocationList3X1M/GetLocationListRequest/LocationPlaceCode());
                } else {
                    logger.debug("Source property GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1M/GetLocationListRequest/LocationPlaceCode", "getLocations/address/locationPlaceCode");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 6: {0}", e.getMessage());
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
