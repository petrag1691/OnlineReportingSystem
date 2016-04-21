package edu.uic.ids.constants;

import java.util.HashMap;

public class FinalConstants {
	public static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
	public static final String DB2DRIVER = "com.ibm.db2.jcc.DB2Driver";
	public static final String ORACLEDRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String SQLDRIVER = "com.jdbc.driver.sqldriver";
	public static final String MYSQLPORT = "3306";
	public static final String DB2PORT = "50000";
	public static final String ORACLEPORT = "1521";
	public static final String SQLPORT = "1433";
	public static final String DEFAULT_USER = "s16g24";
	public static final String DEFAULT_PASSWORD = "s16g24RewQ5";
	public static final int RANDOM_PRECISION = 1000000;
	public static final HashMap<String,String>tablesCreateMap;
	public static final HashMap<String,String>tablesDropMap;
	public static final HashMap<String,String>logMap;
	public static final HashMap<String, String>logCreateMap;
	static{
		tablesCreateMap = new HashMap<>();
		tablesDropMap = new HashMap<>();
		logMap = new HashMap<>();
		logCreateMap = new HashMap<>();
		tablesCreateMap.put("country","CREATE TABLE world.country"+ 
			"(Code char(3) NOT NULL DEFAULT '',"+
			"Name char(52) NOT NULL DEFAULT '',"+
			"Continent enum('Asia','Europe','North America','Africa','Oceania','Antarctica','South America') NOT NULL DEFAULT 'Asia',"+
			"Region char(26) NOT NULL DEFAULT '',"+
			"SurfaceArea float(10,2) NOT NULL DEFAULT '0.00',"+
			"IndepYear smallint(6) DEFAULT NULL,"+
			"Population int(11) NOT NULL DEFAULT '0',"+
			"LifeExpectancy float(3,1) DEFAULT NULL,"+
			"GNP float(10,2) DEFAULT NULL,"+
			"GNPOld float(10,2) DEFAULT NULL,"+
			"LocalName char(45) NOT NULL DEFAULT '',"+
			"GovernmentForm char(45) NOT NULL DEFAULT '',"+
			"HeadOfState char(60) DEFAULT NULL,"+
			"Capital char(11) DEFAULT NULL,"+
			"Code2 char(2) NOT NULL DEFAULT '',"+
			"PRIMARY KEY (Code)"+
			") ENGINE=InnoDB DEFAULT CHARSET=latin1"
		);
		tablesCreateMap.put("city", "CREATE TABLE world.city"+
			"(ID int(11) NOT NULL AUTO_INCREMENT,"+
			"Name char(35) NOT NULL DEFAULT '',"+
			"CountryCode char(3) NOT NULL DEFAULT '',"+
			"District char(20) NOT NULL DEFAULT '',"+
			"Population int(11) NOT NULL DEFAULT '0',"+
			"PRIMARY KEY (ID),"+
			"KEY CountryCode (CountryCode),"+
			"CONSTRAINT city_ibfk_1 FOREIGN KEY (CountryCode) REFERENCES world.country (Code)"+
			" ON DELETE CASCADE"+
			") ENGINE=InnoDB AUTO_INCREMENT=4080 DEFAULT CHARSET=latin1"
		);
		tablesCreateMap.put("countrylanguage", "CREATE TABLE  world.countrylanguage("+
		  "CountryCode char(3) NOT NULL DEFAULT '',"+
		  "Language char(30) NOT NULL DEFAULT '',"+
		  "IsOfficial enum('T','F') NOT NULL DEFAULT 'F',"+
		  "Percentage float(4,1) NOT NULL DEFAULT '0.0',"+
		  "PRIMARY KEY (CountryCode,Language),"+
		  "KEY CountryCode (CountryCode),"+
		  "CONSTRAINT countryLanguage_ibfk_1 FOREIGN KEY (CountryCode) REFERENCES world.country (Code)"+
		  " ON DELETE CASCADE"+
		  ")ENGINE=InnoDB DEFAULT CHARSET=latin1"
		);
		tablesDropMap.put("country","DROP TABLE IF EXISTS world.country");
		tablesDropMap.put("city","DROP TABLE IF EXISTS world.city");
		tablesDropMap.put("countrylanguage","DROP TABLE IF EXISTS world.countrylanguage");
		logMap.put("ipaddress", "string");
		logMap.put("logintime", "timestamp");
		logMap.put("schema", "string");
		logMap.put("dbms", "string");
		logMap.put("host", "string");
		logMap.put("username", "string");
		logMap.put("logouttime", "timestamp");
		logCreateMap.put("iplog", "CREATE TABLE s16g24.iplog(id int NOT NULL AUTO_INCREMENT,ipaddress varchar(30) NOT NULL,logintime timestamp NULL,logouttime timestamp  NULL,username varchar(20) not null,dbms varchar(10) not null,dbschema varchar(20) not null,host varchar(15) not null,PRIMARY KEY (id))ENGINE=InnoDB DEFAULT CHARSET=latin1");
	}
	public static double round(double value, double precision) {
		return Math.round(value * precision)/precision;
	}
}
