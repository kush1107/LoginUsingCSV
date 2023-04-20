package com.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;

public class LoginUsingCSV {
	
	private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://naveenautomationlabs.com/opencart/index.php?route=account/login");
    }

    @DataProvider(name = "loginData")
    public static Iterator<Object[]> getLoginData() throws Exception {
        String filePath =System.getProperty("user.dir")+"\\src\\test\\java\\Login.csv"; // path of your csv file
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(filePath)))) {
            List<String[]> records = csvReader.readAll();
            int numRecords = records.size();
            if (numRecords == 0) {
                throw new Exception("CSV file is empty");
            }
            List<Object[]> dataList = new ArrayList<Object[]>();
            Iterator<String[]> iterator = records.iterator();
            while (iterator.hasNext()) {
                String[] record = iterator.next();
                Object[] data = new Object[record.length];
                for (int i = 0; i < record.length; i++) {
                    data[i] = record[i];
                }
                dataList.add(data);
            }
            return dataList.iterator();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }



    @Test(dataProvider = "loginData")
    public void login(String username, String password,String search) {
        WebElement usernameField = driver.findElement(By.id("input-email"));
        WebElement passwordField = driver.findElement(By.id("input-password"));
        //WebElement searchField = driver.findElement(By.xpath("//input[@placeholder='Search']"));
       
       
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
       
       
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      driver.findElement(By.xpath("//input[@value='Login']")).click();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
