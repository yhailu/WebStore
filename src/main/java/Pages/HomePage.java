package Pages;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseTest {

    public String expectedHomepageURL = prop.getProperty("url");

    @FindBy(xpath = "//*[@id=\"searchval\"]")
    private WebElement searchForm;

    @FindBy(xpath = "//*[@id=\"searchForm\"]/div/button")
    private WebElement searchButton;
    public HomePage() {
        PageFactory.initElements(driver, this);
    }

    public String isHomePageDisplayed() {
        return driver.getCurrentUrl();
    }

    public SearchResultsPage searchForItem(String keyword){
        this.searchForm.sendKeys(keyword);
        this.searchButton.click();
        return new SearchResultsPage(keyword);
    }


}