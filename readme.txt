Import the Spring Boot project from https://github.com/Murphy76/CurrencyTest.git
Build jar
Copy currencyexchange-0.0.1-SNAPSHOT.jar file to the desired directory
Run jar file.

DB - embedded h2, creates folder db in current directory
Db connection properties is in the applcation.properties file
Initialization SQL scripts are in the /resources/ folder (schema.sql and data.sql)
Also available h2 Console for ADMIN rolw users by localhost:8080/h2-console




embedded users: 
	username - user
	password - 1
	role - USER
	
	username - admin
	password - 1
	role - ADMIN, USER
	
Available Currency signature:
	EUR, USD,  UAH,  RUB	

Endpoints:
	GET   /api/commissions - get List of defined commissions
	POST  /api/comissions - set commission (consumes Commission object)
	GET   /api/exchange-rates - get List of defined rates
	POST  /api/exchange-rates - set rate (consumes ExchangeRate object)
	POST  /api/exchange - request for currency exchange (consumes ExchangeRequest )
	
Jbjects definition:
	Commission:
	{
		"commissionPt" : number,
		"from": Currency 
		"to": Currency 
	}
	*restriction: "from" not equals "to", commissionPt =  [0;100)
	
	ExchangeRate:
	{
		"rate": number ,
		"from": Currency ,
		"to": Currency 
	}
	*restriction: "from" != "to", rate >0
	
	ExchangeRequest:
	{
		"amountFrom": number,
		"amountTo" : number, 
		"currencyFrom": Currency,
		"currencyTo":Currency,
		"operationType": type of operation "GIVE" / "GET"
	}
	*direct ecchange currencyFrom to currencyTo should contains the amountFrom value and operationType "GIVE" 
	*back ecchange currencyFrom to currencyTo should contains the desired amountTo value and operationType "GET"
	
	 Swagger API documentation available by url http://localhost:8080/swagger-ui.html
	 
	 ----------Docker------
	 Build paalication
	 Open terminal window
	 Change working directory to root project location.
	 Build docker image     docker build -t currencyimg .
	 Create docker volume   docker volume create currencydb
	 Ececute                docker run --rm --name currency -v currencydb:/usr/src/app/data -d -p 8080:8080 currencyimg
	 To stop container execute 
	                        docker stop currency
	 
	 
	 
	