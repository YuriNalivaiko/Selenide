package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static java.time.LocalDate.*;


public class OrderingCardDeliveryTest {

    String generateDate(int daysToAdd) {
        return now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }


    @Test
    // Заполнение всех полей данной формы валидными значениями
    public void orderCardDeliveryTestFirst() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Краснодар");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $x(".//div[@class='notification__title']")
                .should(text("Успешно"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
        $("[data-test-id='notification'] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Заполнение поля "Город" невалидным значением
    public void orderCardDeliveryTestSecond() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Ессентуки");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='city'].input .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Пустое поле "Город"
    public void orderCardDeliveryTestThird() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='city'].input .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Заполнение поля "Дата встречи" невалидным значением
    public void orderCardDeliveryTestFourth() {
        String planningDate = generateDate(-1);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Воронеж");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='date'] .input .input__sub")
                .shouldHave(exactText("Заказ на выбранную дату невозможен"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Пустое поле "Дата встречи"
    public void orderCardDeliveryTestFifth() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Воронеж");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue("");
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='date'] .input .input__sub")
                .shouldHave(exactText("Неверно введена дата"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Заполнение поля "Фамилия и имя" невалидным значением
    public void orderCardDeliveryTestSixth() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Veliyev Maxim");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='name'].input .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Пустое поле "Фамилия и имя"
    public void orderCardDeliveryTestSeventh() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='name'].input .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Заполнение поля "Мобильный телефон" невалидным значением
    public void orderCardDeliveryTestEighth() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("7+9756249171");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='phone'].input .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Пустое поле "Мобильный телефон"
    public void orderCardDeliveryTestNinth() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("");
        $("[data-test-id='agreement']").click();
        $("[type=button] .button__content").click();
        $("[data-test-id='phone'].input .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Заполнение формы валидными значениями без использования чекбокса
    public void orderCardDeliveryTestTenth() {
        String planningDate = generateDate(4);
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Уфа");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue(planningDate);
        $("[data-test-id='name'] input ").setValue("Велев Максим");
        $("[data-test-id='phone'] input ").setValue("+79756249171");
        $("[type=button] .button__content").click();
        $("[data-test-id='agreement'] .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }

    @Test
    // Пустые поля в форме "Карта с доставкой"
    public void orderCardDeliveryTestEleventh() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("");
        $("[data-test-id ='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id ='date'] input").setValue("");
        $("[data-test-id='name'] input ").setValue("");
        $("[data-test-id='phone'] input ").setValue("");
        $("[type=button] .button__content").click();
        $("[data-test-id='city'].input .input__sub")
                .shouldHave(exactText("Поле обязательно для заполнения"), Duration.ofSeconds(5000))
                .shouldBe(Condition.visible);
    }
}


