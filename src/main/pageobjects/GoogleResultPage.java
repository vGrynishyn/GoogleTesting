package pageobjects;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;

public interface GoogleResultPage {
    void clickNeededNumOfResultLinks(int position);
    ArrayList<String> getResultLinks();
    String getSearchLinkFromResults(String expectedDomainName, int numOfSearchPage);
    ArrayList<WebElement> getSearcResultWebElements();
    void clickToNextPage();
}
