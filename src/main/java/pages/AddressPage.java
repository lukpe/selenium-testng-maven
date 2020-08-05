package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.test.ExcelDriver;

import java.util.HashMap;
import java.util.Map;

public class AddressPage {
    public WebDriver driver;

    public AddressPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//button//span[contains(text(),'Proceed to checkout')]")
    WebElement checkOut;

    public boolean verifyAddressData() {
        Map<String, String> addressMap = fillAddressMap();
        String xpath = "//ul[@id='address_delivery']//li[@class='";
        boolean found = true;
        for (int i = 0; i < 2; i++) {
            for (Map.Entry<String, String> entry : addressMap.entrySet()) {
                if (i == 1) {
                    xpath = "//ul[@id='address_invoice']//li[@class='";
                }
                WebElement addressField = driver.findElement(By.xpath(xpath + entry.getKey() + "']"));
                found = addressField.getText().contentEquals(entry.getValue());
                if (!found) {
                    break;
                }
            }
        }
        return found;
    }

    private Map<String, String> fillAddressMap() {
        ExcelDriver e = new ExcelDriver();
        Map<String, String> addressMap = new HashMap<>();
        try {
            String fullName = e.getColumnValue("firstname") + " " + e.getColumnValue("lastname");
            addressMap.put("address_firstname address_lastname", fullName);
            addressMap.put("address_company", e.getColumnValue("company"));
            String streetAddress = e.getColumnValue("address1") + " " + e.getColumnValue("address2");
            addressMap.put("address_address1 address_address2", streetAddress);
            String cityAddress = e.getColumnValue("city") + ", " + e.getColumnValue("id_state")
                    + " " + e.getColumnValue("postcode");
            addressMap.put("address_city address_state_name address_postcode", cityAddress);
            addressMap.put("address_country_name", e.getColumnValue("id_country"));
            addressMap.put("address_phone", e.getColumnValue("phone"));
            addressMap.put("address_phone_mobile", e.getColumnValue("phone_mobile"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return addressMap;
    }

    public void proceedCheckout() {
        checkOut.click();
    }

}
