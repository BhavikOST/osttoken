package com.ost.alpha;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:out/cucumber", "json:out/cucumber.json"},
        glue = "com.ost.alpha.Steps",
        features = "src/test/resources/features/"
)
public class RunnerTest {
}
