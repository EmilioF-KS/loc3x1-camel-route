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
 * LOC3X1GetLocationListReplyMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1GetLocationListReplyMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg ()
 * Target: GetLocationListResponseMsg:GetLocationListResponseMsg ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1GetLocationListReplyMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1GetLocationListReplyMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg
     */
    public static class GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg {
        private String GetLocationList3X1BResponse/GetLocationListReply/Location;

        public String getGetLocationList3X1BResponse/GetLocationListReply/Location() {
            return GetLocationList3X1BResponse/GetLocationListReply/Location;
        }

        public void setGetLocationList3X1BResponse/GetLocationListReply/Location(String GetLocationList3X1BResponse/GetLocationListReply/Location) {
            this.GetLocationList3X1BResponse/GetLocationListReply/Location = GetLocationList3X1BResponse/GetLocationListReply/Location;
        }

        private String GetLocationList3X1BResponse/GetLocationListReply/StatusInformation;

        public String getGetLocationList3X1BResponse/GetLocationListReply/StatusInformation() {
            return GetLocationList3X1BResponse/GetLocationListReply/StatusInformation;
        }

        public void setGetLocationList3X1BResponse/GetLocationListReply/StatusInformation(String GetLocationList3X1BResponse/GetLocationListReply/StatusInformation) {
            this.GetLocationList3X1BResponse/GetLocationListReply/StatusInformation = GetLocationList3X1BResponse/GetLocationListReply/StatusInformation;
        }

    }

    /**
     * Target model class for GetLocationListResponseMsg:GetLocationListResponseMsg
     */
    public static class GetLocationListResponseMsg:GetLocationListResponseMsg {
        private String GetLocationListResponse/GetLocationListReply/Location;

        public String getGetLocationListResponse/GetLocationListReply/Location() {
            return GetLocationListResponse/GetLocationListReply/Location;
        }

        public void setGetLocationListResponse/GetLocationListReply/Location(String GetLocationListResponse/GetLocationListReply/Location) {
            this.GetLocationListResponse/GetLocationListReply/Location = GetLocationListResponse/GetLocationListReply/Location;
        }

        private String GetLocationListResponse/GetLocationListReply/StatusInformation;

        public String getGetLocationListResponse/GetLocationListReply/StatusInformation() {
            return GetLocationListResponse/GetLocationListReply/StatusInformation;
        }

        public void setGetLocationListResponse/GetLocationListReply/StatusInformation(String GetLocationListResponse/GetLocationListReply/StatusInformation) {
            this.GetLocationListResponse/GetLocationListReply/StatusInformation = GetLocationListResponse/GetLocationListReply/StatusInformation;
        }

    }

    /**
     * Main mapping method to transform GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg to GetLocationListResponseMsg:GetLocationListResponseMsg
     */
    public GetLocationListResponseMsg:GetLocationListResponseMsg mapGetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsgToGetLocationListResponseMsg:GetLocationListResponseMsg(GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg source) {
        try {
            logger.info("Starting mapping from GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg to GetLocationListResponseMsg:GetLocationListResponseMsg");
            
            // Create output GetLocationListResponseMsg:GetLocationListResponseMsg object
            GetLocationListResponseMsg:GetLocationListResponseMsg target = new GetLocationListResponseMsg:GetLocationListResponseMsg();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg to GetLocationListResponseMsg:GetLocationListResponseMsg");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg to GetLocationListResponseMsg:GetLocationListResponseMsg", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(GetLocationList3X1BResponseMsg:GetLocationList3X1BResponseMsg source, GetLocationListResponseMsg:GetLocationListResponseMsg target) {
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
     * Execute mapping 2: Move mapping from GetLocationList3X1BResponse/GetLocationListReply/StatusInformation to GetLocationListResponse/GetLocationListReply/StatusInformation
     * Source: GetLocationList3X1BResponse/GetLocationListReply/StatusInformation ()
     * Target: GetLocationListResponse/GetLocationListReply/StatusInformation ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: GetLocationList3X1BResponse/GetLocationListReply/StatusInformation -> GetLocationListResponse/GetLocationListReply/StatusInformation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof GetLocationList3X1BResponse/GetLocationListReply/StatusInformation && target instanceof GetLocationListResponse/GetLocationListReply/StatusInformation) {
                GetLocationList3X1BResponse/GetLocationListReply/StatusInformation sourceObj = (GetLocationList3X1BResponse/GetLocationListReply/StatusInformation) source;
                GetLocationListResponse/GetLocationListReply/StatusInformation targetObj = (GetLocationListResponse/GetLocationListReply/StatusInformation) target;

                // Map GetLocationList3X1BResponse/GetLocationListReply/StatusInformation to GetLocationListResponse/GetLocationListReply/StatusInformation
                if (sourceObj.getGetLocationList3X1BResponse/GetLocationListReply/StatusInformation() != null) {
                    targetObj.setGetLocationListResponse/GetLocationListReply/StatusInformation(sourceObj.getGetLocationList3X1BResponse/GetLocationListReply/StatusInformation());
                    logger.debug("Moved {0} -> {1}: {2}", "GetLocationList3X1BResponse/GetLocationListReply/StatusInformation", "GetLocationListResponse/GetLocationListReply/StatusInformation", sourceObj.getGetLocationList3X1BResponse/GetLocationListReply/StatusInformation());
                } else {
                    logger.debug("Source property GetLocationList3X1BResponse/GetLocationListReply/StatusInformation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "GetLocationList3X1BResponse/GetLocationListReply/StatusInformation", "GetLocationListResponse/GetLocationListReply/StatusInformation");
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
