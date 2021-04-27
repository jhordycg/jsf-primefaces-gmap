package dev.jhordycg.java.jsf.demo.gmap.controller;

import dev.jhordycg.java.jsf.demo.gmap.dao.MarkerDao;
import dev.jhordycg.java.jsf.demo.gmap.dao.UbigeoDao;
import dev.jhordycg.java.jsf.demo.gmap.services.MapProps;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Named("geocodeC")
@SessionScoped
public class GeocodeController implements Serializable {
    private final DefaultMapModel defaultModel = new DefaultMapModel();
    private final UbigeoDao ubigeoDao = new UbigeoDao();
    private final MarkerDao markerDao = new MarkerDao();
    private List<String> departments = new ArrayList<>();
    private List<String> provinces = new ArrayList<>();
    private List<String> districts = new ArrayList<>();
    private String mapCenter = MapProps.MAP_CENTER;
    private String selectedDepartment;
    private String selectedProvince;
    private String address;
    private LatLng latLng;
    private Marker marker;

    @PostConstruct
    void init() {
        initializeMarkersFromDatabase();
    }

    private void initializeMarkersFromDatabase() {
        try {
            List<dev.jhordycg.java.jsf.demo.gmap.model.Marker> models = markerDao.getAll();
            for (dev.jhordycg.java.jsf.demo.gmap.model.Marker model : models) {
                Marker newMarker = new Marker(new LatLng(model.getLatitude(), model.getLongitude()));
                newMarker.setTitle(model.getName() + "-" + model.getId());
                defaultModel.addOverlay(newMarker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail));
    }

    public void createMarker() {
        try {
            int id = markerDao.create(new dev.jhordycg.java.jsf.demo.gmap.model.Marker
                    ("DEMO", address, latLng.getLat(), latLng.getLng())
            );
            Marker newMarker = new Marker(latLng, "DEMO-" + id);
            defaultModel.addOverlay(newMarker);
            mapCenter = latLng.getLat() + "," + latLng.getLng();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        addMessage("¡Marker creado!", "Dirección: " + address + "\nLatitud: " + latLng.getLat() + "\nLongitud: " + latLng.getLng());
    }

    public void deleteMarker() {
        try {
            int markerId = Integer.parseInt(marker.getTitle().replace("DEMO-", ""));
            if (markerDao.delete(markerId)) {
                defaultModel.getMarkers().remove(marker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onGeocode(GeocodeEvent event) {
        List<GeocodeResult> results = event.getResults();
        if (results == null || results.isEmpty()) {
            addMessage("¡Marker creado!", "Dirección: \nLatitud: \nLongitud: ");
        } else {
            this.address = results.get(0).getAddress();
            this.latLng = results.get(0).getLatLng();
            addMessage("¡Dirección encontrada!", "Dirección: " + address + "\nLatitud: " + latLng.getLat() + "\nLongitud: " + latLng.getLng());
        }
    }

    public void onOverlaySelect(OverlaySelectEvent event) {
        this.marker = (Marker) event.getOverlay();
    }

    public DefaultMapModel getDefaultModel() {
        return defaultModel;
    }

    public String getMapCenter() {
        return mapCenter;
    }

    public List<String> getDepartments() {

        if (departments == null || departments.isEmpty()) {
            try {
                departments = ubigeoDao.getDepartments();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return departments;
    }

    public List<String> getProvinces() {
        if ((provinces == null || provinces.isEmpty()) && (selectedDepartment != null && !selectedDepartment.isEmpty())) {
            try {
                provinces = ubigeoDao.getProvinces(selectedDepartment);
                districts.clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return provinces;
    }

    public List<String> getDistricts() {
        if ((districts == null || districts.isEmpty()) && (selectedProvince != null && !selectedProvince.isEmpty())) {
            try {
                districts = ubigeoDao.getDistricts(selectedProvince);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return districts;
    }

    public Marker getMarker() {
        return marker;
    }

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public String getSelectedProvince() {
        return selectedProvince;
    }

    public void setSelectedProvince(String selectedProvince) {
        this.selectedProvince = selectedProvince;
    }
}
