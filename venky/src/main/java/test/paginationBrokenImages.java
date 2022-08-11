package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.Test;

import freemarker.template.utility.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;

public class paginationBrokenImages {

	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriverManager.edgedriver().setup();
		WebDriver driver = new EdgeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

		driver.get("https://www.devweb.proxtera.app/search/packaging");

		Thread.sleep(4000);

		String nextButtonClassName = driver.findElement(By.xpath(
				"//button[@class=\"MuiButtonBase-root MuiPaginationItem-root MuiPaginationItem-sizeMedium MuiPaginationItem-outlined MuiPaginationItem-rounded MuiPaginationItem-previousNext css-19xm0h7-MuiButtonBase-root-MuiPaginationItem-root\"][@aria-label=\"Go to next page\"]"))
				.getAttribute("class");

		while (!nextButtonClassName.contains("disabled")) {

			// 2.1. get all list of all images

			List<WebElement> allImages = driver.findElements(By.tagName("img"));
			// get total images count
			System.out.println("all  images counts:  " + allImages.size());
			List<WebElement> activeImages = new ArrayList<WebElement>();
			Thread.sleep(4000);

			// 2.2. iterate link list : exclude all images do not have href attribute

			for (int k = 0; k < allImages.size(); k++) {
				System.out.println(allImages.get(k).getAttribute("src"));
				if (allImages.get(k).getAttribute("src") != null
						&& (!allImages.get(k).getAttribute("src").contains("javascript"))) {
					activeImages.add(allImages.get(k));
				}
			}

			// get active Images count
			System.out.println("active  images counts:  " + activeImages.size());
			Thread.sleep(4000);
			// 2.3. check the href url, http connection api
			// 200 --->ok
			// 404--->not found
			// 400--->bad request
			// 500--->internal error

			for (int l = 0; l < activeImages.size(); l++) {
				try {
					HttpURLConnection connection = (HttpURLConnection) new URL(activeImages.get(l).getAttribute("src"))
							.openConnection();
					connection.connect();

					String response = connection.getResponseMessage();// ok
					if (!response.equalsIgnoreCase("ok")) {
						System.out.println(response);
					}

					connection.disconnect();

					System.out.println(activeImages.get(l).getAttribute("src") + "---->" + response);
				} catch (MalformedURLException e) {
				}
				;
			}

			WebElement ele = driver.findElement(By.xpath(
					"//button[@class=\"MuiButtonBase-root MuiPaginationItem-root MuiPaginationItem-sizeMedium MuiPaginationItem-outlined MuiPaginationItem-rounded MuiPaginationItem-previousNext css-19xm0h7-MuiButtonBase-root-MuiPaginationItem-root\"][@aria-label=\"Go to next page\"]"));
			ele.click();
			Thread.sleep(4000);
			System.out.println("Next Page");

		}

	}
}
