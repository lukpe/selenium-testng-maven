# selenium-testng-maven
Selenium framework for testing e-commerce page ([automationpractice.com](http://automationpractice.com))

## requirements
* Java 11
* Maven 3.6.3 or later
* Chrome and/or Firefox

## how to run
* `git clone https://github.com/lukpe/ecommerce-framework.git`
* `mvn test`

| optional parameters |
|---------------------|
`-Dbrowser=firefox`
`-Dbrowser=chrome-remote`
`-Dbrowser=firefox-remote`

[test.properties]: resources/test.properties
[docker]: https://www.docker.com/
[docker-compose.yml]: resources/docker-compose.yml
* If no parameter -> test locally under **chrome** (_browser_ parameter in [test.properties])
* If `-remote` -> _gridURL_ parameter from [test.properties]
* Added [docker-compose.yml] to run `-Dbrowser=chrome-remote` on _localhost_ with [docker]

## highlights
* [Page Factory](https://github.com/SeleniumHQ/selenium/wiki/PageFactory)
* [TestNG](https://testng.org/doc/)
* [Maven Surefire Reports](https://maven.apache.org/surefire/maven-surefire-report-plugin/)
* [webdrivermanager](https://github.com/bonigarcia/webdrivermanager)
* [Log4j](https://logging.apache.org/log4j/2.x/) -> `./logs` folder
* screenshots on failure -> `./screenshots` folder
* configuration file -> `./resources/test.properties`
* generated data (login credentials, address) saved to an Excel sheet -> `./resources/TestData.xls`

## scenarios
### [Scenario_01_VerifyHomePage](/src/test/java/org/test/Scenario_01_VerifyHomePage.java)
* Check for presence/validate: _page title, logo image, search bar, shopping cart ,"Sign in" link_
### [Scenario_02_CreateAccount](/src/test/java/org/test/Scenario_02_CreateAccount.java)
1. Create account and save generated data to TestData.xls
2. Verify if personal information is correctly displayed on the account page.
### [Scenario_03_OrderProduct](src/test/java/org/test/Scenario_03_OrderProduct.java)
[testng.xml]: ./testng.xml
[TestData.xls]: ./resources/TestData.xls
###### Product `name`, `quantity` and `payment` method parametrized in [testng.xml]
1. Search for a product, save its name, price and desired quantity to [TestData.xls] and proceed to checkout.
2. Verify the summary page before and after adding product quantity (`if quantity > 1`) also verify total prices with and without tax.
3. Log in.
4. Verify address data.
5. Verify shipping price and terms and conditions acceptance requirement.
6. Choose payment method and verify payment confirmation, total price and extract order reference number.