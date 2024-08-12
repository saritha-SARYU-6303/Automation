package Assignments;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class WebPageScreenShotsTest {
	@DataProvider
	public Object[] getdata() {
		Object[] obj = new Object[3];
		obj[0] = "chrome";
		obj[1] = "edge";
		obj[2] = "firefox";
		return obj;
	}

	@Test(dataProvider = "getdata")
	public static void screenShot_Test(String browser) throws IOException {
		WebDriver driver = null;

		switch (browser) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--disable-notifications");
			chromeOptions.addArguments("--disable-popup-blocking");
			driver = new ChromeDriver(chromeOptions);
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("Invalid browser name!");
			break;
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(25));
		driver.manage().window().maximize();
		String date = new Date().toString();
		String da = date.replace(" ", "_");
		String db = date.replace(":", "_");
		String file = da + "_" + browser;
		System.out.println(file);
		driver.get("https://www.getcalley.com/page-sitemap.xml");
		for (int i = 1; i <= 5; i++) {
			try {
				driver.findElement(By.xpath("//table[@id='sitemap']/tbody/tr[" + i + "]/td")).click();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Screenshot sh = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
					.takeScreenshot(driver);
			File des = new File("./sreenshot/" + browser + "_" + i + db + ".png");
			ImageIO.write(sh.getImage(), "PNG", des);
			driver.navigate().back();
		}
		driver.close();

	}	

}
