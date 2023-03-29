package com.library.steps;

import com.library.pages.*;
import com.library.utility.BrowserUtil;
import com.library.utility.ConfigurationReader;
import com.library.utility.Driver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;

import java.sql.*;

public class US07_StepDefs_Gianpaulo {
    LoginPage loginPage = new LoginPage();


    BasePage basePage = new BookPage();

    BorrowedBooksPage borrowedBooksPage = new BorrowedBooksPage();

    DashBoardPage dashBoardPage = new DashBoardPage();

    @Given("the {string} is on the home page")
    public void theIsOnTheHomePage(String student) {
        Driver.getDriver().get("https://library2.cydeo.com/login.html");
        loginPage.emailBox.sendKeys("student38@library");
        loginPage.passwordBox.sendKeys("libraryUser");
        loginPage.loginButton.click();
    }

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        dashBoardPage.navigateModule(moduleName);


    }
    @And("the user searches book name called {string}")
    public void theUserSearchesBookNameCalled(String Books) {
        Books = "Head First Java";
        basePage.SearchBar.sendKeys(Books, Keys.ENTER);
    }

    @When("the user clicks Borrow Book")
    public void theUserClicksBorrowBook() {
        BrowserUtil.waitFor(2);
        basePage.BorrowBookButton.click();
        Assert.assertTrue(basePage.message.isDisplayed());
    }

    @Then("verify that book is shown in {string} page")
    public void verifyThatBookIsShownInPage(String arg0) {
        BrowserUtil.waitFor(2);
        basePage.BorrowingBooksPage.click();
    }

    @And("verify logged student has same book in database")
    public void verifyLoggedStudentHasSameBookInDatabase() throws SQLException {
        String expectedOutput = "NOT RETURNED";
        String actualOutput = borrowedBooksPage.NotReturnedMessage.getText();
        Assert.assertEquals(expectedOutput, actualOutput);

        Connection conn = DriverManager.getConnection(ConfigurationReader.getProperty("library2.db.url"), ConfigurationReader.getProperty("library2.db.username"), ConfigurationReader.getProperty("library2.db.password"));
        Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = statement.executeQuery("select full_name, b.name, bb.borrowed_date FROM users u inner join book_borrow bb on u.id = bb.user_id inner join books b on bb.book_id = b.id where full_name = 'Test Student 38' order by  3 desc");
        ResultSetMetaData rsmd = rs.getMetaData();
        rs.next();
        String ActualBook = rs.getString(2);
        String expectedBook = "Head First Java";
        Assert.assertEquals(expectedBook, ActualBook);
    }


}

