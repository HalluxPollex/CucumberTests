package pl.mystore.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// This line specifies that the Cucumber class will be used to run the tests
@RunWith(Cucumber.class)

// This annotation specifies the options to be used during test execution
@CucumberOptions(

        // This line specifies the path to the feature file to be executed
        features = "src/cucumber/features/mystore-testlab/mystore-add-product.feature",

        // This line specifies the package where the step definitions are located
        glue = "classpath:pl.mystore",

        // This line specifies the plugins to be used during the test execution
        plugin = {"pretty", "html:out.html"})

// This class executes the steps of the "add product"
class mystoreAddProductStepsTest {

}