package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    private final SelenideElement header = $("header");
    private final SelenideElement chooseServiceButton =
            $x("//a[contains(.,'Выбрать сервис')]");
    private final SelenideElement servicesBlock =
            $x("//h2[contains(.,'Сервисы')]");
    private final SelenideElement knowledgeBaseBlock =
            $x("//h2[contains(.,'База знаний')]");
    private final SelenideElement newsBlock =
            $x("//h2[contains(.,'Новости')]");
    private final SelenideElement footer = $("footer");

    private final SelenideElement cookieAcceptButton =
            $x("//div[contains(@class,'l-cookie')]//button");

    public HomePage openMainPage() {
        open("/");
        if (cookieAcceptButton.exists()) {
            cookieAcceptButton.click();
        }
        return this;
    }

    public SelenideElement getHeader() {
        return header;
    }

    public SelenideElement getChooseServiceButton() {
        return chooseServiceButton;
    }

    public SelenideElement getServicesBlock() {
        return servicesBlock;
    }

    public SelenideElement getKnowledgeBaseBlock() {
        return knowledgeBaseBlock;
    }

    public SelenideElement getFooter() {
        return footer;
    }
}