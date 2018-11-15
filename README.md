
# Test-automation-Cucumber

Cucumber is a behavior driven development (BDD) approach to write automation test script to test Web. 
It enables you to write and execute automated acceptance/unit tests. It is cross-platform, open source and free. 


## Tools

* Maven
* Cucumber-JVM
  

## Requirements

In order to utilise this project you need to have the following installed locally:

* Maven 3
* Java 1.8

## Feature files explaination

UserJourney.feature : 
	
	Given: New user created and his/her name is "Customer Name"
    When: He/She got reward from Company
    Then: His/Her BQA balance should increased by "Expected Points"
	
	As mentioned in above scenarion, new user will be created then Reward given to that customers and customer's balance will increased.
	"Custoemer Name"  - Replace with the real time user name
	"Expected Points" - Replace with the real time points based on given in Reward transaction.
	
	
## How To Run

This project has only one feature to test.

To run all features, navigate to `test-automation-quickstart` directory and run in terminal below command:

`mvn clean install` 

To run this from any IDE (intellij or Eclipse) navigate to 'UserJourney.feature' file and right click > Run

## Reporting

For reporting user need to look into consol, whichh will give you detailed output for each and every steps or API

For scenario reports, please navigate to 'repos/ostTest/out/cucumber/index.html'

## What we can implement more:

* Optimize the code for API HTTP/HTTPS call with various frameworks like Restassured.
* Reporting can be better with third party reporting tools like Extent Report
* Move the API call code to different files and jsut consumes results in Steps files.
* Variables like Secret key and API key should not expose in the code, it should encrypted or part of your environment variables in servers

