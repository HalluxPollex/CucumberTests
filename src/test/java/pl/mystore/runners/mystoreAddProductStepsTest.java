package pl.mystore.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/cucumber/features/mystore-testlab/mystore-add-product.feature",
        glue = "classpath:pl.mystore",
        plugin = {"pretty", "html:out.html"})

class mystoreAddProductStepsTest {

}