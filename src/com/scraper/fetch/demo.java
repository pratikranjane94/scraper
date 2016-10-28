package com.scraper.fetch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.UnknownHostException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class demo {
	public static void main(String[] args) {
		demo d = new demo();
		String pack, temp;
		int st=1;
		try {
			File file = new File("/home/bridgelabz6/Downloads/opt/pack.txt");
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			pack = br.readLine();
			temp = pack;
			if (pack == null) {
				System.out.println("file is empty");
			} else {
				while (pack != null) {
					System.out.println("Game Name= " + pack);
					st=d.getPackage(pack);
					pack = br.readLine();
					if(st==0)
					{
						try{
							File down = new File("/home/bridgelabz6/Downloads/opt/noDownloadLink.csv");
							FileWriter fw = new FileWriter(down.getAbsoluteFile(), true);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.append(temp);
							bw.append("^");
							bw.newLine();
							bw.close();
							}catch(Exception e)
							{
								e.printStackTrace();
							}
					}
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public int getPackage(String pack) {
		try {
			String apk = "http://download.freeapk.ru/en.php?package=";
			String downSite = apk.concat(pack);
			Document doc = Jsoup.connect(downSite).userAgent("Chrome/47.0.2526.80").timeout(10000).get();
			String info = doc.getElementById("applink").attr("href");
			System.out.println("Info:" + info);
			if (info.equals("")) {
				info = info.replaceAll(info, "Broken Link");
			}
			System.out.println("Info:" + info);
			File down = new File("/home/bridgelabz6/Downloads/opt/downSite.csv");
			FileWriter fw = new FileWriter(down.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.append(pack);
			bw.append("^");
			bw.append(info);
			bw.append("^");
			bw.newLine();
			bw.close();
		} catch (UnknownHostException e) {
			try {
				Thread.sleep(1000);
			} catch (Exception e2) {
				e.printStackTrace();
			}
			demo d = new demo();
			d.getPackage(pack);
		}catch (Exception e) {
			return 0;
		}
		return 1;
	}
}