package de.felixbublitz.casestudy.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AutoCompleteController {

	private final Database db;

	public AutoCompleteController(Database db) {
		this.db = db;
	}
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.OPTIONS, RequestMethod.TRACE, RequestMethod.HEAD}, value = "/api/v1/auto-complete/{term:.*}", produces = { "application/json" })
	public @ResponseBody ResponseEntity<String> returnMethodNotAllowed() {
	      return ServiceError.getResponseEntity(ApplicationData.ERROR_METHOD_NOT_ALLOWED);
	}
	
	

	@RequestMapping(method= RequestMethod.GET, value = "/api/v1/auto-complete/{term:[A-Za-z ]{3,}}", produces = { "application/json" })
	public @ResponseBody String returnSearchResult(@PathVariable("term") String term) {
		return db.getSearchResult(term).toString();
	}
	
	
	
	@RequestMapping(value = "/api/v1/auto-complete/{term:[A-Za-z ]{0,2}}", produces = { "application/json" })
	  public @ResponseBody ResponseEntity<String> handleShortTermError() {	
	      return ServiceError.getResponseEntity(ApplicationData.ERROR_TOO_SHORT);
	}
	
	@RequestMapping(value = "/api/v1/auto-complete/{term:.*[^A-Za-z0-9 ].*}", produces = { "application/json" })
	  public @ResponseBody ResponseEntity<String> handlInvalidCharacterError() {	
	      return ServiceError.getResponseEntity(ApplicationData.ERROR_INVALID_CHAR);
	}

	@RequestMapping("/api/v1/auto-complete/")
	public @ResponseBody String showDefaultMessage() {
		return "service running";
	}
	
	  @RequestMapping(value = "/api/v1/auto-complete/{term:.*\\d.*}",  produces = { "application/json" })
	  public @ResponseBody ResponseEntity<String> handleError() {	
	      return ServiceError.getResponseEntity(ApplicationData.ERROR_NUMERIC);
	    }
}