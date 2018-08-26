package cz.cuni.mff.javaui.budgetapp.misc;

import org.eclipse.e4.ui.model.application.MApplication;

import cz.cuni.mff.javaui.budgetapp.parts.GraphPart;
import cz.cuni.mff.javaui.budgetapp.parts.PeriodsPart;
import cz.cuni.mff.javaui.budgetapp.parts.RecordInformationPart;
import cz.cuni.mff.javaui.budgetapp.parts.UserInformationPart;

/**
 * This class is meant solely to simplify retrieving certain data from the
 * application context more easily.
 * 
 * @author Andrej Jurco
 *
 */
public class DataLoader {
	public static int getUser(MApplication application) {
		if (application.getContext().get("user") == null)
			return 0;
		return (int) application.getContext().get("user");
	}

	public static PeriodsPart getPeriods(MApplication application) {
		return (PeriodsPart) application.getContext().get("periodsPart");
	}

	public static GraphPart getGraph(MApplication application) {
		return (GraphPart) application.getContext().get("graphPart");
	}

	public static RecordInformationPart getRecords(MApplication application) {
		return (RecordInformationPart) application.getContext().get("recordInformationPart");
	}

	public static UserInformationPart getUserInfo(MApplication application) {
		UserInformationPart uip = (UserInformationPart) application.getContext().get("userInformationPart");
		return uip;
	}

	public static int getPeriod(MApplication application) {
		if (application.getContext().get("period") == null)
			return 0;
		return (int) application.getContext().get("period");
	}
}