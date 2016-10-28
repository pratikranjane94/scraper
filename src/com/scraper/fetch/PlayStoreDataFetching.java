/*File Name		: PlayStoreDataFetching.java
 *Created By	: PRATIK RANJANE
 *Purpose		: Getting game information from PlayStore such as Game name, Version, Size,
 *				  Publish date, Package name and storing it into CSV file.
 * */
package com.scraper.fetch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class PlayStoreDataFetching {
	public ArrayList<String> getPlayStoreData(String url)// displaying game info from PlayStore
	{

		ArrayList<String> playStoreDetails = new ArrayList<>();
		// ArrayList<String> err=new ArrayList<String>();
		try {
			// fetching the document over HTTP
			Document doc = Jsoup.connect(url).userAgent("Chrome/47.0.2526.80").timeout(10000).get();

			// getting game title class to fetch title
			Elements t = doc.getElementsByClass("document-title");

			// getting game info class to fetch version size publish date
			Elements g = doc.getElementsByClass("document-subtitle");
			Elements info = doc.getElementsByClass("meta-info");

			// getting game package name
			String pack = url.substring(url.indexOf("id=") + 3);

			// getting game info
			String title = t.select("[class=id-app-title]").text();
			String genre = g.select("[itemprop=genre]").text();
			String version = info.select("[itemprop=softwareVersion]").text();
			String size = info.select("[itemprop=fileSize]").text();
			String pDate = info.select("[itemprop=datePublished]").text();
			
			// if no data fetched
			if (title.equals("") && genre.equals("") && version.equals("") && size.equals("") && pDate.equals("")
					&& pack.equals("")) {
				return null;
			} else {
				playStoreDetails.add(title);
				playStoreDetails.add(genre);
				playStoreDetails.add(size);
				playStoreDetails.add(version);
				playStoreDetails.add(pDate);
				playStoreDetails.add(pack);
				
				// showing game name
				System.out.println("----------Play Store Data--------------");
				System.out.println("Title of Game: " + title);
				// showing genre of game
				System.out.println("Genre:" + genre);
				// showing software version
				System.out.println("Version: " + version);
				// showing file size
				System.out.println("File Size: " + size);
				// showing publish date
				System.out.println("Update date: " + pDate);
				// showing package name
				System.out.println("Package Name:" + pack);
			}

		} catch (UnknownHostException u) {
			try {
				Thread.sleep(1000);	//wait for a second
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//if unknown host exception occurs call the same method again
			PlayStoreDataFetching asdf = new PlayStoreDataFetching();
			asdf.getPlayStoreData(url);

		}

		catch (Exception e) {

			return null;
		}
		return playStoreDetails;
	}

			// creating CSV file for playStore Data
	public boolean createCsv(ArrayList<String> playStoreDetails)
	{
		String title = playStoreDetails.get(0);
		String genre = playStoreDetails.get(1);
		String size = playStoreDetails.get(2);
		String version = playStoreDetails.get(3);
		String pDate = playStoreDetails.get(4);
		String pack = playStoreDetails.get(5);
		try {
			//file path where data to be save
			File file = new File("/home/bridgelabz6/Downloads/opt/gameInfo.csv"); 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				bw.append("PlayStore Title^Genre^Size^Version^Publish Date^Package^");
				bw.append("Apk Title^Genre^Size^Version^Publish Date^Download Link^");
				bw.newLine();
			}

			else{
			//adding data to file
				//bw.append(title+"^"+genre+"^"+size+"^"+version+"^"+pDate+"^"+pack+"^");
				bw.append(title);
				bw.append("^");
				bw.append(genre);
				bw.append("^");
				bw.append(size);
				bw.append("^");
				bw.append(version);
				bw.append("^");
				bw.append(pDate);
				bw.append("^");
				bw.append(pack);
				bw.append("^");
				bw.close();
			}
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public String getPackage(ArrayList<String> playStoreDetails) {
		// getting package name from PlayStore URL
		String pack = playStoreDetails.get(5); 
		return pack;
	}

}
