package demo.wrappers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    ChromeDriver driver;

    public Wrappers(ChromeDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    } 
    @FindBy(css="input[name='q']")
    WebElement searchField;

    @FindBy(css="div[class*='zg-M3Z']")
    List<WebElement> sortByElements;

    @FindBy(css="div[class*='XQDdHH']")
    List<WebElement> starRatings;

    ////div[@class='UkUFwK']//ancestor::div[contains(@class,'yKfJKb')]//div[@class='KzDlHZ']
    
    @FindBy(xpath="//div[@class='UkUFwK']")
    List<WebElement> discounts;
    
    @FindBy(xpath="//div[@class='XqNaEv']//following-sibling::div[contains(text(),'above')]")
    List<WebElement> filters;

    @FindBy(xpath="//span[@class='Wphh3N']")
    List<WebElement> reviews;

    By productTitle = By.xpath(".//ancestor::div[contains(@class,'yKfJKb')]//div[@class='KzDlHZ']");
    By imgURL = By.xpath("(.//parent::div//preceding-sibling::a[@target='_blank'])[1]");
    By mugTitle = By.xpath(".//parent::div//preceding-sibling::a[@class='wjcEIp']") ;

    

    public void launchBrowser(String URL){
        driver.get(URL);
    }

    public void searchBox(String product){
        searchField.sendKeys(product,Keys.ENTER);
        searchWaits();
    }

    public void sortBy(String sort) throws InterruptedException{
        for(WebElement sortByElement:sortByElements){
            if(sortByElement.getText().equals(sort)){
                sortByElement.click();
                break;
            }
        }
        Thread.sleep(2000);
    }

    public int findTheValue(String str){
        int count=0;
        if(str.equals("Stars")){
            for(WebElement starElement:starRatings){
                if((Double.parseDouble(starElement.getText()))<=4){
                    count++;
                }
            }
           return count; 
        }
        return 0;
        
    }

    public StringBuilder getTitileAndDiscount(int num){
        StringBuilder text =new StringBuilder();
        for(WebElement discount:discounts){
            String discountPercentage = discount.getText();
            //System.out.println(discountPercentage);
            String[] value=discountPercentage.split("%");
            //System.out.println(value[0]);
            if((Integer.parseInt(value[0]))>num){
                String title=discount.findElement(productTitle).getText();
                text.append(title).append(" ").append(discountPercentage).append("\n");
            }
        }
        return text;
        
    }
    public void filterSelection(String str) throws InterruptedException{
        for(WebElement filter:filters){
            if(filter.getText().contains(str)){
                filter.click();
            }
        }
        Thread.sleep(2000);
    }

    public void topReviewCount(){
        ArrayList<Integer> reviewsCount= new ArrayList<Integer>();
            for(WebElement review:reviews){
                String s=review.getText();
                s=s.substring(1,s.length()-1);
                s = s.replace(",", ""); 
                reviewsCount.add(Integer.parseInt(s));          
            }
            Collections.sort(reviewsCount);
            //reviewsCount.forEach(s->System.out.println(s));
            Collections.reverse(reviewsCount);
            //reviewsCount.forEach(s->System.out.println(s));
            List<Integer> ls=reviewsCount.stream().limit(5).collect(Collectors.toList());
            //System.out.println("limited-----------");
            //ls.forEach(s->System.out.println(s));
            getTitleAndUrl(ls);
    }

    public void getTitleAndUrl(List<Integer> highestReviews){
        for(WebElement review:reviews){
            for(Integer count:highestReviews){
                if(review.getText().replace(",", "").contains(count.toString())){
                    String title =review.findElement(mugTitle).getText();
                    String imageURL = review.findElement(imgURL).getAttribute("href");
                    System.out.println("Product Title: "+title);
                    System.out.println("Image URL: "+imageURL);
                }
            }
        }
    }



    public void searchWaits(){
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("/search"));
    }

}
