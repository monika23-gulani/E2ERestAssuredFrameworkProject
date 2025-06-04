#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Validating place APIs

  @AddPlace @Regression
  Scenario: Verify if Place is successfully added using AddPlaceAPI
    Given Add Place payload with "<name>" "<language>" "<address>"
    When user calls "AddPlaceAPI" with "POST" Http request
    Then the API call got success with status code 200
    And "status" in response code is "OK"
		And "scope" in response code is "APP"
		And verify place_id created maps to "<name>" using "getPlaceAPI"
  
Examples:
|name 		|language			|address					 |
|AAhouse	|English			|World cross center|
|BBhouse  |Spanish      | Sea cross center |

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
    Given DeletePlace Payload
    When user calls "deletePlaceAPI" with "DEL" Http request
    Then the API call got success with status code 200
    And "status" in response code is "OK"
	