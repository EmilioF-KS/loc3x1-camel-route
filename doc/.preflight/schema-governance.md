# Schema Governance Checklist

Generated: 2025-10-24T17:36:47.074612
XSD Total: 383
Groups: 299
Variant Groups: 53

## Rules
- Prefer canonical namespaces without xN suffix when available
- Normalise trailing letter variants after numeric version (e.g., loc3x1m → loc3x1)
- Ensure imports reference canonical namespaces where groups exist

## Canonical Groups
- id: `ei.corporate.get_state_or_province_list_reply_crp11`
  base: `http://ei/corporate/get_state_or_province_list_reply_crp11`
  canonical: `http://ei/corporate/get_state_or_province_list_reply_crp11x1`
  file: `sample/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceListReply.xsd`
- id: `ei.corporate.get_state_or_province_request_crp11`
  base: `http://ei/corporate/get_state_or_province_request_crp11`
  canonical: `http://ei/corporate/get_state_or_province_request_crp11x1`
  file: `sample/StateOrProvinceRetrievalCRP11X1/GetStateOrProvinceRequest.xsd`
- id: `ei.location.location_list_reply_\1`
  base: `http://ei/location/location_list_reply_\1`
  canonical: `http://ei/location/location_list_reply_loc3x1b`
  file: `sample/LocationRetrievalLOC3X1B/LocationListReply.xsd`
  variants: http://ei/location/location_list_reply_loc3x1b, http://ei/location/location_list_reply_loc3x1m
- id: `ei.location.location_request_\1`
  base: `http://ei/location/location_request_\1`
  canonical: `http://ei/location/location_request_loc3x1b`
  file: `sample/LocationRetrievalLOC3X1B/LocationRequest.xsd`
  variants: http://ei/location/location_request_loc3x1b, http://ei/location/location_request_loc3x1m
- id: `ei.location.get_location_list_request_\1`
  base: `http://ei/location/get_location_list_request_\1`
  canonical: `http://ei/location/get_location_list_request_loc3x1b`
  file: `sample/LocationRetrievalLOC3X1B/GetLocationListRequest.xsd`
  variants: http://ei/location/get_location_list_request_loc3x1b, http://ei/location/get_location_list_request_loc3x1m
- id: `ei.corporate.get_country_request_crp10`
  base: `http://ei/corporate/get_country_request_crp10`
  canonical: `http://ei/corporate/get_country_request_crp10x1`
  file: `sample/CountryRetrievalCRP10X1/GetCountryRequest.xsd`
- id: `ei.corporate.get_country_reply_crp10`
  base: `http://ei/corporate/get_country_reply_crp10`
  canonical: `http://ei/corporate/get_country_reply_crp10x1`
  file: `sample/CountryRetrievalCRP10X1/GetCountryReply.xsd`
- id: `ei.corporate.get_country_list_reply_crp10`
  base: `http://ei/corporate/get_country_list_reply_crp10`
  canonical: `http://ei/corporate/get_country_list_reply_crp10x1`
  file: `sample/CountryRetrievalCRP10X1/GetCountryListReply.xsd`
- id: `ei.core.policy_identifier`
  base: `http://ei/core/policy_identifier`
  canonical: `http://ei/core/policy_identifier`
  file: `sample/Schemas/PolicyIdentifier.xsd`
  variants: http://ei/core/policy_identifier, http://ei/core/policy_identifierx1
- id: `ei.core.organization_information`
  base: `http://ei/core/organization_information`
  canonical: `http://ei/core/organization_information`
  file: `sample/Schemas/OrganizationInformation.xsd`
- id: `ei.core.currency`
  base: `http://ei/core/currency`
  canonical: `http://ei/core/currency`
  file: `sample/Schemas/Currency.xsd`
- id: `ei.core.birth_date_information`
  base: `http://ei/core/birth_date_information`
  canonical: `http://ei/core/birth_date_information`
  file: `sample/Schemas/BirthDateInformation.xsd`
- id: `ei.core.time_zone`
  base: `http://ei/core/time_zone`
  canonical: `http://ei/core/time_zone`
  file: `sample/Schemas/TimeZone.xsd`
