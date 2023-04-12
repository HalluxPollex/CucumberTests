package pl.mystore.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;
import java.util.List;


public class mystoreAddProductSteps {

    private WebDriver driver;
    private String expectedReferenceNumber;
    private String expectedTotalAmount;

    @Given("the user is logged in with the credentials from the first task")
    public void the_user_is_logged_in_with_the_credentials_from_the_first_task() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=authentication&back=my-account");
        driver.findElement(By.id("field-email")).clear();
        driver.findElement(By.id("field-email")).sendKeys("adam.kowalski@test.com");

        driver.findElement(By.id("field-password")).clear();
        driver.findElement(By.id("field-password")).sendKeys("Pa$$word");

        driver.findElement(By.id("submit-login")).click();

        //throw new io.cucumber.java.PendingException();
    }

    @When("the user selects the {string} and verifies a discount is applied")
    public void theUserSelectsTheProductAndVerifiesADiscountIsApplied(String product) {

        WebElement searchField = driver.findElement(By.cssSelector("input[name='s']"));
        searchField.sendKeys(product);
        searchField.submit();

        driver.findElement(By.xpath("//a[@class='thumbnail product-thumbnail']")).click();

        String discounOnPage = driver.findElement(By.className("discount-percentage")).getText().replaceAll("[^\\d]", "");
        Assertions.assertEquals("20", discounOnPage);

    }

    /*@When("the user selects the Hummingbird Printed Sweater and verifies a discount is applied")
    public void theUserSelectsTheHummingbirdPrintedSweaterAndVerifiesADiscountIsApplied() {

        WebElement searchField = driver.findElement(By.cssSelector("input[name='s']"));
        searchField.sendKeys("Hummingbird Printed Sweater");
        searchField.submit();

        driver.findElement(By.xpath("//a[@class='thumbnail product-thumbnail']")).click();

        String discounOnPage = driver.findElement(By.className("discount-percentage")).getText().replaceAll("[^\\d]", "");
        Assertions.assertEquals(discounOnPage, "20");

    }*/

    @And("the user selects {string}")
    public void theUserSelectsSize(String size) {

        WebElement dropdown = driver.findElement(By.id("group_1"));
        Select select = new Select(dropdown);
        select.selectByVisibleText(size);
    }

    @And("the user selects {int} pieces")
    public void theUserSelectsPieces(int qty) {

        WebElement qtyInput = driver.findElement(By.id("quantity_wanted"));

        String quantity = "\b" + qty + "\ue017";
        qtyInput.sendKeys(quantity);

        //qtyInput.sendKeys(Keys.ENTER);

        /*WebElement spinUpBtn = driver.findElement(By.xpath("//button[contains(@class, 'bootstrap-touchspin-up')]"));

        for (int i = 1; i < qty; i++) {

            driver.findElement(By.id("quantity_wanted")).click();
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(100));
            spinUpBtn.click();

        }*/
    }

    @And("the user adds the product to cart")
    public void theUserAddsTheProductToCart() {

        WebElement addToCartBtn = driver.findElement(By.cssSelector("button.add-to-cart"));

        if (addToCartBtn.isEnabled()){
            addToCartBtn.click();
        }else {
            String message = "Add to cart button is disabled, item is not available";
            System.out.println(message);
            throw new RuntimeException(message);
        }
    }

    @And("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.findElement(By.cssSelector(".cart-content-btn a.btn-primary")).click();
        driver.findElement(By.cssSelector(".text-sm-center a.btn-primary")).click();

    }

    @And("the user confirms the address")
    public void theUserConfirmsTheAddress() {

        driver.findElement(By.name("confirm-addresses")).click();

    }

    @And("the user selects the Pick up in store option")
    public void theUserSelectsThePickUpInStoreOption() {

        WebElement pickUpInStore = driver.findElement(By.id("delivery_option_8"));
        WebElement confirmBtn = driver.findElement(By.name("confirmDeliveryOption"));

        if (pickUpInStore.isSelected()){
            confirmBtn.click();
        }else{
            pickUpInStore.click();
            confirmBtn.click();
        }
    }

    @And("the user selects Pay by Check option")
    public void theUserSelectsPayByCheckOption() {

       WebElement payByCheck = driver.findElement(By.name("payment-option"));
       payByCheck.click();

    }

    @And("the user clicks Order with an obligation to pay")
    public void theUserClicksOrderWithAnObligationToPay() {

        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();
        driver.findElement(By.cssSelector("#payment-confirmation button[type='submit']")).click();

    }

    @And("the user takes a screenshot of the order confirmation with the total amount")
    public void theUserTakesAScreenshotOfTheOrderConfirmationWithTheTotalAmount() {

        // Extract the reference number from the element
        WebElement referenceElement = driver.findElement(By.id("order-reference-value"));
        String referenceText = referenceElement.getText();
        this.expectedReferenceNumber = referenceText.substring(referenceText.length()-9);

        // Extract the amount of EUR from the element
        this.expectedTotalAmount = driver.findElement(By.cssSelector("tr.total-value td:nth-child(2)")).getText();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        /*String screenName = "srn" + LocalDateTime.now();
        String cleanName = screenName.replace(":", "").replace(".", "");*/

        String cleanName = expectedReferenceNumber;
        String screenPath = "src/test/java/pl/mystore/screenshots/"+cleanName+".png";

        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        screenshot.renameTo(new File(screenPath));

    }

    @And("the user navigates to order history and details page")
    public void theUserNavigatesToOrderHistoryAndDetailsPage() {

        driver.findElement(By.cssSelector("a.account")).click();
        driver.findElement(By.id("history-link")).click();

    }

    @Then("the order with {string} status and the same total amount is displayed in the list")
    public void theOrderWithStatusAndTheSameTotalAmountIsDisplayedInTheList(String requiredOrderStatus) {

        // Find the first row of the table
        List<WebElement> orderRows = driver.findElements(By.cssSelector("table tbody tr"));

        boolean referenceFound = false;

        for (int i = 0; i < orderRows.size(); i++ ){

            String orderNumber = orderRows.get(i).findElement(By.tagName("th")).getText();
            String paymentAmount = orderRows.get(i).findElements(By.tagName("td")).get(1).getText();
            String status = orderRows.get(i).findElements(By.tagName("td")).get(3).getText();

            if (expectedReferenceNumber.equals(orderNumber)){

                if (expectedTotalAmount.equals(paymentAmount)){

                    if (status.equals(requiredOrderStatus)){

                        System.out.println("Expected reference number: "+ expectedReferenceNumber +"; Reference found in Order history: "+orderNumber);
                        System.out.println("Expected amount: "+ expectedTotalAmount +"; Amount found in Order history: "+paymentAmount);
                        System.out.println("Expected status: "+requiredOrderStatus+"; Status found in Order history: "+status);

                        referenceFound = true;
                        break;

                    }else {

                        throw new AssertionError("Expected status is: "+requiredOrderStatus+ "Actual status:"+status);
                    }

                }else {

                    throw new AssertionError("Expected amount: "+ expectedTotalAmount +" Actual amount: "+paymentAmount);
                }

            } else if (i == orderRows.size()-1 && !referenceFound) {

                throw new AssertionError("No expected reference number: "+ expectedReferenceNumber +" is found");

            }

        }

    }

}
