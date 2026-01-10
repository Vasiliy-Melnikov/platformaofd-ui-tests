package tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.HomePage;

@Epic("Platforma OFD Marketing Site")
@Feature("Главная страница")
public class HomePageTests extends TestBase {

    private final HomePage homePage = new HomePage();

    @Test
    @Story("Smoke: открытие главной")
    @DisplayName("Главная страница открывается, хедер отображается")
    void mainPageOpens() {
        homePage.openMainPage()
                .headerShouldBeVisible()
                .bodyShouldContainHeroTexts();
    }

    @Test
    @Story("Навигация по хедеру")
    @DisplayName("Хедер содержит основные пункты меню")
    void headerContainsMainMenuItems() {
        homePage.openMainPage()
                .headerShouldContainMenuItems();
    }

    @Test
    @Story("Навигация внутри главной страницы")
    @DisplayName("Кнопка «Выбрать сервис» ведет на страницу «Сервисы»")
    void chooseServiceOpensServicesPage() {
        homePage.openMainPage()
                .clickChooseService()
                .shouldBeOnServicesPage();
    }

    @Test
    @Story("Контент блока сервисов")
    @DisplayName("В блоке «Сервисы» есть оператор фискальных данных")
    void servicesBlockContainsOfdCard() {
        homePage.openMainPage()
                .servicesBlockShouldContainOfdCard();
    }

    @Test
    @Story("Блок Базы знаний")
    @DisplayName("Блок «База знаний» содержит основные разделы")
    void knowledgeBaseBlockContainsSections() {
        homePage.openMainPage()
                .knowledgeBaseBlockShouldContainSections();
    }

    @Test
    @Story("Переход в базу знаний")
    @DisplayName("Клик по «Перейти в Базу знаний» открывает страницу БЗ")
    void openKnowledgeBasePage() {
        homePage.openMainPage()
                .clickGoToKnowledgeBase()
                .shouldBeOnKnowledgeBasePage();
    }

    @Test
    @Story("Блок новостей")
    @DisplayName("В блоке «Новости» есть хотя бы одна дата в формате dd.MM.yyyy")
    void newsBlockHasAnyDatedNews() {
        homePage.openMainPage()
                .newsBlockShouldHaveAnyDateDdMmYyyy();
    }

    @Test
    @Story("Переход к новости")
    @DisplayName("Клик по заголовку первой новости открывает страницу новости")
    void openFirstNewsDetails() {
        homePage.openMainPage();
        String title = homePage.clickFirstVisibleNewsAndGetTitle();
        homePage.openedNewsPageShouldContainTitle(title);
    }

    @Test
    @Story("Футер – контакты")
    @DisplayName("Футер содержит телефон, email и адрес компании")
    void footerContainsContacts() {
        homePage.openMainPage()
                .footerShouldBeVisible()
                .footerShouldContainContacts();
    }

    @Test
    @Story("Футер – параметры подключения кассы")
    @DisplayName("В футере указаны URL/IP/порт для подключения кассы")
    void footerContainsKktConnectionParams() {
        homePage.openMainPage()
                .footerShouldBeVisible()
                .footerShouldContainKktParams();
    }
}