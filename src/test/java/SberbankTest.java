import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.function.Function;

public class SberbankTest extends MyWebDriverBasicClass {

    private String MAIN_PAGE = "http://www.sberbank.ru/ru/person";
    private String ELEMENT_TOP_MENU = "//button[@aria-label='Меню %s']/parent::li";
    private String SUB_ELEMENT_TOP_MENU = ELEMENT_TOP_MENU
            + "//a[@class='lg-menu__sub-link' and text()='%s']";
    private String ELEMENT_FOR_CHECK_SECOND_PAGE = "//h1[text()='%s']";
    private String CALCULATE_ONLINE = "//img/parent::a[contains(@href,'https') and not(@aria-hidden)]";
    private String SUM_INSURANCE_COVER = "//div[@class='b-form-box-title ng-binding' and text()='%s']";
    private String SUM_SPORT_POLICY = "//span[text()='Спортивный']/parent::div//span[@ng-show=\"dopPack.SYSNAME == 'dopSPORT'\"]";
    private String CLICK_ACCEPT = "//span[text()='%s']";
    private String ELEMENT_ON_THIRD_PAGE = "//span[text()='Введите данные застрахованных латинскими буквами, как в загранпаспорте']";
    private String INPUT_INSURED = "//input[@ng-model='namespace.%s']";
    private String INPUT_POLICYHOLDER = "//input[@ng-model='formdata.insurer.%s']";
    private String PASSPORT_DATA = "//input[@name='%s']";
    private String PASSPORT_ISSUE_PLACE = "//textarea[@name='issuePlace']";
    private String CONTINUE_BUTTON = "//span[text()='Продолжить']";
    private String ERROR_TEXT = "//div[text()='Заполнены не все обязательные поля']";

    @Test
    public void homework() {

        webDriver.get(MAIN_PAGE);

        clickTopMenuAndSubMenu("Страхование", "Страхование путешественников");

        waitLoadAndCheckElement("Страхование путешественников");

        clickToCalculateOnline(CALCULATE_ONLINE);

        clickSumInsuranceCover("Минимальная");

        clickButton("Оформить");

        waitLoadDrawUp();

        inputSurnameInsured("Ivanov");
        inputNameInsured("Ivan");
        inputDateInsured("05.06.1975");

        inputLastNamePolicyholder("Иванов");
        inputFirstNamePolicyholder("Сергей");
        inputMiddleNamePolicyholder("Иванович");
        inputBirthdayPolicyholder("15.11.1996");

        inputPassportDate("11.11.2004");
        inputPassportSeries("5466");
        inputPassportNumber("345654");
        inputPassportIssuePlace("ОВД Москвы");

        checkSurnameInsured("Ivanov");
        checkNameInsured("Ivan");
        checkDateInsured("05.06.1975");

        checkLastNamePolicyholder("Иванов");
        checkFirstNamePolicyholder("Сергей");
        checkMiddleNamePolicyholder("Иванович");
        checkBirthdayPolicyholder("15.11.1996");

        checkPassportSeries("5466");
        checkPassportNumber("345654");
        checkPassportDate("11.11.2004");
        checkPassportIssuePlace("ОВД Москвы");

        findAndClick(CONTINUE_BUTTON);

        Assert.assertTrue(checkPresentElementOnPage(ERROR_TEXT));
    }

    private void clickButton(String name) {
        findAndClick(String.format(CLICK_ACCEPT, name));
    }

    private void clickTopMenuAndSubMenu(String nameElementMenu, String nameSubElementMenu) {
        findAndClick(String.format(ELEMENT_TOP_MENU, nameElementMenu));
        findAndClick(String.format(SUB_ELEMENT_TOP_MENU, nameElementMenu, nameSubElementMenu));
    }

    private void waitLoadDrawUp() {
        waitPresenceElement(ELEMENT_ON_THIRD_PAGE);
    }

    public void waitLoadAndCheckElement(String nameElement) {
        waitPresenceElement(String.format(ELEMENT_FOR_CHECK_SECOND_PAGE, nameElement));
        Assert.assertTrue(checkPresentElementOnPage(String.format(ELEMENT_FOR_CHECK_SECOND_PAGE, nameElement)));
    }

