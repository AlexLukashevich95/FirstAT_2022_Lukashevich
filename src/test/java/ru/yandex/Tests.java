package ru.yandex;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.PageFactory;
import pages.PageFactoryYandex;

public class Tests extends BaseTest{

    @Feature("Проверка результатов поиска")
    @DisplayName("Проверка результатов поиска c помощью PF")
    @Test
    public void testPF(){
        chromeDriver.get("https://yandex.ru");
        PageFactoryYandex pageFactoryYandex = PageFactory.initElements(chromeDriver, PageFactoryYandex.class);
        pageFactoryYandex.findTitle("таблица");
        Assertions.assertTrue(pageFactoryYandex.getResults().stream().anyMatch(x->x.getText().contains("Таблица — Википедия")),
                "Статьи содержащие таблица не найдены для поискового слова Таблица — Википедия");
        pageFactoryYandex.goToResultPage("Таблица — Википедия");

        Assertions.assertEquals(pageFactoryYandex.checkTable().getText(), "Преподаватели кафедры программирования", "Таблица -Преподаватели кафедры программирования- на странице не найдена");
        Assertions.assertTrue(pageFactoryYandex.checkTableData("Сергей","Владимирович","Сергей","Адамович"),"Данные в первой и последней строке не совпадают с входными");
    }
}
