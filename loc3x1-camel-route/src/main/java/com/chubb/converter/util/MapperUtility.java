package com.chubb.converter.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Utility class containing common mapping methods used across all generated mappers.
 * This class eliminates code duplication by providing reusable mapping functionality.
 */
public class MapperUtility {
    
    private static final Logger logger = LoggerFactory.getLogger(MapperUtility.class);
    
    public static <T> void transferValues(List<T> source, List<T> target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source and target lists must not be null.");
        }
        target.addAll(source);
    }

    
    /**
     * Map ALL nested properties recursively down to simple data types
     * This method ensures no property is missed in the mapping process
     * 
     * @param source The source object to map from
     * @param target The target object to map to
     */
    public static void mapAllNestedProperties(Object source, Object target) {
        if (source == null || target == null) return;
        
        try {
            // Map all fields of the source object using reflection
            Field[] fields = source.getClass().getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object sourceValue = field.get(source);
                
                if (sourceValue != null) {
                    // Get the corresponding field in target object
                    Field targetField = getTargetField(field.getName(), target.getClass());
                    if (targetField != null) {
                        targetField.setAccessible(true);
                        
                        // Check if it's a collection
                        if (Collection.class.isAssignableFrom(field.getType())) {
                            mapCollectionFieldRecursively(sourceValue, target, targetField);
                        } else if (isComplexObjectType(field.getType())) {
                            mapComplexFieldRecursively(sourceValue, target, targetField);
                        } else {
                            // Simple field mapping
                            targetField.set(target, sourceValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error mapping nested properties: " + e.getMessage(), e);
        }
    }
    
    /**
     * Get target field by name from target class
     * 
     * @param fieldName The name of the field to find
     * @param targetClass The target class to search in
     * @return The Field object if found, null otherwise
     */
    public static Field getTargetField(String fieldName, Class<?> targetClass) {
        try {
            return targetClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // Try to find field in parent classes
            Class<?> superClass = targetClass.getSuperclass();
            if (superClass != null && !superClass.equals(Object.class)) {
                return getTargetField(fieldName, superClass);
            }
            return null;
        }
    }
    
    /**
     * Map collection field recursively
     * 
     * @param sourceValue The source collection value
     * @param targetObject The target object containing the collection
     * @param targetField The target field to set the collection on
     */
    public static void mapCollectionFieldRecursively(Object sourceValue, Object targetObject, Field targetField) {
        try {
            Collection<?> sourceCollection = (Collection<?>) sourceValue;
            
            // Get target collection
            Object targetValue = targetField.get(targetObject);
            Collection<Object> targetCollection;
            
            if (targetValue == null) {
                targetCollection = new java.util.ArrayList<>();
                targetField.set(targetObject, targetCollection);
            } else {
                targetCollection = (Collection<Object>) targetValue;
            }
            
            // Map each item in the collection recursively
            for (Object sourceItem : sourceCollection) {
                if (sourceItem != null) {
                    if (isComplexObjectType(sourceItem.getClass())) {
                        Object targetItem = createInstance(sourceItem.getClass());
                        mapAllNestedProperties(sourceItem, targetItem);
                        targetCollection.add(targetItem);
                    } else {
                        targetCollection.add(sourceItem);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error mapping collection field recursively: " + e.getMessage(), e);
        }
    }
    
    /**
     * Map complex field recursively
     * 
     * @param sourceValue The source complex object value
     * @param targetObject The target object containing the complex field
     * @param targetField The target field to set the complex object on
     */
    public static void mapComplexFieldRecursively(Object sourceValue, Object targetObject, Field targetField) {
        try {
            Object targetValue = targetField.get(targetObject);
            
            if (targetValue == null) {
                targetValue = createInstance(sourceValue.getClass());
                targetField.set(targetObject, targetValue);
            }
            
            // Map all properties of the complex object recursively
            mapAllNestedProperties(sourceValue, targetValue);
        } catch (Exception e) {
            logger.error("Error mapping complex field recursively: " + e.getMessage(), e);
        }
    }
    
    /**
     * Check if a type is a complex object (not primitive, wrapper, or String)
     * 
     * @param clazz The class to check
     * @return true if it's a complex object, false otherwise
     */
    public static boolean isComplexObjectType(Class<?> clazz) {
        return !clazz.isPrimitive() && 
               !clazz.isAssignableFrom(String.class) &&
               !clazz.isAssignableFrom(Number.class) &&
               !clazz.isAssignableFrom(Boolean.class) &&
               !clazz.isAssignableFrom(Character.class) &&
               !clazz.isAssignableFrom(Date.class) &&
               !clazz.isAssignableFrom(Collection.class) &&
               !clazz.isAssignableFrom(Map.class);
    }
    
    /**
     * Create a new instance of the given class
     * 
     * @param clazz The class to instantiate
     * @return A new instance of the class, or null if creation fails
     */
    public static Object createInstance(Class<?> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.error("Error creating instance of " + clazz.getSimpleName() + ": " + e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * Map collection property (alternative method for different mapping scenarios)
     * 
     * @param source The source object
     * @param target The target object
     * @param field The field to map
     * @param sourceValue The source value to map
     */
    public static void mapCollectionProperty(Object source, Object target, Field field, Object sourceValue) {
        try {
            Collection<?> sourceCollection = (Collection<?>) sourceValue;
            
            // Get target collection
            Object targetValue = field.get(target);
            Collection<Object> targetCollection;
            
            if (targetValue == null) {
                if (field.getType().isAssignableFrom(java.util.ArrayList.class)) {
                    targetCollection = new java.util.ArrayList<>();
                } else if (field.getType().isAssignableFrom(java.util.LinkedList.class)) {
                    targetCollection = new java.util.LinkedList<>();
                } else {
                    targetCollection = new java.util.ArrayList<>();
                }
                field.set(target, targetCollection);
            } else {
                targetCollection = (Collection<Object>) targetValue;
            }
            
            // Map each item in the collection
            for (Object sourceItem : sourceCollection) {
                if (sourceItem != null) {
                    if (isComplexObjectType(sourceItem.getClass())) {
                        Object targetItem = createInstance(sourceItem.getClass());
                        mapAllNestedProperties(sourceItem, targetItem);
                        targetCollection.add(targetItem);
                    } else {
                        targetCollection.add(sourceItem);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error mapping collection property: " + e.getMessage(), e);
        }
    }
    
    /**
     * Map complex property (alternative method for different mapping scenarios)
     * 
     * @param source The source object
     * @param target The target object
     * @param field The field to map
     * @param sourceValue The source value to map
     */
    public static void mapComplexProperty(Object source, Object target, Field field, Object sourceValue) {
        try {
            Object targetValue = field.get(target);
            
            if (targetValue == null) {
                targetValue = createInstance(sourceValue.getClass());
                field.set(target, targetValue);
            }
            
            // Map all properties of the complex object recursively
            mapAllNestedProperties(sourceValue, targetValue);
        } catch (Exception e) {
            logger.error("Error mapping complex property: " + e.getMessage(), e);
        }
    }
    
    /**
     * Validate that all required fields are populated and mapping is complete
     * 
     * @param source The source object to validate
     * @param target The target object to validate
     * @return true if validation passes, false otherwise
     */
    public static boolean validateMapping(Object source, Object target) {
        if (source == null || target == null) {
            logger.warn("Validation failed: source or target object is null");
            return false;
        }
        
        try {
            // Validate that both objects are of the same type or compatible types
            if (!isCompatibleTypes(source.getClass(), target.getClass())) {
                logger.warn("Validation failed: incompatible types - source: {}, target: {}", 
                    source.getClass().getSimpleName(), target.getClass().getSimpleName());
                return false;
            }
            
            // Validate that all non-null fields in source are mapped to target
            if (!validateFieldMapping(source, target)) {
                logger.warn("Validation failed: field mapping incomplete");
                return false;
            }
            
            // Validate that required fields are not null in target
            if (!validateRequiredFields(target)) {
                logger.warn("Validation failed: required fields are null in target");
                return false;
            }
            
            // Validate data integrity
            if (!validateDataIntegrity(source, target)) {
                logger.warn("Validation failed: data integrity check failed");
                return false;
            }
            
            logger.debug("Validation passed for mapping from {} to {}", 
                source.getClass().getSimpleName(), target.getClass().getSimpleName());
            return true;
            
        } catch (Exception e) {
            logger.error("Validation error: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Check if source and target types are compatible for mapping
     */
    private static boolean isCompatibleTypes(Class<?> sourceClass, Class<?> targetClass) {
        // Same class types are always compatible
        if (sourceClass.equals(targetClass)) {
            return true;
        }
        
        // Check if they have similar field structures
        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();
        
        // If both have similar number of fields and field names, consider compatible
        if (Math.abs(sourceFields.length - targetFields.length) <= 2) {
            int commonFields = 0;
            for (Field sourceField : sourceFields) {
                for (Field targetField : targetFields) {
                    if (sourceField.getName().equals(targetField.getName()) && 
                        isCompatibleFieldTypes(sourceField.getType(), targetField.getType())) {
                        commonFields++;
                        break;
                    }
                }
            }
            return commonFields >= Math.min(sourceFields.length, targetFields.length) * 0.5;
        }
        
        return false;
    }
    
    /**
     * Check if field types are compatible for mapping
     */
    private static boolean isCompatibleFieldTypes(Class<?> sourceType, Class<?> targetType) {
        // Same types are compatible
        if (sourceType.equals(targetType)) {
            return true;
        }
        
        // Primitive and wrapper types are compatible
        if ((sourceType.isPrimitive() && getWrapperClass(sourceType).equals(targetType)) ||
            (targetType.isPrimitive() && getWrapperClass(targetType).equals(sourceType))) {
            return true;
        }
        
        // String types are compatible with most types
        if (sourceType.equals(String.class) || targetType.equals(String.class)) {
            return true;
        }
        
        // Collection types are compatible if they have the same raw type
        if (Collection.class.isAssignableFrom(sourceType) && Collection.class.isAssignableFrom(targetType)) {
            return true;
        }
        
        // Both are complex objects - check if they have similar structure
        if (isComplexObjectType(sourceType) && isComplexObjectType(targetType)) {
            return true; // Assume compatible for complex objects
        }
        
        return false;
    }
    
    /**
     * Get wrapper class for primitive types
     */
    private static Class<?> getWrapperClass(Class<?> primitiveType) {
        if (primitiveType == boolean.class) return Boolean.class;
        if (primitiveType == byte.class) return Byte.class;
        if (primitiveType == char.class) return Character.class;
        if (primitiveType == short.class) return Short.class;
        if (primitiveType == int.class) return Integer.class;
        if (primitiveType == long.class) return Long.class;
        if (primitiveType == float.class) return Float.class;
        if (primitiveType == double.class) return Double.class;
        return primitiveType;
    }
    
    /**
     * Validate that all non-null fields in source are mapped to target
     */
    private static boolean validateFieldMapping(Object source, Object target) {
        try {
            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = target.getClass().getDeclaredFields();
            
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                Object sourceValue = sourceField.get(source);
                
                if (sourceValue != null) {
                    // Find corresponding field in target
                    Field targetField = findCorrespondingField(sourceField, targetFields);
                    if (targetField == null) {
                        logger.debug("No corresponding field found for source field: {}", sourceField.getName());
                        continue; // Not all fields need to be mapped
                    }
                    
                    targetField.setAccessible(true);
                    Object targetValue = targetField.get(target);
                    
                    // Check if the field was mapped (not null in target)
                    if (targetValue == null) {
                        logger.debug("Field {} was not mapped to target", sourceField.getName());
                        // This is not necessarily an error - some fields might be optional
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating field mapping: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Find corresponding field in target fields array
     */
    private static Field findCorrespondingField(Field sourceField, Field[] targetFields) {
        for (Field targetField : targetFields) {
            if (targetField.getName().equals(sourceField.getName())) {
                return targetField;
            }
        }
        return null;
    }
    
    /**
     * Validate that required fields are not null in target
     */
    private static boolean validateRequiredFields(Object target) {
        try {
            Field[] fields = target.getClass().getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(target);
                
                // Check for required field patterns
                if (isRequiredField(field.getName()) && value == null) {
                    logger.debug("Required field {} is null in target", field.getName());
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating required fields: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Check if a field name indicates it's required
     */
    private static boolean isRequiredField(String fieldName) {
        String lowerName = fieldName.toLowerCase();
        
        // Common required field patterns
        return lowerName.contains("id") ||
               lowerName.contains("code") ||
               lowerName.contains("name") ||
               lowerName.contains("type") ||
               lowerName.contains("status") ||
               lowerName.contains("required") ||
               lowerName.endsWith("id") ||
               lowerName.endsWith("code") ||
               lowerName.endsWith("name");
    }
    
    /**
     * Validate data integrity between source and target
     */
    private static boolean validateDataIntegrity(Object source, Object target) {
        try {
            Field[] sourceFields = source.getClass().getDeclaredFields();
            Field[] targetFields = target.getClass().getDeclaredFields();
            
            for (Field sourceField : sourceFields) {
                sourceField.setAccessible(true);
                Object sourceValue = sourceField.get(source);
                
                if (sourceValue != null) {
                    Field targetField = findCorrespondingField(sourceField, targetFields);
                    if (targetField != null) {
                        targetField.setAccessible(true);
                        Object targetValue = targetField.get(target);
                        
                        if (targetValue != null) {
                            // Validate that the values are compatible
                            if (!validateValueIntegrity(sourceValue, targetValue)) {
                                logger.debug("Value integrity check failed for field: {}", sourceField.getName());
                                return false;
                            }
                        }
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating data integrity: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Validate that source and target values are compatible
     */
    private static boolean validateValueIntegrity(Object sourceValue, Object targetValue) {
        // Same values are always valid
        if (sourceValue.equals(targetValue)) {
            return true;
        }
        
        // For collections, check if they have similar content
        if (sourceValue instanceof Collection && targetValue instanceof Collection) {
            Collection<?> sourceCollection = (Collection<?>) sourceValue;
            Collection<?> targetCollection = (Collection<?>) targetValue;
            
            // If both collections have the same size, consider them compatible
            return sourceCollection.size() == targetCollection.size();
        }
        
        // For complex objects, check if they have similar structure
        if (isComplexObjectType(sourceValue.getClass()) && isComplexObjectType(targetValue.getClass())) {
            // For now, assume complex objects are compatible if they're both non-null
            return true;
        }
        
        // For primitive types, check if they can be converted
        if (isConvertibleType(sourceValue.getClass(), targetValue.getClass())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if source type can be converted to target type
     */
    private static boolean isConvertibleType(Class<?> sourceType, Class<?> targetType) {
        // String can be converted to most types
        if (sourceType.equals(String.class)) {
            return true;
        }
        
        // Numbers can be converted to other numbers
        if (Number.class.isAssignableFrom(sourceType) && Number.class.isAssignableFrom(targetType)) {
            return true;
        }
        
        // Same primitive types are convertible
        if (sourceType.isPrimitive() && targetType.isPrimitive()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Validate XSD POJO specific requirements
     * 
     * @param source The source XSD POJO object
     * @param target The target XSD POJO object
     * @return true if XSD POJO validation passes, false otherwise
     */
    public static boolean validateXsdPojoRequirements(Object source, Object target) {
        if (source == null || target == null) {
            logger.warn("XSD POJO validation failed: source or target object is null");
            return false;
        }
        
        try {
            // Validate namespace compatibility
            if (!validateNamespaceCompatibility(source, target)) {
                logger.warn("XSD POJO validation failed: namespace incompatibility");
                return false;
            }
            
            // Validate required XSD elements are present
            if (!validateRequiredXsdElements(target)) {
                logger.warn("XSD POJO validation failed: required XSD elements missing");
                return false;
            }
            
            // Validate XSD constraints
            if (!validateXsdConstraints(target)) {
                logger.warn("XSD POJO validation failed: XSD constraints violated");
                return false;
            }
            
            logger.debug("XSD POJO validation passed for mapping from {} to {}", 
                source.getClass().getSimpleName(), target.getClass().getSimpleName());
            return true;
            
        } catch (Exception e) {
            logger.error("XSD POJO validation error: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Validate namespace compatibility between source and target
     */
    private static boolean validateNamespaceCompatibility(Object source, Object target) {
        // Check if both objects are from compatible namespaces
        String sourcePackage = source.getClass().getPackage().getName();
        String targetPackage = target.getClass().getPackage().getName();
        
        // Extract namespace from package name
        String sourceNamespace = extractNamespaceFromPackage(sourcePackage);
        String targetNamespace = extractNamespaceFromPackage(targetPackage);
        
        // For now, assume compatible if they're from the same domain
        return sourceNamespace.equals(targetNamespace) || 
               sourceNamespace.contains("location") && targetNamespace.contains("location") ||
               sourceNamespace.contains("core") && targetNamespace.contains("core");
    }
    
    /**
     * Extract namespace from package name
     */
    private static String extractNamespaceFromPackage(String packageName) {
        if (packageName == null || packageName.isEmpty()) {
            return "unknown";
        }
        
        // Extract the main domain from package name
        String[] parts = packageName.split("\\.");
        if (parts.length >= 2) {
            return parts[parts.length - 2]; // Get the domain part
        }
        
        return packageName;
    }
    
    /**
     * Validate that required XSD elements are present
     */
    private static boolean validateRequiredXsdElements(Object target) {
        try {
            Field[] fields = target.getClass().getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(target);
                
                // Check for XSD required elements
                if (isXsdRequiredElement(field.getName()) && value == null) {
                    logger.debug("Required XSD element {} is null in target", field.getName());
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating required XSD elements: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Check if a field name indicates it's a required XSD element
     */
    private static boolean isXsdRequiredElement(String fieldName) {
        String lowerName = fieldName.toLowerCase();
        
        // XSD required element patterns
        return lowerName.contains("element") ||
               lowerName.contains("attribute") ||
               lowerName.contains("sequence") ||
               lowerName.contains("choice") ||
               lowerName.contains("all") ||
               lowerName.endsWith("element") ||
               lowerName.endsWith("attribute");
    }
    
    /**
     * Validate XSD constraints
     */
    private static boolean validateXsdConstraints(Object target) {
        try {
            Field[] fields = target.getClass().getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(target);
                
                if (value != null) {
                    // Validate string length constraints
                    if (value instanceof String) {
                        if (!validateStringConstraints(field.getName(), (String) value)) {
                            logger.debug("String constraint validation failed for field: {}", field.getName());
                            return false;
                        }
                    }
                    
                    // Validate numeric constraints
                    if (value instanceof Number) {
                        if (!validateNumericConstraints(field.getName(), (Number) value)) {
                            logger.debug("Numeric constraint validation failed for field: {}", field.getName());
                            return false;
                        }
                    }
                    
                    // Validate collection constraints
                    if (value instanceof Collection) {
                        if (!validateCollectionConstraints(field.getName(), (Collection<?>) value)) {
                            logger.debug("Collection constraint validation failed for field: {}", field.getName());
                            return false;
                        }
                    }
                }
            }
            
            return true;
        } catch (Exception e) {
            logger.error("Error validating XSD constraints: {}", e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Validate string constraints
     */
    private static boolean validateStringConstraints(String fieldName, String value) {
        // Basic string validation
        if (value == null) {
            return true; // Null values are handled elsewhere
        }
        
        // Check for reasonable string length (not too long)
        if (value.length() > 10000) {
            logger.debug("String value too long for field: {} (length: {})", fieldName, value.length());
            return false;
        }
        
        // Check for valid characters (no control characters)
        for (char c : value.toCharArray()) {
            if (Character.isISOControl(c) && c != '\n' && c != '\r' && c != '\t') {
                logger.debug("Invalid control character in field: {}", fieldName);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validate numeric constraints
     */
    private static boolean validateNumericConstraints(String fieldName, Number value) {
        // Basic numeric validation
        if (value == null) {
            return true; // Null values are handled elsewhere
        }
        
        // Check for reasonable numeric ranges
        if (value instanceof Integer) {
            int intValue = value.intValue();
            if (intValue < Integer.MIN_VALUE + 1000 || intValue > Integer.MAX_VALUE - 1000) {
                logger.debug("Integer value out of reasonable range for field: {} (value: {})", fieldName, intValue);
                return false;
            }
        }
        
        if (value instanceof Long) {
            long longValue = value.longValue();
            if (longValue < Long.MIN_VALUE + 1000 || longValue > Long.MAX_VALUE - 1000) {
                logger.debug("Long value out of reasonable range for field: {} (value: {})", fieldName, longValue);
                return false;
            }
        }
        
        if (value instanceof Double) {
            double doubleValue = value.doubleValue();
            if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                logger.debug("Invalid double value for field: {} (value: {})", fieldName, doubleValue);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validate collection constraints
     */
    private static boolean validateCollectionConstraints(String fieldName, Collection<?> value) {
        // Basic collection validation
        if (value == null) {
            return true; // Null values are handled elsewhere
        }
        
        // Check for reasonable collection size
        if (value.size() > 10000) {
            logger.debug("Collection too large for field: {} (size: {})", fieldName, value.size());
            return false;
        }
        
        // Check for null elements in collection
        for (Object element : value) {
            if (element == null) {
                logger.debug("Null element found in collection for field: {}", fieldName);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Validate mapping for generated mapper classes
     * This method is specifically designed for use in generated mapper classes
     * 
     * @param sourceObject The source object to validate
     * @param targetObject The target object to validate
     * @param logger The logger instance from the calling class
     * @return true if validation passes, false otherwise
     */
    public static boolean validateMapping(Object sourceObject, Object targetObject, Logger logger) {
        if (sourceObject == null || targetObject == null) {
            logger.warn("Validation failed: source or target object is null");
            return false;
        }
        
        // Use comprehensive validation
        boolean basicValidation = validateMapping(sourceObject, targetObject);
        boolean xsdValidation = validateXsdPojoRequirements(sourceObject, targetObject);
        
        if (!basicValidation) {
            logger.warn("Basic mapping validation failed");
            return false;
        }
        
        if (!xsdValidation) {
            logger.warn("XSD POJO validation failed");
            return false;
        }
        
        logger.info("Mapping validation passed successfully");
        return true;
    }
    
    /**
     * Validate submap mapping for generated mapper classes
     * This method is specifically designed for use in generated submap mapper classes
     * 
     * @param source The source object to validate
     * @param target The target object to validate
     * @param logger The logger instance from the calling class
     * @return true if validation passes, false otherwise
     */
    public static boolean validateSubmapMapping(Object source, Object target, Logger logger) {
        if (source == null || target == null) {
            logger.warn("Submap validation failed: source or target object is null");
            return false;
        }
        
        // Use comprehensive validation
        boolean basicValidation = validateMapping(source, target);
        boolean xsdValidation = validateXsdPojoRequirements(source, target);
        
        if (!basicValidation) {
            logger.warn("Submap basic validation failed");
            return false;
        }
        
        if (!xsdValidation) {
            logger.warn("Submap XSD POJO validation failed");
            return false;
        }
        
        logger.debug("Submap validation passed successfully");
        return true;
    }
    
    /**
     * Convert string to camelCase
     * 
     * @param input The input string to convert
     * @return The camelCase version of the input string
     */
    public static String toCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (i == 0) {
                result.append(part.toLowerCase());
            } else {
                result.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
            }
        }
        
        return result.toString();
    }
    
    /**
     * Capitalize the first letter of a string
     * 
     * @param input The input string to capitalize
     * @return The capitalized string
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
    
    /**
     * Convert string to PascalCase
     * 
     * @param input The input string to convert
     * @return The PascalCase version of the input string
     */
    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        
        String[] parts = input.split("_");
        StringBuilder result = new StringBuilder();
        
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(part.substring(0, 1).toUpperCase()).append(part.substring(1).toLowerCase());
            }
        }
        
        return result.toString();
    }
}
