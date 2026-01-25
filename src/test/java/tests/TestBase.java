package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverConfig;
import config.WebDriverConfigProvider;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.sessionId;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void setupSelenideConfig() {
        WebDriverConfig cfg = WebDriverConfigProvider.get();

        Configuration.baseUrl = cfg.baseUrl();
        Configuration.browser = cfg.browser();
        Configuration.browserSize = cfg.browserSize();
        Configuration.pageLoadStrategy = cfg.pageLoadStrategy();

        if (!cfg.browserVersion().isBlank()) {
            Configuration.browserVersion = cfg.browserVersion();
        }

        if (cfg.isRemote()) {
            Configuration.remote = cfg.remoteUrl().toString();
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", cfg.enableVNC(),
                    "enableVideo", cfg.enableVideo()
            ));
            Configuration.browserCapabilities = capabilities;
        }
    }
    @BeforeEach
    void addAllureListener() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
        );
    }

    @AfterEach
    void addAttachments() {
        String sId = sessionId() != null ? sessionId().toString() : "no-session";
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        closeWebDriver();
        Attach.addVideo(sId);
    }
}
