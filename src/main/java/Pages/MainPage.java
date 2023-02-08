package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {
    @FindBy (className = "slds-icon-waffle")
    public WebElement btn_appLauncher;

    @FindBy (xpath="//input[@placeholder='Search apps and items...']")
    public WebElement txt_appLauncher;

    @FindBy (xpath="//p[@class='slds-truncate']/b")
    public WebElement link_appLauncherResult;

    @FindBy (xpath = "//lightning-icon[@class='slds-icon-utility-setup slds-button__icon slds-global-header__icon forceIcon slds-icon_container']")
    public WebElement btn_Setup;

    @FindBy (xpath = "//li[@data-aura-class='uiMenuItem onesetupSetupMenu Item']")
    public WebElement btn_SetupOption;

    @FindBy (className = "setupGear")
    public WebElement btn_setup;

    @FindBy (className = "userProfileCardTriggerRoot oneUserProfileCardTrigger")
    public WebElement btn_userProfile;

    @FindBy (xpath = "//a[text()='Settings']")
    public WebElement btn_profile_settings;

    @FindBy (xpath = "//input[@title='Search Setup']")
    public WebElement txt_SetupMainSearch;






}
