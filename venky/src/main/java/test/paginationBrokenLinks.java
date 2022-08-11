package test;

import java.awt.AWTException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class paginationBrokenLinks {
	public static void main(String[] args) throws InterruptedException, AWTException, MalformedURLException, IOException {
		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

driver.get("https://www.devweb.proxtera.app/search/textiles");
		
		Thread.sleep(4000);
String  nextButtonClassName = driver.findElement(By.xpath("//button[@class=\"MuiButtonBase-root MuiPaginationItem-root MuiPaginationItem-sizeMedium MuiPaginationItem-outlined MuiPaginationItem-rounded MuiPaginationItem-previousNext css-19xm0h7-MuiButtonBase-root-MuiPaginationItem-root\"][@aria-label=\"Go to next page\"]")).getAttribute("class");

	while(!nextButtonClassName.contains("disabled")) {
		
		//1.1  get all list of all links 

				List<WebElement> allLinks = driver.findElements(By.tagName("a"));
//				allLinks.addAll(driver.findElements(By.tagName("img")));
				// get total links count
				System.out.println("all Links  counts:  " + allLinks.size());
				List<WebElement> activeLinks = new ArrayList<WebElement>();
				Thread.sleep(8000);
				
				
		//1.2. iterate link list : exclude all links do not have href attribute
				for (int i = 0; i < allLinks.size(); i++) {
					System.out.println(allLinks.get(i).getAttribute("href"));
					if (allLinks.get(i).getAttribute("href") != null && (!allLinks.get(i).getAttribute("href").contains("javascript"))) {
						activeLinks.add(allLinks.get(i));
					}
				}
		        //get active links count
				System.out.println("active Links counts:  " + activeLinks.size());
				Thread.sleep(8000);
		//1.3. check the href url, http connection api
				//200 --->ok
					//404--->not found
					//400--->bad request
					//500--->internal error
				 for (int j = 0; j < activeLinks.size(); j++) {
					 HttpURLConnection connection= (HttpURLConnection) new URL(activeLinks.get(j).getAttribute("href")).openConnection();
					 connection.setConnectTimeout(8000);
					 connection.connect();
					String response= connection.getResponseMessage();//ok
					 connection.disconnect();
					 System.out.println(activeLinks.get(j).getAttribute("href")+"---->"+response);
				 }
				 
					WebElement ele	=driver.findElement(By.xpath("//button[@class=\"MuiButtonBase-root MuiPaginationItem-root MuiPaginationItem-sizeMedium MuiPaginationItem-outlined MuiPaginationItem-rounded MuiPaginationItem-previousNext css-19xm0h7-MuiButtonBase-root-MuiPaginationItem-root\"][@aria-label=\"Go to next page\"]"));
					ele.click();
					Thread.sleep(4000);
					System.out.println("Next Page");
	}
	}
		
}