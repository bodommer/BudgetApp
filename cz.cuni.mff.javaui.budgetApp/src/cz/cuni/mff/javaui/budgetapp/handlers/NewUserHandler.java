package cz.cuni.mff.javaui.budgetapp.handlers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.javaui.budgetapp.dialogs.NewUser;

public class NewUserHandler {
    @Execute
    public void execute(MApplication application) {
    	   	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
        // create exception on purpose to demonstrate ErrorDialog
    	String url = "";
    	File[] listOfFiles = new File[0];
    	try {
    		url = (new URL(Platform.getInstallLocation().getURL()+Platform.getProduct().getName()+".ini")).toString();
	    	File folder = new File(url);
	    	listOfFiles = folder.listFiles();
    	} catch (MalformedURLException m) {
    		m.printStackTrace();
    	}
    	
    	List<String> names = new ArrayList<String>();
    	
    	for (int i = 0; i < listOfFiles.length; i++) {
    	    if (listOfFiles[i].isFile()) {
    		    if (listOfFiles[i].getName().matches("*.bap")) {
    		        names.add(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().length()-4));
    		    }
    	    }  
    	}
    	/*
    	try {
    		NewUserDialog nud = new NewUserDialog(shell);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	*/
    	
        try {
            String s = null;
            System.out.println(s.length());
        } catch (NullPointerException e) {
            // build the error message and include the current stack trace
            MultiStatus status = createMultiStatus(e.getLocalizedMessage(), e);
            // show error dialog
            //ErrorDialog.openError(shell, "Error", url, status);
        }
    }

    private static MultiStatus createMultiStatus(String msg, Throwable t) {

        List<Status> childStatuses = new ArrayList<>();
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

        for (StackTraceElement stackTrace: stackTraces) {
            Status status = new Status(IStatus.ERROR,
                    "com.example.e4.rcp.todo", stackTrace.toString());
            childStatuses.add(status);
        }

        MultiStatus ms = new MultiStatus("com.example.e4.rcp.todo",
                IStatus.ERROR, childStatuses.toArray(new Status[] {}),
                t.toString(), t);
        return ms;
    }
}
