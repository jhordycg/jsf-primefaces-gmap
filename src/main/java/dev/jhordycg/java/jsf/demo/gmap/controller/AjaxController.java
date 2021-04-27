package dev.jhordycg.java.jsf.demo.gmap.controller;

import org.primefaces.event.map.MarkerDragEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;

@Named("ajaxC")
@SessionScoped
public class AjaxController implements Serializable {
    private final DefaultMapModel defaultModel = new DefaultMapModel();
    private Marker marker;

    @PostConstruct
    void init() {
        AttributesController.initializeMarkers(defaultModel);
        defaultModel.getMarkers().forEach(m -> m.setDraggable(true));
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public void onMarkerDrag(MarkerDragEvent event) {
        Marker dragged = event.getMarker();
        addMessage(dragged.getTitle(), "Arrastraste el marcador a " + dragged.getLatlng());
    }

    public void onPointSelect(PointSelectEvent event) {
        LatLng latLng = event.getLatLng();
        addMessage("Latitud y Longitud", "latitud: " + latLng.getLat() + "\nlongitud: " + latLng.getLng());
    }

    public void onOverlaySelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
        addMessage(marker.getTitle(), "Hiciste click en " + marker.getTitle());
    }

    public DefaultMapModel getDefaultModel() {
        return defaultModel;
    }

    public Marker getMarker() {
        return marker;
    }
}
