package verint.com;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
/*
 * @Author : VIKAS YADAV	
 */
public class SeleniumCode {

	WebDriver driver;
	String strURL="http://ms-vnext.net/UpdateArchive/";
	Map<String, Object> prefs = null;
	List<String> strFiles=null;
	
	@SuppressWarnings("deprecation")
	public void invokeBrowser() {
		System.setProperty("webdriver.chrome.driver","src/main/java/verint/com/Utils/chromedriver.exe");	
		strFiles=new ArrayList<String>();
		
		prefs = new HashMap<String, Object>();
				
		/*prefs.put("download.default_directory",System.getProperty("user.dir") + File.separator + "src"+File.separator+"main"+File.separator+"java"+File.separator+
				"verint"+File.separator+"com"+File.separator+"Utils"+File.separator+"DowloadedFiles");*/
		
		prefs.put("download.default_directory",System.getProperty("user.dir"));		
				
		// Adding cpabilities to ChromeOptions
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
				
		// Printing set download directory
		System.out.println(options.getExperimentalOption("prefs"));
		
		driver = new ChromeDriver(options);
		driver.get(strURL);
		driver.manage().window().maximize();
	}
	
	public List<String> downloadFileWithKBNumber(String selectedKbNumber) {
		
		List<WebElement> elmkbArticleID=driver.findElements(By.xpath("//table[@id='updates-table']//tr//td[2]//a[text()='"+selectedKbNumber+"']"));
		highLightElement(elmkbArticleID, driver);
		
		List<WebElement> elmFilelinks=driver.findElements(By.xpath("//table[@id='updates-table']//tr//td[2]//a[text()='"+selectedKbNumber+"']/parent::td//following-sibling::td[@class='files']//a"));
		for(WebElement web:elmFilelinks){
			strFiles.add(prefs.get("download.default_directory")+File.separator+web.getText());
			web.click();
		}
		return strFiles;
	}
	
	public List<String> downloadFileWithFileName(String selectedFileName) {
		//table[@id='updates-table']//tr//td[text()='Dynamic Update for Windows 10 Version Next (Build 15010) for x64-based Systems']
		List<WebElement> elmFileName=driver.findElements(By.xpath("//table[@id='updates-table']//tr//td[text()='"+selectedFileName+"']"));
		highLightElement(elmFileName, driver);
		
		List<WebElement> elmFilelinks=driver.findElements(By.xpath("//table[@id='updates-table']//tr//td[text()='"+selectedFileName+"']//following-sibling::td[@class='files']//a"));
		for(WebElement web:elmFilelinks){
			strFiles.add(prefs.get("download.default_directory")+File.separator+web.getText());
			web.click();
		}
		return strFiles;
	}
	
	public void highLightElement(List<WebElement> element,WebDriver driver){
		try{
			for(WebElement web:element){
				((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid red'", web);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println();
		}
	}

	
	
}
