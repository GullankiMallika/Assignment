package Tests;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.CsvWriter;
import utils.TestLogger;

import java.io.IOException;
import java.util.List;

public class EcommerceTests extends TestBase {

    @Test(priority = 1)
    public void testCrawlProducts() throws IOException, InterruptedException {
        driver.get("https://www.amazon.in");

        // Search for "laptop"
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("laptop");
        searchBox.submit();
        Thread.sleep(3000);

        // Extract products
        List<WebElement> products = driver.findElements(By.cssSelector(".s-main-slot .s-result-item"));
        CsvWriter csvWriter = new CsvWriter("outputs/products.csv");

        for (WebElement product : products) {
            try {
                String name = product.findElement(By.cssSelector("h2")).getText();
                String price = product.findElement(By.cssSelector(".a-price-whole")).getText();
                String rating = product.findElement(By.cssSelector(".a-icon-alt")).getAttribute("innerHTML");
                String url = product.findElement(By.cssSelector("h2 a")).getAttribute("href");
                csvWriter.writeProduct(name, price, rating, url);
            } catch (Exception e) {
                // Ignore products with incomplete details
            }
        }

        csvWriter.close();
    }

    @Test(priority = 2)
    public void testValidateProductPage() throws InterruptedException {
        // Example product URL
        String productUrl = "https://www.amazon.in/dp/exampleProduct";
        driver.get(productUrl);
        Thread.sleep(3000);

        // Validate Add to Cart button
        boolean isAddToCartPresent = driver.findElements(By.id("add-to-cart-button")).size() > 0;
        TestLogger.logTestResult("Add to Cart Button Validation", isAddToCartPresent);
        Assert.assertTrue(isAddToCartPresent, "Add to Cart button is missing!");

        // Validate Product Details
        boolean isProductDetailsPresent = driver.findElements(By.id("productDescription")).size() > 0;
        TestLogger.logTestResult("Product Description Validation", isProductDetailsPresent);
        Assert.assertTrue(isProductDetailsPresent, "Product description is missing!");

        // Validate Image Gallery
        boolean isImageGalleryPresent = driver.findElements(By.id("imageBlockContainer")).size() > 0;
        TestLogger.logTestResult("Image Gallery Validation", isImageGalleryPresent);
        Assert.assertTrue(isImageGalleryPresent, "Image gallery is missing!");
    }
}