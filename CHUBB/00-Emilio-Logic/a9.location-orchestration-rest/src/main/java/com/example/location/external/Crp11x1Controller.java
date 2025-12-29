package com.example.location.external;

import com.example.location.external.dto.crp11x1.*;
import com.example.location.external.service.Crp11x1Resolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/crp11x1")
public class Crp11x1Controller {
	@Autowired(required = false)
	private Crp11x1Resolver resolver;

	@PostMapping("/getStateOrProvince")
	public ResponseEntity<?> getStateOrProvince(@RequestBody GetStateOrProvinceRequestDto body) {
		boolean byCode = body != null && body.getStateOrProvinceCode() != null;
		boolean byAbbr = body != null && body.getPostalStateAbbreviation() != null;
		if (!byCode && !byAbbr)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					Map.of("faultMessageText", "EIRV0010E StateOrProvinceCode or PostalStateAbbreviation is required"));
		if (resolver == null)
			return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
					.body(Map.of("faultMessageText", "CRP11X1 resolver not configured"));
		Optional<GetStateOrProvinceResponseDto> out = resolver.resolve(body);
		return out.<ResponseEntity<?>>map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
				.status(HttpStatus.NOT_FOUND).body(Map.of("faultMessageText", "CRP11X1 state/province not found")));
	}
}