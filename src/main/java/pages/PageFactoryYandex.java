package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.*;

public class PageFactoryYandex {
    private WebDriver chromeDriver;


    @FindBy(how= How.XPATH, using = "//input[contains(@class,'mini-suggest__input')]")
    WebElement searchField;

    @FindBy(how= How.XPATH, using = "//button[contains(@class,'mini-suggest__button')]")
    WebElement searchButton;

    @FindBy(how= How.XPATH, using = "//span[contains(@class,'organic__title')]")
    List<WebElement> results;

    public PageFactoryYandex(WebDriver chromeDriver){
        this.chromeDriver=chromeDriver;
    }

    public void findTitle (String keysFind){
        searchField.click();
        searchField.sendKeys(keysFind);
        searchButton.click();
    }

    public List<WebElement> getResults() {
        return results;
    }

    public void goToResultPage(String title){
        WebElement resultPage = results.stream().filter(x->x.getText().contains(title)).findFirst().get();
        resultPage.click();
        Set<String> handlesSet = chromeDriver.getWindowHandles();
        List<String> handlesList = new ArrayList<String>(handlesSet);
        chromeDriver.switchTo().window(handlesList.get(1));
    }
    @FindBy(how= How.XPATH, using = "//table[contains(caption,'Преподаватели кафедры программирования')]/caption")
    WebElement tableTitle;

    @FindBy(how= How.XPATH, using = "//table[contains(caption,'Преподаватели кафедры программирования')]//tr/th")
    List<WebElement> tableColumnNames;

    @FindBy(how= How.XPATH, using = "//table[contains(caption,'Преподаватели кафедры программирования')]//tr/td")
    List<WebElement> tableData;


    public WebElement checkTable(){
        return tableTitle;

    }

    public boolean checkTableData(String nameFirst,String patronymicFirst,String nameLast,String patronymicLast){
        boolean checkResult = false;
        List<Map<String,String>> tableDataComplete = new ArrayList<>();
        int counter=0;
        while (counter<tableData.size()){
            Map<String,String> tableRowData = new HashMap<>();
            for(WebElement columnName : tableColumnNames){
                tableRowData.put(
                        columnName.getText(),
                        tableData.get(counter).getText());
                counter++;
            }
            tableDataComplete.add(tableRowData);
        }

        if(tableDataComplete.get(0).get("Имя").equals(nameFirst)&&
                tableDataComplete.get(0).get("Отчество").equals(patronymicFirst)&&
                tableDataComplete.get(tableDataComplete.size()-1).get("Имя").equals(nameLast)&&
                tableDataComplete.get(tableDataComplete.size()-1).get("Отчество").equals(patronymicLast)){
            checkResult=true;
        }
        return checkResult;
    }

}
