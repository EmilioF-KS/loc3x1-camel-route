
package com.example.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorItem {
    private String errorCode;
    private String errorDescription;
    private String errorSeverityLevel; // W/I/E
    private String errorSourceIdentifier;
}
