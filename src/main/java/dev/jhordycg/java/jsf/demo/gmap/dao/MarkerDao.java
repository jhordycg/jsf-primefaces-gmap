package dev.jhordycg.java.jsf.demo.gmap.dao;

import dev.jhordycg.java.jsf.demo.gmap.model.Marker;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkerDao implements Serializable {
    public int create(dev.jhordycg.java.jsf.demo.gmap.model.Marker entity) throws SQLException {
        String sql = "INSERT INTO marker(name, address, latitude, longitude) VALUE (?,?,?,?);";
        int generatedKey = -1;
        try (Connection connection = MySqlDB.connectDataSource()) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, entity.getName());
                ps.setString(2, entity.getAddress());
                ps.setDouble(3, entity.getLatitude());
                ps.setDouble(4, entity.getLongitude());
                if (ps.executeUpdate() == 1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        while (rs.next()) {
                            generatedKey = rs.getInt(1);
                        }
                    }
                }
            }
        }
        return generatedKey;
    }

    public boolean delete(int markerId) throws SQLException {
        boolean deleted = false;
        try (Connection connection = MySqlDB.connect();) {
            String sql = "DELETE FROM marker WHERE marker_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, markerId);
                deleted = ps.executeUpdate() == 1;
            }
        }
        return deleted;
    }

    public List<Marker> getAll() throws SQLException {
        List<Marker> markers = new ArrayList<>();
        try (Connection connection = MySqlDB.connect();) {
            String sql = "SELECT marker_id, name, address, latitude, longitude FROM marker;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Marker marker = new Marker(
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getDouble("latitude"),
                                rs.getDouble("longitude")
                        );
                        marker.setId(rs.getInt("marker_id"));
                        markers.add(marker);
                    }
                }
            }
        }
        return markers;
    }

    public static void main(String[] args) {
        MarkerDao dao = new MarkerDao();
        try {
            int key = dao.create(new dev.jhordycg.java.jsf.demo.gmap.model.Marker
                    ("demo1", "alguna", 0, 0)
            );
            if (dao.delete(key)) {
                System.out.println("el registro se eliminó ...");
            }
            //System.out.println(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
