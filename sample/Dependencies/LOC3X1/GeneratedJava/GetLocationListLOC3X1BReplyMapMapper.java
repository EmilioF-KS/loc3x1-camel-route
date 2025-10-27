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
 * GetLocationListLOC3X1BReplyMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from GetLocationListLOC3X1BReplyMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: location_list_reply_loc3x1m:LocationListReply ()
 * Target: LocationListReply:LocationListReply ()
 * Target Namespace: http://LocationModule
 */
@Component
public class GetLocationListLOC3X1BReplyMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(GetLocationListLOC3X1BReplyMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for location_list_reply_loc3x1m:LocationListReply
     */
    public static class location_list_reply_loc3x1m:LocationListReply {
        private String Location;

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        private String StatusInformation;

        public String getStatusInformation() {
            return StatusInformation;
        }

        public void setStatusInformation(String StatusInformation) {
            this.StatusInformation = StatusInformation;
        }

    }

    /**
     * Target model class for LocationListReply:LocationListReply
     */
    public static class LocationListReply:LocationListReply {
        private String Location;

        public String getLocation() {
            return Location;
        }

        public void setLocation(String Location) {
            this.Location = Location;
        }

        private String StatusInformation;

        public String getStatusInformation() {
            return StatusInformation;
        }

        public void setStatusInformation(String StatusInformation) {
            this.StatusInformation = StatusInformation;
        }

    }

    /**
     * Main mapping method to transform location_list_reply_loc3x1m:LocationListReply to LocationListReply:LocationListReply
     */
    public LocationListReply:LocationListReply maplocation_list_reply_loc3x1m:LocationListReplyToLocationListReply:LocationListReply(location_list_reply_loc3x1m:LocationListReply source) {
        try {
            logger.info("Starting mapping from location_list_reply_loc3x1m:LocationListReply to LocationListReply:LocationListReply");
            
            // Create output LocationListReply:LocationListReply object
            LocationListReply:LocationListReply target = new LocationListReply:LocationListReply();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from location_list_reply_loc3x1m:LocationListReply to LocationListReply:LocationListReply");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map location_list_reply_loc3x1m:LocationListReply to LocationListReply:LocationListReply", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(location_list_reply_loc3x1m:LocationListReply source, LocationListReply:LocationListReply target) {
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
     * Execute mapping 2: Move mapping from StatusInformation to StatusInformation
     * Source: StatusInformation ()
     * Target: StatusInformation ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Move mapping: StatusInformation -> StatusInformation
            // Source namespace: 
            // Target namespace: 

            // Type-safe mapping using generated models
            if (source instanceof StatusInformation && target instanceof StatusInformation) {
                StatusInformation sourceObj = (StatusInformation) source;
                StatusInformation targetObj = (StatusInformation) target;

                // Map StatusInformation to StatusInformation
                if (sourceObj.getStatusInformation() != null) {
                    targetObj.setStatusInformation(sourceObj.getStatusInformation());
                    logger.debug("Moved {0} -> {1}: {2}", "StatusInformation", "StatusInformation", sourceObj.getStatusInformation());
                } else {
                    logger.debug("Source property StatusInformation is null, skipping mapping");
                }
            } else {
                // Fallback to reflection-based mapping
                mapPropertyByReflection(source, target, "StatusInformation", "StatusInformation");
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
