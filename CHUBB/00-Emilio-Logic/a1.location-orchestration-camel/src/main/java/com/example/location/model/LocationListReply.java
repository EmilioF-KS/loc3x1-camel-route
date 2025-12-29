
package com.example.location.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class LocationListReply {
    private List<Location> location = new ArrayList<>();
    private StatusInformation statusInformation = new StatusInformation();
}
