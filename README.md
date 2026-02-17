# Playwright_API
Api Automation with Playwright
mvn clean test "-DsuiteXmlFile=testng.xml"   
mvn clean test "-DsuiteXmlFile=testng.xml" "-Dgroups=smoke"
mvn test "-DsuiteXmlFile=target/surefire-reports/testng-failed.xml"
