# Technikon
A Renovation Contractor agency application

## Description 
In this application, administrators are able to access information pertaining to customers and the repairs of their properties, which are stored in a database. Moreover, it gives its customers the opportunity to monitor the progress of repair or renovation work on their property. 

## For a Property Owner, the application provides the following features:  
- Accept or reject a new Repair
- Register a new Repair for a Property Owner's Property 

## For an Administrator, the application provides the following features:
- Display all Properties of a Property Owner
- Register a new Property Owner in the database as a new customer
- Register a new property to a Property Owner
- Display all pending repairs
- Recommending Repair costs to Property Owner
- Recommending the start and end dates of the Repair to the Property Owner
- Remove Property Owner from the database
- Remove Properties from the database
- Remove Repairs from the database 

## The following Use Cases are inmplemented: 
- Creation of a Property Owner with all the necessary details. Registering two properties in Property Owner by checking for incorrect data entry.

- Display all the Properties of a Property Owner and registration of a Repair for a particular Property

- The admin gets all pending repairs, proposes the costs, start and end dates. The property owners accept or decline the repairs. The admin checks the start of works and the end when they are done.

- The property-owners get a full report of their properties and the statuses of their repairs. The admin gets a full report of the repairs in all statuses.

## Installations
- Java JDK 17 (LTS) 
- Maven
- MySQL Server

## How to run
To run this project clone the repository.You will need to change the username and password
fields in the persistence.xml file to match your database credentials, then run Technikon.java file.
