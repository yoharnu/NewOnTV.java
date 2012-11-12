/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoharnu
 */
class Series {

    private String seriesid;
    private String language;
    private String seriesName;
    private String overview;
    private String firstAired;
    private String imdb_id;
    private String airTime;
    private String network;
    private LinkedList<Episode> episodes;
    private boolean inCache;

    Series() {
        this.seriesid = null;
        this.language = null;
        this.seriesName = null;
        this.overview = null;
        this.firstAired = null;
        this.imdb_id = null;
        this.airTime = null;
        this.network = null;
        this.inCache = false;
    }

    Series(String id) {
        this.seriesid = id;
        this.inCache = false;
        this.episodes = null;
        for (int i = 0; i < Cache.shows.size(); i++) {
            Series temp = Cache.shows.get(i);
            if (temp.seriesid.equals(id)) {
                inCache = true;
                this.language = temp.language;
                this.seriesName = temp.seriesName;
                this.overview = temp.overview;
                this.firstAired = temp.firstAired;
                this.imdb_id = temp.imdb_id;
                this.airTime = temp.airTime;
                this.network = temp.network;
                i = Cache.shows.size();
            }
        }
        if (inCache == false) {
            try {
                URL url = new URL(NewOnTV.MirrorPath + "/api/" + NewOnTV.APIkey + "/series/" + id + "/" + NewOnTV.language + ".xml");
                Scanner t = null;
                try {
                    t = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));
                } catch (IOException ex) {
                    Logger.getLogger(Series.class.getName()).log(Level.SEVERE, null, ex);
                    System.exit(1);
                }
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
                        if (data != null) {
                            if (tag.equals("Airs_Time")) {
                                this.airTime = data.replaceAll("PST", "").replaceAll("EST", "").replaceAll("ET/PT", "").trim();
                            } else if (tag.equals("FirstAired")) {
                                this.firstAired = data;
                            } else if (tag.equals("IMDB_ID")) {
                                this.imdb_id = data;
                            } else if (tag.equals("Language")) {
                                this.language = data;
                            } else if (tag.equals("Network")) {
                                this.network = data;
                            } else if (tag.equals("Overview")) {
                                this.overview = data.replaceAll("&quot;", "\"").replaceAll("&amp;", "&");
                            } else if (tag.equals("SeriesName")) {
                                this.seriesName = data.replaceAll("&amp;", "&");
                            }
                        }
                    }
                }
                t.close();
            } catch (MalformedURLException ex) {
                Logger.getLogger(Series.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
        }
        //downloadEpInfo();
    }

    /**
     * @return the seriesid
     */
    public String getSeriesid() {
        return seriesid;
    }

    /**
     * @return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @return the seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @return the firstAired
     */
    public String getFirstAired() {
        return firstAired;
    }

    /**
     * @return the imdb_id
     */
    public String getImdb_id() {
        return imdb_id;
    }

    /**
     * @return the airTime
     */
    public String getAirTime() {
        return airTime;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @return the episodes
     */
    public LinkedList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * @param seriesid the seriesid to set
     */
    public void setSeriesid(String seriesid) {
        this.seriesid = seriesid;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @param seriesName the seriesName to set
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    /**
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @param firstAired the firstAired to set
     */
    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    /**
     * @param imdb_id the imdb_id to set
     */
    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    /**
     * @param airTime the airTime to set
     */
    public void setAirTime(String airTime) {
        this.airTime = airTime;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * @param episodes the episodes to set
     */
    public void setEpisodes(LinkedList<Episode> episodes) {
        this.episodes = episodes;
    }

    /**
     * @return the inCache
     */
    public boolean isInCache() {
        return inCache;
    }

    /**
     * @param inCache the inCache to set
     */
    public void setInCache(boolean inCache) {
        this.inCache = inCache;
    }

    public void downloadEpInfo() {
        Calendar cal = GregorianCalendar.getInstance();
        String today = cal.get(GregorianCalendar.YEAR) + "-" + (cal.get(GregorianCalendar.MONTH) + 1) + "-" + cal.get(GregorianCalendar.DAY_OF_MONTH);
        //today = "2012-11-11";
        try {
            URL url = new URL(NewOnTV.MirrorPath + "/api/GetEpisodeByAirDate.php?apikey="
                    + NewOnTV.APIkey + "&seriesid=" + seriesid + "&airdate=" + today
                    + "&language=" + NewOnTV.language);
            Scanner t;
            try {
                t = new Scanner(new BufferedReader(new InputStreamReader(url.openStream())));

                this.episodes = new LinkedList<Episode>();
                Episode currEp = null;
                while (t.hasNextLine()) {
                    String tag = t.nextLine();
                    String[] splits = tag.split("<");
                    if (splits.length > 1) {
                        splits = splits[1].split(">");

                        if (splits[0].equals("Episode")) {
                            currEp = new Episode(this.seriesName, this.network, this.airTime);
                            this.episodes.add(currEp);
                        }

                        tag = null;
                        if (!splits[0].startsWith("/")) {
                            tag = splits[0];
                        }
                        String data = null;
                        if (splits.length > 1) {
                            data = splits[1];
                        }
                        if (data != null) {
                            if (tag.equals("SeasonNumber")) {
                                currEp.setSeason(data);
                            } else if (tag.equals("EpisodeNumber")) {
                                currEp.setEpisode(data);
                            } else if (tag.equals("EpisodeName")) {
                                currEp.setEpName(data);
                            } else if (tag.equals("Overview")) {
                                currEp.setOverview(data);
                            }
                        }
                    }
                }
                t.close();
            } catch (IOException ex) {
                Logger.getLogger(Series.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Series.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
}
