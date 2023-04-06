package com.library.steps;

import com.library.pages.BookPage;
import com.library.pages.DashBoardPage;
import com.library.pages.LoginPage;
import com.library.utility.BrowserUtil;
import com.library.utility.DB_Util;
import com.library.utility.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.eo.Se;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class US06_StepDefs_nara {
String librarian_username;
String librarian_password;
String library_url;
    LoginPage loginPage = new LoginPage();
    BookPage bookPage=new BookPage();
    DashBoardPage dashBoardPage=new DashBoardPage();




    @Given("the {string} on the home page")
    public void the_on_the_home_page(String librarian) {

       // Driver.getDriver().get("https://library2.cydeo.com/login.html");

       loginPage.login(librarian);

        BrowserUtil.waitFor(2);

       /* loginPage.login(librarian_username,librarian_password);
        BrowserUtil.waitFor(4);*/
      //  Driver.getDriver().get("https://library2.cydeo.com/login.html");

      /*  loginPage.emailBox.sendKeys("librarian43@library");
        loginPage.passwordBox.sendKeys("libraryUser");
        loginPage.loginButton.click();*/


    }
    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {

        dashBoardPage.Books.click();
        BrowserUtil.waitFor(2);



    }
    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String string) {

        BrowserUtil.waitForClickablility(bookPage.addBook,4);

      //  BrowserUtil.waitFor(4);

        bookPage.addBook.click();

       bookPage.bookName.sendKeys(string);

    }
    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String string2) {

        bookPage.isbn.sendKeys(string2);
    }
    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String string3) {

        bookPage.year.sendKeys(string3);
    }
    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String string4) {

        bookPage.author.sendKeys(string4);
    }
    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String string5) {
        Select Category=new Select(bookPage.categoryDropdown);
        Category.selectByVisibleText(string5);


    }
    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {

        BrowserUtil.waitFor(2);
       bookPage.saveChanges.click();

    }
    @Then("verify {string} message is displayed")
    public void verify_message_is_displayed(String string6) {



        WebElement successMessage = Driver.getDriver().findElement(By.xpath("//div[@role='alert']"));
        Assert.assertTrue(successMessage.isDisplayed());

    }
    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String string7) {



        DB_Util.runQuery("select name from books");

        List<String> expectedNameList = DB_Util.getColumnDataAsList(1);

        Assert.assertTrue(expectedNameList.contains(string7));


     /*   List<Map<String, String>> allRowAsListOfMap = DB_Util.getAllRowAsListOfMap();

        for (Map<String, String> rowMap : allRowAsListOfMap) {
            System.out.println(rowMap);
        }
*/
      //  List<ArrayList> expectedBooks=
        // Assert.assertTrue(allRowAsListOfMap.contains());






    }


}