- id: `ei.core.medical_institution`
  base: `http://ei/core/medical_institution`
  canonical: `http://ei/core/medical_institution`
  file: `sample/Schemas/MedicalInstitution.xsd`
  variants: http://ei/core/medical_institution, http://ei/core/medical_institutionx1, http://ei/core/medical_institutionx2, http://ei/core/medical_institutionx3
- id: `ei.location.location`
  base: `http://ei/location/location`
  canonical: `http://ei/location/location`
  file: `sample/Schemas/Location.xsd`
- id: `ei.core.alliance_sbu_monthly_financial`
  base: `http://ei/core/alliance_sbu_monthly_financial`
  canonical: `http://ei/core/alliance_sbu_monthly_financial`
  file: `sample/Schemas/AllianceSBUMonthlyFinancial.xsd`
- id: `ei.core.business_contact`
  base: `http://ei/core/business_contact`
  canonical: `http://ei/core/business_contact`
  file: `sample/Schemas/BusinessContact.xsd`
  variants: http://ei/core/business_contact, http://ei/core/business_contactx1, http://ei/core/business_contactx2, http://ei/core/business_contactx3, http://ei/core/business_contactx4
- id: `ei.core.taxing_jurisdiction`
  base: `http://ei/core/taxing_jurisdiction`
  canonical: `http://ei/core/taxing_jurisdiction`
  file: `sample/Schemas/TaxingJurisdiction.xsd`
  variants: http://ei/core/taxing_jurisdiction, http://ei/core/taxing_jurisdictionx1
- id: `ei.core.individual_identifier`
  base: `http://ei/core/individual_identifier`
  canonical: `http://ei/core/individual_identifier`
  file: `sample/Schemas/IndividualIdentifier.xsd`
  variants: http://ei/core/individual_identifier, http://ei/core/individual_identifierx1
- id: `ei.core.legal_representative`
  base: `http://ei/core/legal_representative`
  canonical: `http://ei/core/legal_representative`
  file: `sample/Schemas/LegalRepresentative.xsd`
  variants: http://ei/core/legal_representative, http://ei/core/legal_representativex1, http://ei/core/legal_representativex2, http://ei/core/legal_representativex3
- id: `ei.core.location_occupancy_aggregation`
  base: `http://ei/core/location_occupancy_aggregation`
  canonical: `http://ei/core/location_occupancy_aggregation`
  file: `sample/Schemas/LocationOccupancyAggregation.xsd`
  variants: http://ei/core/location_occupancy_aggregation, http://ei/core/location_occupancy_aggregationx1, http://ei/core/location_occupancy_aggregationx2
- id: `ei.core.business_event_key`
  base: `http://ei/core/business_event_key`
  canonical: `http://ei/core/business_event_key`
  file: `sample/Schemas/BusinessEventKey.xsd`
- id: `ei.core.product_identifier`
  base: `http://ei/core/product_identifier`
  canonical: `http://ei/core/product_identifier`
  file: `sample/Schemas/ProductIdentifier.xsd`
- id: `ei.core.item`
  base: `http://ei/core/item`
  canonical: `http://ei/core/item`
  file: `sample/Schemas/Item.xsd`
- id: `ei.core.offering_status_information`
  base: `http://ei/core/offering_status_information`
  canonical: `http://ei/core/offering_status_information`
  file: `sample/Schemas/OfferingStatusInformation.xsd`
- id: `ei.core.strategic_alliance_sbu`
  base: `http://ei/core/strategic_alliance_sbu`
  canonical: `http://ei/core/strategic_alliance_sbu`
  file: `sample/Schemas/StrategicAllianceSBU.xsd`
- id: `ei.core.competitor`
  base: `http://ei/core/competitor`
  canonical: `http://ei/core/competitor`
  file: `sample/Schemas/Competitor.xsd`
- id: `ei.core.public_protection_information`
  base: `http://ei/core/public_protection_information`
  canonical: `http://ei/core/public_protection_information`
  file: `sample/Schemas/PublicProtectionInformation.xsd`
- id: `ei.core.document_business_type`
  base: `http://ei/core/document_business_type`
  canonical: `http://ei/core/document_business_type`
  file: `sample/Schemas/DocumentBusinessType.xsd`
