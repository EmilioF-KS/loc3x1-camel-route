# Contracts Validator Report

## Paths
- `inventory.json`: /Users/albertohernandez/Documents/projects/camel-route/contracts/inventory.json
- `inventory.md`: /Users/albertohernandez/Documents/projects/camel-route/contracts/inventory.md

## Counts
- JSON WSDL: 8
- JSON XSD: 382
- JSON BPEL: -1
- JSON Total: 390
- MD WSDL: 8
- MD XSD: 382
- MD BPEL: 2
- MD Total: 392
- Counts Match: True

## File Existence
- OK Files: 390
- Missing Files: 0

## Imports
- OK WSDL Imports: 23
- OK XSD Imports: 304
- Unresolved WSDL Imports: 0
- Unresolved XSD Imports: 0

## Namespaces
- JSON Target Namespaces: 389
- JSON Import Namespaces: 170
- MD Namespaces: 432
- JSON Targets Missing in MD: 0
- MD Extras Not in JSON: 25

## XML Validation
- XSD targetNamespace mismatches: 0
- XSD import cycles: 5
  - /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntExclusionVariation.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntExclusionVariation.xsd
  - /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntHazard.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd
  - /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntLossValueOption.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd
  - /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntProductPart.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd
  - /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRestrictionOption.xsd -> /Users/albertohernandez/Documents/projects/camel-route/sample/Schemas/PrdctCompntRuleGroup.xsd
- WSDL operation mismatches: 0
- WSDL binding mismatches: 0

## Summary
- Ok Files: 390
- Missing Files: 0
- Ok Wsdl Imports: 23
- Ok Xsd Imports: 304
- Unresolved Wsdl: 0
- Unresolved Xsd: 0
- Counts Match: True
- Json Targets Not In Md: 0
- Md Extras: 25
- Xsd Ns Mismatches: 0
- Xsd Cycles: 5
- Wsdl Op Mismatches: 0
- Wsdl Binding Mismatches: 0

## Issues
- XSD import cycles detected: 5
