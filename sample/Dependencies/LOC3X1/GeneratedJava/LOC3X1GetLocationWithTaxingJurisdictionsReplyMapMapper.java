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
 * LOC3X1GetLocationWithTaxingJurisdictionsReplyMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1GetLocationWithTaxingJurisdictionsReplyMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg ()
 * Target: GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1GetLocationWithTaxingJurisdictionsReplyMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1GetLocationWithTaxingJurisdictionsReplyMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg
     */
    public static class GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg {
        private String GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location;

        public String getGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location() {
            return GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        public void setGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location(String GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location) {
            this.GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location = GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        private String GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;

        public String getGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation() {
            return GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

        public void setGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation(String GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) {
            this.GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation = GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

    }

    /**
     * Target model class for GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg
     */
    public static class GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg {
        private String GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location;

        public String getGetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location() {
            return GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        public void setGetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location(String GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location) {
            this.GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location = GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        private String GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;

        public String getGetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation() {
            return GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

        public void setGetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation(String GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) {
            this.GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation = GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

    }

    /**
     * Main mapping method to transform GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg to GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg
     */
    public GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg mapGetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsgToGetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg(GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg source) {
        try {
            logger.info("Starting mapping from GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg to GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg");
            
            // Create output GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg object
            GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg target = new GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg to GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg to GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(GetLocationWithTaxingJurisdictions3X1BResponseMsg:GetLocationWithTaxingJurisdictions3X1BResponseMsg source, GetLocationWithTaxingJurisdictionsResponseMsg:GetLocationWithTaxingJurisdictionsResponseMsg target) {
        // Execute mappings in order
        executeMapping1(source, target);
        executeMapping2(source, target);
    }

    /**
     * Execute mapping 1: 
     * Source:  ()
     * Target:  ()
     */
    private void executeMapping1(Object source, Object target) {
    }

    /**
     * Execute mapping 2: Move mapping from GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation to GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation
     * Source: GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation ()
     * Target: GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation -> GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation && target instanceof GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) {
                GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation sourceObj = (GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) source;
                GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation targetObj = (GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) target;

                // Map GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation to GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation
                if (sourceObj.getGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation() != null) {
                    targetObj.setGetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation(sourceObj.getGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation", "GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation", sourceObj.getGetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation());
                } else {
                    logger.debug("Source property GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationWithTaxingJurisdictions3X1BResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation", "GetLocationWithTaxingJurisdictionsResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation");
            }
        } catch (Exception e) {
            logger.warn("Error in move mapping 2: {0}", e.getMessage());
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