- id: `ei.core.bill_payor`
  base: `http://ei/core/bill_payor`
  canonical: `http://ei/core/bill_payor`
  file: `sample/Schemas/BillPayor.xsd`
- id: `ei.core.document_property`
  base: `http://ei/core/document_property`
  canonical: `http://ei/core/document_property`
  file: `sample/Schemas/DocumentProperty.xsd`
- id: `ei.core.extended_insurance_admin_role`
  base: `http://ei/core/extended_insurance_admin_role`
  canonical: `http://ei/core/extended_insurance_admin_role`
  file: `sample/Schemas/ExtendedInsuranceAdminRole.xsd`
- id: `ei.core.entity_alias_name`
  base: `http://ei/core/entity_alias_name`
  canonical: `http://ei/core/entity_alias_name`
  file: `sample/Schemas/EntityAliasName.xsd`
  variants: http://ei/core/entity_alias_name, http://ei/core/entity_alias_namex1
- id: `ei.core.language`
  base: `http://ei/core/language`
  canonical: `http://ei/core/language`
  file: `sample/Schemas/Language.xsd`
  variants: http://ei/core/language, http://ei/core/languagex1
- id: `ei.core.performance_metric`
  base: `http://ei/core/performance_metric`
  canonical: `http://ei/core/performance_metric`
  file: `sample/Schemas/PerformanceMetric.xsd`
- id: `ei.core.exposure`
  base: `http://ei/core/exposure`
  canonical: `http://ei/core/exposurex1`
  file: `sample/Schemas/Exposurex1.xsd`
  variants: http://ei/core/exposurex1, http://ei/core/exposurex2
- id: `ei.core.assigned_resource_id_by_role`
  base: `http://ei/core/assigned_resource_id_by_role`
  canonical: `http://ei/core/assigned_resource_id_by_role`
  file: `sample/Schemas/AssignedResourceIdByRole.xsd`
- id: `ei.core.docusign_envelope`
  base: `http://ei/core/docusign_envelope`
  canonical: `http://ei/core/docusign_envelope`
  file: `sample/Schemas/DocuSignEnvelope.xsd`
- id: `ei.core.payroll_report`
  base: `http://ei/core/payroll_report`
  canonical: `http://ei/core/payroll_report`
  file: `sample/Schemas/PayrollReport.xsd`
- id: `ei.core.household`
  base: `http://ei/core/household`
  canonical: `http://ei/core/household`
  file: `sample/Schemas/Household.xsd`
  variants: http://ei/core/household, http://ei/core/householdx1
- id: `ei.core.user_authorization`
  base: `http://ei/core/user_authorization`
  canonical: `http://ei/core/user_authorization`
  file: `sample/Schemas/UserAuthorization.xsd`
- id: `ei.core.businessinformation`
  base: `http://ei/core/businessinformation`
  canonical: `http://ei/core/businessinformation`
  file: `sample/Schemas/BusinessInformation.xsd`
- id: `ei.core.policy_processing_information`
  base: `http://ei/core/policy_processing_information`
  canonical: `http://ei/core/policy_processing_information`
  file: `sample/Schemas/PolicyProcessingInformation.xsd`
- id: `ei.core.individual_name`
  base: `http://ei/core/individual_name`
  canonical: `http://ei/core/individual_name`
  file: `sample/Schemas/IndividualName.xsd`
- id: `ei.core.commission_rates_by_policy_category`
  base: `http://ei/core/commission_rates_by_policy_category`
  canonical: `http://ei/core/commission_rates_by_policy_category`
  file: `sample/Schemas/CommissionRatesByPolicyCategory.xsd`
- id: `ei.core.prdct_compnt_exclusion_variation`
  base: `http://ei/core/prdct_compnt_exclusion_variation`
  canonical: `http://ei/core/prdct_compnt_exclusion_variation`
  file: `sample/Schemas/PrdctCompntExclusionVariation.xsd`
- id: `ei.core.notification_property`
  base: `http://ei/core/notification_property`
  canonical: `http://ei/core/notification_property`
  file: `sample/Schemas/NotificationProperty.xsd`
