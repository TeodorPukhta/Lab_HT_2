package SmokeTests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.util.concurrent.TimeUnit;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.testng.AssertJUnit.assertTrue;

public class SmokeTest {
    private WebDriver webDriver;
    public static final String HOME_PAGE_URL = "https://rozetka.com.ua/ua/";
    public static final String COMPUTERS_CATEGORY = "//a[@class='menu-categories__link'][contains(@href,'computers')]";
    public static final String COMPUTER_BRAND_CATEGORY = "a[href='/producer/asus/']";
    public static final String SORT_FILTER_BUTTON = "//select[@class='select-css ng-untouched ng-pristine ng-valid ng-star-inserted']";
    public static final String CSS_SORT_FROM_EXPENSIVE = "option[value='3: expensive']";
    public static final String ICON_BUY_ITEM = "//button[contains(@class,'buy-button')]";
    public static final String TAGNAME_IMG = "img";

    public static final String PATRICALLINK_REPAIR_CATEGORY = "та ремонт";
    public static final String CLASS_REPAIR_TOOL = "tile-cats";
    public static final String CSS_SORT_ACTION = "option[value='5: action']";

    public static final String PATRICALLINK_TV_CATEGORY = ", ТВ ";
    public static final String CSS_BRAND_CATEGORY = "a[href='/producer/sony/']";
    public static final String CSS_SORT_FROM_CHEAP = "option[value='2: cheap']";
    public static final String BASKET_ICON = "//li[contains(@class,'item--cart')]";
    public static final String SUM_PRICE_TEXT = "//div[@class='cart-receipt__sum-price']//span[not(@*)]";
    public static final int EXPECTED_AMOUNT = 150000;

    @BeforeTest
    public void profileSetUp() {
        chromedriver().setup();
    }

    @BeforeMethod
    public void testSetUp() {
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void checkThatBucketContainsProductsAboveExpectedPrice(){
        webDriver.get(HOME_PAGE_URL);
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(COMPUTERS_CATEGORY)));
        webDriver.findElement(By.xpath(COMPUTERS_CATEGORY)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(COMPUTER_BRAND_CATEGORY)));
        webDriver.findElement(By.cssSelector(COMPUTER_BRAND_CATEGORY)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SORT_FILTER_BUTTON)));
        webDriver.findElement(By.xpath(SORT_FILTER_BUTTON)).click();
        webDriver.findElement(By.cssSelector(CSS_SORT_FROM_EXPENSIVE)).click();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        webDriver.findElements(By.xpath(ICON_BUY_ITEM)).get(0).click();
        webDriver.findElements(By.tagName(TAGNAME_IMG)).get(1).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(PATRICALLINK_REPAIR_CATEGORY)));
        webDriver.findElement(By.partialLinkText(PATRICALLINK_REPAIR_CATEGORY)).click();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        WebElement element1 = webDriver.findElements(By.className(CLASS_REPAIR_TOOL)).get(2);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element1);
        element1.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SORT_FILTER_BUTTON)));
        webDriver.findElement(By.xpath(SORT_FILTER_BUTTON)).click();
        webDriver.findElement(By.cssSelector(CSS_SORT_ACTION)).click();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement element2 = webDriver.findElements(By.xpath(ICON_BUY_ITEM)).get(0);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element2);
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        element2.click();
        webDriver.findElements(By.tagName(TAGNAME_IMG)).get(1).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(PATRICALLINK_TV_CATEGORY)));
        webDriver.findElement(By.partialLinkText(PATRICALLINK_TV_CATEGORY)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_BRAND_CATEGORY)));
        webDriver.findElement(By.cssSelector(CSS_BRAND_CATEGORY)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SORT_FILTER_BUTTON)));
        webDriver.findElement(By.xpath(SORT_FILTER_BUTTON)).click();
        webDriver.findElement(By.cssSelector(CSS_SORT_FROM_CHEAP)).click();
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(ICON_BUY_ITEM)));
        webDriver.findElements(By.xpath(ICON_BUY_ITEM)).get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(BASKET_ICON)));
        webDriver.findElement(By.xpath(BASKET_ICON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SUM_PRICE_TEXT)));
        assertTrue(EXPECTED_AMOUNT < Integer.parseInt(webDriver.findElement(By.xpath(SUM_PRICE_TEXT)).getText()));
    }

    @AfterMethod
    public void tearDown() {
        webDriver.close();
    }
}
