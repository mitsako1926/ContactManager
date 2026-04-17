package com.mitsako1926.contactManager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/contactmanager";
    private static final String USER = "contact_app_user";
    private static final String PASSWORD = "app_user@mitsik.com";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
	
}
