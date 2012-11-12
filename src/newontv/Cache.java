/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoharnu
 */
public class Cache {

    static LinkedList<Series> shows = new LinkedList<Series>();
    private static final String delimeter = "%";

    static void read() {
        File cache = new File("cache");
        if (!cache.exists()) {
            cache.mkdir();
        }
        File[] listFiles = cache.listFiles();
        for (int i = 0; i < listFiles.length; i++) {
            readSeries(listFiles[i]);
        }
    }

    static void write(Series series) {
        File file = new File(new File("cache"), series.getSeriesid());
        if (!file.exists()) {
            try {
                PrintStream cache = new PrintStream(file);
                cache.print(series.getLanguage() + delimeter
                        + series.getSeriesName() + delimeter
                        + series.getOverview() + delimeter
                        + series.getFirstAired() + delimeter
                        + series.getImdb_id() + delimeter
                        + series.getAirTime() + delimeter
                        + series.getNetwork());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    static private void readSeries(File temp) {
        Series series = new Series();
        series.setSeriesid(temp.getName());
        try {
            Scanner s = new Scanner(new BufferedReader(new FileReader(temp)));

            String line = s.nextLine();
            //System.out.println(line);
            String[] splits = line.split(delimeter);
            series.setLanguage(splits[0]);
            series.setSeriesName(splits[1].replaceAll("&amp;", "&"));
            series.setOverview(splits[2]);
            series.setFirstAired(splits[3]);
            series.setImdb_id(splits[4]);
            series.setAirTime(splits[5]);
            series.setNetwork(splits[6]);

            if (s != null) {
                s.close();
            }
            shows.add(series);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Cache.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
