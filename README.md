#  Selenium Java Automation Framework – Expand Testing

##  Project Overview
This project is a **Selenium-Java Automation Test Framework** developed for the Expand Testing practice application:

🔗 https://practice.expandtesting.com  

The framework follows **Page Object Model (POM)** and is designed to be:
- Reusable  
- Scalable  
- Maintainable  

It automates **end-to-end scenarios** across multiple modules as per the problem statement.

---

##  Modules Covered

###  Module 1: User Authentication 
- Verify successful login with valid credentials  
- Verify login failure with wrong password  
- Verify logout functionality and session end  
- Verify validation for empty username and password  

---

###  Module 2: Notes Management 
- Create a new note and verify it appears  
- Edit note and verify updates  
- Delete note and verify removal  
- Filter notes by category  

---

###  Module 3: Form Interactions 
- Enter values in input fields and verify  
- Select dropdown values  
- Check/uncheck checkboxes  
- Select radio buttons
---
  ###  Module 4: Alerts 
- Handle JS Alert and verify confirmation text  
- Handle JS Confirm (dismiss) and verify result message  
- Handle JS Prompt, input text, and verify displayed result  

---

###  Module 5: Form Validations 
- Validate error when creating note with empty title  
- Validate registration with weak password  
- Validate login with invalid email format  

---

###  Additional Features
- Data-driven testing using Excel  
- Dynamic wait handling using WebDriverWait  
- Screenshot capture on failure  
- Extent Reports for execution results  

---

## Tech Stack

- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- WebDriverManager  
- Apache POI (Excel)  
- ExtentReports  

---

##  Project Structure
```
ExpandTest/
│
├── src/
│ ├── main/java/com/expandtesting/framework/
│ │ ├── base/
│ │ │ ├── BaseTest.java
│ │ │ └── BasePage.java
│ │ │
│ │ ├── config/
│ │ │ └── ConfigReader.java
│ │ │
│ │ ├── pages/
│ │ │ ├── AuthLoginPage.java
│ │ │ ├── SecureAreaPage.java
│ │ │ ├── RegisterPage.java
│ │ │ ├── NotesLoginPage.java
│ │ │ ├── NotesPage.java
│ │ │ ├── FormPage.java
│ │ │ └── AlertPage.java
│ │ │
│ │ ├── utils/
│ │ │ ├── ExcelUtils.java
│ │ │ └── ScreenshotUtil.java
│ │ │
│ │ └── listeners/
│ │ └── TestListener.java
│ │
│ ├── test/java/com/expandtesting/tests/
│ │ ├── AuthTests.java
│ │ ├── NotesTests.java
│ │ └── FormTests.java
│
├── src/test/resources/
│ ├── config.properties
│ └── testdata.xlsx
│
├── screenshots/
├── reports/
├── testng.xml
├── pom.xml
└── README.md
```

---

## ⚙️ Framework Design Highlights

###  Page Object Model (POM)
- Each page has a dedicated class  
- All locators & actions inside Page classes  
- Test classes only call methods  

---

###  Configuration Management
- Config stored in `config.properties`
- Includes:
  - Browser  
  - Base URL  
  - Timeout  

---

###  Data-Driven Testing
- Registration data read from Excel  
- Login uses predefined credentials  
- Unique email generated dynamically  

---

###  Wait Strategy
- Uses **WebDriverWait + ExpectedConditions**  
- No `Thread.sleep()` used  

---

###  WebDriver Management
- Managed using **WebDriverManager**  
- Browser selection via config file  

---

###  Screenshot on Failure
- Implemented using TestNG `ITestListener`  
- Screenshots stored in `/screenshots`  

---

###  Reporting
- ExtentReports generates HTML reports  
- Includes:
  - Test status  
  - Failure screenshots  

---

##  How to Run


 run via TestNG:

* Right-click `testng.xml` → Run

---

##  Business Rules Followed

-  No Thread.sleep() used  
-  No hardcoded URLs  
-  Config-driven framework  
-  Proper POM structure  
-  Executed via testng.xml  

---

##  Good Practices Implemented

- Excel-based DataProvider  
- Dynamic test data generation  
- Clean reusable methods  


---

##  Author

**Deepika Kantheti**  
