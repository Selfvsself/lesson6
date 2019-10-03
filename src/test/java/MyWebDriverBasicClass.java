import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;

public class MyWebDriverBasicClass {

    public WebDriver webDriver;
    public Wait<WebDriver> wait;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
        webDriver = new ChromeDriver();
        wait = new WebDriverWait(webDriver, 20, 1000);
        webDriver.manage().window().maximize();
    }

    @After
    public void close() {
        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        tabs.stream().forEach(tab -> {
            webDriver.switchTo().window(tab).close();
        });
    }

    public WebElement findAndClick(String xPath) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(webDriver.findElement(By.xpath(xPath))));
        element.click();
        return element;
    }

    public boolean checkPresentElementOnPage(String xPath) {
        return webDriver.findElements(By.xpath(xPath)).size() != 0;
    }

    public WebElement findElement(String xPath) {
        return webDriver.findElement(By.xpath(xPath));
    }

    public void checkValueElement(String xPath, String value) {
        Assert.assertEquals(findElement(xPath).getAttribute("value"), value);
    }

    public boolean waitPresenceElement(String xPath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        return webDriver.findElements(By.xpath(xPath)).size() != 0;
    }

    public void clickElementJavaScript(String xPath) {
        WebElement element = webDriver.findElement(By.xpath(xPath));
        JavascriptExecutor executor = (JavascriptExecutor) webDriver;
        executor.executeScript("arguments[0].click();", element);
    }

}
