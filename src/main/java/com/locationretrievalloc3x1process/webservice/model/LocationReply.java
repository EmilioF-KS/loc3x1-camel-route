package com.locationretrievalloc3x1process.webservice.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LocationReply {
    private List<Location> locations = new ArrayList<>();
    private List<String> warnings = new ArrayList<>();

    public void addWarning(String code, String message) {
        warnings.add(code + ": " + message);
    }

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	@Override
	public String toString() {
		return "LocationReply [locations=" + locations + ", warnings=" + warnings + "]";
	}
    
    
}