- id: `ei.core.prior_carrier`
  base: `http://ei/core/prior_carrier`
  canonical: `http://ei/core/prior_carrier`
  file: `sample/Schemas/PriorCarrier.xsd`
- id: `ei.core.document_reference_information`
  base: `http://ei/core/document_reference_information`
  canonical: `http://ei/core/document_reference_information`
  file: `sample/Schemas/DocumentReferenceInformation.xsd`
- id: `ei.core.policy_type_information`
  base: `http://ei/core/policy_type_information`
  canonical: `http://ei/core/policy_type_information`
  file: `sample/Schemas/PolicyTypeInformation.xsd`
- id: `ei.core.issued_policy_form`
  base: `http://ei/core/issued_policy_form`
  canonical: `http://ei/core/issued_policy_form`
  file: `sample/Schemas/IssuedPolicyForm.xsd`
- id: `ei.core.policy_transaction_identifier`
  base: `http://ei/core/policy_transaction_identifier`
  canonical: `http://ei/core/policy_transaction_identifier`
  file: `sample/Schemas/PolicyTransactionIdentifier.xsd`
- id: `ei.core.header`
  base: `http://ei/core/header`
  canonical: `http://ei/core/header`
  file: `sample/Schemas/Header.xsd`
- id: `ei.core.campaign_contact_alliance_sbu_association`
  base: `http://ei/core/campaign_contact_alliance_sbu_association`
  canonical: `http://ei/core/campaign_contact_alliance_sbu_association`
  file: `sample/Schemas/CampaignContactAllianceSBUAssociation.xsd`
- id: `ei.core.phone_information`
  base: `http://ei/core/phone_information`
  canonical: `http://ei/core/phone_information`
  file: `sample/Schemas/PhoneInformation.xsd`
- id: `ei.core.endpoint_information`
  base: `http://ei/core/endpoint_information`
  canonical: `http://ei/core/endpoint_information`
  file: `sample/Schemas/EndpointInformation.xsd`
  variants: http://ei/core/endpoint_information, http://ei/core/endpoint_informationx1
- id: `ei.core.accounting_period`
  base: `http://ei/core/accounting_period`
  canonical: `http://ei/core/accounting_period`
  file: `sample/Schemas/AccountingPeriod.xsd`
- id: `ei.core.business_party`
  base: `http://ei/core/business_party`
  canonical: `http://ei/core/business_party`
  file: `sample/Schemas/BusinessParty.xsd`
  variants: http://ei/core/business_party, http://ei/core/business_partyx1
- id: `ei.core.territory`
  base: `http://ei/core/territory`
  canonical: `http://ei/core/territory`
  file: `sample/Schemas/Territory.xsd`
- id: `ei.core.rating_territory`
  base: `http://ei/core/rating_territory`
  canonical: `http://ei/core/rating_territory`
  file: `sample/Schemas/RatingTerritory.xsd`
  variants: http://ei/core/rating_territory, http://ei/core/rating_territoryx1
- id: `ei.core.business_party_covered_risk_interest`
  base: `http://ei/core/business_party_covered_risk_interest`
  canonical: `http://ei/core/business_party_covered_risk_interest`
  file: `sample/Schemas/BusinessPartyCoveredRiskInterest.xsd`
- id: `ei.core.business_process_location_type`
  base: `http://ei/core/business_process_location_type`
  canonical: `http://ei/core/business_process_location_type`
  file: `sample/Schemas/BusinessProcessLocationType.xsd`
- id: `ei.core.risk_class_grade_information`
  base: `http://ei/core/risk_class_grade_information`
  canonical: `http://ei/core/risk_class_grade_information`
  file: `sample/Schemas/RiskClassGradeInformation.xsd`
- id: `ei.core.uw_industry_segment`
  base: `http://ei/core/uw_industry_segment`
  canonical: `http://ei/core/uw_industry_segment`
  file: `sample/Schemas/UWIndustrySegment.xsd`
- id: `ei.core.messageinformation`
  base: `http://ei/core/messageinformation`
  canonical: `http://ei/core/messageinformation`
  file: `sample/Schemas/MessageInformation.xsd`
