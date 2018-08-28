package cz.cuni.mff.javaui.budgetapp.database;

/**
 * This interface is a data source for data manipulations. Should a user want to
 * use a different database, he shall only need to change these Strings.
 * 
 * @author Andrej Jurco
 *
 */
public interface DBData {
	public static final String host = "35.205.233.138";
	public static final String database = "middleware-189423:europe-west1:budgetdb";
	public static final String user = "test";
	public static final String password = "testtest";
	public static final String serverTimeZone = "UTC";
	public static final String instance = "middleware-189423:europe-west1:budgetdb";
}
