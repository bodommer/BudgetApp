package cz.cuni.mff.javaui.budgetapp.handlers.user;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.database.DBData;
import cz.cuni.mff.javaui.budgetapp.dialogs.NewUserDialog;

/**
 * Handler for the event of adding a new user.
 * 
 * @author Andrej Jurco
 *
 */
public class NewUserHandler {
	@Execute
	public void execute(MApplication application, Shell shell) {

		NewUserDialog nud;
		nud = new NewUserDialog(shell);
		nud.create();
		if (nud.open() == Window.OK) {
			CreateUser(shell, nud.getName());

		}
		// nud.close();
	}

	private boolean CreateUser(Shell shell, String name) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			MessageDialog.openError(shell, "Unable to connect",
					"The application was unable to connect to the database. Please try again later.");
			return false;
		}

		try (Connection conn = DriverManager
				.getConnection(String.format("jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=%s", DBData.host,
						DBData.database, DBData.user, DBData.password, DBData.serverTimeZone))) {

			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO budget_db.user (name, balance, last_edit, created) VALUES(?, ?, ?, ?)");
			ps.setString(1, name);
			ps.setInt(2, 0);
			ps.setDate(3, new Date(System.currentTimeMillis()));
			ps.setDate(4, new Date(System.currentTimeMillis()));
			if (ps.execute()) {
				MessageDialog.openInformation(shell, "User created", "User creation done successfully");
			}

		} catch (SQLException ex) {
			MessageDialog.openError(shell, "User not created",
					"Database error. Check your internet connection and try again.");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
		return true;
	}
}
