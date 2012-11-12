/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package newontv;

/**
 *
 * @author yoharnu
 */
class Episode {

    private String season;
    private String episode;
    private String overview;
    private String epName;
    private String seriesName;
    private String network;
    private String airTime;

    public Episode(String seriesName, String network, String airTime) {
        this.seriesName = seriesName;
        this.airTime = airTime;
        this.season = null;
        this.network = network;
        this.episode = null;
        this.overview = null;
        this.epName = null;
    }

    public void print() {
        System.out.print("<dl><dt>" + this.seriesName + "<span style=\"padding-left: 50px\">" + this.network + "</span></dt>");
        System.out.format("<dd>%s<span style=\"padding-left: 50px\">s%02de%02d</span><br/>", this.epName, new Integer(this.season), new Integer(this.episode));
        if(this.overview != null){
        System.out.print("<b>Overview:</b> " + this.overview + "</dd>");
        }
        else{
            System.out.print("<b>Overview:</b> Not Available</dd>");
        }
    }

    /**
     * @return the season
     */
    public String getSeason() {
        return season;
    }

    /**
     * @param season the season to set
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * @return the episode
     */
    public String getEpisode() {
        return episode;
    }

    /**
     * @param episode the episode to set
     */
    public void setEpisode(String episode) {
        this.episode = episode;
    }

    /**
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return the epName
     */
    public String getEpName() {
        return epName;
    }

    /**
     * @param epName the epName to set
     */
    public void setEpName(String epName) {
        this.epName = epName;
    }

    /**
     * @return the seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * @return the network
     */
    public String getNetwork() {
        return network;
    }

    /**
     * @return the airTime
     */
    public String getAirTime() {
        return airTime;
    }
}
