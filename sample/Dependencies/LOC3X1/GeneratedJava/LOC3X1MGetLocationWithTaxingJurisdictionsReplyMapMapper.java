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
 * LOC3X1MGetLocationWithTaxingJurisdictionsReplyMap Mapper - Generated from MAP file
 * This class contains all the mapping logic extracted from LOC3X1MGetLocationWithTaxingJurisdictionsReplyMap.map
 * Generated on: 2025-08-25 16:52:07
 *
 * Source: getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse ()
 * Target: GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg ()
 * Target Namespace: http://Location2MediationModule
 */
@Component
public class LOC3X1MGetLocationWithTaxingJurisdictionsReplyMapMapper {

    private static final Logger logger = LoggerFactory.getLogger(LOC3X1MGetLocationWithTaxingJurisdictionsReplyMapMapper.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Source model class for getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse
     */
    public static class getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse {
        private String getLocationWithTaxInfoResponse/location;

        public String getgetLocationWithTaxInfoResponse/location() {
            return getLocationWithTaxInfoResponse/location;
        }

        public void setgetLocationWithTaxInfoResponse/location(String getLocationWithTaxInfoResponse/location) {
            this.getLocationWithTaxInfoResponse/location = getLocationWithTaxInfoResponse/location;
        }

        private String getLocationWithTaxInfoResponse/location/fireDistrictCode;

        public String getgetLocationWithTaxInfoResponse/location/fireDistrictCode() {
            return getLocationWithTaxInfoResponse/location/fireDistrictCode;
        }

        public void setgetLocationWithTaxInfoResponse/location/fireDistrictCode(String getLocationWithTaxInfoResponse/location/fireDistrictCode) {
            this.getLocationWithTaxInfoResponse/location/fireDistrictCode = getLocationWithTaxInfoResponse/location/fireDistrictCode;
        }

        private String getLocationWithTaxInfoResponse/location/stateOrProvinceName;

        public String getgetLocationWithTaxInfoResponse/location/stateOrProvinceName() {
            return getLocationWithTaxInfoResponse/location/stateOrProvinceName;
        }

        public void setgetLocationWithTaxInfoResponse/location/stateOrProvinceName(String getLocationWithTaxInfoResponse/location/stateOrProvinceName) {
            this.getLocationWithTaxInfoResponse/location/stateOrProvinceName = getLocationWithTaxInfoResponse/location/stateOrProvinceName;
        }

        private String getLocationWithTaxInfoResponse/location/taxInfo/taxCode;

        public String getgetLocationWithTaxInfoResponse/location/taxInfo/taxCode() {
            return getLocationWithTaxInfoResponse/location/taxInfo/taxCode;
        }

        public void setgetLocationWithTaxInfoResponse/location/taxInfo/taxCode(String getLocationWithTaxInfoResponse/location/taxInfo/taxCode) {
            this.getLocationWithTaxInfoResponse/location/taxInfo/taxCode = getLocationWithTaxInfoResponse/location/taxInfo/taxCode;
        }

        private String getLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode;

        public String getgetLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode() {
            return getLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode;
        }

        public void setgetLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode(String getLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode) {
            this.getLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode = getLocationWithTaxInfoResponse/location/taxInfo/alternateTaxCode;
        }

    }

    /**
     * Target model class for GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg
     */
    public static class GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg {
        private String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location;

        public String getGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location() {
            return GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        public void setGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location(String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location) {
            this.GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location = GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location;
        }

        private String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;

        public String getGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction() {
            return GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;
        }

        public void setGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction(String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction) {
            this.GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction = GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;
        }

        private String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;

        public String getGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction() {
            return GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;
        }

        public void setGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction(String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction) {
            this.GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction = GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction;
        }

        private String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;

        public String getGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation() {
            return GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

        public void setGetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation(String GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation) {
            this.GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation = GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/StatusInformation;
        }

    }

    /**
     * Main mapping method to transform getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse to GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg
     */
    public GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg mapgetLocationWithTaxInfoResponse:getLocationWithTaxInfoResponseToGetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg(getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse source) {
        try {
            logger.info("Starting mapping from getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse to GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg");
            
            // Create output GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg object
            GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg target = new GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg();
            
            // Execute mappings in order
            executeMappings(source, target);
            
            logger.info("Successfully completed mapping from getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse to GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg");
            return target;
        } catch (Exception e) {
            logger.error("Error during mapping: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to map getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse to GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg", e);
        }
    }

    /**
     * Execute all property mappings in execution order
     */
    private void executeMappings(getLocationWithTaxInfoResponse:getLocationWithTaxInfoResponse source, GetLocationWithTaxingJurisdictions3X1MResponseMsg:GetLocationWithTaxingJurisdictions3X1MResponseMsg target) {
        // Execute mappings in order
        executeMapping1(source, target);
        executeMapping2(source, target);
        executeMapping3(source, target);
        executeMapping4(source, target);
    }

    /**
     * Execute mapping 1: 
     * Source:  ()
     * Target:  ()
     */
    private void executeMapping1(Object source, Object target) {
    }

    /**
     * Execute mapping 2: Custom mapping from getLocationWithTaxInfoResponse/location/fireDistrictCode to GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction with Java logic
     * Source: getLocationWithTaxInfoResponse/location/fireDistrictCode ()
     * Target: GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction ()
     */
    private void executeMapping2(Object source, Object target) {
        try {
            // Custom mapping: getLocationWithTaxInfoResponse/location/fireDistrictCode -> GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 2");

            // Custom Java code from MAP file:
            java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode;
            java.util.List __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction = (java.util.List)GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction;
            // Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction
            java.lang.String FireDistrictCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode;
            boolean __result__4 = FireDistrictCode == null;
            if (__result__4){
            return ;
            }
            else{
            }
            java.lang.String __result__10 = FireDistrictCode.trim();
            FireDistrictCode = __result__10;
            int __result__12;
            {// text length
            __result__12 = FireDistrictCode.length();
            }
            int textLength = __result__12;
            boolean __result__14 = textLength == 0;
            if (__result__14){
            return ;
            }
            else{
            }
            commonj.sdo.DataObject __result__19;
            {// create TaxingJurisdiction
            com.ibm.websphere.bo.BOFactory factory =
            (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
            __result__19 = factory.create("http://ei/core/taxing_jurisdiction","TaxingJurisdiction");
            }
            commonj.sdo.DataObject TaxingJurisdiction = __result__19;
            java.lang.String __result__21 = "FireDistrict";
            TaxingJurisdiction.setString("TaxingJurisdictionTypeName", __result__21);
            TaxingJurisdiction.setString("TaxingJurisdictionCode", FireDistrictCode);
            boolean __result__27;
            {// add item to list
            __result__27 = __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction.add(TaxingJurisdiction);
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;/**/Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction&#xD;&#xA;&quot;">
            //    <type xsi:type="com.ibm.wbit.activity:NullElementType"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode" variable="true" assignable="false" input="true">
            //    <dataOutputs target="//@executableElements.2"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@dataOutputs.0" value="FireDistrictCode" localVariable="//@localVariables.0" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="FireDistrictCode == null" assignable="false">
            //    <dataOutputs target="//@executableElements.4"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.3/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
            //      <executableGroups executableElements="//@executableElements.4/@conditionalActivities.0/@executableElements.0"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="FireDistrictCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.6/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //    <parameters name="String" dataInputs="//@executableElements.5/@dataOutputs.0">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </parameters>
            //    <result>
            //      <dataOutputs target="//@executableElements.7"/>
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.6/@result/@dataOutputs.0" value="FireDistrictCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.8/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="text length" description="The length of the given text" category="text" template="&lt;%return%> &lt;%input%>.length();">
            //    <parameters name="input" dataInputs="//@executableElements.7/@dataOutputs.0" displayName="input">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </parameters>
            //    <result name="length" displayName="length">
            //      <dataOutputs target="//@executableElements.9"/>
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.8/@result/@dataOutputs.0" value="textLength" localVariable="//@localVariables.1" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="textLength == 0" assignable="false">
            //    <dataOutputs target="//@executableElements.11"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.10/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
            //      <executableGroups executableElements="//@executableElements.11/@conditionalActivities.0/@executableElements.0"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="create TaxingJurisdiction" description="create a new TaxingJurisdiction {http://ei/core/taxing_jurisdiction}" category="SCA and BO services" template="com.ibm.websphere.bo.BOFactory factory = &#xA;   (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService(&quot;com/ibm/websphere/bo/BOFactory&quot;);&#xA; &lt;%return%> factory.create(&quot;http://ei/core/taxing_jurisdiction&quot;,&quot;TaxingJurisdiction&quot;);">
            //    <result>
            //      <dataOutputs target="//@executableElements.13"/>
            //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction" nillable="false"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.12/@result/@dataOutputs.0" value="TaxingJurisdiction" localVariable="//@localVariables.2" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;FireDistrict&quot;" assignable="false">
            //    <dataOutputs target="//@executableElements.15"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.14/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionTypeName" field="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="FireDistrictCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.17"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.16/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionCode" field="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction" variable="true">
            //    <dataOutputs target="//@executableElements.20/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="TaxingJurisdiction" localVariable="//@localVariables.2" variable="true">
            //    <dataOutputs target="//@executableElements.20/@parameters.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="add item to list" description="Add the provided Object at the end of the list" category="list" template="&lt;%return%> &lt;%list%>.add(&lt;%object%>);">
            //    <parameters name="list" dataInputs="//@executableElements.18/@dataOutputs.0" displayName="list">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //    </parameters>
            //    <parameters name="object" dataInputs="//@executableElements.19/@dataOutputs.0" displayName="object">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
            //    </parameters>
            //    <result name="added" displayName="added">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //    </result>
            //  </executableElements>
            //  <localVariables name="FireDistrictCode">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </localVariables>
            //  <localVariables name="textLength">
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //  </localVariables>
            //  <localVariables name="TaxingJurisdiction">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </localVariables>
            //  <executableGroups executableElements="//@executableElements.1 //@executableElements.2"/>
            //  <executableGroups executableElements="//@executableElements.3 //@executableElements.4"/>
            //  <executableGroups executableElements="//@executableElements.5 //@executableElements.6 //@executableElements.7 //@executableElements.8 //@executableElements.9"/>
            //  <executableGroups executableElements="//@executableElements.10 //@executableElements.11"/>
            //  <executableGroups executableElements="//@executableElements.12 //@executableElements.13"/>
            //  <executableGroups executableElements="//@executableElements.14 //@executableElements.15"/>
            //  <executableGroups executableElements="//@executableElements.16 //@executableElements.17"/>
            //  <executableGroups executableElements="//@executableElements.18 //@executableElements.19 //@executableElements.20"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!3:4,1
            //!SMAP!4:5,1
            //!SMAP!5:6,1
            //!SMAP!7:7,1
            //!SMAP!10:11,1
            //!SMAP!11:12,1
            //!SMAP!12:13,4
            //!SMAP!13:17,1
            //!SMAP!14:18,1
            //!SMAP!15:19,1
            //!SMAP!17:20,1
            //!SMAP!19:24,6
            //!SMAP!20:30,1
            //!SMAP!21:31,1
            //!SMAP!22:32,1
            //!SMAP!24:33,1
            //!SMAP!27:34,4
            //!SMAP!1000000:196,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 2: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 3: Custom mapping from getLocationWithTaxInfoResponse/location/stateOrProvinceName to GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction with Java logic
     * Source: getLocationWithTaxInfoResponse/location/stateOrProvinceName ()
     * Target: GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction ()
     */
    private void executeMapping3(Object source, Object target) {
        try {
            // Custom mapping: getLocationWithTaxInfoResponse/location/stateOrProvinceName -> GetLocationWithTaxingJurisdictions3X1MResponse/GetLocationWithTaxingJurisdictionsReply/Location[1]/TaxingJurisdiction
            // Source namespace: 
            // Target namespace: 
            logger.debug("Executing custom mapping 3");

            // Custom Java code from MAP file:
            java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName;
            java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode;
            java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode;
            java.util.List __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction = (java.util.List)GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction;
            // Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction
            commonj.sdo.DataObject __result__2;
            {// create TaxingJurisdiction
            com.ibm.websphere.bo.BOFactory factory =
            (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
            __result__2 = factory.create("http://ei/core/taxing_jurisdiction","TaxingJurisdiction");
            }
            commonj.sdo.DataObject TaxingJurisdiction = __result__2;
            java.lang.String __result__4 = "TaxingTerritory";
            TaxingJurisdiction.setString("TaxingJurisdictionTypeName", __result__4);
            java.lang.String Location_taxInfo_taxCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode;
            boolean __result__8 = Location_taxInfo_taxCode != null;
            if (__result__8){
            java.lang.String __result__12 = Location_taxInfo_taxCode.trim();
            TaxingJurisdiction.setString("TaxingJurisdictionCode", __result__12);
            }
            else{
            }
            java.lang.String Location_stateOrProvinceName = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName;
            boolean __result__17 = Location_stateOrProvinceName != null;
            if (__result__17){
            java.lang.String __result__21 = Location_stateOrProvinceName.trim();
            TaxingJurisdiction.setString("TaxingJurisdictionName", __result__21);
            }
            else{
            }
            boolean __result__26;
            {// add item to list
            __result__26 = __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction.add(TaxingJurisdiction);
            }
            java.lang.String Location_taxInfo_alternateTaxCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode;
            boolean __result__29 = Location_taxInfo_alternateTaxCode == null;
            if (__result__29){
            return ;
            }
            else{
            }
            java.lang.String __result__35 = Location_taxInfo_alternateTaxCode.trim();
            Location_taxInfo_alternateTaxCode = __result__35;
            int __result__37;
            {// text length
            __result__37 = Location_taxInfo_alternateTaxCode.length();
            }
            int textLength = __result__37;
            boolean __result__39 = textLength == 0;
            if (__result__39){
            return ;
            }
            else{
            }
            commonj.sdo.DataObject __result__44;
            {// create LineOfBusinessTaxRate
            com.ibm.websphere.bo.BOFactory factory =
            (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
            __result__44 = factory.create("http://ei/core/line_of_business_tax_ratex1","LineOfBusinessTaxRate");
            }
            commonj.sdo.DataObject LOBTaxRate = __result__44;
            java.lang.String __result__46 = "Auto";
            LOBTaxRate.setString("TaxRateLineOfBusinessName", __result__46);
            LOBTaxRate.setString("TaxRateLineOfBusinessCode", Location_taxInfo_alternateTaxCode);
            java.util.List __result__50 = TaxingJurisdiction.getList("LineOfBusinessTaxRate");
            boolean __result__52;
            {// add item to list
            __result__52 = __result__50.add(LOBTaxRate);
            }
            //@generated:com.ibm.wbit.activity.ui
            //<?xml version="1.0" encoding="UTF-8"?>
            //<com.ibm.wbit.activity:CompositeActivity xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:com.ibm.wbit.activity="http:///com/ibm/wbit/activity.ecore" name="ActivityMethod">
            //  <parameters name="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <parameters name="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <parameters name="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode" objectType="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </parameters>
            //  <exceptions>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
            //  </exceptions>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;/**/Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction&#xD;&#xA;&quot;">
            //    <type xsi:type="com.ibm.wbit.activity:NullElementType"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="create TaxingJurisdiction" description="create a new TaxingJurisdiction {http://ei/core/taxing_jurisdiction}" category="SCA and BO services" template="com.ibm.websphere.bo.BOFactory factory = &#xA;   (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService(&quot;com/ibm/websphere/bo/BOFactory&quot;);&#xA; &lt;%return%> factory.create(&quot;http://ei/core/taxing_jurisdiction&quot;,&quot;TaxingJurisdiction&quot;);">
            //    <result>
            //      <dataOutputs target="//@executableElements.2"/>
            //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction" nillable="false"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.1/@result/@dataOutputs.0" value="TaxingJurisdiction" localVariable="//@localVariables.2" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;TaxingTerritory&quot;" assignable="false">
            //    <dataOutputs target="//@executableElements.4"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.3/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionTypeName" field="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode" variable="true" assignable="false" input="true">
            //    <dataOutputs target="//@executableElements.6"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.5/@dataOutputs.0" value="Location_taxInfo_taxCode" localVariable="//@localVariables.3" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_taxCode != null" assignable="false">
            //    <dataOutputs target="//@executableElements.8"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.7/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_taxCode" localVariable="//@localVariables.3" variable="true">
            //        <dataOutputs target="//@executableElements.8/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.8/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.8/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.8/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionCode" field="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.8/@conditionalActivities.0/@executableElements.0 //@executableElements.8/@conditionalActivities.0/@executableElements.1 //@executableElements.8/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName" variable="true" assignable="false" input="true">
            //    <dataOutputs target="//@executableElements.10"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.9/@dataOutputs.0" value="Location_stateOrProvinceName" localVariable="//@localVariables.4" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName != null" assignable="false">
            //    <dataOutputs target="//@executableElements.12"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.11/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName" localVariable="//@localVariables.4" variable="true">
            //        <dataOutputs target="//@executableElements.12/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //        <parameters name="String" dataInputs="//@executableElements.12/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </parameters>
            //        <result>
            //          <dataOutputs target="//@executableElements.12/@conditionalActivities.0/@executableElements.2"/>
            //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //        </result>
            //      </executableElements>
            //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.12/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionName" field="true">
            //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //      </executableElements>
            //      <executableGroups executableElements="//@executableElements.12/@conditionalActivities.0/@executableElements.0 //@executableElements.12/@conditionalActivities.0/@executableElements.1 //@executableElements.12/@conditionalActivities.0/@executableElements.2"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction" variable="true">
            //    <dataOutputs target="//@executableElements.15/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="TaxingJurisdiction" localVariable="//@localVariables.2" variable="true">
            //    <dataOutputs target="//@executableElements.15/@parameters.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="add item to list" description="Add the provided Object at the end of the list" category="list" template="&lt;%return%> &lt;%list%>.add(&lt;%object%>);">
            //    <parameters name="list" dataInputs="//@executableElements.13/@dataOutputs.0" displayName="list">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //    </parameters>
            //    <parameters name="object" dataInputs="//@executableElements.14/@dataOutputs.0" displayName="object">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
            //    </parameters>
            //    <result name="added" displayName="added">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode" variable="true" assignable="false" input="true">
            //    <dataOutputs target="//@executableElements.17"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.16/@dataOutputs.0" value="Location_taxInfo_alternateTaxCode" localVariable="//@localVariables.0" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_alternateTaxCode == null" assignable="false">
            //    <dataOutputs target="//@executableElements.19"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.18/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
            //      <executableGroups executableElements="//@executableElements.19/@conditionalActivities.0/@executableElements.0"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_alternateTaxCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.21/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
            //    <parameters name="String" dataInputs="//@executableElements.20/@dataOutputs.0">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </parameters>
            //    <result>
            //      <dataOutputs target="//@executableElements.22"/>
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.21/@result/@dataOutputs.0" value="Location_taxInfo_alternateTaxCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.23/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="text length" description="The length of the given text" category="text" template="&lt;%return%> &lt;%input%>.length();">
            //    <parameters name="input" dataInputs="//@executableElements.22/@dataOutputs.0" displayName="input">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
            //    </parameters>
            //    <result name="length" displayName="length">
            //      <dataOutputs target="//@executableElements.24"/>
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.23/@result/@dataOutputs.0" value="textLength" localVariable="//@localVariables.5" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="textLength == 0" assignable="false">
            //    <dataOutputs target="//@executableElements.26"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:BranchElement" dataInputs="//@executableElements.25/@dataOutputs.0">
            //    <conditionalActivities>
            //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
            //      <executableGroups executableElements="//@executableElements.26/@conditionalActivities.0/@executableElements.0"/>
            //      <condition value="true"/>
            //    </conditionalActivities>
            //    <conditionalActivities>
            //      <condition value=""/>
            //    </conditionalActivities>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="create LineOfBusinessTaxRate" description="create a new LineOfBusinessTaxRate {http://ei/core/line_of_business_tax_ratex1}" category="SCA and BO services" template="com.ibm.websphere.bo.BOFactory factory = &#xA;   (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService(&quot;com/ibm/websphere/bo/BOFactory&quot;);&#xA; &lt;%return%> factory.create(&quot;http://ei/core/line_of_business_tax_ratex1&quot;,&quot;LineOfBusinessTaxRate&quot;);">
            //    <result>
            //      <dataOutputs target="//@executableElements.28"/>
            //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1" nillable="false"/>
            //    </result>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.27/@result/@dataOutputs.0" value="LOBTaxRate" localVariable="//@localVariables.1" variable="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="&quot;Auto&quot;" assignable="false">
            //    <dataOutputs target="//@executableElements.30"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.29/@dataOutputs.0" value="LOBTaxRate.TaxRateLineOfBusinessName" field="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_alternateTaxCode" localVariable="//@localVariables.0" variable="true">
            //    <dataOutputs target="//@executableElements.32"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.31/@dataOutputs.0" value="LOBTaxRate.TaxRateLineOfBusinessCode" field="true">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="TaxingJurisdiction.LineOfBusinessTaxRate" field="true">
            //    <dataOutputs target="//@executableElements.35/@parameters.0"/>
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="LOBTaxRate" localVariable="//@localVariables.1" variable="true">
            //    <dataOutputs target="//@executableElements.35/@parameters.1"/>
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
            //  </executableElements>
            //  <executableElements xsi:type="com.ibm.wbit.activity:LibraryActivity" name="add item to list" description="Add the provided Object at the end of the list" category="list" template="&lt;%return%> &lt;%list%>.add(&lt;%object%>);">
            //    <parameters name="list" dataInputs="//@executableElements.33/@dataOutputs.0" displayName="list">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
            //    </parameters>
            //    <parameters name="object" dataInputs="//@executableElements.34/@dataOutputs.0" displayName="object">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
            //    </parameters>
            //    <result name="added" displayName="added">
            //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
            //    </result>
            //  </executableElements>
            //  <localVariables name="Location_taxInfo_alternateTaxCode">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </localVariables>
            //  <localVariables name="LOBTaxRate">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
            //  </localVariables>
            //  <localVariables name="TaxingJurisdiction">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
            //  </localVariables>
            //  <localVariables name="Location_taxInfo_taxCode">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </localVariables>
            //  <localVariables name="Location_stateOrProvinceName">
            //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
            //  </localVariables>
            //  <localVariables name="textLength">
            //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
            //  </localVariables>
            //  <executableGroups executableElements="//@executableElements.1 //@executableElements.2"/>
            //  <executableGroups executableElements="//@executableElements.3 //@executableElements.4"/>
            //  <executableGroups executableElements="//@executableElements.5 //@executableElements.6"/>
            //  <executableGroups executableElements="//@executableElements.7 //@executableElements.8"/>
            //  <executableGroups executableElements="//@executableElements.9 //@executableElements.10"/>
            //  <executableGroups executableElements="//@executableElements.11 //@executableElements.12"/>
            //  <executableGroups executableElements="//@executableElements.13 //@executableElements.14 //@executableElements.15"/>
            //  <executableGroups executableElements="//@executableElements.16 //@executableElements.17"/>
            //  <executableGroups executableElements="//@executableElements.18 //@executableElements.19"/>
            //  <executableGroups executableElements="//@executableElements.20 //@executableElements.21 //@executableElements.22 //@executableElements.23 //@executableElements.24"/>
            //  <executableGroups executableElements="//@executableElements.25 //@executableElements.26"/>
            //  <executableGroups executableElements="//@executableElements.27 //@executableElements.28"/>
            //  <executableGroups executableElements="//@executableElements.29 //@executableElements.30"/>
            //  <executableGroups executableElements="//@executableElements.31 //@executableElements.32"/>
            //  <executableGroups executableElements="//@executableElements.33 //@executableElements.34 //@executableElements.35"/>
            //</com.ibm.wbit.activity:CompositeActivity>
            //@generated:end
            //!SMAP!*S WBIACTDBG
            //!SMAP!*L
            //!SMAP!2:6,6
            //!SMAP!3:12,1
            //!SMAP!4:13,1
            //!SMAP!5:14,1
            //!SMAP!7:15,1
            //!SMAP!8:16,1
            //!SMAP!9:17,1
            //!SMAP!12:18,1
            //!SMAP!13:19,1
            //!SMAP!16:23,1
            //!SMAP!17:24,1
            //!SMAP!18:25,1
            //!SMAP!21:26,1
            //!SMAP!22:27,1
            //!SMAP!26:31,4
            //!SMAP!28:35,1
            //!SMAP!29:36,1
            //!SMAP!30:37,1
            //!SMAP!32:38,1
            //!SMAP!35:42,1
            //!SMAP!36:43,1
            //!SMAP!37:44,4
            //!SMAP!38:48,1
            //!SMAP!39:49,1
            //!SMAP!40:50,1
            //!SMAP!42:51,1
            //!SMAP!44:55,6
            //!SMAP!45:61,1
            //!SMAP!46:62,1
            //!SMAP!47:63,1
            //!SMAP!49:64,1
            //!SMAP!50:65,1
            //!SMAP!52:66,4
            //!SMAP!1000000:373,1

            // TODO: Implement custom mapping logic based on extracted Java code
        } catch (Exception e) {
            logger.warn("Error in custom mapping 3: {0}", e.getMessage());
        }
    }

    /**
     * Execute mapping 4: 
     * Source:  ()
     * Target:  ()
     */
    private void executeMapping4(Object source, Object target) {
    }

    /**
     * Method 1: JavaCode_1
     * Context: MAP Custom Mapping
     */
    private void createBusinessObject() {
        java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode;
        java.util.List __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction = (java.util.List)GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction;
        // Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction
        java.lang.String FireDistrictCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_fireDistrictCode;
        boolean __result__4 = FireDistrictCode == null;
        if (__result__4){
        return ;
        }
        else{
        }
        java.lang.String __result__10 = FireDistrictCode.trim();
        FireDistrictCode = __result__10;
        int __result__12;
        {// text length
        __result__12 = FireDistrictCode.length();
        }
        int textLength = __result__12;
        boolean __result__14 = textLength == 0;
        if (__result__14){
        return ;
        }
        else{
        }
        commonj.sdo.DataObject __result__19;
        {// create TaxingJurisdiction
        com.ibm.websphere.bo.BOFactory factory =
        (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
        __result__19 = factory.create("http://ei/core/taxing_jurisdiction","TaxingJurisdiction");
        }
        commonj.sdo.DataObject TaxingJurisdiction = __result__19;
        java.lang.String __result__21 = "FireDistrict";
        TaxingJurisdiction.setString("TaxingJurisdictionTypeName", __result__21);
        TaxingJurisdiction.setString("TaxingJurisdictionCode", FireDistrictCode);
        boolean __result__27;
        {// add item to list
        __result__27 = __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction.add(TaxingJurisdiction);
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <type xsi:type="com.ibm.wbit.activity:NullElementType"/>
        //    <dataOutputs target="//@executableElements.2"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <dataOutputs target="//@executableElements.4"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
        //      <executableGroups executableElements="//@executableElements.4/@conditionalActivities.0/@executableElements.0"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <dataOutputs target="//@executableElements.6/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <parameters name="String" dataInputs="//@executableElements.5/@dataOutputs.0">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </parameters>
        //    <result>
        //      <dataOutputs target="//@executableElements.7"/>
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </result>
        //    <dataOutputs target="//@executableElements.8/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <parameters name="input" dataInputs="//@executableElements.7/@dataOutputs.0" displayName="input">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </parameters>
        //    <result name="length" displayName="length">
        //      <dataOutputs target="//@executableElements.9"/>
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
        //    <dataOutputs target="//@executableElements.11"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
        //      <executableGroups executableElements="//@executableElements.11/@conditionalActivities.0/@executableElements.0"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <result>
        //      <dataOutputs target="//@executableElements.13"/>
        //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction" nillable="false"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
        //    <dataOutputs target="//@executableElements.15"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.17"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.20/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    <dataOutputs target="//@executableElements.20/@parameters.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
        //    <parameters name="list" dataInputs="//@executableElements.18/@dataOutputs.0" displayName="list">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    </parameters>
        //    <parameters name="object" dataInputs="//@executableElements.19/@dataOutputs.0" displayName="object">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
        //    </parameters>
        //    <result name="added" displayName="added">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
    }

    /**
     * Method 2: JavaCode_2
     * Context: MAP Custom Mapping
     */
    private void createBusinessObject() {
        java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName;
        java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode;
        java.lang.String __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode = (java.lang.String)getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode;
        java.util.List __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction = (java.util.List)GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction;
        // Variable GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location[1]_TaxingJurisdiction is represented as GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction
        commonj.sdo.DataObject __result__2;
        {// create TaxingJurisdiction
        com.ibm.websphere.bo.BOFactory factory =
        (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
        __result__2 = factory.create("http://ei/core/taxing_jurisdiction","TaxingJurisdiction");
        }
        commonj.sdo.DataObject TaxingJurisdiction = __result__2;
        java.lang.String __result__4 = "TaxingTerritory";
        TaxingJurisdiction.setString("TaxingJurisdictionTypeName", __result__4);
        java.lang.String Location_taxInfo_taxCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_taxCode;
        boolean __result__8 = Location_taxInfo_taxCode != null;
        if (__result__8){
        java.lang.String __result__12 = Location_taxInfo_taxCode.trim();
        TaxingJurisdiction.setString("TaxingJurisdictionCode", __result__12);
        }
        else{
        }
        java.lang.String Location_stateOrProvinceName = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_stateOrProvinceName;
        boolean __result__17 = Location_stateOrProvinceName != null;
        if (__result__17){
        java.lang.String __result__21 = Location_stateOrProvinceName.trim();
        TaxingJurisdiction.setString("TaxingJurisdictionName", __result__21);
        }
        else{
        }
        boolean __result__26;
        {// add item to list
        __result__26 = __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_Location1_TaxingJurisdiction.add(TaxingJurisdiction);
        }
        java.lang.String Location_taxInfo_alternateTaxCode = __getLocationWithTaxInfoResponse_getLocationWithTaxInfoResponse_location_taxInfo_alternateTaxCode;
        boolean __result__29 = Location_taxInfo_alternateTaxCode == null;
        if (__result__29){
        return ;
        }
        else{
        }
        java.lang.String __result__35 = Location_taxInfo_alternateTaxCode.trim();
        Location_taxInfo_alternateTaxCode = __result__35;
        int __result__37;
        {// text length
        __result__37 = Location_taxInfo_alternateTaxCode.length();
        }
        int textLength = __result__37;
        boolean __result__39 = textLength == 0;
        if (__result__39){
        return ;
        }
        else{
        }
        commonj.sdo.DataObject __result__44;
        {// create LineOfBusinessTaxRate
        com.ibm.websphere.bo.BOFactory factory =
        (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
        __result__44 = factory.create("http://ei/core/line_of_business_tax_ratex1","LineOfBusinessTaxRate");
        }
        commonj.sdo.DataObject LOBTaxRate = __result__44;
        java.lang.String __result__46 = "Auto";
        LOBTaxRate.setString("TaxRateLineOfBusinessName", __result__46);
        LOBTaxRate.setString("TaxRateLineOfBusinessCode", Location_taxInfo_alternateTaxCode);
        java.util.List __result__50 = TaxingJurisdiction.getList("LineOfBusinessTaxRate");
        boolean __result__52;
        {// add item to list
        __result__52 = __result__50.add(LOBTaxRate);
        }
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <type xsi:type="com.ibm.wbit.activity:NullElementType"/>
        //    <result>
        //      <dataOutputs target="//@executableElements.2"/>
        //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction" nillable="false"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
        //    <dataOutputs target="//@executableElements.4"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.6"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <dataOutputs target="//@executableElements.8"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_taxInfo_taxCode" localVariable="//@localVariables.3" variable="true">
        //        <dataOutputs target="//@executableElements.8/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.8/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.8/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.8/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionCode" field="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.8/@conditionalActivities.0/@executableElements.0 //@executableElements.8/@conditionalActivities.0/@executableElements.1 //@executableElements.8/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <dataOutputs target="//@executableElements.10"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <dataOutputs target="//@executableElements.12"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" value="Location_stateOrProvinceName" localVariable="//@localVariables.4" variable="true">
        //        <dataOutputs target="//@executableElements.12/@conditionalActivities.0/@executableElements.1/@parameters.0"/>
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:JavaActivity" name="trim" category="java.lang.String" className="java.lang.String" memberName="trim">
        //        <parameters name="String" dataInputs="//@executableElements.12/@conditionalActivities.0/@executableElements.0/@dataOutputs.0">
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </parameters>
        //        <result>
        //          <dataOutputs target="//@executableElements.12/@conditionalActivities.0/@executableElements.2"/>
        //          <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //        </result>
        //      </executableElements>
        //      <executableElements xsi:type="com.ibm.wbit.activity:Expression" dataInputs="//@executableElements.12/@conditionalActivities.0/@executableElements.1/@result/@dataOutputs.0" value="TaxingJurisdiction.TaxingJurisdictionName" field="true">
        //        <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //      </executableElements>
        //      <executableGroups executableElements="//@executableElements.12/@conditionalActivities.0/@executableElements.0 //@executableElements.12/@conditionalActivities.0/@executableElements.1 //@executableElements.12/@conditionalActivities.0/@executableElements.2"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <dataOutputs target="//@executableElements.15/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    <dataOutputs target="//@executableElements.15/@parameters.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
        //    <parameters name="list" dataInputs="//@executableElements.13/@dataOutputs.0" displayName="list">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    </parameters>
        //    <parameters name="object" dataInputs="//@executableElements.14/@dataOutputs.0" displayName="object">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
        //    </parameters>
        //    <result name="added" displayName="added">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    </result>
        //    <dataOutputs target="//@executableElements.17"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <dataOutputs target="//@executableElements.19"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
        //      <executableGroups executableElements="//@executableElements.19/@conditionalActivities.0/@executableElements.0"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <dataOutputs target="//@executableElements.21/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <parameters name="String" dataInputs="//@executableElements.20/@dataOutputs.0">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </parameters>
        //    <result>
        //      <dataOutputs target="//@executableElements.22"/>
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </result>
        //    <dataOutputs target="//@executableElements.23/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <parameters name="input" dataInputs="//@executableElements.22/@dataOutputs.0" displayName="input">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.String"/>
        //    </parameters>
        //    <result name="length" displayName="length">
        //      <dataOutputs target="//@executableElements.24"/>
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
        //    <dataOutputs target="//@executableElements.26"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    <conditionalActivities>
        //      <executableElements xsi:type="com.ibm.wbit.activity:ReturnElement"/>
        //      <executableGroups executableElements="//@executableElements.26/@conditionalActivities.0/@executableElements.0"/>
        //      <condition value="true"/>
        //    </conditionalActivities>
        //    <conditionalActivities>
        //      <condition value=""/>
        //    </conditionalActivities>
        //    <result>
        //      <dataOutputs target="//@executableElements.28"/>
        //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1" nillable="false"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
        //    <dataOutputs target="//@executableElements.30"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.32"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.35/@parameters.0"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    <dataOutputs target="//@executableElements.35/@parameters.1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
        //    <parameters name="list" dataInputs="//@executableElements.33/@dataOutputs.0" displayName="list">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.util.List"/>
        //    </parameters>
        //    <parameters name="object" dataInputs="//@executableElements.34/@dataOutputs.0" displayName="object">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Object"/>
        //    </parameters>
        //    <result name="added" displayName="added">
        //      <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="boolean"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="LineOfBusinessTaxRate" namespace="http://ei/core/line_of_business_tax_ratex1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="TaxingJurisdiction" namespace="http://ei/core/taxing_jurisdiction"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="int"/>
    }

    /**
     * Method 3: JavaCode_3
     * Context: MAP Custom Mapping
     */
    private void createBusinessObject() {
        commonj.sdo.DataObject __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_StatusInformation = (commonj.sdo.DataObject)GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_StatusInformation;
        commonj.sdo.DataObject __result__1;
        {// create StatusInformation
        com.ibm.websphere.bo.BOFactory factory =
        (com.ibm.websphere.bo.BOFactory) new com.ibm.websphere.sca.ServiceManager().locateService("com/ibm/websphere/bo/BOFactory");
        __result__1 = factory.create("http://ei/core/status_informationx1","StatusInformation");
        }
        commonj.sdo.DataObject statusInformation = __result__1;
        java.lang.String __result__3 = "S";
        statusInformation.setString("StatusCode", __result__3);
        __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_StatusInformation = statusInformation;
        GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_StatusInformation = __GetLocationWithTaxingJurisdictions3X1MResponseMsg_GetLocationWithTaxingJurisdictions3X1MResponse_GetLocationWithTaxingJurisdictionsReply_StatusInformation;
        //    <type xsi:type="com.ibm.wbit.activity:JavaElementType" name="java.lang.Exception"/>
        //    <result>
        //      <dataOutputs target="//@executableElements.1"/>
        //      <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="StatusInformation" namespace="http://ei/core/status_informationx1" nillable="false"/>
        //    </result>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="StatusInformation" namespace="http://ei/core/status_informationx1"/>
        //    <dataOutputs target="//@executableElements.3"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="string" namespace="http://www.w3.org/2001/XMLSchema" nillable="false"/>
        //    <dataOutputs target="//@executableElements.5"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="StatusInformation" namespace="http://ei/core/status_informationx1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="StatusInformation" namespace="http://ei/core/status_informationx1"/>
        //    <type xsi:type="com.ibm.wbit.activity:XSDElementType" name="StatusInformation" namespace="http://ei/core/status_informationx1"/>
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
