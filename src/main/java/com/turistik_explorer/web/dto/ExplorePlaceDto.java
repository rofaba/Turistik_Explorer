package com.turistik_explorer.web.dto;

public class ExplorePlaceDto {

    private Long id;
    private String name;
    private Double latitud;
    private Double longitud;
    private String type; // POI | HOTEL | RESTAURANT

    public ExplorePlaceDto() {
    }

    public ExplorePlaceDto(Long id, String name, Double latitud, Double longitud, String type) {
        this.id = id;
        this.name = name;
        this.latitud = latitud;
        this.longitud = longitud;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Importante: getters con nombre "Latitud/Longitud" para que en JS exista p.latitud / p.longitud
    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public void setType(String type) {
        this.type = type;
    }
}