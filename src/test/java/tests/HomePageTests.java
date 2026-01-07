package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Platforma OFD Marketing Site")
@Feature("Главная страница")
public class HomePageTests extends TestBase {

    private final HomePage homePage = new HomePage();

    @Test
    @Story("Smoke: открытие главной")
    @DisplayName("Главная страница открывается, хедер отображается")
    void mainPageOpens() {
        homePage.openMainPage();
        homePage.getHeader().shouldBe(visible);
        $("body").shouldHave(text("Умные"), text("решения"));
        $("body").shouldHave(text("8 (495) 252-50-50"));
    }

    @Test
    @Story("Навигация по хедеру")
    @DisplayName("Хедер содержит основные пункты меню")
    void headerContainsMainMenuItems() {
        homePage.openMainPage();
        $("header").shouldHave(
                text("Сервисы"),
                text("Тарифы"),
                text("База знаний"),
                text("Рынкам"),
                text("Новым регионам"),
                text("О нас"),
                text("Акции")
        );
    }

    @Test
    @Story("Навигация внутри главной страницы")
    @DisplayName("Кнопка «Выбрать сервис» ведет на страницу «Сервисы»")
    void chooseServiceOpensServicesPage() {
        homePage.openMainPage();
        homePage.getChooseServiceButton().click();
        webdriver().shouldHave(urlContaining("/services"));
        $("h1, h2").shouldHave(text("Сервисы"));
    }

    @Test
    @Story("Контент блока сервисов")
    @DisplayName("В блоке «Сервисы» есть оператор фискальных данных")
    void servicesBlockContainsOfdCard() {
        homePage.openMainPage();
        homePage.getServicesBlock().parent().shouldHave(
                text("оператор фискальных"),
                text("данных")
        );
    }

    @Test
    @Story("Блок Базы знаний")
    @DisplayName("Блок «База знаний» содержит основные разделы")
    void knowledgeBaseBlockContainsSections() {
        homePage.openMainPage();
        homePage.getKnowledgeBaseBlock().shouldBe(visible);
        $("body").shouldHave(
                text("Кассы, чеки"),
                text("Личный кабинет"),
                text("ОФД"),
                text("ЭДО"),
                text("Отчётность"),
                text("Маркировка")
        );
    }

    @Test
    @Story("Переход в базу знаний")
    @DisplayName("Клик по «Перейти в Базу знаний» открывает страницу БЗ")
    void openKnowledgeBasePage() {
        homePage.openMainPage();
        $x("//a[contains(.,'Перейти в Базу знаний')]").click();
        webdriver().shouldHave(urlContaining("baza-znaniy"));
        $("body").shouldHave(text("База знаний"));
    }

    @Test
    @Story("Блок новостей")
    @DisplayName("В блоке «Новости» есть хотя бы одна дата в формате dd.MM.yyyy")
    void newsBlockHasAnyDatedNews() {
        homePage.openMainPage();
        String newsBlockText = $x("//h2[.='Новости']/parent::*").getText();
        System.out.println("News block text:\n" + newsBlockText);
        Pattern datePattern = Pattern.compile("\\b\\d{2}\\.\\d{2}\\.\\d{4}\\b");
        Matcher matcher = datePattern.matcher(newsBlockText);

        assertThat(matcher.find())
                .as("Ожидаем, что в блоке «Новости» есть хотя бы одна дата в формате dd.MM.yyyy")
                .isTrue();
    }

    @Test
    @Story("Переход к новости")
    @DisplayName("Клик по заголовку первой новости открывает страницу новости")
    void openFirstNewsDetails() {
        homePage.openMainPage();
        SelenideElement firstVisibleNewsLink = $$x("//h2[.='Новости']/following::h2/a")
                .findBy(visible);
        String title = firstVisibleNewsLink.getText();
        firstVisibleNewsLink.scrollIntoView(true).click();
        $$("h1, h2").findBy(text(title)).shouldBe(visible);
    }

    @Test
    @Story("Футер – контакты")
    @DisplayName("Футер содержит телефон, email и адрес компании")
    void footerContainsContacts() {
        homePage.openMainPage();
        homePage.getFooter().scrollTo().shouldBe(visible);
        homePage.getFooter().shouldHave(
                text("8 (495) 252-50-50"),
                text("info@platformaofd.ru"),
                text("Москва, ул. Усачёва, д. 33, стр. 1")
        );
    }

    @Test
    @Story("Футер – параметры подключения кассы")
    @DisplayName("В футере указаны URL/IP/порт для подключения кассы")
    void footerContainsKktConnectionParams() {
        homePage.openMainPage();
        homePage.getFooter().scrollTo().shouldBe(visible);
        homePage.getFooter().shouldHave(
                text("Параметры подключения кассы"),
                text("URL: ofdp.platformaofd.ru"),
                text("IP: 185.170.204.91"),
                text("Порт: 21101")
        );
    }
}