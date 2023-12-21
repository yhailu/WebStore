package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class SearchResultsPage extends BaseTest {

    @FindBy(css = "input[data-testid='itemAddCart']")
    List<WebElement> itemAddCartDiv;

    @FindBy(css = ".btn-container")
    List<WebElement> itemCartButtons;

    @FindBy(css = "a[href='/viewcart.cfm']")
    WebElement viewCartButton;
    String search_url;
    @FindBy(xpath = "//*[@id=\"ProductBoxContainer\"]/div[1]/a/span")
    List<WebElement> products;

    @FindBy(css="[aria-label*='last page']")
    WebElement lastPageElement;
    public SearchResultsPage(String search_term) {
        PageFactory.initElements(driver, this);
        this.search_url = prop.getProperty("url") + "search/" + search_term.replaceAll("\\s", "-") +".html";

    }

    public SearchResultsPage search(int page){
        driver.get(this.search_url + "?page=" + page);
        return this;
    }

    public List<String> getResultsTitles(){
        List<String> itemTitles = new ArrayList<>();
        for (WebElement element : this.products) {
            itemTitles.add(element.getText());
        }
        return itemTitles;
    }



    public SearchResultsPage addLastItemToCart() {
        List<WebElement> nestedDropDown =
                this.itemAddCartDiv.get(this.itemAddCartDiv.size() - 1).findElements(By.tagName("select"));
        if (!nestedDropDown.isEmpty()) {
            new Select(nestedDropDown.get(0)).selectByIndex(1);
        }
        this.itemCartButtons.get(this.itemCartButtons.size() - 1).click();
        return this;
    }

    public CartPage goToCart() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.elementToBeClickable(new By.ByCssSelector("a[href='/viewcart.cfm']")));
        WebElement viewCartButton = driver.findElement(new By.ByCssSelector("a[href='/viewcart.cfm']"));
        viewCartButton.click();
        return new CartPage();
    }

    public int lastPage(){
        int lastPage = Integer.parseInt(this.lastPageElement.getText());
        System.out.println("Last page of searchResultsPage: " + lastPage);
        return lastPage;
    }

}
