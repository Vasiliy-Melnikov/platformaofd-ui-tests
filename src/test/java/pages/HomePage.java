package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ClickOptions;

import java.time.Duration;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class HomePage {

    private final SelenideElement cookieAcceptButton =
            $x("//div[contains(@class,'l-cookie')]//button");

    private final SelenideElement body = $("body");
    private final SelenideElement header = $("header");

    private final SelenideElement chooseServiceButton =
            $x("//a[contains(.,'Выбрать сервис')]");

    private final SelenideElement servicesTitle =
            $x("//h2[contains(.,'Сервисы')]");

    private final SelenideElement knowledgeBaseTitle =
            $x("//h2[contains(.,'База знаний')]");

    private final SelenideElement goToKnowledgeBaseLink =
            $x("//a[contains(.,'Перейти в Базу знаний')]");

    private final SelenideElement newsTitle =
            $x("//h2[.='Новости']");

    private final SelenideElement newsBlock =
            $x("//h2[.='Новости']/parent::*");

    private final ElementsCollection newsLinks =
            $$x("//h2[.='Новости']/following::h2/a");

    private final SelenideElement footer = $("footer");

    public HomePage openMainPage() {
        open("/");
        acceptCookiesIfVisible();
        return this;
    }

    public HomePage clickChooseService() {
        chooseServiceButton.shouldBe(visible).click();
        return this;
    }

    private void hideChatWidgetIfExists() {
        executeJavaScript("""
            document.querySelector('jdiv.hoverArea__EiY98')?.remove();
            document.querySelectorAll('jdiv').forEach(e => e.style.display='none');
        """);
    }

    private void acceptCookiesIfVisible() {
        if (!cookieAcceptButton.exists()) return; // баннера нет — ок

        hideChatWidgetIfExists();

        try {
            cookieAcceptButton.shouldBe(visible, Duration.ofSeconds(5))
                    .shouldBe(enabled)
                    .scrollIntoView(true)
                    .click();
        } catch (Exception ignored) {
            hideChatWidgetIfExists();
            cookieAcceptButton.click(ClickOptions.usingJavaScript());
        }
    }

    public HomePage clickGoToKnowledgeBase() {
        goToKnowledgeBaseLink.shouldBe(visible).click();
        return this;
    }

    public String clickFirstVisibleNewsAndGetTitle() {
        SelenideElement link = newsLinks.findBy(visible);
        String title = link.getText();
        link.scrollIntoView(true).click();
        return title;
    }

    public HomePage headerShouldBeVisible() {
        header.shouldBe(visible);
        return this;
    }

    public HomePage bodyShouldContainHeroTexts() {
        body.shouldHave(text("Умные"), text("решения"));
        body.shouldHave(text("8 (495) 252-50-50"));
        return this;
    }

    public HomePage headerShouldContainMenuItems() {
        header.shouldHave(
                text("Сервисы"),
                text("Тарифы"),
                text("База знаний"),
                text("Рынкам"),
                text("Новым регионам"),
                text("О нас"),
                text("Акции")
        );
        return this;
    }

    public HomePage shouldBeOnServicesPage() {
        webdriver().shouldHave(urlContaining("/services"));
        $$("h1, h2").findBy(text("Сервисы")).shouldBe(visible);
        return this;
    }

    public HomePage servicesBlockShouldContainOfdCard() {
        servicesTitle.shouldBe(visible).parent().shouldHave(text("оператор фискальных данных"));
        return this;
    }

    public HomePage knowledgeBaseBlockShouldContainSections() {
        knowledgeBaseTitle.shouldBe(visible);
        body.shouldHave(
                text("Кассы, чеки"),
                text("Личный кабинет"),
                text("ОФД"),
                text("ЭДО"),
                text("Отчётность"),
                text("Маркировка")
        );
        return this;
    }

    public HomePage shouldBeOnKnowledgeBasePage() {
        webdriver().shouldHave(urlContaining("baza-znaniy"));
        body.shouldHave(text("База знаний"));
        return this;
    }

    public HomePage newsBlockShouldHaveAnyDateDdMmYyyy() {
        newsTitle.shouldBe(visible);
        String text = newsBlock.getText();
        Pattern datePattern = Pattern.compile("\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b");
        if (!datePattern.matcher(text).find()) {
            throw new AssertionError("В блоке «Новости» не найдена дата формата dd.MM.yyyy");
        }
        return this;
    }

    public HomePage openedNewsPageShouldContainTitle(String title) {
        $$("h1, h2").findBy(text(title)).shouldBe(visible);
        return this;
    }

    public HomePage footerShouldBeVisible() {
        footer.scrollTo().shouldBe(visible);
        return this;
    }

    public HomePage footerShouldContainContacts() {
        footer.shouldHave(
                text("8 (495) 252-50-50"),
                text("info@platformaofd.ru"),
                text("Москва, ул. Усачёва, д. 33, стр. 1")
        );
        return this;
    }

    public HomePage footerShouldContainKktParams() {
        footer.shouldHave(
                text("Параметры подключения кассы"),
                text("URL: ofdp.platformaofd.ru"),
                text("IP: 185.170.204.91"),
                text("Порт: 21101")
        );
        return this;
    }
}