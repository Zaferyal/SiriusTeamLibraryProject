package com.library.steps;

import com.library.pages.*;
import com.library.utility.ConfigurationReader;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US03_Steps_Brahim {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String string) {
        Driver.getDriver().get(ConfigurationReader.getProperty("library_url"));
        loginPage.emailBox.sendKeys(ConfigurationReader.getProperty("librarian_username"));
        loginPage.passwordBox.sendKeys(ConfigurationReader.getProperty("librarian_password"));
        loginPage.loginButton.click();
    }
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
        bookPage.mainCategoryElement.click();
    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {
        DB_Util.createConnection();
        DB_Util.runQuery("select * from book_categories");
        String actual = bookPage.mainCategoryElement.getText();
        String expected = ""+DB_Util.getColumnDataAsList(3);
        Assert.assertEquals(actual,expected);
        DB_Util.destroy();


    }
}
