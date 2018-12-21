package com.TP_ESEO_MAVEN_COURS2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		dropTable(tableName);
		readCSV(filePath);
	}

	private static String filePath = "src/ressources/laposte_hexasmal.csv";

	private static String DBName = "LAPOSTE";

	private static String tableName = "villes";

	private static String username = "Bij";

	private static String password = "Archos";

	/**
	 * Lecture du fichier 
	 * @param path
	 */
	public static void readCSV(String path) {

		String line = "";
		String cvsSplitBy = ";";
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(filePath));

			// Lecture première ligne
			line = br.readLine();
			String[] aux = line.split(cvsSplitBy);
			createTable(tableName, aux);

			// LECTURE DES AUTRES LIGNES
			int i = 0;
			while ((line = br.readLine()) != null) {
				line = br.readLine();
				aux = line.split(cvsSplitBy);
				addToTable(tableName, aux);
			}

		} catch (Exception e) {
			System.out.println("In read CSV : " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Creation de la connexion
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Connection connect() throws ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/" + DBName;
		Connection conn = null;
		try {
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("In connect() : " + e);
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Suppression d'une table dans la DB
	 * 
	 * @param tableName
	 */
	public static void dropTable(String tableName) {
		tableName = tableName.toUpperCase();
		String sql = "DROP TABLE " + tableName + ";";
		Statement stmt;
		try {
//			System.out.println(sql);
			Connection conn = connect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		} catch (Exception e) {
			System.out.println("In dropTable() : " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Creation d'une table dans la DB
	 * 
	 * @param tableName
	 * @param columnNames
	 */
	public static void createTable(String tableName, String[] columnNames) {
		tableName = tableName.toUpperCase();
		String sql = "CREATE TABLE " + tableName + " (";
		for (int i = 0; i < columnNames.length; i++) {
			sql += " " + columnNames[i] + " VARCHAR(255),";
		}
		sql = sql.substring(0, sql.length() - 1) + ");";
		Statement stmt;
		try {
			Connection conn = connect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		} catch (Exception e) {
			System.out.println("In createTable() : " + e);
			e.printStackTrace();
		}
	}

	/**
	 * Ajoute des valeurs dans une table donnée
	 * 
	 * @param tableName
	 * @param values
	 */
	public static void addToTable(String tableName, String[] values) {
		tableName = tableName.toUpperCase();
		String sql = "INSERT INTO " + tableName + " VALUES ( ";
		for (int i = 0; i < values.length; i++) {
			sql += "'" + values[i] + "', ";
		}
		sql = sql.substring(0, sql.length() - 2) + ");";
		Statement stmt;
		try {
			Connection conn = connect();
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			conn.close();
		} catch (Exception e) {
			System.out.println("In assToTable() : " + e);
			e.printStackTrace();
		}
	}

}
