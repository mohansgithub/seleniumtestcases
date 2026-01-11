package tests;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.chrome.ChromeOptions;

public class SauceDemoTests {
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void setup() {
		ChromeOptions options = new ChromeOptions();
	//	options.addArguments("--headless=new");
		options.addArguments("--disable-gpu");
		options.addArguments("--window-size=1920,1080");
		driver = new ChromeDriver(options);
		wait = new WebDriverWait(driver,Duration.ofSeconds(20));
		driver.get("https://www.saucedemo.com/");
	
	}
    @Test
    public void openWebsite() {
        // Set path if required, else use WebDriverManager
        
    driver.findElement(By.id("user-name")).sendKeys("standard_user");
    driver.findElement(By.id("password")).sendKeys("secret_sauce");
    driver.findElement(By.id("login-button")).click();
    try
    {
    WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-test='title']")));
    Assert.assertTrue(we.isDisplayed(),"Element is visible test passed");
    }
    catch(TimeoutException te)
    {
    	System.out.println("Label Products not found until 10 seconds");
    	Assert.fail("Login page not found within 10 seconds hence failing test case");
    }
    }
    
    @Test(dependsOnMethods={"openWebsite"})
    public void addItemsToCart() throws InterruptedException {
    	//Thread.sleep(20000);
    	driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
    	driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
    	driver.findElement(By.xpath("//div[@class='inventory_item_name' and @data-test='inventory-item-name']"));
    	try {
    	WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inventory_item_name' and @data-test='inventory-item-name']")));
    	//WebElement we = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inventory_item_name' and text()='Sauce Labs Backpack']")));

			Assert.assertTrue(we.isDisplayed(),"Item added to cart");
    	}
    	catch(TimeoutException te) {
    		System.out.println("item not added to cart");
    		Assert.fail("Item not added to cart");
    	}
    }
        @AfterMethod
    public void takeScreenshot(ITestResult result) {
        // take screenshot if test failed
        if (!result.isSuccess()) {
        	ScreenshotUtil.takeScreenshot(driver, result.getName() + "_Failed");
        }
        // always capture last screen
        ScreenshotUtil.takeScreenshot(driver, result.getName() + "_LastScreen");
        
    }
	
    @AfterClass
    public void tearDown() throws InterruptedException {
    	Thread.sleep(2000);
    	driver.quit();
    }
}
