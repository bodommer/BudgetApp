package cz.cuni.mff.javaui.budgetapp.database;

/**
 * This interface is a data source for data manipulations. Should a user want to
 * use a different database, he shall only need to change these Strings.
 * 
 * @author Andrej Jurco
 *
 */
public interface DBData {
	public static final String host = "localhost";
	public static final String database = "budget_db";
	public static final String user = "root";
	public static final String password = "root";
	public static final String serverTimeZone = "UTC";
}
