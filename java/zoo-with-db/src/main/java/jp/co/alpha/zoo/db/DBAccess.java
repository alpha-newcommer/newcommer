package jp.co.alpha.zoo.db;

import java.lang.reflect.InvocationTargetException;
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

import jp.co.alpha.zoo.animal.Animal;
import jp.co.alpha.zoo.animal.AnimalFactory;
import jp.co.alpha.zoo.animal.AnimalType;
import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.exception.SystemException;
import jp.co.alpha.zoo.ribbon.RibbonManager;
import jp.co.alpha.zoo.ribbon.RibbonType;

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

	/**
	 * マスタより檻の名前とCageオブジェクトのマップ取得
	 * @return
	 */
	public List<Cage> getCages() {
		List<Cage> cageList = new ArrayList<>();
		String query = "SELECT cd, name, type FROM m_cage;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				String name = rslt.getString("name");
				String type = rslt.getString("type");
				Cage cage = (Cage) Class.forName(type).getConstructor(String.class).newInstance(name);
				cage.setCd(rslt.getInt("cd"));
				cageList.add(cage);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			throw new SystemException("檻マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableList(cageList);
	}

	/**
	 * マスタより動物名 のリストを取得
	 * @return
	 */
	public List<AnimalType> getAnimalTypeList() {
		List<AnimalType> animalTypeList = new ArrayList<>();
		String query = "SELECT cd, name, type FROM m_animal;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				int cd = rslt.getInt("cd");
				String name = rslt.getString("name");
				String type = rslt.getString("type");
				@SuppressWarnings("unchecked")
				Class<? extends Animal> animalClass  = (Class<? extends Animal>) Class.forName(type);
				animalTypeList.add(new AnimalType(cd, name, animalClass));
			}
		} catch (SQLException | ClassNotFoundException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableList(animalTypeList);
	}
	
	/**
	 * 指定のIDの動物を取得
	 * @param id
	 * @return
	 */
	public Animal getAnimal(int id) {
		Animal animal = null;
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, cage_cd, animal_cd, weight FROM t_cage_animal ");
		query.append("WHERE id=").append(id).append(";");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			if (rslt.next()) {
				int animalCd = rslt.getInt("animal_cd");
				int weight = rslt.getInt("weight");
				animal = AnimalFactory.createAnimal(id, animalCd, weight);
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}
		
		return animal;
	}
	
	/**
	 * 動物を檻に格納する。
	 * 
	 * @param cage
	 * @param animalCd
	 * @param weight
	 */
	public void addAnimalToCage(Cage cage, int animalCd, int weight) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO t_cage_animal(cage_cd, animal_cd, weight) VALUES (");
		query.append(cage.getCd()).append(",");
		query.append(animalCd).append(",");
		query.append(weight);
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
		query.append("SELECT id, cage_cd, animal_cd, weight FROM t_cage_animal WHERE ");
		query.append("cage_cd=").append(cage.getCd()).append(";");
		
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				int id = rslt.getInt("id");
				int animalCd = rslt.getInt("animal_cd");
				int weight = rslt.getInt("weight");
				Animal animal = AnimalFactory.createAnimal(id, animalCd, weight);
				animalList.add(animal);
			}
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_cage_animal）からのデータ取得に失敗しました。", e);
		}
		
		return Collections.unmodifiableList(animalList);
	}

	/**
	 * マスタよりリボン種 のリストを取得
	 * @return
	 */
	public List<RibbonType> getRibbonTypes() {
		List<RibbonType> ribbonTypeList = new ArrayList<>();
		String query = "SELECT cd, name FROM m_ribbon;";
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			while (rslt.next()) {
				int cd = rslt.getInt("cd");
				String name = rslt.getString("name");
				ribbonTypeList.add(new RibbonType(cd, name));
			}
		} catch (SQLException e) {
			throw new SystemException("動物マスタデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableList(ribbonTypeList);
	}

	public void setRibbon(int ribbonCd, Animal animal) {
		StringBuilder query = new StringBuilder();
		Map<RibbonType, Animal> ribbonMap = getRibbonMap();
		boolean isUpdate = false;
		for (RibbonType type : ribbonMap.keySet()) {
			if (type.getCd() == ribbonCd) {
				isUpdate = true;
				break;
			}
		}
		if (isUpdate) {
			// 既存のリボン情報を更新する場合
			query.append("UPDATE t_ribbon_animal SET ");
			query.append("animal_id=").append(animal.getId()).append(" ");
			query.append("WHERE ribbon_cd='").append(ribbonCd).append("';");
		}
		else {
			// 新しくリボン情報を登録する場合
			query.append("INSERT INTO t_ribbon_animal(animal_id, ribbon_cd) VALUES (");
			query.append(animal.getId()).append(",");
			query.append(ribbonCd);
			query.append(");");
		}
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();) {
			stm.execute(query.toString());
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_ribbon_animal）への登録に失敗しました。", e);
		}
	}
	
	/**
	 * リボンとリボンを設定している動物のマップ取得
	 * @return
	 */
	public Map<RibbonType, Animal> getRibbonMap() {
		Map<RibbonType, Animal> ribbonMap = new LinkedHashMap<>();
		StringBuilder query = new StringBuilder();
		query.append("SELECT t1.id, t1.ribbon_cd, t1.animal_id, t2.animal_cd, t2.weight ");
		query.append("FROM t_ribbon_animal t1 ");
		query.append("JOIN t_cage_animal t2 ON t1.animal_id = t2.id ");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				int ribbonCd = rslt.getInt("ribbon_cd");
				RibbonType ribbonType = null;
				for (RibbonType type : RibbonManager.getRibbonTypes()) {
					if (type.getCd() == ribbonCd) {
						ribbonType = type;
						break;
					}
				}
				int id = rslt.getInt("animal_id");
				int animalCd = rslt.getInt("animal_cd");
				int weight = rslt.getInt("weight");
				Animal animal = AnimalFactory.createAnimal(id, animalCd, weight);
				ribbonMap.put(ribbonType, animal);
			}
		} catch (SQLException e) {
			throw new SystemException("テーブル（t_ribbon_animal）からのデータ取得に失敗しました。", e);
		}

		return Collections.unmodifiableMap(ribbonMap);
	}
	
	public List<String> getAllAnimalInfList() {
		List<String> animalInfList = new ArrayList<>();
		
		StringBuilder query = new StringBuilder();
		query.append("SELECT t1.id, m1.name AS cage_name, t1.weight, m2.name AS animal_name, t2.ribbon_cd ");
		query.append("FROM t_cage_animal t1 ");
		query.append("JOIN m_cage m1 ON t1.cage_cd = m1.cd ");
		query.append("JOIN m_animal m2 ON t1.animal_cd = m2.cd ");
		query.append("LEFT JOIN t_ribbon_animal t2 ON t1.id = t2.animal_id;");
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query.toString());) {
			while (rslt.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append(rslt.getString("cage_name")).append("\t");
				sb.append(rslt.getInt("id")).append("\t");
				sb.append(rslt.getString("animal_name")).append("\t");
				sb.append(rslt.getInt("weight")).append("\t");
				int ribbonCd = rslt.getInt("ribbon_cd");
				RibbonType ribbonType = RibbonManager.getRibbonType(ribbonCd);
				if (ribbonType != null) {
					sb.append(ribbonType.getName());
				}
				animalInfList.add(sb.toString());
			}
		} catch (SQLException e) {
			throw new SystemException("各種テーブルからのデータ取得に失敗しました。", e);
		}
		
		return animalInfList;
	}
}
