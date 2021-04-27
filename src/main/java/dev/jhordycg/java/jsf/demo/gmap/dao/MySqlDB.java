package dev.jhordycg.java.jsf.demo.gmap.dao;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

class MySqlDB {
    private static final String DB_USER = "jhordycg";
    private static final String DB_PWD = "jhordycg";
    private static final String DB_HOST = "127.0.0.1";
    private static final Integer DB_PORT = 3306;
    private static final String DB_NAME = "gmap";
    private static final String DB_PROPERTIES = "?allowPublicKeyRetrieval=true&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + DB_PROPERTIES;
    private static final Logger log = Logger.getLogger("database");

    private MySqlDB() {
    }

    private static void registerDriverForMySQL() {
        log.info("Searching driver for MySQL ...");
        try {
            Class<?> t = Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("Generating a new driver instance for MySQL ...");
            t.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected static Connection connect() throws SQLException {
        registerDriverForMySQL();
        log.info("Establishing connection 2...");
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
    }

    protected static Connection connectDataSource() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUseSSL(false);
        dataSource.setAllowPublicKeyRetrieval(true);
        dataSource.setServerName(DB_HOST);
        dataSource.setDatabaseName(DB_NAME);
        dataSource.setPortNumber(3306);
        dataSource.setUser(DB_USER);
        dataSource.setPassword(DB_PWD);
        registerDriverForMySQL();
        log.info("Searching driver for MySQL ...");
        try {
            Class<?> t = Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("Generating a new driver instance for MySQL ...");
            t.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        log.info("Establishing connection 2...");
        return dataSource.getConnection();
    }
}