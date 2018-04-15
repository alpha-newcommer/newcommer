package jp.co.alpha.zoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.co.alpha.zoo.animal.AnimalType;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.exception.SystemException;

public class DBAccess {
	/**
	 * インスタンス
	 */
	public static final DBAccess INSTANCE = new DBAccess();

	private static final String DB_CONNECTION_STR = "jdbc:postgresql://localhost:5432/alpha";
	private static final String DB_USR = "postgres";
	private static final String DB_PASS = "postgres";

	/**
	 * コンストラクタ
	 */
	private DBAccess() {
		// JDBCドライバのロード
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new SystemException("JDBCドライバのロードに失敗しました。", e);
		}
	}

	public Map<String, Cage> getCages() {
		Map<String, Cage> cageMap = new LinkedHashMap<>();
		String query = "SELECT id, name, type FROM m_cage;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				String name = rslt.getString("name");
				String type = rslt.getString("type");
				Cage cage = (Cage) Class.forName(type).newInstance();
				cage.setId(rslt.getInt("id"));
				cageMap.put(name, cage);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new SystemException("檻マスタデータ取得に失敗しました。", e);
		}

		return cageMap;
	}

	public List<AnimalType> getAnimalTypes() {
		List<AnimalType> animalTypeList = new ArrayList<>();
		String query = "SELECT id, name FROM m_name;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				int id = rslt.getInt("id");
				String name = rslt.getString("name");
				animalTypeList.add(new AnimalType(id, name));
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}

		return animalTypeList;
	}
}
