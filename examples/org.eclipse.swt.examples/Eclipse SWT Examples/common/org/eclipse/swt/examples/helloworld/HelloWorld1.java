package org.eclipse.swt.examples.helloworld;

/*
* Licensed Materials - Property of IBM,
* SWT - The Simple Widget Toolkit,
* (c) Copyright IBM Corp 1998, 1999.
*/

/* Imports */

import org.eclipse.swt.widgets.*;

/*
* This example demonstrates the minimum amount of code required to open an SWT Shell
* and process the events.
*/
public class HelloWorld1 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}