package Pages;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CartPage extends BaseTest {
    @FindBy(css = "button.emptyCartButton")
    WebElement emptyCartButton;

    @FindBy(css = ".ReactModalPortal footer button:first-of-type")
    WebElement emptyCartModal;


    public CartPage(){
        PageFactory.initElements(driver, this);
    }

    public CartPage emptyCart() {
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(new By.ByCssSelector("button.emptyCartButton")));
        this.emptyCartButton.click();
        this.emptyCartModal.click();
        new WebDriverWait(driver, Duration.ofSeconds(60))
                .ignoring(WebDriverException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(new By.ByCssSelector(".cartEmpty .empty-cart__text")));
        return this;
    }

}
