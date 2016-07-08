package com.scraper.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import com.scraper.FileStreaming.FileStreaming;
import com.scraper.fetch.ApkSiteDataFetching;
import com.scraper.fetch.PlayStoreDataFetching;
import com.scraper.googleSearch.PlayStoreUrlFetching;

public class PlayStoreTest 
{
	public static void main(String[] args) throws Exception
	{
		PlayStoreUrlFetching purl=new PlayStoreUrlFetching();
		PlayStoreDataFetching psdf=new PlayStoreDataFetching();
		ApkSiteDataFetching asdf=new ApkSiteDataFetching();
		
		ArrayList<String> playStoreDetails=new ArrayList<String>();
		ArrayList<String> apkSiteDetails=new ArrayList<String>();
		
		String url="";
		
		String workingDirectory = System.getProperty("/home/bridgelabz6/Downloads");			
		File file = new File(workingDirectory, "game.txt");							
		System.out.println("Final filepath : " + file.getAbsolutePath());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		if(line==null)
		{
			System.out.println("file is empty");
		}
		else
		{
			while(line!=null)
			{
				System.out.println("game name= "+line);
				url=purl.findUrl(line);
				line=br.readLine();
		
		
		//getting playstore site data
		playStoreDetails=psdf.getPlayStoreData(url);
		
		//creating json of playstore data
		psdf.createJson(playStoreDetails);
		
		//getting playstore package name
		String pack=psdf.getPackage(playStoreDetails);
		
		//getting apk-dl site data
		apkSiteDetails=asdf.createApkSiteDetails(pack);
		
		//creating json of dl-apk site details
		asdf.createJson(apkSiteDetails);		
		
			}
		}
		
		
		
	}
	
}
