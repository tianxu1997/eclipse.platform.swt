package org.eclipse.swt.internal;

import java.util.EventObject;

/**
 * This class is the cross-platform version of the
 * java.util.EventObject class.
 * <p>
 * It is part of our effort to provide support for both J2SE
 * and J2ME platforms. Under this scheme, classes need to 
 * extend EventObjectCompatibility instead of
 * java.util.EventObject.
 * </p>
 * <p>
 * Note: java.util.EventObject is not part of CDC and CLDC.
 * </p>
 */
public class EventObjectCompatibility extends EventObject {

/**
 * Constructs a new instance of this class.
 *
 * @param source the object which fired the event
 */
public EventObjectCompatibility(Object source) {
	super(source);
}
}