- id: `ei.core.user_profile_identifier`
  base: `http://ei/core/user_profile_identifier`
  canonical: `http://ei/core/user_profile_identifier`
  file: `sample/Schemas/UserProfileIdentifier.xsd`
- id: `ei.core.status_information`
  base: `http://ei/core/status_information`
  canonical: `http://ei/core/status_informationx1`
  file: `sample/Schemas/StatusInformationX1.xsd`
  variants: http://ei/core/status_informationx1, http://ei/core/status_informationx2
- id: `ei.core.business_contact_type`
  base: `http://ei/core/business_contact_type`
  canonical: `http://ei/core/business_contact_type`
  file: `sample/Schemas/BusinessContactType.xsd`
- id: `ei.core.access_attribute`
  base: `http://ei/core/access_attribute`
  canonical: `http://ei/core/access_attribute`
  file: `sample/Schemas/AccessAttribute.xsd`
- id: `ei.core.individual_information`
  base: `http://ei/core/individual_information`
  canonical: `http://ei/core/individual_information`
  file: `sample/Schemas/IndividualInformation.xsd`
  variants: http://ei/core/individual_information, http://ei/core/individual_informationx1, http://ei/core/individual_informationx2
- id: `ei.core.error`
  base: `http://ei/core/error`
  canonical: `http://ei/core/error`
  file: `sample/Schemas/Error.xsd`
- id: `ei.core.business_party_characteristic`
  base: `http://ei/core/business_party_characteristic`
  canonical: `http://ei/core/business_party_characteristic`
  file: `sample/Schemas/BusinessPartyCharacteristic.xsd`
- id: `ei.core.text_analytics_concept`
  base: `http://ei/core/text_analytics_concept`
  canonical: `http://ei/core/text_analytics_concept`
  file: `sample/Schemas/TextAnalyticsConcept.xsd`
- id: `ei.core.premium_apportionment`
  base: `http://ei/core/premium_apportionment`
  canonical: `http://ei/core/premium_apportionment`
  file: `sample/Schemas/PremiumApportionment.xsd`
- id: `ei.core.form_destination_receiver`
  base: `http://ei/core/form_destination_receiver`
  canonical: `http://ei/core/form_destination_receiver`
  file: `sample/Schemas/FormDestinationReceiver.xsd`
- id: `ei.core.extended_insurer`
  base: `http://ei/core/extended_insurer`
  canonical: `http://ei/core/extended_insurer`
  file: `sample/Schemas/ExtendedInsurer.xsd`
  variants: http://ei/core/extended_insurer, http://ei/core/extended_insurerx1, http://ei/core/extended_insurerx2
- id: `ei.core.customer_segment`
  base: `http://ei/core/customer_segment`
  canonical: `http://ei/core/customer_segment`
  file: `sample/Schemas/CustomerSegment.xsd`
- id: `ei.core.processing_option`
  base: `http://ei/core/processing_option`
  canonical: `http://ei/core/processing_option`
  file: `sample/Schemas/ProcessingOption.xsd`
- id: `ei.core.cache_key`
  base: `http://ei/core/cache_key`
  canonical: `http://ei/core/cache_key`
  file: `sample/Schemas/CacheKey.xsd`
- id: `ei.core.telecomm_electronic_address`
  base: `http://ei/core/telecomm_electronic_address`
  canonical: `http://ei/core/telecomm_electronic_address`
  file: `sample/Schemas/TelecommElectronicAddress.xsd`
- id: `ei.core.vehicle_registrant`
  base: `http://ei/core/vehicle_registrant`
  canonical: `http://ei/core/vehicle_registrant`
  file: `sample/Schemas/VehicleRegistrant.xsd`
  variants: http://ei/core/vehicle_registrant, http://ei/core/vehicle_registrantx1
- id: `ei.core.dfi_account`
  base: `http://ei/core/dfi_account`
  canonical: `http://ei/core/dfi_account`
  file: `sample/Schemas/DFIAccount.xsd`
- id: `ei.core.policy_term_identifier`
  base: `http://ei/core/policy_term_identifier`
  canonical: `http://ei/core/policy_term_identifier`
  file: `sample/Schemas/PolicyTermIdentifier.xsd`
