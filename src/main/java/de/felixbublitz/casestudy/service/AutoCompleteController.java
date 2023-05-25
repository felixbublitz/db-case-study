package de.felixbublitz.casestudy.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AutoCompleteController {

	private final Database db;

	public AutoCompleteController(Database db) {
		this.db = db;
	}

	@RequestMapping(value = "/api/v1/auto-complete/{term}", produces = { "application/json" })
	public @ResponseBody String getAttr(@PathVariable("term") String term) {
		return db.getSearchResult(term).toString();
	}

	@RequestMapping("/api/v1/auto-complete/")
	public @ResponseBody String showDefaultMessage() {
		return "service running";
	}
}