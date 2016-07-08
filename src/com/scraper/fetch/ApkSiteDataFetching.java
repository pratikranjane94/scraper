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

public class ApkSiteDataFetching 
{
	public ArrayList<String> createApkSiteDetails(String pack)
	{ 
		ArrayList<String> apkSiteDetails=new ArrayList<String>();
		String apk="https://apk-dl.com/";
		String apkSite=apk.concat(pack);
		//System.out.println(apkSite);
		
		try {
	    	// fetch the document over HTTP
	    	Document doc = Jsoup.connect(apkSite).userAgent("Chrome/47.0.2526.80").timeout(10000).get();
	    	
		    //getting info class
		    Elements info=doc.getElementsByClass("info");
		    String genre=info.select("[itemprop=applicationCategory]").attr("content"); 	//getting genre
		    String title=info.select("[itemprop=name]").attr("content");					//getting title
		    String version=info.select("[itemprop=softwareVersion]").attr("content");		//getting version
		    String pDate=info.select("[itemprop=datePublished]").attr("content");			//getting publish date
		    
		    //Elements doc1=doc.getElementsByClass("text-center");
		    String ss=doc.select("a[rel]").text();											//getting size	
		    String sSize = ss.substring(ss.indexOf("MB")-5, ss.indexOf("MB"));
		    
		    //getting size from data
		    double fSize=Double.parseDouble(sSize);											//converting string to double
		    int size1=(int) Math.round(fSize);												//rounding size
		    String size=Integer.toString(size1);											//converting back to String
		    size=size.concat("M");															//concatenating MB to size
		    
		    String downLink=doc.getElementsByClass("btn-md").select("[rel=nofollow]").attr("href");//getting download link
		    downLink=("https:").concat(downLink.trim());
		    Document doc1 = Jsoup.connect(downLink).userAgent("Chrome/47.0.2526.80").timeout(10000).get();
			 
			String downUrl=doc1.getElementsByTag("p").select("a[href]").attr("href");
		    System.out.println("");
		    System.out.println("----------Dl-apk site data--------------");
		    //getting game info
		    System.out.println("Title: "+title);
		    System.out.println("Apk Site genre: "+genre);
		    System.out.println("Version: "+version);
		    System.out.println("Published Date: "+pDate);
		    System.out.println("Size: "+size);
		    if(title.equals("") && genre.equals("") && version.equals("") && size.equals("") && pDate.equals("") )
		    {
		    	System.out.println("NO data is fetched");
		    }
		    else
		    {
		    	 apkSiteDetails.add(title);
		    	 apkSiteDetails.add(genre);
		    	 apkSiteDetails.add(size);
		    	 apkSiteDetails.add(version);
		    	 apkSiteDetails.add(pDate);
		    	 apkSiteDetails.add(downLink);
		    }
	    } 
	    catch (IOException e) 
	    {
	    e.printStackTrace();
	    }
		return apkSiteDetails;	
	}
	public void createJson(ArrayList<String> apkSiteDetails) //creating json file of fetched info
	  {
		  JSONArray apkObj=new JSONArray();
		  JSONObject obj = new JSONObject();
		  String title=apkSiteDetails.get(0);
		  String genre=apkSiteDetails.get(1);
		  String size=apkSiteDetails.get(2);
		  String version=apkSiteDetails.get(3);
		  String pDate=apkSiteDetails.get(4);
		  String downUrl=apkSiteDetails.get(5);
		  try
		  {
			  apkObj.put(title);
			  apkObj.put(genre);
			  apkObj.put(size);
			  apkObj.put(version);
			  apkObj.put(pDate);
			  apkObj.put(downUrl);
			  obj.put("dl-apk", apkObj);
		    System.out.println(apkObj);
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
	/*public String getDownloadLink(ArrayList<String> apkSiteDetails)
	{
		String url="";
		String downLink;
		try
		{
		 downLink=apkSiteDetails.get(5);
		 Document doc1 = Jsoup.connect(downLink).userAgent("Chrome/47.0.2526.80").timeout(10000).get();
		 
		 url=doc1.getElementsByTag("p").select("a[href]").attr("href");
		// System.out.println("url:"+url);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return url;
	}*/
	
	
}

