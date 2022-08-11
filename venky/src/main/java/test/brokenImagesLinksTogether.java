package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class brokenImagesLinksTogether {

	public static void main(String[] args) throws InterruptedException, AWTException, MalformedURLException, IOException {
//		WebDriverManager.edgedriver().setup();
//		WebDriver driver = new EdgeDriver();
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

		driver.get("https://www.devweb.proxtera.app/search/food");
		
		Thread.sleep(6000);
		






//		WebElement ele = driver
//				.findElement(By.xpath("//div[@class=\"display-flex flex-row flex-wrap items-center\"]/div[contains(text(),\"Apparel\")]"));
//
//		JavascriptExecutor executor = (JavascriptExecutor)  driver;
//		executor.executeScript("arguments[0].click();", ele);
//
//		Thread.sleep(4000);
		
		
// link = a tag
//images = img tag

//1.1  get all list of all links 

		List<WebElement> linksList = driver.findElements(By.tagName("a"));
		linksList.addAll(driver.findElements(By.tagName("img")));
		System.out.println("size of all links and images---->  " + linksList.size());
		
		List<WebElement> activeLinks = new ArrayList<WebElement>();
		Thread.sleep(5000);
		
//1.2. iterate link list : exclude all links do not have href attribute
		for (int i = 0; i < linksList.size(); i++) {
			System.out.println(linksList.get(i).getAttribute("href"));
			if (linksList.get(i).getAttribute("href") != null && (!linksList.get(i).getAttribute("href").contains("javascript"))) {
				activeLinks.add(linksList.get(i));
			}
		}
        //get the size of active links list
		System.out.println("active Links counts:  " + activeLinks.size());
		Thread.sleep(8000);
		//1.3. check the href url, http connection api
		//200 --->ok
			//404--->not found
			//400--->bad request
			//500--->internal error
		
		 for (int j = 0; j < linksList.size(); j++) {
			 HttpURLConnection connection= (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
			 connection.setConnectTimeout(5000);
			 connection.connect();
			String response= connection.getResponseMessage();//ok
			 connection.disconnect();
			 System.out.println(activeLinks.get(j).getAttribute("href")+"---->"+response);
		 }
	}	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
