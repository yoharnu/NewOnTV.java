/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author dthoma31
 */
public class CheckNew {

    static private LinkedList<Episode> eight;
    static private LinkedList<Episode> eightThirty;
    static private LinkedList<Episode> nine;
    static private LinkedList<Episode> nineThirty;
    static private LinkedList<Episode> ten;
    static private LinkedList<Episode> tenThirty;
    static private LinkedList<Episode> eleven;
    static private LinkedList<Episode> elevenThirty;

    static void run(String filename) {
        Cache.read();
        Scanner s = null;
        eight = null;
        eightThirty = null;
        nine = null;
        nineThirty = null;
        ten = null;
        tenThirty = null;
        try {
            s = new Scanner(new BufferedReader(new FileReader(filename)));

            while (s.hasNextLine()) {
                String id = s.nextLine();
                Series series = new Series(id);
                series.downloadEpInfo();
                if (series.getEpisodes() != null) {
                    Cache.write(series);
                    for (int i = 0; i < series.getEpisodes().size(); i++) {
                        Episode temp = series.getEpisodes().get(i);

                        if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("8:00PM")
                                || temp.getAirTime().matches("20:00")) {
                            if (eight == null) {
                                eight = new LinkedList<Episode>();
                            }
                            CheckNew.eight.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("8:30PM")
                                || temp.getAirTime().matches("20:30")) {
                            if (eightThirty == null) {
                                eightThirty = new LinkedList<Episode>();
                            }
                            CheckNew.eightThirty.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("9:00PM")
                                || temp.getAirTime().matches("21:00")) {
                            if (nine == null) {
                                nine = new LinkedList<Episode>();
                            }
                            CheckNew.nine.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("9:30PM")
                                || temp.getAirTime().matches("21:30")) {
                            if (nineThirty == null) {
                                nineThirty = new LinkedList<Episode>();
                            }
                            CheckNew.nineThirty.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("10:00PM")
                                || temp.getAirTime().matches("22:00")) {
                            if (ten == null) {
                                ten = new LinkedList<Episode>();
                            }
                            CheckNew.ten.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("10:30PM")
                                || temp.getAirTime().matches("22:30")) {
                            if (tenThirty == null) {
                                tenThirty = new LinkedList<Episode>();
                            }
                            CheckNew.tenThirty.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("11:00PM")
                                || temp.getAirTime().matches("23:00")) {
                            if (eleven == null) {
                                eleven = new LinkedList<Episode>();
                            }
                            CheckNew.eleven.add(temp);
                        } else if (temp.getAirTime().replaceAll(" ", "").equalsIgnoreCase("11:30PM")
                                || temp.getAirTime().matches("23:30")) {
                            if (elevenThirty == null) {
                                elevenThirty = new LinkedList<Episode>();
                            }
                            CheckNew.elevenThirty.add(temp);
                        } else {
                            System.err.println("Error: Unknown time (" + series.getSeriesName() + "  " + series.getAirTime() + ")");
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error:  File not found");
            System.exit(1);
        } finally {
            if (s != null) {
                s.close();
            }
        }
        System.out.print("<span style=\"font-family: Arial, Helvetica, Verdana, sans-serif\">");
        if (CheckNew.eight == null && CheckNew.eightThirty == null && CheckNew.nine == null && CheckNew.nineThirty == null && CheckNew.ten == null && CheckNew.tenThirty == null && CheckNew.eleven == null && CheckNew.elevenThirty == null) {
            System.out.print("There is nothing new on tonight.");
        } else {
            System.out.print("<dl>");
            if (eight != null) {
                System.out.print("<dt>8:00 PM<dt><dd>");
                for (int i = 0; i < eight.size(); i++) {
                    Episode temp = eight.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (eightThirty != null) {
                System.out.print("<dt>8:30 PM<dt><dd>");

                for (int i = 0; i < eightThirty.size(); i++) {
                    Episode temp = eightThirty.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (nine != null) {
                System.out.print("<dt>9:00 PM<dt><dd>");
                for (int i = 0; i < nine.size(); i++) {
                    Episode temp = nine.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (nineThirty != null) {
                System.out.print("<dt>9:30 PM<dt><dd>");
                for (int i = 0; i < nineThirty.size(); i++) {
                    Episode temp = nineThirty.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (ten != null) {
                System.out.print("<dt>10:00 PM<dt><dd>");
                for (int i = 0; i < ten.size(); i++) {
                    Episode temp = ten.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (tenThirty != null) {
                System.out.print("<dt>10:30 PM<dt><dd>");
                for (int i = 0; i < tenThirty.size(); i++) {
                    Episode temp = tenThirty.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (eleven != null) {
                System.out.print("<dt>11:00 PM<dt><dd>");
                for (int i = 0; i < eleven.size(); i++) {
                    Episode temp = eleven.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            if (elevenThirty != null) {
                System.out.print("<dt>11:30 PM<dt><dd>");
                for (int i = 0; i < elevenThirty.size(); i++) {
                    Episode temp = elevenThirty.get(i);
                    temp.print();
                }
                System.out.print("</dd>");
            }
            System.out.print("</dl>");
        }
        System.out.print("</span>\n");
    }

    /**
     * @return the eight
     */
    static LinkedList<Episode> getEight() {
        return eight;
    }

    /**
     * @param eight the eight to set
     */
    static void setEight(LinkedList<Episode> eight) {
        CheckNew.eight = eight;
    }

    /**
     * @return the eightThirty
     */
    static LinkedList<Episode> getEightThirty() {
        return eightThirty;
    }

    /**
     * @param eightThirty the eightThirty to set
     */
    static void setEightThirty(LinkedList<Episode> eightThirty) {
        CheckNew.eightThirty = eightThirty;
    }

    /**
     * @return the nine
     */
    static LinkedList<Episode> getNine() {
        return nine;
    }

    /**
     * @param nine the nine to set
     */
    static void setNine(LinkedList<Episode> nine) {
        CheckNew.nine = nine;
    }

    /**
     * @return the nineThirty
     */
    static LinkedList<Episode> getNineThirty() {
        return nineThirty;
    }

    /**
     * @param nineThirty the nineThirty to set
     */
    static void setNineThirty(LinkedList<Episode> nineThirty) {
        CheckNew.nineThirty = nineThirty;
    }

    /**
     * @return the ten
     */
    static LinkedList<Episode> getTen() {
        return ten;
    }

    /**
     * @param ten the ten to set
     */
    static void setTen(LinkedList<Episode> ten) {
        CheckNew.ten = ten;
    }

    /**
     * @return the tenThirty
     */
    static LinkedList<Episode> getTenThirty() {
        return tenThirty;
    }

    /**
     * @param tenThirty the tenThirty to set
     */
    static void setTenThirty(LinkedList<Episode> tenThirty) {
        CheckNew.tenThirty = tenThirty;
    }
}
