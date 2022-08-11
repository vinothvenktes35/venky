package test;

import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class refer {
	
	public static void main(String[] args) throws InterruptedException, AWTException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
	//To check Element Present:

		if(driver.findElements(By.xpath("value")).size() != 0){
		System.out.println("Element is Present");
		}else{
		System.out.println("Element is Absent");
		}
	

		if(driver.findElement(By.xpath("value"))!= null){
		System.out.println("Element is Present");
		}else{
		System.out.println("Element is Absent");
		}
		//To check Visible:

		if( driver.findElement(By.cssSelector("a > font")).isDisplayed()){
		System.out.println("Element is Visible");
		}else{
		System.out.println("Element is InVisible");
		}
	//	To check Enable:

		if( driver.findElement(By.cssSelector("a > font")).isEnabled()){
		System.out.println("Element is Enable");
		}else{
		System.out.println("Element is Disabled");
		}
		//To check text present

		if(driver.getPageSource().contains("Text to check")){
		System.out.println("Text is present");
		}else{
		System.out.println("Text is absent");
		}
		
//		if(getDriver().getPageSource().contains("unregister (testing only)")){
//			System.out.println("unregister (testing only) Text is present");
//			action.JSClick(getDriver(), unregisterBtn);
//			}else{
//			System.out.println("unregister (testing only) Text is absent");
//			}
}}
