package de.felixbublitz.casestudy.service;

public class ApplicationData {
	public final static String FILE_TRAINSTATION_LIST = "D_Bahnhof_2016_01_alle.csv";
	public final static String CSV_DELIMITER = ";";

	public final static int CSV_EVA = 0;
	public final static int CSV_DS = 1;
	public final static int CSV_NAME = 2;
	public final static int CSV_VERKEHR = 3;
	public final static int CSV_LAENGE = 4;
	public final static int CSV_BREITE = 5;
	public final static String JSON_STATION_SIZE = "number_of_stations_found";
	public final static String JSON_STATION_LIST = "station_list";
	public final static String JSON_TIME = "time_taken";
	public final static String JSON_ERROR_CODE = "error_code";
	public final static String JSON_ERROR_DESC = "error_description";
	
	public final static ServiceError ERROR_TOO_SHORT = new ServiceError(400, "Length of search term must be at least 3");
	public final static ServiceError ERROR_NUMERIC = new ServiceError(400, "Numeric characters are not allowed");
	public final static ServiceError ERROR_INVALID_CHAR = new ServiceError(400, "Invalid character");
	public final static ServiceError ERROR_METHOD_NOT_ALLOWED = new ServiceError(405, "Method not allowed");
	public final static ServiceError ERROR_INTERNAL_ERROR = new ServiceError(500, "Internal error");
	public final static ServiceError ERROR_NOT_FOUND = new ServiceError(404, "Ressource not found");


}