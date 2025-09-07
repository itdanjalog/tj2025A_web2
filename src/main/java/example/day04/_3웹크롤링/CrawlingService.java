package example.day04._3웹크롤링;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CrawlingService {

    // 1. 한국 부동산 크롤링 // https://www.karnews.or.kr/robots.txt
    public List<String> task1() {
        try {
            List<String> titles = new ArrayList<>();
            for( int i = 1 ; i <= 10 ; i++ ) {
                String URL = "https://www.karnews.or.kr/news/articleList.html?page="+i+"&total=5043&box_idxno=&sc_section_code=S1N1&view_type=sm";
                Document doc = Jsoup.connect(URL).get();
                // 기사 제목이 들어있는 a[href*='articleView.html']
                Elements links = doc.select(".titles > a");
                for (Element link : links) {
                    String title = link.text().trim();
                    if (!title.isBlank()) {
                        titles.add(title);
                    }
                }
            }
            return titles;
        }catch (Exception e ){ System.out.println("e = " + e); }
        return null;
    }

    // 2. YES24 베스트셀러 크롤링 , https://www.yes24.com/robots.txt
    public List<Map<String, String>> task2() {
        try {
            List<Map<String, String>> books = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                String URL = "https://www.yes24.com/product/category/bestseller?categoryNumber=001&pageNumber="
                        + i + "&pageSize=24";
                Document doc = Jsoup.connect(URL).get();

                // 책 제목들
                Elements titleEls = doc.select(".info_name > .gd_name");
                // 책 가격들 (동일 순서로 매칭됨)
                Elements priceEls = doc.select(".info_price .yes_b");
                // 책 가격들 (동일 순서로 매칭됨)
                Elements imgEls = doc.select(".img_bdr .lazy");

                int count = Math.min(titleEls.size(), priceEls.size());
                for (int idx = 0; idx < count; idx++) {
                    String title = titleEls.get(idx).text().trim();
                    String price = priceEls.get(idx).text().trim();
                    String img = imgEls.get(idx).attr("data-original").trim();

                    if (!title.isBlank() && !price.isBlank()) {
                        Map<String, String> book = new HashMap<>();
                        book.put("title", title);
                        book.put("price", price);
                        book.put("img", img);
                        books.add(book);
                    }
                }
            }
            return books;

        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return null;
    }


    // https://weather.daum.net/robots.txt
    public Map<String, String> task3() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        Map<String, String> weather = new HashMap<>();

            driver.get("https://weather.daum.net/");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 위치
        WebElement loc = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector(".wrap_location .tit_location")));
        weather.put("위치", loc.getText());

        // 현재 온도
        WebElement temp = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector(".wrap_weather .num_deg")));
        weather.put("현재온도", temp.getText());

        // 날씨 상태
        WebElement status = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector(".wrap_weather .txt_sub")));
        weather.put("날씨", status.getText());

        // 어제와 비교
        WebElement compare = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector(".wrap_weather .txt_sub2")));
        weather.put("어제와비교", compare.getText());

        driver.quit();
        return weather;
    }



    public List<String> task4() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--disable-gpu");
        WebDriver driver = new ChromeDriver(options);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<String> reviews = new ArrayList<>();

        try {
            // 리뷰 페이지 URL 접속
            driver.get("https://cgv.co.kr/cnm/cgvChart/movieChart/89833");

            // 스크롤 반복 (예: 최대 5번 스크롤 → 필요시 조정)
            int scrollCount = 0;
            int maxScrolls = 5;
            int lastHeight = 0;

            while (scrollCount < maxScrolls) {
                // 1. 현재까지 로드된 리뷰 가져오기
                List<WebElement> reviewEls = driver.findElements(
                        By.cssSelector(".reveiwCard_txt__RrTgu")
                );

                for (WebElement el : reviewEls) {
                    String text = el.getText().trim();
                    if (!text.isBlank() && !reviews.contains(text)) {
                        reviews.add(text);
                    }
                }

                // 2. 스크롤 내리기 (화면 최하단으로)
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(1500); // 로딩 대기 (필요시 조정)

                // 3. 새로 로드되었는지 확인
                int newHeight = ((Number) js.executeScript(
                        "return document.body.scrollHeight")).intValue();
                if (newHeight == lastHeight) {
                    break; // 더 이상 새 데이터 없음 → 종료
                }
                lastHeight = newHeight;
                scrollCount++;
            }

        } catch (Exception e) {
            System.out.println("크롤링 오류: " + e.getMessage());
        } finally {
            driver.quit();
        }

        return reviews;
    }

}

