package cz.cuni.mff.javaui.budgetapp.database;

/**
 * This interface is a data source for data manipulations. Should a user want to
 * use a different database, he shall only need to change these Strings.
 * 
 * @author Andrej Jurco
 *
 */
public interface DBData {
	public static final String host = "db4free.net:3306";
	public static final String database = "budget_db";
	public static final String user = "budget_app_user";
	public static final String password = "";
	public static final String serverTimeZone = "UTC";
	public static final String instance = "middleware-189423:europe-west1:budgetdb";
}
