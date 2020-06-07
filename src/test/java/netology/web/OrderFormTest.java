package netology.web;

import com.codeborne.selenide.SelenideElement;
import netology.data.OrderFormData;
import netology.data.OrderFormGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class OrderFormTest {

    private OrderFormData formData;

    @BeforeEach
    void setUpAll() {
        formData = OrderFormGenerator.generateData();
    }

    @Test
    void cardOrderDeliveryFormTest() {
        open("http://localhost:9999");

        $("[data-test-id='city'] input").setValue(formData.getCity());

        SelenideElement dateElement = $("[data-test-id=date] input[class=input__control]");
        dateElement.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        String orderDate = OrderFormGenerator.getOrderDate(3);
        dateElement.setValue(orderDate);

        $("[data-test-id=name] input").setValue(formData.getName());
        $("[data-test-id=phone] input").setValue(formData.getPhone());
        $("[data-test-id=agreement]").click();

        SelenideElement planBtnElement = $$("button").find(exactText("Запланировать"));
        planBtnElement.click();

        SelenideElement planSuccessElement = $("[data-test-id=success-notification]");
        planSuccessElement.shouldHave(text("Встреча успешно запланирована на")).shouldHave(text(orderDate));

        dateElement.sendKeys(Keys.LEFT_CONTROL + "a" + Keys.BACK_SPACE);
        orderDate = OrderFormGenerator.getOrderDate(1);
        dateElement.setValue(orderDate);
        planBtnElement.click();

        SelenideElement replainElement = $("[data-test-id=replan-notification]");
        replainElement.shouldHave(text("Необходимо подтверждение"));
        replainElement.$$("button").find(exactText("Перепланировать")).click();

        planSuccessElement.shouldHave(text("Встреча успешно запланирована на")).shouldHave(text(orderDate));

    }
}
