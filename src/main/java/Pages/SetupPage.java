package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SetupPage {
    @FindBy(xpath = "//input[@placeholder = 'Quick Find']")
    public WebElement search_setupQuickFind;

    @FindBy(xpath = "//a[@title='Object Manager' and @aria-selected='false']")
    public WebElement btn_ObjectManager;

    @FindBy(xpath = "//ul[@role='group']/li/div[@title='Users']/a")
    public WebElement link_Users;





}
