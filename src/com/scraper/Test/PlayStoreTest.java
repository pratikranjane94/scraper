package com.scraper.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import com.scraper.fetch.ApkSiteDataFetching;
import com.scraper.fetch.PlayStoreDataFetching;
import com.scraper.googleSearch.PlayStoreUrlFetching;

public class PlayStoreTest {
	public static void main(String[] args) {
		PlayStoreUrlFetching purl = new PlayStoreUrlFetching();
		PlayStoreDataFetching psdf = new PlayStoreDataFetching();
		ApkSiteDataFetching asdf = new ApkSiteDataFetching();

		ArrayList<String> playStoreDetails = new ArrayList<String>();
		ArrayList<String> apkSiteDetails = new ArrayList<String>();

		String url = "";
		String line;
		String temp = "";
		boolean status = true;
		boolean psStatus = true;
		try {
			File file = new File("/home/bridgelabz6/Downloads/opt/game.csv"); // reading
																				// list
																				// of
																				// games
																				// from
																				// game.txt

			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			line = br.readLine();
			if (line == null) {
				System.out.println("file is empty");
			} else {
				line=br.readLine();
				while (line != null) {
					temp = line;
					String[] gname = line.split("\\^");					
					line=gname[1];			
					System.out.println("Game No: "+gname[0]+" Game Name: " + line);	//read games name
					url = purl.findUrl(line); //getting url for game
					line = br.readLine();

					// exception handling if url not found
					if (url == null) {
						try {
							File notFetched = new File("/home/bridgelabz6/Downloads/opt/UrlnotFetched.csv");

							// if file doesn't exists, then create it
							if (!notFetched.exists()) {
								notFetched.createNewFile();
							}

							FileWriter fw = new FileWriter(notFetched.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							bw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					} // end of handling in url fetching

					// getting playstore site data
					playStoreDetails = psdf.getPlayStoreData(url);

					// handling exception in playstore details
					if (playStoreDetails.equals(null)) {
						try {
							File notFetched = new File("/home/bridgelabz6/Downloads/opt/PlayStoreNotFetched.csv");

							// if file doesn't exists, then create it
							if (!notFetched.exists()) {
								notFetched.createNewFile();
							}

							FileWriter fw = new FileWriter(notFetched.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							bw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					} // end of handling in playstore details

					// creating csv file of playstore data
					psStatus = psdf.createCsv(playStoreDetails);
					
					if (psStatus == false) {
						try {
							File notFetched = new File("/home/bridgelabz6/Downloads/opt/PlayStoreNotFetched.csv");
							File asd = new File("/home/bridgelabz6/Downloads/opt/gameInfo.csv");
							// if file doesn't exists, then create it
							if (!notFetched.exists()) {
								notFetched.createNewFile();
							}
							if (!asd.exists()) {
								asd.createNewFile();
							}
							FileWriter asdfw = new FileWriter(asd.getAbsoluteFile(), true);
							BufferedWriter asdbw = new BufferedWriter(asdfw);
							FileWriter fw = new FileWriter(notFetched.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							asdbw.newLine();
							bw.close();
							asdbw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					}

					// getting playstore package name
					String pack = psdf.getPackage(playStoreDetails);

					// getting apk-dl site data
					apkSiteDetails = asdf.createApkSiteDetails(pack);

					// handling exception in apksite details
					if (apkSiteDetails == null) {
						try {
							File notFetched = new File("/home/bridgelabz6/Downloads/opt/apkDlNotFetched.csv");
							File asd = new File("/home/bridgelabz6/Downloads/opt/gameInfo.csv");
							// if file doesn't exists, then create it
							if (!notFetched.exists()) {
								notFetched.createNewFile();
							}
							if (!asd.exists()) {
								asd.createNewFile();
							}
							FileWriter asdfw = new FileWriter(asd.getAbsoluteFile(), true);
							BufferedWriter asdbw = new BufferedWriter(asdfw);
							FileWriter fw = new FileWriter(notFetched.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							asdbw.newLine();
							bw.close();
							asdbw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					} // end of handling in apk-dl

					
					// creating csv file of apk-dl site details
					status = asdf.createCsv(apkSiteDetails);
					System.out.println("Status:" + status);
					if (status == false) {
						try {
							File notFetched = new File("/home/bridgelabz6/Downloads/opt/apkDlNotFetched.csv");
							File asd = new File("/home/bridgelabz6/Downloads/opt/gameInfo.csv");
							
							// if file doesn't exists, then create it
							if (!notFetched.exists()) {
								notFetched.createNewFile();
							}
							if (!asd.exists()) {
								asd.createNewFile();
							}
							FileWriter asdfw = new FileWriter(asd.getAbsoluteFile(), true);
							BufferedWriter asdbw = new BufferedWriter(asdfw);
							FileWriter fw = new FileWriter(notFetched.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							asdbw.newLine();
							bw.close();
							asdbw.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						continue;
					}

				} // end of while
			} // end of else
			br.close();
		} // end of try
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}// end of main

}
