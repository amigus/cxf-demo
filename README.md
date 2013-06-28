About
=====

An Apache CXF demo application that implements a simple content service using 
REST (JAX-RS) and SOAP (JAX-WS) and a single page web site that makes use of it
using AngularJS and JQuery-UI. 

Usage
=====

To build it and run the unit tests:

	mvn install

To run it:

	mvn install
	cd webapps/webservices
	mvn jetty:run &
	cd ../website
	mvn jetty:run

The Website will be available [here](http://localhost:8080/website/index.html)

The SOAP service will be available [here](http://localhost:8081/webservices/content-service)

The WSDL will be available [here](http://localhost:8081/webservices/content-service?wsdl)
