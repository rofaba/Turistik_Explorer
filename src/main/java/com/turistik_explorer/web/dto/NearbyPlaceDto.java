package com.turistik_explorer.web.dto;


public class NearbyPlaceDto {
    private Long id;
    private String name;
    private double latitud;
    private double longitud;
    private double distanceKm;
    private String type; // HOTEL | RESTAURANT

    public NearbyPlaceDto() {}

    public NearbyPlaceDto(Long id, String name, double latitud, double longitud, double distanceKm, String type) {
        this.id = id;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.distanceKm = distanceKm;
        this.type = type;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public double getDistanceKm() { return distanceKm; }
    public String getType() { return type; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setLatitud(double latitud) { this.latitud = latitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }
    public void setDistanceKm(double distanceKm) { this.distanceKm = distanceKm; }
    public void setType(String type) { this.type = type; }
}