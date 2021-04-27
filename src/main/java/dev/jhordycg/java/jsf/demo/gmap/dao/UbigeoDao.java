package dev.jhordycg.java.jsf.demo.gmap.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UbigeoDao implements Serializable {
    public List<String> getDepartments() throws SQLException {
        List<String> departments = new ArrayList<>();
        String sql = "SELECT DISTINCT department FROM ubigeo;";
        try (Connection conn = MySqlDB.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        departments.add(rs.getString("department"));
                    }
                }
            }
        }
        return departments;
    }

    public List<String> getProvinces(String department) throws SQLException {
        List<String> provinces = new ArrayList<>();
        String sql = "SELECT DISTINCT province FROM ubigeo WHERE department=?;";
        try (Connection conn = MySqlDB.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, department);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        provinces.add(rs.getString("province"));
                    }
                }
            }
        }
        return provinces;
    }

    public List<String> getDistricts(String province) throws SQLException {
        List<String> districts = new ArrayList<>();
        String sql = "SELECT DISTINCT district FROM ubigeo WHERE province=?;";
        try (Connection conn = MySqlDB.connect()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, province);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        districts.add(rs.getString("district"));
                    }
                }
            }
        }
        return districts;
    }
}
