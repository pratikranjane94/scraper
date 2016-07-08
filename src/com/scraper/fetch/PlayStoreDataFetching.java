package com.scraper.fetch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PlayStoreDataFetching 
{ 
	  public ArrayList<String> getPlayStoreData(String url) 	//displaying game info from playstore
		{
		  
		  ArrayList<String> playStoreDetails=new ArrayList<>();
		    try {
		    	// fetching the document over HTTP
		    	//String url="https://play.google.com/store/apps/details?id=com.fingersoft.failhard";
		    	Document doc = Jsoup.connect(url).userAgent("Chrome/47.0.2526.80").timeout(10000).get();
		   		
		   		// getting game title class
			    Elements t = doc.getElementsByClass("document-title");
			    
			    
			    //getting game info class
			    Elements g=doc.getElementsByClass("document-subtitle");
			    Elements info = doc.getElementsByClass("meta-info");  

			    //getting game package name
			    String pack = url.substring(url.indexOf("id=")+3);		    
			    
			    //getting game info
			    String title=t.select("[class=id-app-title]").text(); 
			    String genre=g.select("[itemprop=genre]").text();
			    String version=info.select("[itemprop=softwareVersion]").text();
			    String size=info.select("[itemprop=fileSize]").text();
			    String pDate=info.select("[itemprop=datePublished]").text();

			    if(title.equals("") && genre.equals("") && version.equals("") && size.equals("") && pDate.equals("") && pack.equals("") )
			    {
			    	System.out.println("NO data is fetched");
			    }
			    else
			    {
			    playStoreDetails.add(title);
			    playStoreDetails.add(genre);
			    playStoreDetails.add(size);
			    playStoreDetails.add(version);
			    playStoreDetails.add(pDate);
			    playStoreDetails.add(pack);
			    System.out.println("----------Play Store Data--------------");
			    // showing game name
			    System.out.println("Title of Game: " + title);
			    // showing genre of game
			    System.out.println("Genre:"+genre);
			    //showing software version
			    System.out.println("Version: "+version);
			    //showing file size
			    System.out.println("File Size: "+size);
			    //showing publish date
			    System.out.println("Update date: "+pDate);
			    //showing package name
			    System.out.println("Package Name:"+pack);
			    }
			 
		    } 
		    catch (IOException e) 
		    {
		    e.printStackTrace();
		    }
		    return playStoreDetails;
		}	
	  
	  public void createJson(ArrayList<String> playStoreDetails) //creating json file of fetched info
	  {
		  JSONArray psObj = new JSONArray();
		  JSONObject obj=new JSONObject();
		  String title=playStoreDetails.get(0);
		  String genre=playStoreDetails.get(1);
		  String size=playStoreDetails.get(2);
		  String version=playStoreDetails.get(3);
		  String pDate=playStoreDetails.get(4);
		  String pack=playStoreDetails.get(5);
		  try
		  {
			psObj.put(title);
			psObj.put(genre);
			psObj.put(size);
			psObj.put(version);
			psObj.put(pDate);
			psObj.put(pack);
			obj.put("Playstore",psObj);
			System.out.println(obj);
			File file = new File("/home/bridgelabz6/Downloads/opt/eclipse-installer/scraper/Scraper/src/com/scraper/asd.csv");

				// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(obj.toString());
			//bw.append(",");
			bw.newLine();
			bw.close();

			System.out.println("Done");
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
		  }
		 
	  }
	  
	  public String getPackage(ArrayList<String> playStoreDetails)
	  {
		  String pack=playStoreDetails.get(5);
		  return pack;
	  }
	  
}	
