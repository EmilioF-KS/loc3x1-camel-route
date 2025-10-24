package com.locationretrievalloc3x1process.webservice.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

import com.chubb.location.library.interfaces.SoapClientInterface;

@RestController
@RequestMapping("/api")
public class Locationretrievalloc3x1processController {
	private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("Locationretrievalloc3x1process Service Eli")
    private String greetingMessage;
	
    @GetMapping("/index")
    public String execute() {
    	LOGGER.debug("Locationretrievalloc3x1processController.execute");
    	
    	try {    	    	
	    	Class<?> clazz = Class.forName("com.chubb.location.library.LocationRetrievalLOC3X1BClient");
	        Object instance = clazz.getDeclaredConstructor().newInstance();
	        LOGGER.debug("instance : " + instance);
	    	
	    	Class<?> clazzReq = Class.forName("com.chubb.generated.ei.location.get_location_list_request_loc3x1b.GetLocationListRequest");
	                    
	        Object instanceReq = clazzReq.getDeclaredConstructor().newInstance();
	        LOGGER.debug("instanceReq : " + instanceReq);
	            
	        if ("outputclasstoupdate".contains("com.")) {
		        Class<?> clazzRes = Class.forName("outputclasstoupdate");
		
		        // Create a new instance (assumes a no-arg constructor)
		        Object instanceRes = clazzRes.getDeclaredConstructor().newInstance();
		        LOGGER.debug("instanceRes : " + instanceRes);
		
		        if (instance instanceof SoapClientInterface) {
		          	instanceRes = ((SoapClientInterface) instance).invokeService(instanceReq);
		           	LOGGER.debug("final instanceRes : " + instanceRes);
		        }
	        } else {
		        if (instance instanceof SoapClientInterface) {
		          	((SoapClientInterface) instance).invokeService(instanceReq);
		        }
	        }    
	    	
	    } catch (ClassNotFoundException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException |
                 InvocationTargetException e) {
            e.printStackTrace();
        }
    	
        return greetingMessage;
    }
}
