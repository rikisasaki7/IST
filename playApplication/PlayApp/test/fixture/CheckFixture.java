package fixture;

import com.avaje.ebean.Ebean;

public class CheckFixture {

	final public static String INSERT_CHECK_ONE_RECORD = "insert into checks (id, name, result, created, modified) " + 
			 "values(1, 'kara_d', 'resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46')";
	final public static String INSERT_CHECK_FIFTEEN_RECORD = 
			"insert into checks (id, name, result, created, modified) values(1, 'kara_1', '1resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(2, 'kara_2', '2resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(3, 'kara_3', '3resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(4, 'kara_4', '4resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(5, 'kara_5', '5resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(6, 'kara_6', '6resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(7, 'kara_7', '7resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(8, 'kara_8', '8resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(9, 'kara_9', '9resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(10, 'kara_10', '10resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(11, 'kara_11', '11resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(12, 'kara_12', '12resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(13, 'kara_13', '13resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(14, 'kara_14', '14resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); "
			+ "insert into checks (id, name, result, created, modified) values(15, 'kara_15', '15resultです', '2013-08-20 12:23:45', '2013-08-20 12:23:46'); ";

	public static void createPaginateRecords() {
		Ebean.execute(Ebean.createSqlUpdate(INSERT_CHECK_FIFTEEN_RECORD));
	}
}
