import core.Browser;
import core.LogInformation;
import core.TestListener;
import org.junit.Assert;
import org.testng.annotations.*;
import pageobjects.impl.GoogleStartPageImpl;

import java.util.ArrayList;
import java.util.Collection;


/**
 * @author vGrynishyn
 * @since 31/09/2017
 */

@Listeners({TestListener.class})
public class GoogleSearchTests {

    private static final String URL = "https://www.google.com.ua/";
    private static final String PATTERN = "automation";
    private static final String EXPECTED_DOMAIN_NAME = "testautomationday.com";

    @AfterMethod
    public static void afterTest(){
        Browser.closeBrowser();
    }


    /*
     *  Test #1. Open Google. Search for “automation”. Open the first link on search results page. Verify that title contains searched word
     */
    @Test
    @Parameters("pattern")
    public void searchPatternAndCheckBrowserTitleTest(@Optional(PATTERN) String pattern){
        new GoogleStartPageImpl()
                .openWebPage(URL)
                .searchPattern(pattern)
                .clickNeededNumOfResultLinks(0);
        Assert.assertTrue(Browser.getTitle().contains(pattern));
    }

    /*
     *  Test #2. Open Google. Search for “automation”. Verify that there is expected domain (“testautomationday.com”) on search results  pages (page: 1-5).
     */
    @Test
    @Parameters({"pattern", "pagesNum", "expectedDomainName"})
    public void tryToFindExpectedDomainNameTest(@Optional(PATTERN) String pattern, @Optional("5") int pagesNum, @Optional(EXPECTED_DOMAIN_NAME) String expectedDomainName){
        String actualLink = new GoogleStartPageImpl()
            .openWebPage(URL)
            .searchPattern(pattern)
            .getSearchLinkFromResults(expectedDomainName, pagesNum);
        Assert.assertTrue("There is not found expected domain name(page:1-5).", actualLink.contains(expectedDomainName));
    }

    /*
     * Test #3. Additional test since previous serch with word "automation" does not have expected domain name 'testautomationday.com' in search results.
     * Open Google. Search for “automation”. Verify that there is expected domain (“testautomationday.com”) on search results  pages (page: 1-5).
     */
    @Test(dataProvider = "foundLinks")
    @Parameters("linkText")
    public void checkIfGoogleSearchCanFindTestAutomationDayDomainTest(String foundLink){
        LogInformation.info(String.format("Verify expected domain name '%s' is present in %s.",EXPECTED_DOMAIN_NAME, foundLink));
        Assert.assertTrue(String.format("Could not find expected domain name '%s' in '%s' link.", EXPECTED_DOMAIN_NAME, foundLink),foundLink.contains(EXPECTED_DOMAIN_NAME));
    }

    @DataProvider(name = "foundLinks", parallel = true)
    public Object[] getLinks() {
        ArrayList<String> links = (new GoogleStartPageImpl())
                .openWebPage(URL)
                .searchPattern(PATTERN+"day")
                .getResultLinks();
        Collection<Object[]> data = new ArrayList();
        links.forEach(item -> data.add(new Object[]{item}));
        Browser.closeBrowser();
        return links.toArray();
    }
}
