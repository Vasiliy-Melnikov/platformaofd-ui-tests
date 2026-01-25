package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:config/${env}.properties"
})
public interface WebDriverConfig extends Config {

    @Key("baseUrl")
    @DefaultValue("https://platformaofd.ru")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("browserVersion")
    @DefaultValue("")
    String browserVersion();

    @Key("browserSize")
    @DefaultValue("1920x1080")
    String browserSize();

    @Key("pageLoadStrategy")
    @DefaultValue("eager")
    String pageLoadStrategy();

    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();

    @Key("remoteUrl")
    URL remoteUrl();

    @Key("enableVNC")
    @DefaultValue("false")
    boolean enableVNC();

    @Key("enableVideo")
    @DefaultValue("false")
    boolean enableVideo();
}
