package br.ada.ecommerce.test;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"br.ada.ecommerce.test.hook", "br.ada.ecommerce.test.authentication"})
public class CucumberRunner {
}
