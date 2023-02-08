package Utilities;

import org.openqa.selenium.support.PageFactory;

public class PageManager extends Base{
        /*
        The PageManager is a class that holds all different pages we use in the project, every page
        needs his own driver to be initiated inorder to work, this class will control all pages initiated within
        the same driver (automated controlled browser)
     */

    public static void InitiatePages(){
        loginPage = PageFactory.initElements(driver, Pages.LoginPage.class);
        mainPage = PageFactory.initElements(driver, Pages.MainPage.class);
        setupPage = PageFactory.initElements(driver, Pages.SetupPage.class);

    }
}
