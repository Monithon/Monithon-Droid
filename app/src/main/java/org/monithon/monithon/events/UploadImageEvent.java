package org.monithon.monithon.events;

/**
 * Created by MMo on 03/12/2014.
 */
public class UploadImageEvent {
    private String file_path;
    private String url;
    private Float lat;
    private Float lon;

    public UploadImageEvent(String file_path, String url, Float lat, Float lon){
        this.file_path = file_path;
        this.url = url;
        this.lat = lat;
        this.lon = lon;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }
}
