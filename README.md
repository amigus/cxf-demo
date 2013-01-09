About
=====

An Apache CXF demo application that implements a simple content service using 
REST (JAX-RS) and SOAP (JAX-WS). 

Usage
=====

To build the web application and run the unit tests:

	mvn install

To run the web application using **Jetty**:

	mvn install
	cd webapps/webservices
	mvn -P 'derby,jetty' jetty:run-war

To run the web application using **Tomcat**:

	mvn install
	cd webapps/webservices
	mvn -P 'derby,tomcat' tomcat:run-war

The SOAP service will be available [here](http://localhost:8080/webservices/content-service)

The WSDL will be available [here](http://localhost:8080/webservices/content-service?wsdl)

To run the war using an **existing Tomcat** installation:

	mvn install
	cp webapps/webservices/target/webservices-1.0-SNAPSHOT.war $CATALINA_BASE/webapps/

