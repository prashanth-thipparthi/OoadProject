# Job Portal

## Team Members

Akriti Kapur  
Amith Gopal  
Prashanth Thipparthi  

## Tech Stack
Database: Postgres      
Front End: ReactJS    
Backend: Spring Boot, Hibernate 

## Steps to follow to setup the development environment for the project

1. Install the postgres Database. https://www.postgresql.org/download/
2. Postgres Server runs on the port 5432. Connect to the server using the pgAdmin.
3. Run the sql script https://github.com/tnreddy09/OoadProject/blob/master/sql_queries/revise_complete_database_creation. It will create the database required for the application.
4. Modify the application.properties according to the developement environment.https://github.com/tnreddy09/OoadProject/tree/master/ooad/src/main/resources

## Setup the application

1. Download the sts bundle. https://spring.io/tools.
2. Clone the git repo.
3. Open the project as a Sprint Boot Application.
4. Verify the application.properties file.
5. Run the project as a Spring Boot Application.


## File Structure

The backend part of the project (Spring Boot application) is divided into the following folders: 

**1. demo folder**

   This folder contains the following .java files. These basically files which are used to provide some functionality common to the whole project specifically to the controllers.    
   
      a.  AddApplicationParameters: This class represents the parameters of the request body while adding an application  
      b.  AddJobParameters: This class represents the parameters of the request body while adding a job to the Job table to the database   
      c.  CorsConfig: This class is used to give permissions for requests on the Application server by the UI server both running in the same machine but on different ports  
      d.  CustomJobAndApplicationInfo: This class is designed to accomodate information about job and application   information. This class information will be later serialized by the CustomJobAndApplicationInfoSerializer class 
      e.  CustomJobAndApplicationInfoSerializer: The function of this class is to serialize the contents of the CustomJobAndApplicationInfo class in the form of Json representing the job information and the application information in the form of key-value pairs   
      f.  SignUpParameters: Main application which starts the entire Spring Boot backend Job Portal Application  
      g.  OoadApplication: This class represents the parameters of the request body while signing up into the job portal   

    
**2. demo.controller folder**   

   This folder contains all the .java files which serve as REST API controllers. These files are responsible for redirecting any of the incoming API requests to the concerned functions. They are seggregated based on the table in the database which the APIs are directed to. 
   
      a. ApplicationController: This Application Controller contains handling REST APIs focused on dealing with queries on the Application table in the database. 
      b. CandidateController: This Candidate Controller contains handling REST APIs focused on dealing with queries on the Candidate table in the database. 
      c. CompanyController: This Company Controller contains handling REST APIs focused on dealing with queries on the Company table in the database. 
      d. JobController: This Job Controller contains handling REST APIs focused on dealing with queries on the Job table in the database. 
      e. LogInController: This Login Controller contains handling REST APIs focused on dealing with queries on the Login, Candidate and Company tables in the database. 
      
**3. demo.dao folder**    

   This folder contains all the .java files which basically contain functionality to perform CRUD operations on the database. These files are seggregated based on the table in the database. Each file contains CRUD operation on different tables of the database. 
   
      a. ApplicationDAO: This class contains functions which performs CRUD operations on the Application table in the database 
      b. CandidateDAO: This class contains functions which performs CRUD operations on the Candidate table in the database  
      c. CompanyDAO: This class contains functions which performs CRUD operations on the Company table in the database 
      d. JobDAO: This class contains functions which performs CRUD operations on the Job table in the database 
      e. LogInDAO: This class contains functions which performs CRUD operations on the LogIn table in the database 
      f. SkillsDAO: This class contains functions which performs CRUD operations on the Skills table in the database
      
**4. demo.model**  

   This folder contains all the .java files which basically represent the Object Relational Mapping (ORM) for each of the different tables in the database. 
   
      a. Application: This class is the Object Relational Mapper (ORM) for the applications table in the database 
      b. Candidate: This class is the Object Relational Mapper (ORM) for the candidates table in the database 
      c. Company: This class is the Object Relational Mapper (ORM) for the companies table in the database 
      d. Job: This class is the Object Relational Mapper (ORM) for the jobs table in the database 
      e. Login: This class is the Object Relational Mapper (ORM) for the Login table in the database 
      f. Skills: This class is the Object Relational Mapper (ORM) for the Skills table in the database 
