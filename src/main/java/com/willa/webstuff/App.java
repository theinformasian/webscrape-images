// package com.willa.webstuff;
package com.willa.webstuff;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Hello world!
 *
 */
public class App 
{
    private static WebDriver driver;
    private static PrintWriter pw;
    private static Scanner in;
    private static File f;

    public static void main( String[] args )
    {
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        // driver = new ChromeDriver(options);
        driver = new FirefoxDriver();
        // playground(chrDriver);
        try {
            in = new Scanner(System.in);
            
            System.out.println("=============START=============");
            System.out.println("==========Selenium Go==========");
            
            System.out.println("Enter the URL you'd like to scrape (currently only zillow.com supported):");
            String url = in.nextLine();
            if (url.contains("zillow.com")){
                // TODO: allow user to specify file location
                f = new File("harvest/"+ extractFolderName(url)); // create the parent folder if DNE
                if(!f.mkdirs()) throw new FileNotFoundException();
                f = new File("harvest/"+ extractFolderName(url) + "/shellscript.sh"); // create script file
                driver.get(url);
            } 
            else throw new InvalidPathException(url, "this is not a zillow link");
            
            Thread.sleep(5000);
            List<WebElement> photoCarousel = driver.findElements(By.className("photo-tile-image"));
            Thread.sleep(5000);
            System.out.println("Number of Photos: " + photoCarousel.size()); 
            ArrayList<String> srcs = new ArrayList<>();

            for (WebElement e : photoCarousel) {
                if (e.getAttribute("src") != null)
                    srcs.add(e.getAttribute("src"));
                else
                    srcs.add(e.getAttribute("data-src"));
                System.out.println(e.getTagName() + ": src=\"" + srcs.get(srcs.size() - 1) + "\"");
            }
            System.out.println("=========Selenium Stop=========\n");
            System.out.println("=========Write wget script=========");
            pw = new PrintWriter(f);
            pw.println("#!/bin/sh");
            for (String s : srcs) { // replace last 7 characters (p_h.jpg) with uncropped_scaled_within_1536_1152.webp
                pw.println("wget --output-document=photo_" + srcs.indexOf(s) + ".webp " + s.substring(0, s.length() - 7)
                        + "uncropped_scaled_within_1536_1152.webp");
            }

             /** TODO: execute shellscript.sh, then delete the file */
             // TODO: chmod +x the shellscript
            
        } catch (Exception e) {
            System.out.println("=============ERROR=============");
            System.err.println(e);
            System.out.println("===============================");
        } finally {
            driver.quit();
            pw.close();
            in.close();
        }
    }
    private static void playground(WebDriver d) {
        try {
            // System.out.println("=============START=============");
            // d.get("https://www.selenium.dev/documentation/nl/");
            // Thread.sleep(1000);

            // // find child elements with xpath selectors
            // WebElement target = d.findElement(By.className("topics"));
            // List<WebElement> children = d.findElements(By.xpath("//*[@id='sidebar']/div[2]/ul/li"));
            // System.out.println("Found # of children: " + children.size());
            // for (WebElement c : children) System.out.println(c.getAttribute("title"));

            String test = "this is a test-p_h.jpg";
            System.out.println("compare:");
            System.out.println(test);
            System.out.println(test.substring(0, test.length()-7));

           
        } catch (Exception e) {
            System.out.println("=============ERROR=============");
            System.err.println(e);
            System.out.println("===============================");
        } finally {
            d.quit();
        }
    }
    /**
     * Generates a pretty folder name from input URL 
     */
    protected static String extractFolderName(String toParse) {
        StringTokenizer tokens = new StringTokenizer(toParse, "/");
        String curr = "";
        // eat through string until the next token is right after "homedetails"
        while(!curr.equals("homedetails")){
            curr = tokens.nextToken();
        }

        return tokens.nextToken(); // as per ex. should be "449-Coe-Ave-San-Jose-CA-95125"
    }
}
