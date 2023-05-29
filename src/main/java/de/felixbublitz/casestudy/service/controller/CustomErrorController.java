package de.felixbublitz.casestudy.service.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.felixbublitz.casestudy.service.ApplicationData;
import de.felixbublitz.casestudy.service.ServiceError;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController  {

    @RequestMapping(value = "/error",  produces = { "application/json" }) 
    public @ResponseBody ResponseEntity<String>  handleError(HttpServletRequest request) {
    	
    	 Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	    
    	    if (status != null) {
    	        Integer statusCode = Integer.valueOf(status.toString());
    	    
    	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
    	    		return ServiceError.getResponseEntity(ApplicationData.ERROR_NOT_FOUND);
    	        }
    	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
    	    		return ServiceError.getResponseEntity(ApplicationData.ERROR_INTERNAL_ERROR);
    	        }
    	    }
    	    return ServiceError.getResponseEntity(ApplicationData.ERROR_INTERNAL_ERROR);
    	    
    }
}