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

    //Initialization of the webdriver instance
    private WebDriver driver;

    // Declaration of a private instances to be used later from the class
    private String expectedReferenceNumber;
    private String expectedTotalAmount;

    // Define a Cucumber step to navigate logged in with the
    @Given("the user is logged in with the credentials from the first task")
    public void the_user_is_logged_in_with_the_credentials_from_the_first_task() {

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Create a new instance of ChromeDriver with the specified options
        driver = new ChromeDriver(options);

        // Set the implicit wait timeout to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Maximize the browser window
        driver.manage().window().maximize();

        // Load the login page
        driver.get("https://mystore-testlab.coderslab.pl/index.php?controller=authentication&back=my-account");

        // Clear the email field and enter the email address
        driver.findElement(By.id("field-email")).clear();
        driver.findElement(By.id("field-email")).sendKeys("adam.kowalski@test.com");

        // Clear the password field and enter the password
        driver.findElement(By.id("field-password")).clear();
        driver.findElement(By.id("field-password")).sendKeys("Pa$$word");

        // Click the login button
        driver.findElement(By.id("submit-login")).click();
    }

    // Define a Cucumber step to find the product from the search bar and to check the discount is applied
    @When("the user selects the {string} and verifies a discount is applied")
    public void theUserSelectsTheProductAndVerifiesADiscountIsApplied(String product) {

        // Find the search field element and enter the name of the product
        WebElement searchField = driver.findElement(By.cssSelector("input[name='s']"));
        searchField.sendKeys(product);
        searchField.submit();

        // Click on the first product displayed in the search results
        driver.findElement(By.xpath("//a[@class='thumbnail product-thumbnail']")).click();

        // Get the discount percentage from the product page and remove all non-numeric characters
        String discounOnPage = driver.findElement(By.className("discount-percentage")).getText().replaceAll("[^\\d]", "");

        // Verify if the discount percentage is equal to 20 using assertions
        Assertions.assertEquals("20", discounOnPage);
    }

    // Define a Cucumber step to select the size of the product
    @And("the user selects {string}")
    public void theUserSelectsSize(String size) {

        // Find the dropdown element by its ID
        WebElement dropdown = driver.findElement(By.id("group_1"));

        // Create a new Select object from the dropdown element
        Select select = new Select(dropdown);

        // Select the option from the dropdown by visible text
        select.selectByVisibleText(size);
    }

    // Define a Cucumber step to select the quantity
    @And("the user selects {int} pieces")
    public void theUserSelectsPieces(int qty) {

        // Find the quantity input field
        WebElement qtyInput = driver.findElement(By.id("quantity_wanted"));

        // Create string (backspace + qty + right arrow)
        String quantity = "\b" + qty + "\ue017";

        // Send quantity value to the input field
        qtyInput.sendKeys(quantity);
    }

    // Define a Cucumber step to add product to the card
    @And("the user adds the product to cart")
    public void theUserAddsTheProductToCart() {

        // Find the "Add to Cart" button on the web page using a CSS selector
        WebElement addToCartBtn = driver.findElement(By.cssSelector("button.add-to-cart"));

        // If the button is enabled, click on it to add the product to the cart
        if (addToCartBtn.isEnabled()){
            addToCartBtn.click();
        }else {

            // If the button is disabled, create an error message and throw a runtime exception
            String message = "Add to cart button is disabled, item is not available";
            throw new RuntimeException(message);
        }
    }

    // Define a Cucumber step to proceed to the checkout
    @And("the user proceeds to checkout")
    public void theUserProceedsToCheckout() {

        // Set a timeout for the implicit wait to 2 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));

        // Find the "Proceed to Checkout" button in the card and then on the web page using a CSS selector, and click it
        driver.findElement(By.cssSelector(".cart-content-btn a.btn-primary")).click();
        driver.findElement(By.cssSelector(".text-sm-center a.btn-primary")).click();
    }

    // Define a Cucumber step to confirm the address
    @And("the user confirms the address")
    public void theUserConfirmsTheAddress() {

        // Find the "Confirm Addresses" button on the web page using its name attribute, and click it
        driver.findElement(By.name("confirm-addresses")).click();
    }

    // Define a Cucumber step to select the "pick up in store" option
    @And("the user selects the Pick up in store option")
    public void theUserSelectsThePickUpInStoreOption() {

        // Find the "Pick up in store" option on the web page using its ID attribute
        WebElement pickUpInStore = driver.findElement(By.id("delivery_option_8"));

        // Find the "Confirm" button on the web page using its name attribute
        WebElement confirmBtn = driver.findElement(By.name("confirmDeliveryOption"));

        // If the "Pick up in store" option is already selected, click the "Confirm" button
        if (pickUpInStore.isSelected()){
            confirmBtn.click();
        }else{

            // If the "Pick up in store" option is not selected, click it and then click the "Confirm" button
            pickUpInStore.click();
            confirmBtn.click();
        }
    }

    // Define a Cucumber step to select the "Pay by Check" option
    @And("the user selects Pay by Check option")
    public void theUserSelectsPayByCheckOption() {

        // Find the "Pay by Check" option on the web page using its name attribute
        WebElement payByCheck = driver.findElement(By.name("payment-option"));

        // Click the "Pay by Check" option
        payByCheck.click();
    }

    // Cucumber step to confirm all terms and confirm
    @And("the user clicks Order with an obligation to pay")
    public void theUserClicksOrderWithAnObligationToPay() {

        // Find the checkbox for agreeing to the terms and conditions and click it
        driver.findElement(By.id("conditions_to_approve[terms-and-conditions]")).click();

        // Find the "Order with an obligation to pay" button on the web page using a CSS selector and click it
        driver.findElement(By.cssSelector("#payment-confirmation button[type='submit']")).click();
    }

    // Cucumber step to take a screenshot of the order confirmation page with the total amount
    @And("the user takes a screenshot of the order confirmation with the total amount")
    public void theUserTakesAScreenshotOfTheOrderConfirmationWithTheTotalAmount() {

        // Extract the reference number from the element
        WebElement referenceElement = driver.findElement(By.id("order-reference-value"));
        String referenceText = referenceElement.getText();

        // Assign the clean reference number (last 9 chars) to the global variable
        this.expectedReferenceNumber = referenceText.substring(referenceText.length()-9);

        // Extract the amount of EUR from the element and assign to global variable
        this.expectedTotalAmount = driver.findElement(By.cssSelector("tr.total-value td:nth-child(2)")).getText();

        // Wait for 1 second to allow time for the page to fully load
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        // Use reference number of the current order as a name for the screenshot
        String cleanName = expectedReferenceNumber;

        // Define the file path and name for the screenshot
        String screenPath = "src/test/java/pl/mystore/screenshots/"+cleanName+".png";

        // Take a screenshot using the TakesScreenshot interface
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        // Save the screenshot to the specified file path and name
        screenshot.renameTo(new File(screenPath));
    }

    // Cucumber step navigates the user to the order history page.
    @And("the user navigates to order history and details page")
    public void theUserNavigatesToOrderHistoryAndDetailsPage() {

        // Find and click the "My Account" link
        driver.findElement(By.cssSelector("a.account")).click();

        // Find and click the "Order history" link
        driver.findElement(By.id("history-link")).click();
    }

    // Cucumber step checks if there is reference number of ongoing order is in the history of orders and checks the order details
    @Then("the order with {string} status and the same total amount is displayed in the list")
    public void theOrderWithStatusAndTheSameTotalAmountIsDisplayedInTheList(String requiredOrderStatus) {

        // Find the first row of the table
        List<WebElement> orderRows = driver.findElements(By.cssSelector("table tbody tr"));

        // Flag to keep track if the expected reference number is found or not
        boolean referenceFound = false;

        // Loop through each row of the order history table
        for (int i = 0; i < orderRows.size(); i++ ){

            // Extract the order number, payment amount, and status from the current row
            String orderNumber = orderRows.get(i).findElement(By.tagName("th")).getText();
            String paymentAmount = orderRows.get(i).findElements(By.tagName("td")).get(1).getText();
            String status = orderRows.get(i).findElements(By.tagName("td")).get(3).getText();

            // If the current row matches the expected reference number
            if (expectedReferenceNumber.equals(orderNumber)){

                // Check if the payment amount and status of the current row also match the expected values
                if (expectedTotalAmount.equals(paymentAmount)){

                    if (status.equals(requiredOrderStatus)){

                        // Print out the matching details and set the referenceFound flag to true
                        System.out.println("Expected reference number: "+ expectedReferenceNumber +"; Reference found in Order history: "+orderNumber);
                        System.out.println("Expected amount: "+ expectedTotalAmount +"; Amount found in Order history: "+paymentAmount);
                        System.out.println("Expected status: "+requiredOrderStatus+"; Status found in Order history: "+status);

                        referenceFound = true;

                        //exit a loop
                        break;

                    }else {

                        // Throw an AssertionError if the status does not match the expected value
                        throw new AssertionError("Expected status is: "+requiredOrderStatus+ "Actual status:"+status);
                    }

                }else {

                    // Throw an AssertionError if the payment amount does not match the expected value
                    throw new AssertionError("Expected amount: "+ expectedTotalAmount +" Actual amount: "+paymentAmount);
                }

            } else if (i == orderRows.size()-1 && !referenceFound) {

                // Throw an AssertionError if the expected reference number is not found in the order history table
                throw new AssertionError("No expected reference number: "+ expectedReferenceNumber +" is found");
            }
        }
    }
}