    public void clickToCalculateOnline(String xPath) {
        clickElementJavaScript(CALCULATE_ONLINE);

        ArrayList<String> tabs = new ArrayList<String>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(1));
    }

    public void clickSumInsuranceCover(String valueSum) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Оформить']")));

        wait.until(new Function<WebDriver, Object>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !webDriver.findElement(By.xpath(SUM_SPORT_POLICY)).getText().equals("0,00  i");
            }
        });
        WebElement element = webDriver.findElement(By.xpath(SUM_SPORT_POLICY));
        String lastValue = element.getText();
        System.out.println(lastValue);

        findAndClick(String.format(SUM_INSURANCE_COVER, valueSum));

        wait.until(new Function<WebDriver, Object>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !webDriver.findElement(By.xpath(SUM_SPORT_POLICY)).getText().equals(lastValue);
            }
        });
        String nowValue = element.getText();
        System.out.printf(nowValue);
    }

    public void inputSurnameInsured(String name) {
        WebElement element = findAndClick(String.format(INPUT_INSURED, "SURNAME_ENG"));
        element.sendKeys(name);
    }

    public void inputNameInsured(String name) {
        WebElement element = findAndClick(String.format(INPUT_INSURED, "NAME_ENG"));
        element.sendKeys(name);
    }

    public void inputDateInsured(String date) {
        WebElement element = findAndClick(String.format(INPUT_INSURED, "BIRTHDATE"));
        element.sendKeys(date.replaceAll("[.]", ""));
    }

    public void inputLastNamePolicyholder(String name) {
        WebElement element = findAndClick(String.format(INPUT_POLICYHOLDER, "LASTNAME"));
        element.sendKeys(name);
    }

    public void inputFirstNamePolicyholder(String name) {
        WebElement element = findAndClick(String.format(INPUT_POLICYHOLDER, "FIRSTNAME"));
        element.sendKeys(name);
    }

    public void inputMiddleNamePolicyholder(String name) {
        WebElement element = findAndClick(String.format(INPUT_POLICYHOLDER, "MIDDLENAME"));
        element.sendKeys(name);
    }

    public void inputBirthdayPolicyholder(String date) {
        WebElement element = findAndClick(String.format(INPUT_POLICYHOLDER, "BIRTHDATE"));
        element.sendKeys(date.replaceAll("[.]", ""));
    }

    public void inputPassportSeries(String series) {
        WebElement element = findAndClick(String.format(PASSPORT_DATA, "passport_series"));
        element.sendKeys(series);
    }

    public void inputPassportNumber(String number) {
        WebElement element = findAndClick(String.format(PASSPORT_DATA, "passport_number"));
        element.sendKeys(number);
    }

    public void inputPassportDate(String date) {
        WebElement element = findAndClick(String.format(PASSPORT_DATA, "issueDate"));
        element.sendKeys(date.replaceAll("[.]", ""));
    }

    public void inputPassportIssuePlace(String place) {
        WebElement element = findAndClick(PASSPORT_ISSUE_PLACE);
        element.sendKeys(place);
    }

    public void checkSurnameInsured(String name) {
        String valueElement = String.format(INPUT_INSURED, "SURNAME_ENG");
        checkValueElement(valueElement, name);
    }

    public void checkNameInsured(String name) {
        String valueElement = String.format(INPUT_INSURED, "NAME_ENG");
        checkValueElement(valueElement, name);
    }

    public void checkDateInsured(String date) {
        String valueElement = String.format(INPUT_INSURED, "BIRTHDATE");
        checkValueElement(valueElement, date);
    }

    public void checkLastNamePolicyholder(String name) {
        String valueElement = String.format(INPUT_POLICYHOLDER, "LASTNAME");
        checkValueElement(valueElement, name);
    }

    public void checkFirstNamePolicyholder(String name) {
        String valueElement = String.format(INPUT_POLICYHOLDER, "FIRSTNAME");
        checkValueElement(valueElement, name);
    }

    public void checkMiddleNamePolicyholder(String name) {
        String valueElement = String.format(INPUT_POLICYHOLDER, "MIDDLENAME");
        checkValueElement(valueElement, name);
    }

    public void checkBirthdayPolicyholder(String date) {
        String valueElement = String.format(INPUT_POLICYHOLDER, "BIRTHDATE");
        checkValueElement(valueElement, date);
    }

    public void checkPassportSeries(String series) {
        String valueElement = String.format(PASSPORT_DATA, "passport_series");
        checkValueElement(valueElement, series);
    }

    public void checkPassportNumber(String number) {
        String valueElement = String.format(PASSPORT_DATA, "passport_number");
        checkValueElement(valueElement, number);
    }

    public void checkPassportDate(String date) {
        String valueElement = String.format(PASSPORT_DATA, "issueDate");
        checkValueElement(valueElement, date);
    }

    public void checkPassportIssuePlace(String place) {
        checkValueElement(PASSPORT_ISSUE_PLACE, place);
    }


}
