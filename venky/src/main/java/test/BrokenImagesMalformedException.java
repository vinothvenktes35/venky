package test;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
public class BrokenImagesMalformedException {
	
	
	
   public static boolean isUrlValid(String url) {
	   
      try {
         URL obj = new URL(url);
         obj.toURI();
         return true;
      } catch (MalformedURLException e) {
         return false;
      } catch (URISyntaxException e) {
         return false;
      }
   }
   public static void main(String[] args) throws IOException {
	   WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		driver.get("https://www.devweb.proxtera.app/search/food/");
		
      String url = "https://www.devweb.proxtera.app/search/food/";
      if(isUrlValid(url)) {
         URL obj = new URL(url);
         //Opening a connection
         HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
         //Sending the request
         conn.setRequestMethod("GET");
         int response = conn.getResponseCode();
         if (response == 200) {
            //Reading the response to a StringBuffer
            Scanner responseReader = new Scanner(conn.getInputStream());
            StringBuffer buffer = new StringBuffer();
            while (responseReader.hasNextLine()) {
               buffer.append(responseReader.nextLine()+"\n");
            }
            responseReader.close();
            //Printing the Response
            System.out.println(buffer.toString());
         }
      }else {
         System.out.println("Enter valid URL");
      }
   }
}