- id: `ei.core.survey_evaluation_information`
  base: `http://ei/core/survey_evaluation_information`
  canonical: `http://ei/core/survey_evaluation_information`
  file: `sample/Schemas/SurveyEvaluationInformation.xsd`
- id: `ei.core.marketing_campaign`
  base: `http://ei/core/marketing_campaign`
  canonical: `http://ei/core/marketing_campaign`
  file: `sample/Schemas/MarketingCampaign.xsd`
- id: `ei.core.mortgagee_invoice_document`
  base: `http://ei/core/mortgagee_invoice_document`
  canonical: `http://ei/core/mortgagee_invoice_document`
  file: `sample/Schemas/MortgageeInvoiceDocument.xsd`
- id: `ei.core.extendedbranch`
  base: `http://ei/core/extendedbranch`
  canonical: `http://ei/core/extendedbranch`
  file: `sample/Schemas/ExtendedBranch.xsd`
- id: `ei.core.iso_symbol_information`
  base: `http://ei/core/iso_symbol_information`
  canonical: `http://ei/core/iso_symbol_information`
  file: `sample/Schemas/ISOSymbolInformation.xsd`
- id: `ei.core.physicaladdress`
  base: `http://ei/core/physicaladdress`
  canonical: `http://ei/core/physicaladdress`
  file: `sample/Schemas/PhysicalAddress.xsd`
- id: `ei.core.tax`
  base: `http://ei/core/tax`
  canonical: `http://ei/core/taxx2`
  file: `sample/Schemas/TaxX2.xsd`
- id: `ei.core.loss_payee`
  base: `http://ei/core/loss_payee`
  canonical: `http://ei/core/loss_payee`
  file: `sample/Schemas/LossPayee.xsd`
- id: `ei.core.marketing_program`
  base: `http://ei/core/marketing_program`
  canonical: `http://ei/core/marketing_program`
  file: `sample/Schemas/MarketingProgram.xsd`
- id: `ei.core.ppc_aggregated_summary`
  base: `http://ei/core/ppc_aggregated_summary`
  canonical: `http://ei/core/ppc_aggregated_summary`
  file: `sample/Schemas/PPCAggregatedSummary.xsd`
  variants: http://ei/core/ppc_aggregated_summary, http://ei/core/ppc_aggregated_summaryx1
- id: `ei.core.policy_type_identifier`
  base: `http://ei/core/policy_type_identifier`
  canonical: `http://ei/core/policy_type_identifierx1`
  file: `sample/Schemas/PolicyTypeIdentifierX1.xsd`
- id: `ei.core.driver`
  base: `http://ei/core/driver`
  canonical: `http://ei/core/driver`
  file: `sample/Schemas/Driver.xsd`
  variants: http://ei/core/driver, http://ei/core/driverx1, http://ei/core/driverx2, http://ei/core/driverx3
- id: `ei.core.risk_rating_group`
  base: `http://ei/core/risk_rating_group`
  canonical: `http://ei/core/risk_rating_group`
  file: `sample/Schemas/RiskRatingGroup.xsd`
- id: `ei.core.parent_activity_status_information`
  base: `http://ei/core/parent_activity_status_information`
  canonical: `http://ei/core/parent_activity_status_information`
  file: `sample/Schemas/ParentActivityStatusInformation.xsd`
- id: `ei.core.bank_vault`
  base: `http://ei/core/bank_vault`
  canonical: `http://ei/core/bank_vault`
  file: `sample/Schemas/BankVault.xsd`
- id: `ei.core.loss_control_location`
  base: `http://ei/core/loss_control_location`
  canonical: `http://ei/core/loss_control_location`
  file: `sample/Schemas/LossControlLocation.xsd`
- id: `ei.core.section_policy_type_cross_reference`
  base: `http://ei/core/section_policy_type_cross_reference`
  canonical: `http://ei/core/section_policy_type_cross_reference`
  file: `sample/Schemas/SectionPolicyTypeCrossReference.xsd`

Note: Full list is available in `contracts/canonical-map.yaml`.
