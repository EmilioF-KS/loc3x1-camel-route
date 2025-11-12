
package com.example.location.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class StatusInformation {
    private String statusCode; // e.g., W
    private List<ErrorItem> error = new ArrayList<>();
}
