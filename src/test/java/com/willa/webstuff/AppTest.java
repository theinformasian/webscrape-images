package com.willa.webstuff;

import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
// import org.openqa.selenium.By;
// import org.openqa.selenium.Keys;
// import org.openqa.selenium.WebDriver;
// import org.openqa.selenium.WebElement;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        // you could've at least given us 
        assertTrue( true );
    }

    @Test
    public void theGoogleTest()
    {
        // driver.get("https://google.com/ncr");
        // driver.findElement(By.name("q")).sendKeys("google" + Keys.ENTER);
        // WebElement firstResult = driver.findElement(By.cssSelector("h3>span"));
        // assertEquals(firstResult.getAttribute("textContent"), "Google");
    }

    @Test
    /** 
     * Should give the following input-output:
     * Src: https://www.zillow.com/homedetails/449-Coe-Ave-San-Jose-CA-95125/19577875_zpid/
     * Result: 449-Coe-Ave-San-Jose-CA-95125
     *  */    
    public void testExtractFolderName(){
            assertEquals(App.extractFolderName("https://www.zillow.com/homedetails/449-Coe-Ave-San-Jose-CA-95125/19577875_zpid/"), "449-Coe-Ave-San-Jose-CA-95125");
        }
    }
