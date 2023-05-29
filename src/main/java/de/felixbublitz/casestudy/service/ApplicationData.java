package de.felixbublitz.casestudy.service;

public class ApplicationData {
	public final static int CSV_EVA = 0;
	public final static int CSV_DS = 1;
	public final static int CSV_NAME = 2;
	public final static int CSV_VERKEHR = 3;
	public final static int CSV_LAENGE = 4;
	public final static int CSV_BREITE = 5;
	public final static String JSON_STATION_SIZE = "number_of_stations_found";
	public final static String JSON_STATION_LIST = "station_list";
	public final static String JSON_ERROR_CODE = "error_code";
	public final static String JSON_ERROR_DESC = "error_description";
	
	public final static ServiceError ERROR_TOO_SHORT = new ServiceError(400, 1, "Length of search term must be at least 3");
	public final static ServiceError ERROR_NUMERIC = new ServiceError(400, 2, "Numeric characters are not allowed");
	public final static ServiceError ERROR_INVALID_CHAR = new ServiceError(400, 3, "Invalid character");
	public final static ServiceError ERROR_METHOD_NOT_ALLOWED = new ServiceError(405, 4, "Method not allowed");




}