package netology.data;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class OrderFormGenerator {

    final static String[] federalCities = {"Москва", "Хабаровск", "Екатеринбург", "Новосибирск", "Курск"};

    private OrderFormGenerator() {
    }

    public static OrderFormData generateData() {
        Faker faker = new Faker(new Locale("ru"));
        Random rndCities = new Random();
        String cityName = federalCities[rndCities.nextInt(federalCities.length)];
        String[] customerName = faker.name().name().split(" ");
        return new OrderFormData(cityName,
                customerName[0] + " " + customerName[1],
                "+7" + faker.phoneNumber().cellPhone());
    }

    public static String getOrderDate(int addDays) {
        LocalDate dateOrder = LocalDate.now().plusDays(addDays);
        return dateOrder.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

}
