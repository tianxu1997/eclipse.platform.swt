package org.eclipse.swt.examples.helloworld;

/*
* Licensed Materials - Property of IBM,
* SWT - The Simple Widget Toolkit,
* (c) Copyright IBM Corp 1998, 1999.
*/

/* Imports */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import java.util.ResourceBundle;

/*
* This example builds on HelloWorld1 and demonstrates the minimum amount 
* of code required to open an SWT Shell with a Label and process the events.
*/

public class HelloWorld2 {
	private static ResourceBundle resHello = ResourceBundle.getBundle("examples_helloworld");

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.CENTER);
	label.setText (resHello.getString("Hello_world"));
	label.setBounds (shell.getClientArea ());
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}