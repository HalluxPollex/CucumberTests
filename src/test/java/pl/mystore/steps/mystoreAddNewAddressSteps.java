package pl.mystore.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.List;

public class mystoreAddNewAddressSteps {

    // Declare and initialize a WebDriver object
    private WebDriver driver;

    // Define a Cucumber step to navigate to the MyStore main page
    @Given("I am on myStore main page")
    public void iAmOnMyStoreMainPage() {

        // Set the system property for the Chrome WebDriver executable
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        // Create a new ChromeOptions object and add an argument to allow remote origins
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Create a new ChromeDriver object with the specified options
        driver = new ChromeDriver(options);

        // Set the implicit wait timeout for the driver to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Maximize the browser window
        driver.manage().window().maximize();

        // Navigate to the MyStore main page
        driver.get("https://mystore-testlab.coderslab.pl/");
    }

    // Define a Cucumber step to click the login button on the MyStore main page
    @And("I click the login button")
    public void iClickTheLoginButton() {

        // Find the login button element using a CSS selector and click it
        driver.findElement(By.cssSelector("a[title='Log in to your customer account']")).click();
    }

    // Define a Cucumber step to log in using valid email and password credentials
    @When("I log in using my valid {string} and {string}")
    public void iLogInUsingMyValidAnd(String email, String password) {

        // Find the email input element by ID, clear its contents, and enter the provided email
        driver.findElement(By.id("field-email")).clear();
        driver.findElement(By.id("field-email")).sendKeys(email);

        // Find the password input element by ID, clear its contents, and enter the provided password
        driver.findElement(By.id("field-password")).clear();
        driver.findElement(By.id("field-password")).sendKeys(password);

        // Find the submit button element by ID and click it to log in
        driver.findElement(By.id("submit-login")).click();
    }

