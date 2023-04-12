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

    private WebDriver driver;

    @Given("I am on myStore main page")
    public void iAmOnMyStoreMainPage() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://mystore-testlab.coderslab.pl/");
    }

    @And("I click the login button")
    public void iClickTheLoginButton() {

        driver.findElement(By.cssSelector("a[title='Log in to your customer account']")).click();
    }

    @When("I log in using my valid {string} and {string}")
    public void iLogInUsingMyValidAnd(String email, String password) {

        driver.findElement(By.id("field-email")).clear();
        driver.findElement(By.id("field-email")).sendKeys(email);

        driver.findElement(By.id("field-password")).clear();
        driver.findElement(By.id("field-password")).sendKeys(password);

        driver.findElement(By.id("submit-login")).click();

    }

    @Then("I should be on my account page")
    public void iShouldBeOnMyAccountPage() {

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals(currentUrl, "https://mystore-testlab.coderslab.pl/index.php?controller=my-account");
    }

    @Given("I go to the Addresses page")
    public void iGoToTheAddressesPage() {

        //driver.findElement(By.cssSelector("a[title='Addresses']")).click();

        List<WebElement> links = driver.findElements(By.cssSelector("div.links a"));
        WebElement secondLink = links.get(1);
        secondLink.click();
    }

    @When("I click the Create new address button")
    public void iClickTheCreateNewAddressButton() {

        driver.findElement(By.xpath("//a[@data-link-action='add-address']")).click();
    }

    @And("I fill in the {string},{string}, {string}, {string}, {string},{string}")
    public void iFillInThe(String Alias, String Address, String City, String PostalCode, String Phone,String Country) {

        driver.findElement(By.id("field-alias")).clear();
        driver.findElement(By.id("field-alias")).sendKeys(Alias);

        driver.findElement(By.id("field-address1")).clear();
        driver.findElement(By.id("field-address1")).sendKeys(Address);

        driver.findElement(By.id("field-city")).clear();
        driver.findElement(By.id("field-city")).sendKeys(City);

        driver.findElement(By.id("field-id_country")).sendKeys(Country);

        driver.findElement(By.id("field-postcode")).clear();
        driver.findElement(By.id("field-postcode")).sendKeys(PostalCode);

        driver.findElement(By.id("field-phone")).clear();
        driver.findElement(By.id("field-phone")).sendKeys(Phone);

        /*WebElement dropdown = driver.findElement(By.id("field-id_state"));
        Select select = new Select(dropdown);
        select.selectByIndex(1);*/
    }


    @And("I click the Save button")
    public void iClickTheSaveButton() {

        driver.findElement(By.xpath("//button[contains(text(),'Save')]")).click();
    }

    @And("I can see success alert")
    public void iCanSeeSuccessAlert() {

        WebElement successAlert = driver.findElement(By.className("alert-success"));
        Assertions.assertTrue(successAlert.isDisplayed());
    }

    @Then("I verify provided details : {string},{string}, {string}, {string}, {string}, {string} are correct")
    public void iVerifyProvidedDetailsAreCorrect(String Alias, String Address, String City, String PostalCode, String Phone, String Country) {

        String actualAlias = driver.findElement(By.tagName("h4")).getText();
        Assertions.assertEquals(Alias, actualAlias);
        //System.out.println(actualAlias);

        String addressText = driver.findElement(By.tagName("address")).getText();
        String[] addressParts = addressText.split("\n");

        String actualAddress = addressParts[1];
        Assertions.assertEquals(Address, actualAddress);
        //System.out.println(actualAddress);

        String actualCity = addressParts[2];
        Assertions.assertEquals(City, actualCity);
        //System.out.println(actualCity);

        String actualPostalCode = addressParts[3];
        Assertions.assertEquals(PostalCode, actualPostalCode);
        //System.out.println(actualPostalCode);

        String actualCountry = addressParts[4];
        Assertions.assertEquals(Country, actualCountry);
        //System.out.println(actualCountry);

        String actualPhone = addressParts[5];
        Assertions.assertEquals(Phone, actualPhone);
        //System.out.println(actualPhone);

    }


    @When("I click the Delete button for the created address")
    public void iClickTheDeleteButtonForTheCreatedAddress() {

        // Find all articles with class 'address'
        List<WebElement> addressArticles = driver.findElements(By.cssSelector("article.address"));

        //addressArticles.size(-1);

        WebElement deleteLink = addressArticles.get(1).findElement(By.cssSelector("div.address-footer a[data-link-action='delete-address']"));
        deleteLink.click();

        //driver.findElement(By.linkText("Delete")).click();
        //driver.findElement(By.xpath("//a[@data-link-action='delete-address']//span[text()='Delete']")).click();
        //driver.findElement(By.cssSelector("a[data-link-action='delete-address']")).click();
    }

    @Then("I check if address was successfully deleted")
    public void iCheckIfAddressWasSuccessfullyDeleted() {

        WebElement alert = driver.findElement(By.cssSelector("article.alert"));
        String classAttribute = alert.getAttribute("class");

        // Check if the success alert is displayed

        if (classAttribute.contains("alert-success")) {

            // Get the text of the success alert and print it out
            //String successAlertText = driver.findElement(By.cssSelector("article.alert-success")).getText();
            String successAlertText = alert.findElement(By.tagName("li")).getText();
            System.out.println("Success: " + successAlertText);

        } else if (classAttribute.contains("alert-danger")){

            String dangerAlertText = alert.findElement(By.tagName("li")).getText();

            System.out.println(dangerAlertText);
            throw new RuntimeException(dangerAlertText);

        }
    }

}
