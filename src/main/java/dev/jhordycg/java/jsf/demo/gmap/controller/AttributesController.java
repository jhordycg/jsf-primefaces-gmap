package dev.jhordycg.java.jsf.demo.gmap.controller;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("attributesC")
@SessionScoped
public class AttributesController implements Serializable {
    private final DefaultMapModel defaultModel = new DefaultMapModel();

    private AttributesController() {
    }

    @PostConstruct
    void init() {
        initializeMarkers(defaultModel);
    }

    static void initializeMarkers(DefaultMapModel mapModel) {
        LatLng peruLatLng = new LatLng(-9.189967, -75.015152);
        LatLng boliviaLatLng = new LatLng(-16.290154, -63.588653);
        LatLng ecuadorLatLng = new LatLng(-1.831239, -78.183406);
        LatLng venezuelaLatLng = new LatLng(6.42375, -66.58973);

        Marker peruMarker = new Marker(peruLatLng, "Perú");
        Marker boliviaMarker = new Marker(boliviaLatLng, "Bolivia");
        Marker ecuadorMarker = new Marker(ecuadorLatLng, "Ecuador");
        Marker venezuelaMarker = new Marker(venezuelaLatLng, "venezuela");

        peruMarker.setClickable(false);
        boliviaMarker.setClickable(false);
        ecuadorMarker.setClickable(false);
        venezuelaMarker.setClickable(false);

        mapModel.addOverlay(peruMarker);
        mapModel.addOverlay(boliviaMarker);
        mapModel.addOverlay(ecuadorMarker);
        mapModel.addOverlay(venezuelaMarker);
    }

    public DefaultMapModel getDefaultModel() {
        return defaultModel;
    }
}