    // Define a Cucumber step to verify that the user is on the My Account page after logging in
    @Then("I should be on my account page")
    public void iShouldBeOnMyAccountPage() {

        // Get the current URL of the browser and assert that it matches the expected My Account page URL
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, "https://mystore-testlab.coderslab.pl/index.php?controller=my-account");
    }

    // Define a Cucumber step to navigate to the Addresses page
    @Given("I go to the Addresses page")
    public void iGoToTheAddressesPage() {

        //driver.findElement(By.cssSelector("a[title='Addresses']")).click();

        // Find all link elements in the div with class "links" and click the second link
        List<WebElement> links = driver.findElements(By.cssSelector("div.links a"));
        WebElement secondLink = links.get(1);
        secondLink.click();
    }

    // Define a Cucumber step to click the Create new address button on the Addresses page
    @When("I click the Create new address button")
    public void iClickTheCreateNewAddressButton() {

        // Find the "Create new address" link element using an XPath expression and click it
        driver.findElement(By.xpath("//a[@data-link-action='add-address']")).click();
    }

    // Define a Cucumber step to fill in address details on the Create new address form
    @And("I fill in the {string},{string}, {string}, {string}, {string},{string}")
    public void iFillInThe(String Alias, String Address, String City, String PostalCode, String Phone,String Country) {

        // Find the input element for the Alias and enter the provided value
        driver.findElement(By.id("field-alias")).clear();
        driver.findElement(By.id("field-alias")).sendKeys(Alias);

        // Find the input element for the Address and enter the provided value
        driver.findElement(By.id("field-address1")).clear();
        driver.findElement(By.id("field-address1")).sendKeys(Address);

        // Find the input element for the City and enter the provided value
        driver.findElement(By.id("field-city")).clear();
        driver.findElement(By.id("field-city")).sendKeys(City);

        // Find the select element for the Country and select the option with the provided value
        driver.findElement(By.id("field-id_country")).sendKeys(Country);

        // Find the input element for the Postal Code and enter the provided value
        driver.findElement(By.id("field-postcode")).clear();
        driver.findElement(By.id("field-postcode")).sendKeys(PostalCode);

        // Find the input element for the Phone and enter the provided value
        driver.findElement(By.id("field-phone")).clear();
        driver.findElement(By.id("field-phone")).sendKeys(Phone);

        /*WebElement dropdown = driver.findElement(By.id("field-id_state"));
        Select select = new Select(dropdown);
        select.selectByIndex(1);*/
    }


    // Define a Cucumber step to click the Save button on the Create new address form
    @And("I click the Save button")
    public void iClickTheSaveButton() {

        // Find the "Save" button element using an XPath expression and click it
        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
    }

    // Define a Cucumber step to check if the success alert is displayed after adding a new address
    @And("I can see success alert")
    public void iCanSeeSuccessAlert() {

        // Find the success alert element by class name and check if it is displayed
        WebElement successAlert = driver.findElement(By.className("alert-success"));
        Assertions.assertTrue(successAlert.isDisplayed());
    }

    // Define a Cucumber step to verify if the provided address details are correct
    @Then("I verify provided details : {string},{string}, {string}, {string}, {string}, {string} are correct")
    public void iVerifyProvidedDetailsAreCorrect(String Alias, String Address, String City, String PostalCode, String Phone, String Country) {

        // Get the actual alias from the page and compare it with the expected value
        String actualAlias = driver.findElement(By.tagName("h4")).getText();
        Assertions.assertEquals(Alias, actualAlias);

        // Get the address text from the page and split it into an array to get the individual parts
        String addressText = driver.findElement(By.tagName("address")).getText();
        String[] addressParts = addressText.split("\n");

        // Get the actual address from the array and compare it with the expected value
        String actualAddress = addressParts[1];
        Assertions.assertEquals(Address, actualAddress);

        // Get the actual city from the array and compare it with the expected value
        String actualCity = addressParts[2];
        Assertions.assertEquals(City, actualCity);

        // Get the actual postal code from the array and compare it with the expected value
        String actualPostalCode = addressParts[3];
        Assertions.assertEquals(PostalCode, actualPostalCode);

        // Get the actual country from the array and compare it with the expected value
        String actualCountry = addressParts[4];
        Assertions.assertEquals(Country, actualCountry);

        // Get the actual phone from the array and compare it with the expected value
        String actualPhone = addressParts[5];
        Assertions.assertEquals(Phone, actualPhone);

    }


    // This method is called when the user wants to click the Delete button for the created address
    @When("I click the Delete button for the created address")
    public void iClickTheDeleteButtonForTheCreatedAddress() {

        // Find all articles with class 'address'
        List<WebElement> addressArticles = driver.findElements(By.cssSelector("article.address"));

        // Find the last article, as that is the one created most recently
        int linkNumber = addressArticles.size()-1;

        // Find the delete button for the last article
        WebElement deleteLink = addressArticles.get(linkNumber).findElement(By.cssSelector("div.address-footer a[data-link-action='delete-address']"));

        // Click the delete button
        deleteLink.click();
    }

    @Then("I check if address was successfully deleted")
    public void iCheckIfAddressWasSuccessfullyDeleted() {

        // Find the article alert
        WebElement alert = driver.findElement(By.cssSelector("article.alert"));

        //get the class attribute of the alert
        String classAttribute = alert.getAttribute("class");

        // Check if the class attribute of the alert contains alert-success
        if (classAttribute.contains("alert-success")) {

            //if so then get the text of the li element of alert
            String successAlertText = alert.findElement(By.tagName("li")).getText();

            //display the success msg and print out text of the li element
            System.out.println("Success: " + successAlertText);

            // else check if the class attribute of the alert contains alert-danger
        } else if (classAttribute.contains("alert-danger")){

            //if so then get the text of the li element of alert
            String dangerAlertText = alert.findElement(By.tagName("li")).getText();

            // Throw a runtime exception with the danger alert text
            throw new RuntimeException(dangerAlertText);

        }
    }

}
