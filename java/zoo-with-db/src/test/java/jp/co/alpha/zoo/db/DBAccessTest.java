package jp.co.alpha.zoo.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jp.co.alpha.zoo.cage.Cage;
import jp.co.alpha.zoo.cage.CuteCage;
import jp.co.alpha.zoo.cage.LargeCage;

public class DBAccessTest {
	private static IDatabaseTester databaseTester;

	@Before
	public void setUp() throws Exception {
		databaseTester = new JdbcDatabaseTester("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/alpha",
				"postgres", "postgres");
		// テーブルのデータを順番に削除（FK制約があるので）
		String[] truncateTables = {
				"t_ribbon_animal",
				"t_cage_animal",
				"m_ribbon",
				"m_cage",
				"m_animal"};
		for (String tableName : truncateTables) {
			ITable table = new DefaultTable(tableName);
			IDataSet dataSet = new DefaultDataSet(table);
			databaseTester.setDataSet(dataSet);
			databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
			databaseTester.onSetup();
		}
		// シーケンス初期化
		databaseTester.getConnection().getConnection().createStatement().executeQuery("SELECT setval('t_cage_animal_id_seq', 1, true);");
	}
	
	@After
	public void tearDown() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.NONE);
		databaseTester.onTearDown();
	}

	@Test
	public void test_getCages() throws Exception {
		// テストデータ投入
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/test_getCages.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
		
		List<Cage> cageList = DBAccess.INSTANCE.getCages();
		Cage cage = cageList.get(0);
		assertThat(cage.getCd(), is(1));
		assertThat(cage.getName(), is("かーわいぃ檻"));
		assertTrue(cage.getClass().equals(CuteCage.class));
		
		cage = cageList.get(1);
		assertThat(cage.getCd(), is(2));
		assertThat(cage.getName(), is("ひろーーい檻"));
		assertTrue(cage.getClass().equals(LargeCage.class));
	}

	@Test
	public void test_addAnimalToCage() throws Exception {
		// テストデータ投入
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/test_addAnimalToCage.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
		
		// 檻取得
		List<Cage> cageList = DBAccess.INSTANCE.getCages();
		
		// テスト実施
		DBAccess.INSTANCE.addAnimalToCage(cageList.get(1), 1, 2);
		
		// 期待値データ作成
		IDataSet expDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/exp_addAnimalToCage.xml"));
		ITable expectedTable = expDataSet.getTable("t_cage_animal");
		
		// 検証対象データ取得（addAnimalToCageでのデータ更新結果）
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("t_cage_animal");
        
        // 検証
        Assertion.assertEquals(expectedTable, actualTable);

	}
}
