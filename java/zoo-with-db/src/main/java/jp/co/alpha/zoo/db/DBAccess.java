package jp.co.alpha.zoo.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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

	public List<Cage> getCages() {
		List<Cage> cageList = new ArrayList<>();
		execute("SELECT id, name, type FROM m_cage;", (ResultSet rslt) -> {
			try {
				while (rslt.next()) {
					String type = rslt.getString("type");
					Cage cage = (Cage) Class.forName(type).newInstance();
					cage.setId(rslt.getInt("id"));
					cageList.add(cage);
				}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
				throw new SystemException("檻マスタデータ取得に失敗しました。", e);
			}
		});

		return cageList;
	}

	private void execute(String query, Consumer<ResultSet> setResult) {
		try (Connection conn = DriverManager.getConnection(DB_CONNECTION_STR, DB_USR, DB_PASS);
				Statement stm = conn.createStatement();
				ResultSet rslt = stm.executeQuery(query);) {
			setResult.accept(rslt);
		} catch (SQLException e) {
			throw new SystemException("DB接続に失敗しました。", e);
		}

	}
}
