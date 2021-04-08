# provide-support-llc-tech-task
Website Monitoring Tool - This is technical task for Provide Support, LLC (Junior Java Developer).

# Task Condition
[Task Condition](https://github.com/MikhailUsatenko/provide-support-llc-tech-task/blob/master/src/main/resources/java_test_assignment-website_monitoring_tool.pdf)

# How to use app
1. Fill out the form at the top of the page and click the "Add monitoring request" button.
2. After the request is displayed in the table, you can delete it by clicking on the "Delete" button opposite the desired URL.
3. If your request has a status other than "OK", you can click on the "Sound" button to hear the sound of your status.

# How to run app
1. sudo docker-compose -f docker-compose.yml up -d

Note: MongoDB Docker config file [docker-compose.yml](https://github.com/MikhailUsatenko/provide-support-llc-tech-task/blob/master/src/main/java/com/usatenko/demo/docker/docker-compose.yml)

2. mvn clean install -DskipTests
3. java -jar demo-0.0.1-SNAPSHOT.jar

http://localhost:8090/

# Technologies used for development
## Main
1. App Architecture: The monolith app, front-end static resources also
included (html/css/js etc.);
2. Database: MongoDB;
3. Back-End Technologies: Java 11, Spring Boot, Spring Web, Spring Data MongoDB, Thymeleaf, Lombok, JUnit 5;
4. Front-End Technologies: HTML, CSS, Javascript, JQuery;
5. DevOps Technologies: AWS EC2 (Linux Instance), Nginx, Docker-Compose.

## Others 
1. OS for development: Windows 10
2. OS for deployment: Ubuntu 18.04
3. Web-server: Apache Tomcat
4. Build tool: Maven

# Application functionality
1. Testing several sites from the server in parallel and outputting the result to the browser.
2. The monitoring result is displayed as a table on an HTML page
3. Monitoring configuration (adding, configuring, deleting monitoring URLs) is set via the web interface and saved in the database.

# Access to app via browser
The application has already been deployed to AWS EC2 Linux instance.

Now it's available by URL: http://3.142.161.106/
