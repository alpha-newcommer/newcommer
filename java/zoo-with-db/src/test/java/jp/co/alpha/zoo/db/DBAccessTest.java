package jp.co.alpha.zoo.db;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
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
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/Test.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
	}
	
	@After
	public void tearDown() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.NONE);
		databaseTester.onTearDown();
	}

	@Test
	public void test_getCages() {
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

}
