package de.felixbublitz.casestudy.service.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import de.felixbublitz.casestudy.service.ApplicationData;
import de.felixbublitz.casestudy.service.ServiceError;
import de.felixbublitz.casestudy.service.database.Database;

@Controller
public class AutoCompleteController {
	private final Database db;

	public AutoCompleteController(Database db) {
		this.db = db;
	}

	/**
	 * Method not allowed.
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE,
			RequestMethod.OPTIONS, RequestMethod.TRACE,
			RequestMethod.HEAD }, value = "/api/v1/auto-complete/{term:.*}", produces = { "application/json" })
	public @ResponseBody ResponseEntity<String> returnMethodNotAllowed() {
		return ServiceError.getResponseEntity(ApplicationData.ERROR_METHOD_NOT_ALLOWED);
	}

	/**
	 * Process request
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/auto-complete/{term:[A-Za-zÀ-ž ]{3,}}", produces = {
			"application/json" })
	public @ResponseBody ResponseEntity<String> returnSearchResult(@PathVariable("term") String term) {
		if(!db.isLoaded()) return ServiceError.getResponseEntity(ApplicationData.ERROR_INTERNAL_ERROR);
		return new ResponseEntity<String>(db.getSearchResult(term).toString(), HttpStatusCode.valueOf(200));
	}

	/**
	 * Search term too short
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/auto-complete/{term:[A-Za-zÀ-ž ]{0,2}}", produces = { "application/json" })
	public @ResponseBody ResponseEntity<String> handleShortTermError() {
		return ServiceError.getResponseEntity(ApplicationData.ERROR_TOO_SHORT);
	}

	/**
	 * Invalid numeric characters
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/auto-complete/{term:[A-Za-zÀ-ž0-9 ]*\\d[A-Za-z0-9 ]*}", produces = { "application/json" })
	public @ResponseBody ResponseEntity<String> handleError() {
		return ServiceError.getResponseEntity(ApplicationData.ERROR_NUMERIC);
	}

	/**
	 * Invalid other characters
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/api/v1/auto-complete/{term:.*[^A-Za-zÀ-ž0-9 ].*}", produces = { "application/json" })
	public @ResponseBody ResponseEntity<String> handlInvalidCharacterError() {
		return ServiceError.getResponseEntity(ApplicationData.ERROR_INVALID_CHAR);
	}

	/**
	 * Show default message
	 */
	@RequestMapping("/api/v1/auto-complete/")
	public @ResponseBody String showDefaultMessage() {
		return "service running";
	}
	
}