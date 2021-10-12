import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StepDefinitions {

    private final WebDriver driver = new FirefoxDriver();

    @Given("User is on the {string} page")
    public void iAmOnThePage(String arg0) {
        driver.get("http://localhost:4200/");
    }
}
