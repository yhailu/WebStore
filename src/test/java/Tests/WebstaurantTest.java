package Tests;
import Base.BaseTest;
import Pages.CartPage;
import Pages.HomePage;
import Pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class WebstaurantTest extends BaseTest {
    @Test(priority = 1, enabled = true)
    public void testWebstaurantSearchAndCart() {
        Reporter.log("Webstaurant Code Screen Task..", true);

        //Initialize Webstaurant HomePage and Assert HomePage is displayed
        HomePage homepage = new HomePage();
        Assert.assertEquals(homepage.isHomePageDisplayed(), homepage.expectedHomepageURL);

        //Search for keyword and Assert each Product title contains "Table"
        SearchResultsPage searchResultsPage = new SearchResultsPage("stainless work table");
        searchResultsPage.search(1);

        //Get last page number to iterate through pages for Title assertions
        int lastPage = searchResultsPage.lastPage();

        for (int i = 1; i <= lastPage; i++) {
            SearchResultsPage search;
            System.out.println("Testing Page #" + i);

            //Do re-search in loop just reuse old search that you used to get lastPage
            if(i == 1) {
                 search = searchResultsPage;
            }
            else {
                search = searchResultsPage.search(i);
            }

            List<String> itemTitles= search.getResultsTitles();
            System.out.println("# of products on this page: " + itemTitles.size());
            System.out.println(itemTitles);

            //Test products have Table in title on each page
            itemTitles.forEach(title -> {
                softAssert.assertTrue(title.contains("Table"), String.format("Expected: found item with title=%s but doesn't contain=> %s", title, "Table"));
            });

            //On last page get the last item - addToCart - then emptyCart
            if (i == lastPage){
                System.out.println("We are on the last page! --- adding last item to cart");
                //Add Last Product from search results then empty the cart
                CartPage cartPage = search.addLastItemToCart().goToCart();
                cartPage.emptyCart();

            }
        }












    }
}
