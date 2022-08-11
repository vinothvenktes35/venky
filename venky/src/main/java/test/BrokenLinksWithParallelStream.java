package test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrokenLinksWithParallelStream {
	
	public static void main(String[] args) throws InterruptedException {
	WebDriverManager.chromedriver().setup();
	WebDriver driver = new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	driver.manage().deleteAllCookies();
	driver.get("https://www.devweb.proxtera.app/search/food");
	Thread.sleep(8000);
	
	List<WebElement> links = driver.findElements(By.tagName("a"));
	System.out.println("No of links are" + links.size());
	List<String> urlList = new ArrayList<String>();
	
	for (WebElement e : links) {
		String url=e.getAttribute("href");
		urlList.add(url);
		//checkBrokenLink(url);
	}
	
	long startTime = System.currentTimeMillis();
	urlList.parallelStream().forEach(e->checkBrokenLink(e));
	long endTime = System.currentTimeMillis();
	System.out.println("Total time taken"  + (endTime - startTime));
	driver.quit();
	}
	public static void checkBrokenLink(String linkUrl) {
		try {
			URL url = new URL(linkUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5000);
			httpUrlConnection.connect();
			
			if(httpUrlConnection.getResponseCode() >=400) {
				System.out.println(linkUrl + "---->" + httpUrlConnection.getResponseMessage()+ "   is a broken link");
			}else {
				System.out.println(linkUrl + "---->" + httpUrlConnection.getResponseMessage());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	
}
}