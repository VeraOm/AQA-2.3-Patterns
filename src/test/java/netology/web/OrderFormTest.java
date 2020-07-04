package netology.web;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.data.OrderFormData;
import netology.data.OrderFormGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class OrderFormTest {

    private OrderFormData formData = OrderFormGenerator.generateData();
    ;

    private SelenideElement dateElement;
    private SelenideElement planBtnElement;
    private SelenideElement replainElement;
    private SelenideElement planSuccessElement;

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    void cardOrderDeliveryFormTest() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue(formData.getCity());

        dateElement = $("[data-test-id=date] input[class=input__control]");
        dateElement.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        String orderDate = OrderFormGenerator.getOrderDate(3);
        dateElement.setValue(orderDate);

        $("[data-test-id=name] input").setValue(formData.getName());
        $("[data-test-id=phone] input").setValue(formData.getPhone());
        $("[data-test-id=agreement]").click();

        planBtnElement = $$("button").find(exactText("Запланировать"));
        planBtnElement.click();

        planSuccessElement = $("[data-test-id=success-notification]");
        planSuccessElement.shouldHave(text("Встреча успешно запланирована на")).shouldHave(text(orderDate));

        dateElement.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        orderDate = OrderFormGenerator.getOrderDate(4);
        dateElement.setValue(orderDate);
        planBtnElement.click();

        replainElement = $("[data-test-id=replan-notification]");
        replainElement.shouldHave(text("Необходимо подтверждение"));
        replainElement.$$("button").find(exactText("Перепланировать")).click();

        planSuccessElement.shouldHave(text("Встреча успешно запланирована на")).shouldHave(text(orderDate));

    }

}
