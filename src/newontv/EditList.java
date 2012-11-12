/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dthoma31
 */
public class EditList {

    static private LinkedList<Series> shows;
    static private BufferedReader in;
    static private String filename;

    static void run(String filename) {
        System.out.print("Reading cache... ");
        Cache.read();
        System.out.print(" done!\n");

        EditList.filename = filename;

        LinkedList<String> temp = new LinkedList<String>();
        try {
            System.out.print("Reading shows from file...");
            Scanner s = new Scanner(new BufferedReader(new FileReader(filename)));
            while (s.hasNextLine()) {
                temp.add(s.nextLine());
            }
            s.close();
            System.out.print(" done!\n");
        } catch (FileNotFoundException e) {
            System.err.println("\nError:  " + e.getMessage());
            System.exit(1);
        }

        System.out.print("Downloading show information...");
        shows = new LinkedList<Series>();
        for (int i = 0; i < temp.size(); i++) {
            shows.add(new Series(temp.get(i)));
        }
        temp.clear();
        System.out.print(" done!\n");

        in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("\nWhat do you want to do?");
            System.out.println("1.  View List");
            System.out.println("2.  Add Show");
            System.out.println("3.  Remove Show");
            System.out.println("4.  Exit Program");

            String answer = null;
            try {
                answer = in.readLine();
            } catch (IOException ex) {
                Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (answer.equals("1")) {
                EditList.viewList();
            } else if (answer.equals("2")) {
                System.out.println("What is the name of the show you'd like to add?");
                try {
                    EditList.addShow(in.readLine());
                } catch (IOException ex) {
                    Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("That show has successfully been added to your list.");
            } else if (answer.equals("3")) {
                EditList.viewList();
                System.out.println("Which show would you like to remove?");
                try {
                    EditList.removeShow(Integer.parseInt(in.readLine()));
                } catch (IOException ex) {
                    Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("That show has successfully been removed from your list.");
            } else {
                EditList.saveAndExit();
            }
        }

    }

    static private void addShow(String name) {
        try {
            URL url = new URL(NewOnTV.MirrorPath + "/api/GetSeries.php?seriesname=" + name.replaceAll(" ", "%20") + "&language=" + NewOnTV.language);
            Scanner t = null;
            try {
                t = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));
            } catch (IOException ex) {
                Logger.getLogger(Series.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
            LinkedList<Series> tempSeries = new LinkedList<Series>();
            while (t.hasNextLine()) {
                String tag = t.nextLine();
                String[] splits = tag.split("<");
                if (splits.length > 1) {
                    splits = splits[1].split(">");

                    tag = null;
                    if (!splits[0].startsWith("/")) {
                        tag = splits[0];
                    }
                    String data = null;
                    if (splits.length > 1) {
                        data = splits[1];
                    }
                    if (tag != null && data != null) {
                        if (tag.equals("seriesid")) {
                            tempSeries.add(new Series(data));
                        }
                    }
                }
            }
            t.close();
            int answer = 2;
            Series temp = null;
            boolean match = true;

            if (tempSeries.size() > 1) {
                while (answer != 0) {
                    System.out.println("You said \"" + name + "\". Which one matches what you want?");
                    for (int i = 0; i < tempSeries.size(); i++) {
                        System.out.println(i + ".  " + tempSeries.get(i).getSeriesName());
                    }
                    try {
                        int tempest = Integer.parseInt(in.readLine());
                        if (tempest < 0 || tempest >= tempSeries.size()) {
                            throw new IOException();
                        }
                        temp = tempSeries.get(tempest);
                        while (answer != 0 && answer != 1) {
                            System.out.println(temp.getSeriesName());
                            System.out.println("\tFirst aired:  " + temp.getFirstAired());
                            System.out.println("\tOverview:  " + temp.getOverview());
                            System.out.println("Are you sure this is the show you want?\n\t0.  Yes\n\t1.  No");
                            try {
                                answer = Integer.parseInt(in.readLine());

                            } catch (NumberFormatException ex) {
                                System.out.println("ENTER A NUMBER!");
                            } catch (IOException ex) {
                            }
                            if (answer < 0 || answer > 1) {
                                System.out.println("THAT WASN'T ONE OF YOUR CHOICES!");
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("THAT WASN'T ONE OF YOUR CHOICES!");
                    } catch (NumberFormatException ex) {
                        System.out.println("ENTER A NUMBER!");
                    }
                }
            } else if (tempSeries.size() > 0) {
                temp = tempSeries.get(0);
            } else {
                System.out.println("No matches found. Try again.");
                match = false;
            }

            if (match) {
                shows.add(temp);
                EditList.sort();
                Cache.write(temp);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    static private void removeShow(int i) {
        shows.remove(i);
    }

    static private void viewList() {
        for (int i = 0; i < shows.size(); i++) {
            String temp = shows.get(i).getSeriesName();
            System.out.println(i + ".  " + temp.replaceAll("&amp;", "&"));
        }
        System.out.print("\n");
    }

    static private void saveAndExit() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.print("Writing to file...");

        EditList.sort();

        try {
            PrintStream out = new PrintStream(new File(filename));
            for (int i = 0; i < shows.size(); i++) {
                out.println(shows.get(i).getSeriesid());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditList.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(
                    1);
        }
        System.out.print(" done!\n");
        System.exit(0);
    }

    static private void sort() {
        for (int i = 0; i < shows.size(); i++) {
            Series temp = shows.get(i);
            int iHole = i;
            while (iHole > 0 && shows.get(iHole - 1).getSeriesName().compareTo(temp.getSeriesName()) > 0) {
                shows.set(iHole, shows.get(iHole - 1));
                iHole--;
            }
            shows.set(iHole, temp);

        }
    }
}
