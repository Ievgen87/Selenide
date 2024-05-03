package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.conditions.datetime.DateTimeConditions;
import io.opentelemetry.semconv.trace.attributes.SemanticAttributes;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {
    private String generateData(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void fillingOutACreditCard() {
        open("http://localhost:9999/");
        $("[data-test-id=city] input").setValue("Москва");
        String addDate = generateData(4, "dd.MM.yyyy");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue(addDate);
        $("[data-test-id=name] input").setValue("Самогонкин Аппарат");
        $("[data-test-id=phone] input").setValue("+79251234589");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + addDate));


    }
}
