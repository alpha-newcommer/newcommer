package jp.co.alpha.zoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.AnimalImpl;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.exception.SystemException;

public class DBAccess {
	/**
	 * インスタンス
	 */
	public static final DBAccess INSTANCE = new DBAccess();

	private static final String DB_CONNECTION_STR = "jdbc:postgresql://192.168.0.103:5432/alpha";
	private static final String DB_USR = "postgres";
	private static final String DB_PASS = "p0stgres";

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

	/**
	 * マスタより檻の名前とCageオブジェクトのマップ取得
	 * @return
	 */
	public Map<String, Cage> getCages() {
		Map<String, Cage> cageMap = new LinkedHashMap<>();
		String query = "SELECT cd, name, type FROM m_cage;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				String name = rslt.getString("name");
				String type = rslt.getString("type");
				Cage cage = (Cage) Class.forName(type).newInstance();
				cage.setCd(rslt.getInt("cd"));
				cageMap.put(name, cage);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			throw new SystemException("檻マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableMap(cageMap);
	}

	/**
	 * マスタより動物名 のリストを取得
	 * @return
	 */
	public List<String> getAnimalNames() {
		List<String> animalNameList = new ArrayList<>();
		String query = "SELECT name FROM m_animal;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				animalNameList.add(rslt.getString("name"));
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableList(animalNameList);
	}
	
	/**
	 * 指定のIDの動物を取得
	 * @param id
	 * @return
	 */
	public Animal getAnimal(int id) {
		AnimalImpl animal = null;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, cage_cd, weight, name FROM t_cage_animal ");
		query.append("WHERE id=").append(id).append(";");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			if (rslt.next()) {
				animal = new AnimalImpl();
				animal.setId(rslt.getInt("id"));
				animal.setCageCd(rslt.getInt("cage_cd"));
				animal.setName(rslt.getString("name"));
				animal.setWeight(rslt.getInt("weight"));
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}
		
		return animal;
	}
	
	/**
	 * 動物を檻に格納する。
	 * @param animal
	 * @param cage
	 */
	public void addAnimalToCage(Animal animal, Cage cage) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO t_cage_animal(cage_cd, weight, name) VALUES (");
		query.append(cage.getCd()).append(",");
		query.append(animal.getWeight()).append(",");
		query.append("'").append(animal.getName()).append("'");
		query.append(");");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();) {
			stm.execute(query.toString());
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_cage_animal）への登録に失敗しました。", e);
		}
	}

	/**
	 * 指定のIDの動物を取得
	 * @param id
	 * @return
	 */
	public List<Animal> getAnimals(Cage cage) {
		List<Animal> animalList = new ArrayList<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, cage_cd, weight, name FROM t_cage_animal WHERE ");
		query.append("cage_cd=").append(cage.getCd()).append(";");
		
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				AnimalImpl animal = new AnimalImpl();
				animal.setId(rslt.getInt("id"));
				animal.setCageCd(rslt.getInt("cage_cd"));
				animal.setName(rslt.getString("name"));
				animal.setWeight(rslt.getInt("weight"));
				animalList.add(animal);
			}
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_cage_animal）からのデータ取得に失敗しました。", e);
		}
		
		return Collections.unmodifiableList(animalList);
	}

	/**
	 * マスタよりリボン名 のリストを取得
	 * @return
	 */
	public List<String> getRibbonNames() {
		List<String> ribbonNameList = new ArrayList<>();
		String query = "SELECT name FROM m_ribbon;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				ribbonNameList.add(rslt.getString("name"));
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableList(ribbonNameList);
	}

	/**
	 * リボンとリボンを設定している動物のマップ取得
	 * @return
	 */
	public Map<String, Animal> getRibbonMap() {
		Map<String, Animal> ribbonMap = new LinkedHashMap<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT t1.name AS ribbon_name, animal_id, t2.cage_cd, t2.name, t2.weight ");
		query.append("FROM t_ribbon_animal t1, t_cage_animal t2 ");
		query.append("WHERE t1.animal_id = t2.id;");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				String ribbonName = rslt.getString("ribbon_name");
				AnimalImpl animal = new AnimalImpl();
				animal.setId(rslt.getInt("animal_id"));
				animal.setCageCd(rslt.getInt("cage_cd"));
				animal.setName(rslt.getString("name"));
				animal.setWeight(rslt.getInt("weight"));
				ribbonMap.put(ribbonName, animal);
			}
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_ribbon_animal）からのデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableMap(ribbonMap);
	}
	
	public void setRibbon(Animal animal, String ribbonName) {
		StringBuilder query = new StringBuilder();
		Map<String, Animal> ribbonMap = getRibbonMap();
		if (ribbonMap.containsKey(ribbonName)) {
			// 既存のリボン情報を更新する場合
			query.append("UPDATE t_ribbon_animal SET ");
			query.append("animal_id=").append(animal.getId()).append(" ");
			query.append("WHERE name='").append(ribbonName).append("';");
		}
		else {
			// 新しくリボン情報を登録する場合
			query.append("INSERT INTO t_ribbon_animal(animal_id, name) VALUES (");
			query.append(animal.getId()).append(",");
			query.append("'").append(ribbonName).append("'");
			query.append(");");
		}
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();) {
			stm.execute(query.toString());
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_ribbon_animal）への登録に失敗しました。", e);
		}
	}
	
	public List<String> getAllAnimalInfList() {
		List<String> animalInfList = new ArrayList<>();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT t1.id, m1.name AS cage_name, t1.weight, t1.name, t2.name AS ribbon_name ");
		query.append("FROM t_cage_animal t1 ");
		query.append("JOIN m_cage m1 ON t1.cage_cd = m1.cd ");
		query.append("LEFT JOIN t_ribbon_animal t2 ON t1.id = t2.animal_id;");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append(rslt.getString("cage_name")).append("\t");
				sb.append(rslt.getInt("id")).append("\t");
				sb.append(rslt.getString("name")).append("\t");
				sb.append(rslt.getInt("weight")).append("\t");
				String ribbonName = rslt.getString("ribbon_name");
				if (StringUtils.isNotEmpty(ribbonName)) {
					sb.append(ribbonName);
				}
				animalInfList.add(sb.toString());
			}
		} catch (SQLException e) {
			throw new SystemException("各種テーブルからのデータ取得に失敗しました。", e);
		}
		
		return animalInfList;
	}
}
