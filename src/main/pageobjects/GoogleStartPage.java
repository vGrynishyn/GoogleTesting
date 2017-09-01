package pageobjects;

import pageobjects.impl.GoogleResultPageImpl;
import pageobjects.impl.GoogleStartPageImpl;

public interface GoogleStartPage {
    GoogleStartPageImpl openWebPage(String url);
    GoogleResultPageImpl searchPattern(String pattern);
}
