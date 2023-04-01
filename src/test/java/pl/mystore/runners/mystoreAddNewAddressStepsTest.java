package pl.mystore.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/cucumber/features/mystore-testlab/mystore-add-new-address.feature",
        glue = "classpath:pl.mystore",
        plugin = {"pretty", "html:out.html"})

class mystoreAddNewAddressStepsTest {

}