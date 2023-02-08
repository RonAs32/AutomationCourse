package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
    @FindBy(id="username")
    public WebElement txt_username;

    @FindBy(id="password")
    public WebElement txt_password;

    @FindBy(id="Login")
    public WebElement btn_login;

}
