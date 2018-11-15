$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/UserJourney.feature");
formatter.feature({
  "name": "Reward",
  "description": "",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "Provide Reward to new User",
  "description": "",
  "keyword": "Scenario"
});
formatter.step({
  "name": "New user created and his/her name is Test",
  "keyword": "Given "
});
formatter.match({
  "location": "UserJourneySteps.newUserCreation(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "He/She got reward from Company",
  "keyword": "When "
});
formatter.match({
  "location": "UserJourneySteps.rewardTransactionExecute()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "His/Her BQA balance should increased by 8",
  "keyword": "Then "
});
formatter.match({
  "location": "UserJourneySteps.getUserList(int)"
});
formatter.result({
  "status": "passed"
});
});