package com.testinium.base;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.testinium.model.ElementInfo;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    private static BaseTest instance;
    private static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    private static final String ELEMENT_DIR = "src/test/resources/elements.properties";
    private static final ConcurrentHashMap<String, ElementInfo> ELEMENT_MAP = new ConcurrentHashMap<>();

    // Configurable parameters with defaults
    private static final String BROWSER = System.getProperty("browser", "chrome");
    private static final String PLATFORM = System.getProperty("platform", "local");
    private static final String GRID_URL = System.getProperty("gridUrl", "http://localhost:4444/wd/hub");
    private static final String APP_URL = System.getProperty("appUrl", "https://catchylabs-webclient.testinium.com/signIn");

    // **Singleton getInstance() metodu**
    public static BaseTest getInstance() {
        if (instance == null) {
            synchronized (BaseTest.class) {
                if (instance == null) {
                    instance = new BaseTest();
                }
            }
        }
        return instance;
    }

    // **WebDriver getDriver() metodu**
    public static WebDriver getDriver() {
        if (driver == null) {
            getInstance().initializeWebDriver();
        }
        return driver;
    }

    @BeforeScenario
    public void initializeTest() throws MalformedURLException {
        loadElementConfigurations();
        getDriver();
        configureBrowserSettings();
        navigateToApplication();
    }

    @AfterScenario
    public void terminateTest() {
        if (driver != null) {
            driver.quit();
            driver = null; // WebDriver'ı sıfırlayarak tekrar oluşturulmasını sağlıyoruz
            logger.info("Browser session terminated");
        }
    }

    private void initializeWebDriver() {
        if (PLATFORM.equalsIgnoreCase("remote")) {
            initializeRemoteDriver();
        } else {
            initializeLocalDriver();
        }
    }

    private void initializeLocalDriver() {
        switch (BROWSER.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver(configureChromeOptions());
                break;
            case "firefox":
                driver = new FirefoxDriver(configureFirefoxOptions());
                break;
            case "edge":
                driver = new EdgeDriver(configureEdgeOptions());
                break;
            case "safari":
                driver = new SafariDriver(configureSafariOptions());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + BROWSER);
        }
    }

    private void initializeRemoteDriver() {
        try {
            switch (BROWSER.toLowerCase()) {
                case "chrome":
                    driver = new RemoteWebDriver(new URL(GRID_URL), configureChromeOptions());
                    break;
                case "firefox":
                    driver = new RemoteWebDriver(new URL(GRID_URL), configureFirefoxOptions());
                    break;
                case "edge":
                    driver = new RemoteWebDriver(new URL(GRID_URL), configureEdgeOptions());
                    break;
                case "safari":
                    driver = new RemoteWebDriver(new URL(GRID_URL), configureSafariOptions());
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + BROWSER);
            }
        } catch (MalformedURLException e) {
            logger.error("Invalid GRID URL: {}", GRID_URL, e);
            throw new RuntimeException(e);
        }
    }

    private void configureBrowserSettings() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    private void navigateToApplication() {
        driver.get(APP_URL);
        logger.info("Navigated to: {}", APP_URL);
    }

    // Browser Options Configurations
    private ChromeOptions configureChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        return options;
    }

    private FirefoxOptions configureFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        return options;
    }

    private EdgeOptions configureEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--disable-notifications");
        return options;
    }

    private SafariOptions configureSafariOptions() {
        return new SafariOptions();
    }

    // Element Configuration Management
    private void loadElementConfigurations() {
        File[] elementFiles = new File(ELEMENT_DIR).listFiles((dir, name) -> name.endsWith(".json"));

        if (elementFiles == null || elementFiles.length == 0) {
            logger.warn("No element configuration files found in: {}", ELEMENT_DIR);
            return;
        }

        Gson jsonParser = new Gson();
        Type elementType = new TypeToken<List<ElementInfo>>() {}.getType();

        for (File file : elementFiles) {
            try (FileReader reader = new FileReader(file)) {
                List<ElementInfo> elements = jsonParser.fromJson(reader, elementType);
                elements.forEach(element -> ELEMENT_MAP.put(element.getKey(), element));
                logger.info("Loaded {} elements from {}", elements.size(), file.getName());
            } catch (Exception e) {
                logger.error("Error loading element configurations: {}", e.getMessage());
            }
        }
    }

    public static ElementInfo getElementInfo(String elementKey) {
        ElementInfo element = ELEMENT_MAP.get(elementKey);
        if (element == null) {
            logger.error("Element not found in configuration: {}", elementKey);
            throw new RuntimeException("Missing element configuration: " + elementKey);
        }
        return element;
    }
}
