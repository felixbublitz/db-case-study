package de.felixbublitz.casestudy.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


/*
 * Represent a Service Error
 */

public class ServiceError {
	
	public int httpCode;
	public int code;
	public String description;
	
	public ServiceError(int hc, int c, String d) {
		httpCode = hc;
		code = c;
		description = d;
	}
	
	/*
	 * Format a ServiceError as ResponseEntity
	 */
	public static ResponseEntity<String> getResponseEntity(ServiceError error) {
		JSONObject message = new JSONObject();
		message.put(ApplicationData.JSON_ERROR_CODE, error.code);
		message.put(ApplicationData.JSON_ERROR_DESC, error.description);
		
		return new ResponseEntity<String>(message.toString(), HttpStatusCode.valueOf(error.httpCode));
	}

}
