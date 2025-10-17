package com.locationretrievalloc3x1process.webservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chubb.xsd.ei.location.locationlistreplyloc3x1b.LocationListReply;
import com.locationretrievalloc3x1process.webservice.model.LocationRequest;
import com.locationretrievalloc3x1process.webservice.service.mfc.OutputTerminalTypeFinder;

@Service
public class MappingService {

	@Autowired
	LocationListReplyService locationListReplyService;
	
	@Autowired
	RansService ransService;

	public LocationListReply transform(LocationRequest req) throws Exception {
    	//System.out.println("-> LocationListReply.transform : " +  req);
    	
    	List<String> reqAttributes = OutputTerminalTypeFinder.getMappingAttributes();
    	
    	String xmlRes = com.locationretrievalloc3x1process.webservice.mapper.LocationListReply.getLoc3x1xmlFromRand();
    	//System.out.println("::::::: xmlRes :::::: ");
    	//System.out.println("xmlRes : " + xmlRes);
    	//System.out.println(": reqAttributes :: " +  reqAttributes);
    	
    	LocationListReply randReply = ransService.getRandReply(req, reqAttributes);
    	
    	//System.out.println("randReply : " + randReply);
        return randReply;
    }
}
