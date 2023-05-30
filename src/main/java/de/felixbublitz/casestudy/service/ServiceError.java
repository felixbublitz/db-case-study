package de.felixbublitz.casestudy.service;

import org.json.JSONObject;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/*
 * Represents a Web Service Error
 */

public class ServiceError {
	public int httpCode; //http status code
	public int code; //internal error code
	public String description;
	private static int initErrors;
	
	public ServiceError(int hc, String d) {
		httpCode = hc;
		code = initErrors++;
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
