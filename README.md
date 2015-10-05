# SMTP Listener
Sometimes we want to see the emails our application is sending, to check layout, links or whatever.
Some frameworks provide basic solutions for this problem (like PHP Symphony) but others don't and I developed this small tool to address this problem.

# Goal
To provide a simple and easy way to catch all emails sent by an application and show them in a Webmail like web interface.

# How to use it
To start the application you simply need to run the following command inside the project directory:
 ```
 mvn spring-boot:run 
 ```
 Important: If will need to have Maven installed into your machine (if you're using Ubuntu like me just type sudo apt-get install maven) and JDK version 7 (at least).
 
 After that you configure your application to send emails using server localhost, port 25000.
 
 To access the "Inbox" place the following url into your browser:
 ``` 
 http://localhost:8000
 ```
 
 You can change these ports by editing the src/main/resources/application.properties file.

# App Structure 
The application is basically composed by three parts:
* SMTP Listener: it starts a SMTP server (uses Subethamail) and stores every email in memory. When the application is stopped, everything is erased;
* REST API: it uses Spring framework to provide email and attachments data through a REST interface;
* FakeWebmail: user interface built using AngularJS. Looks like a webmail inbox screen, providing an easy way to look at the email. It supports Text/Plain and HTML messages (and combined versions) plus attachments.

  


