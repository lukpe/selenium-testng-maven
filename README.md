# ecommerce-framework
Selenium framework for testing sample e-commerce page ([automationpractice.com](http://automationpractice.com))

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
* [Log4j](https://logging.apache.org/log4j/2.x/) -> `./logs`
* screenshots on failure -> `./screenshots`
* configuration file -> `./resources/test.properties`
* generated data (login credentials, address) saved to excel sheet -> `./resources/LoginData.xls`

## scenarios
### [Scenario_01_VerifyHomePage](/src/test/java/org/test/Scenario_01_VerifyHomePage.java)
* Check for presence/validate: _page title, logo image, search bar, shopping cart ,"Sign in" link_
### [Scenario_02_CreateAccount](/src/test/java/org/test/Scenario_02_CreateAccount.java)
1. Create account and save generated data to LoginData.xls
2. Verify if personal information is correctly displayed on the account page.
### [Scenario_03_OrderProduct](src/test/java/org/test/Scenario_03_OrderProduct.java)
[testng.xml]: https://github.com/lukpe/ecommerce-framework/blob/d3a8d1dca239cd7508dabe621ac32eded8de26a3/testng.xml#L20
1. Log in (take credentials from LoginData.xls).
2. Add a product to cart (product name parametrized in [testng.xml]).
3. Proceed to checkout.
4. Verify cart page.
5. Test adding/removing product quantity.
