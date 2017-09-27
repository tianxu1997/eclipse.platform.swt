/*******************************************************************************
 * Copyright (c) 2000, 2017 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.widgets;


import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.gtk.*;

/**
 * Instances of this class represent a selectable user interface object
 * that represents an item in a table.
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>(none)</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 * <p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 *
 * @see <a href="http://www.eclipse.org/swt/snippets/#table">Table, TableItem, TableColumn snippets</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further information</a>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class TableItem extends Item {
	Table parent;
	Font font;
	Font[] cellFont;
	boolean cached, grayed;
	int columnSetHeight, columnSetWidth;

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>Table</code>), a style value
 * describing its behavior and appearance, and the index
 * at which to place it in the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 * @param index the zero-relative index to store the receiver in its parent
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 *    <li>ERROR_INVALID_RANGE - if the index is not between 0 and the number of elements in the parent (inclusive)</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#checkSubclass
 * @see Widget#getStyle
 */
public TableItem (Table parent, int style, int index) {
	this (parent, style, index, true);
}

/**
 * Constructs a new instance of this class given its parent
 * (which must be a <code>Table</code>) and a style value
 * describing its behavior and appearance. The item is added
 * to the end of the items maintained by its parent.
 * <p>
 * The style value is either one of the style constants defined in
 * class <code>SWT</code> which is applicable to instances of this
 * class, or must be built by <em>bitwise OR</em>'ing together
 * (that is, using the <code>int</code> "|" operator) two or more
 * of those <code>SWT</code> style constants. The class description
 * lists the style constants that are applicable to the class.
 * Style bits are also inherited from superclasses.
 * </p>
 *
 * @param parent a composite control which will be the parent of the new instance (cannot be null)
 * @param style the style of control to construct
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the parent</li>
 *    <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed subclass</li>
 * </ul>
 *
 * @see SWT
 * @see Widget#checkSubclass
 * @see Widget#getStyle
 */
public TableItem (Table parent, int style) {
	this (parent, style, checkNull (parent).getItemCount (), true);
}


TableItem (Table parent, int style, int index, boolean create) {
	super (parent, style);
	this.parent = parent;
	if (create) {
		parent.createItem (this, index);
	} else {
		handle = OS.g_malloc (OS.GtkTreeIter_sizeof ());
		OS.gtk_tree_model_iter_nth_child (parent.modelHandle, handle, 0, index);
	}
}

static Table checkNull (Table control) {
	if (control == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
	return control;
}

Color _getBackground () {
	long /*int*/ [] ptr = new long /*int*/ [1];
	OS.gtk_tree_model_get (parent.modelHandle, handle, Table.BACKGROUND_COLUMN, ptr, -1);
	if (ptr [0] == 0) return parent.getBackground ();
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = new GdkRGBA ();
		OS.memmove(gdkRGBA, ptr [0], GdkRGBA.sizeof);
		OS.gdk_rgba_free (ptr [0]);
		return Color.gtk_new(display, gdkRGBA);
	} else {
		GdkColor gdkColor = new GdkColor ();
		OS.memmove (gdkColor, ptr [0], GdkColor.sizeof);
		OS.gdk_color_free (ptr [0]);
		return Color.gtk_new (display, gdkColor);
	}
}

Color _getBackground (int index) {
	int count = Math.max (1, parent.columnCount);
	if (0 > index || index > count - 1) return _getBackground ();
	long /*int*/ [] ptr = new long /*int*/ [1];
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_tree_model_get (parent.modelHandle, handle, modelIndex + Table.CELL_BACKGROUND, ptr, -1);
	if (ptr [0] == 0) return _getBackground ();
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = new GdkRGBA ();
		OS.memmove(gdkRGBA, ptr [0], GdkRGBA.sizeof);
		OS.gdk_rgba_free (ptr [0]);
		return Color.gtk_new(display, gdkRGBA);
	} else {
		GdkColor gdkColor = new GdkColor ();
		OS.memmove (gdkColor, ptr [0], GdkColor.sizeof);
		OS.gdk_color_free (ptr [0]);
		return Color.gtk_new (display, gdkColor);
	}
}

boolean _getChecked () {
	int [] ptr = new int [1];
	OS.gtk_tree_model_get (parent.modelHandle, handle, Table.CHECKED_COLUMN, ptr, -1);
	return ptr [0] != 0;
}

Color _getForeground () {
	long /*int*/ [] ptr = new long /*int*/ [1];
	OS.gtk_tree_model_get (parent.modelHandle, handle, Table.FOREGROUND_COLUMN, ptr, -1);
	if (ptr [0] == 0) return parent.getForeground ();
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = new GdkRGBA ();
		OS.memmove(gdkRGBA, ptr [0], GdkRGBA.sizeof);
		OS.gdk_rgba_free (ptr [0]);
		return Color.gtk_new(display, gdkRGBA);
	} else {
		GdkColor gdkColor = new GdkColor ();
		OS.memmove (gdkColor, ptr [0], GdkColor.sizeof);
		OS.gdk_color_free (ptr [0]);
		return Color.gtk_new (display, gdkColor);
	}
}

Color _getForeground (int index) {
	int count = Math.max (1, parent.columnCount);
	if (0 > index || index > count - 1) return _getForeground ();
	long /*int*/ [] ptr = new long /*int*/ [1];
	int modelIndex =  parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_tree_model_get (parent.modelHandle, handle, modelIndex + Table.CELL_FOREGROUND, ptr, -1);
	if (ptr [0] == 0) return _getForeground ();
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = new GdkRGBA ();
		OS.memmove(gdkRGBA, ptr [0], GdkRGBA.sizeof);
		OS.gdk_rgba_free (ptr [0]);
		return Color.gtk_new(display, gdkRGBA);
	} else {
		GdkColor gdkColor = new GdkColor ();
		OS.memmove (gdkColor, ptr [0], GdkColor.sizeof);
		OS.gdk_color_free (ptr [0]);
		return Color.gtk_new (display, gdkColor);
	}
}

Image _getImage (int index) {
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return null;
	long /*int*/ [] ptr = new long /*int*/ [1];
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_tree_model_get (parent.modelHandle, handle, modelIndex + Table.CELL_PIXBUF, ptr, -1);
	if (ptr [0] == 0) return null;
	ImageList imageList = parent.imageList;
	int imageIndex = imageList.indexOf (ptr [0]);
	OS.g_object_unref (ptr [0]);
	if (imageIndex == -1) return null;
	return imageList.get (imageIndex);
}

String _getText (int index) {
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return "";
	long /*int*/ [] ptr = new long /*int*/ [1];
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_tree_model_get (parent.modelHandle, handle, modelIndex + Table.CELL_TEXT, ptr, -1);
	if (ptr [0] == 0) return "";
	int length = C.strlen (ptr [0]);
	byte[] buffer = new byte [length];
	C.memmove (buffer, ptr [0], length);
	OS.g_free (ptr [0]);
	return new String (Converter.mbcsToWcs (buffer));
}

@Override
protected void checkSubclass () {
	if (!isValidSubclass ()) error (SWT.ERROR_INVALID_SUBCLASS);
}

void clear () {
	if (parent.currentItem == this) return;
	if (cached || (parent.style & SWT.VIRTUAL) == 0) {
		int columnCount = OS.gtk_tree_model_get_n_columns (parent.modelHandle);
		/* the columns before FOREGROUND_COLUMN contain int values, subsequent columns contain pointers */
		for (int i=Table.CHECKED_COLUMN; i<Table.FOREGROUND_COLUMN; i++) {
			OS.gtk_list_store_set (parent.modelHandle, handle, i, 0, -1);
		}
		for (int i=Table.FOREGROUND_COLUMN; i<columnCount; i++) {
			OS.gtk_list_store_set (parent.modelHandle, handle, i, (long /*int*/)0, -1);
		}
	}
	cached = false;
	font = null;
	cellFont = null;
}

@Override
void destroyWidget () {
	parent.destroyItem (this);
	releaseHandle ();
}

/**
 * Returns the receiver's background color.
 *
 * @return the background color
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 2.0
 */
public Color getBackground () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getBackground ();
}

/**
 * Returns a rectangle describing the size and location of the receiver's
 * text relative to its parent.
 *
 * @return the bounding rectangle of the receiver's text
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.2
 */
public Rectangle getBounds () {
	return DPIUtil.autoScaleDown (getBoundsinPixels ());
}

/**
 * Returns a rectangle describing the size and location of the receiver's
 * text relative to its parent.
 *
 * @return the bounding rectangle of the receiver's text
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.105
 */
Rectangle getBoundsinPixels () {
	// TODO fully test on early and later versions of GTK
	// shifted a bit too far right on later versions of GTK - however, old Tree also had this problem
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	long /*int*/ parentHandle = parent.handle;
	long /*int*/ column = OS.gtk_tree_view_get_column (parentHandle, 0);
	if (column == 0) return new Rectangle (0, 0, 0, 0);
	long /*int*/ textRenderer = parent.getTextRenderer (column);
	long /*int*/ pixbufRenderer = parent.getPixbufRenderer (column);
	if (textRenderer == 0 || pixbufRenderer == 0)  return new Rectangle (0, 0, 0, 0);

	long /*int*/ path = OS.gtk_tree_model_get_path (parent.modelHandle, handle);
	OS.gtk_widget_realize (parentHandle);

	boolean isExpander = OS.gtk_tree_model_iter_n_children (parent.modelHandle, handle) > 0;
	boolean isExpanded = OS.gtk_tree_view_row_expanded (parentHandle, path);
	OS.gtk_tree_view_column_cell_set_cell_data (column, parent.modelHandle, handle, isExpander, isExpanded);

	GdkRectangle rect = new GdkRectangle ();
	OS.gtk_tree_view_get_cell_area (parentHandle, path, column, rect);
	OS.gtk_tree_path_free (path);
	if ((parent.getStyle () & SWT.MIRRORED) != 0) rect.x = parent.getClientWidth () - rect.width - rect.x;
	int right = rect.x + rect.width;

	int [] x = new int [1], w = new int [1];
	parent.ignoreSize = true;
	gtk_cell_renderer_get_preferred_size (textRenderer, parentHandle, w, null);
	parent.ignoreSize = false;
	rect.width = w [0];
	int [] buffer = new int [1];
	if (OS.gtk_tree_view_get_expander_column (parentHandle) == column) {
		OS.gtk_widget_style_get (parentHandle, OS.expander_size, buffer, 0);
		rect.x += buffer [0] + TreeItem.EXPANDER_EXTRA_PADDING;
	}
	OS.gtk_widget_style_get (parentHandle, OS.horizontal_separator, buffer, 0);
	int horizontalSeparator = buffer[0];
	rect.x += horizontalSeparator;

	gtk_tree_view_column_cell_get_position (column, textRenderer, x, null);
	rect.x += x [0];

	if (parent.columnCount > 0) {
		if (rect.x + rect.width > right) {
			rect.width = Math.max (0, right - rect.x);
		}
	}
	int width = OS.gtk_tree_view_column_get_visible (column) ? rect.width + 1 : 0;
	Rectangle r = new Rectangle (rect.x, rect.y, width, rect.height + 1);
	if (parent.getHeaderVisible() && OS.GTK_VERSION > OS.VERSION(3, 9, 0)) {
		r.y += parent.getHeaderHeightInPixels();
	}
	return r;
}

/**
 * Returns the background color at the given column index in the receiver.
 *
 * @param index the column index
 * @return the background color
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public Color getBackground (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getBackground (index);
}

/**
 * Returns a rectangle describing the receiver's size and location
 * relative to its parent at a column in the table.
 *
 * @param index the index that specifies the column
 * @return the receiver's bounding column rectangle
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Rectangle getBounds (int index) {
	checkWidget ();
	return DPIUtil.autoScaleDown (getBoundsInPixels (index));
}

Rectangle getBoundsInPixels (int index) {
	checkWidget();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	long /*int*/ parentHandle = parent.handle;
	long /*int*/ column = 0;
	if (index >= 0 && index < parent.columnCount) {
		column = parent.columns [index].handle;
	} else {
		column = OS.gtk_tree_view_get_column (parentHandle, index);
	}
	if (column == 0) return new Rectangle (0, 0, 0, 0);
	long /*int*/ path = OS.gtk_tree_model_get_path (parent.modelHandle, handle);
	OS.gtk_widget_realize (parentHandle);
	OS.gtk_tree_view_column_cell_set_cell_data (column, parent.modelHandle, handle, false, false);
	GdkRectangle rect = new GdkRectangle ();
	OS.gtk_tree_view_get_cell_area (parentHandle, path, column, rect);
	OS.gtk_tree_path_free (path);
	int [] cw = new int [1], ch = new int [1];
	OS.gtk_tree_view_column_cell_get_size (column, null, null, null, cw, ch);
	rect.height = ch [0];
	if ((parent.getStyle () & SWT.MIRRORED) != 0) rect.x = parent.getClientWidth () - rect.width - rect.x;

	if (index == 0 && (parent.style & SWT.CHECK) != 0) {
		int [] x = new int [1], w = new int [1];
		gtk_tree_view_column_cell_get_position (column, parent.checkRenderer, x, w);
		rect.x += x [0] + w [0];
		rect.width -= x [0] + w [0];
	}
	int width = OS.gtk_tree_view_column_get_visible (column) ? rect.width + 1 : 0;
	Rectangle r = new Rectangle (rect.x, rect.y, width, rect.height + 1);
	if (parent.getHeaderVisible() && OS.GTK_VERSION > OS.VERSION(3, 9, 0)) {
		r.y += parent.getHeaderHeightInPixels();
	}
	return r;
}

/**
 * Returns <code>true</code> if the receiver is checked,
 * and false otherwise.  When the parent does not have
 * the <code>CHECK</code> style, return false.
 *
 * @return the checked state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public boolean getChecked () {
	checkWidget();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	if ((parent.style & SWT.CHECK) == 0) return false;
	return _getChecked ();
}

/**
 * Returns the font that the receiver will use to paint textual information for this item.
 *
 * @return the receiver's font
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public Font getFont () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return font != null ? font : parent.getFont ();
}

/**
 * Returns the font that the receiver will use to paint textual information
 * for the specified cell in this item.
 *
 * @param index the column index
 * @return the receiver's font
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public Font getFont (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	int count = Math.max (1, parent.columnCount);
	if (0 > index || index > count - 1) return getFont ();
	if (cellFont == null || cellFont [index] == null) return getFont ();
	return cellFont [index];
}

/**
 * Returns the foreground color that the receiver will use to draw.
 *
 * @return the receiver's foreground color
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 2.0
 */
public Color getForeground () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getForeground ();
}

/**
 *
 * Returns the foreground color at the given column index in the receiver.
 *
 * @param index the column index
 * @return the foreground color
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public Color getForeground (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getForeground (index);
}

/**
 * Returns <code>true</code> if the receiver is grayed,
 * and false otherwise. When the parent does not have
 * the <code>CHECK</code> style, return false.
 *
 * @return the grayed state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public boolean getGrayed () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	if ((parent.style & SWT.CHECK) == 0) return false;
	return grayed;
}

@Override
public Image getImage () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return getImage (0);
}

/**
 * Returns the image stored at the given column index in the receiver,
 * or null if the image has not been set or if the column does not exist.
 *
 * @param index the column index
 * @return the image stored at the given column index in the receiver
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Image getImage (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getImage (index);
}

/**
 * Returns a rectangle describing the size and location
 * relative to its parent of an image at a column in the
 * table.  An empty rectangle is returned if index exceeds
 * the index of the table's last column.
 *
 * @param index the index that specifies the column
 * @return the receiver's bounding image rectangle
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Rectangle getImageBounds (int index) {
	checkWidget ();
	return DPIUtil.autoScaleDown (getImageBoundsInPixels (index));
}

Rectangle getImageBoundsInPixels (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	long /*int*/ parentHandle = parent.handle;
	long /*int*/ column = 0;
	if (index >= 0 && index < parent.columnCount) {
		column = parent.columns [index].handle;
	} else {
		column = OS.gtk_tree_view_get_column (parentHandle, index);
	}
	if (column == 0) return new Rectangle (0, 0, 0, 0);
	long /*int*/ pixbufRenderer = parent.getPixbufRenderer (column);
	if (pixbufRenderer == 0)  return new Rectangle (0, 0, 0, 0);
	GdkRectangle rect = new GdkRectangle ();
	long /*int*/ path = OS.gtk_tree_model_get_path (parent.modelHandle, handle);
	OS.gtk_widget_realize (parentHandle);
	OS.gtk_tree_view_get_cell_area (parentHandle, path, column, rect);
	OS.gtk_tree_path_free (path);
	if ((parent.getStyle () & SWT.MIRRORED) != 0) rect.x = parent.getClientWidth () - rect.width - rect.x;
	int [] x = new int [1], w = new int[1];
	gtk_tree_view_column_cell_get_position (column, pixbufRenderer, x, w);
	/*
	 * Feature in GTK. When a pixbufRenderer has size of 0x0, gtk_tree_view_column_cell_get_position
	 * returns a position of 0 as well. This causes offset issues meaning that images/widgets/etc.
	 * can be placed over the text. We need to account for the base case of a pixbufRenderer that has
	 * yet to be sized, as per Bug 469277 & 476419. NOTE: this change has been ported to Tables since Tables/Trees both
	 * use the same underlying GTK structure.
	 */
	if (OS.GTK3) {
		if (parent.pixbufSizeSet) {
			if (x [0] > 0) {
				rect.x += x [0];
			}
		} else {
			/*
			 * If the size of the pixbufRenderer hasn't been set, we need to take into account the
			 * position of the textRenderer, to ensure images/widgets/etc. aren't placed over the TableItem's
			 * text.
			 */
			long /*int*/ textRenderer = parent.getTextRenderer (column);
			if (textRenderer == 0)  return new Rectangle (0, 0, 0, 0);
			int [] xText = new int [1], wText = new int [1];
			gtk_tree_view_column_cell_get_position (column, textRenderer, xText, wText);
			rect.x += xText [0];
		}
	} else {
		rect.x += x [0];
	}
	rect.width = w [0];
	int width = OS.gtk_tree_view_column_get_visible (column) ? rect.width : 0;
	return new Rectangle (rect.x, rect.y, width, rect.height + 1);
}

/**
 * Gets the image indent.
 *
 * @return the indent
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public int getImageIndent () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	/* Image indent is not supported on GTK */
	return 0;
}

@Override
String getNameText () {
	if ((parent.style & SWT.VIRTUAL) != 0) {
		if (!cached) return "*virtual*"; //$NON-NLS-1$
	}
	return super.getNameText ();
}

/**
 * Returns the receiver's parent, which must be a <code>Table</code>.
 *
 * @return the receiver's parent
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public Table getParent () {
	checkWidget ();
	return parent;
}

@Override
public String getText () {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return getText (0);
}

/**
 * Returns the text stored at the given column index in the receiver,
 * or empty string if the text has not been set.
 *
 * @param index the column index
 * @return the text stored at the given column index in the receiver
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public String getText (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	return _getText (index);
}

/**
 * Returns a rectangle describing the size and location
 * relative to its parent of the text at a column in the
 * table.  An empty rectangle is returned if index exceeds
 * the index of the table's last column.
 *
 * @param index the index that specifies the column
 * @return the receiver's bounding text rectangle
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.3
 */
public Rectangle getTextBounds (int index) {
	checkWidget ();
	return DPIUtil.autoScaleDown (getTextBoundsInPixels (index));
}

Rectangle getTextBoundsInPixels (int index) {
	checkWidget ();
	if (!parent.checkData (this)) error (SWT.ERROR_WIDGET_DISPOSED);
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return new Rectangle (0, 0, 0, 0);
	// TODO fully test on early and later versions of GTK
	// shifted a bit too far right on later versions of GTK - however, old Tree also had this problem
	long /*int*/ parentHandle = parent.handle;
	long /*int*/ column = 0;
	if (index >= 0 && index < parent.columnCount) {
		column = parent.columns [index].handle;
	} else {
		column = OS.gtk_tree_view_get_column (parentHandle, index);
	}
	if (column == 0) return new Rectangle (0, 0, 0, 0);
	long /*int*/ textRenderer = parent.getTextRenderer (column);
	long /*int*/ pixbufRenderer = parent.getPixbufRenderer (column);
	if (textRenderer == 0 || pixbufRenderer == 0)  return new Rectangle (0, 0, 0, 0);

	long /*int*/ path = OS.gtk_tree_model_get_path (parent.modelHandle, handle);
	OS.gtk_widget_realize (parentHandle);

	boolean isExpander = OS.gtk_tree_model_iter_n_children (parent.modelHandle, handle) > 0;
	boolean isExpanded = OS.gtk_tree_view_row_expanded (parentHandle, path);
	OS.gtk_tree_view_column_cell_set_cell_data (column, parent.modelHandle, handle, isExpander, isExpanded);

	GdkRectangle rect = new GdkRectangle ();
	OS.gtk_tree_view_get_cell_area (parentHandle, path, column, rect);
	OS.gtk_tree_path_free (path);
	if ((parent.getStyle () & SWT.MIRRORED) != 0) rect.x = parent.getClientWidth () - rect.width - rect.x;
	int right = rect.x + rect.width;

	int [] x = new int [1], w = new int [1];
	parent.ignoreSize = true;
	gtk_cell_renderer_get_preferred_size (textRenderer, parentHandle, w, null);
	parent.ignoreSize = false;
	int [] buffer = new int [1];
	if (OS.gtk_tree_view_get_expander_column (parentHandle) == column) {
		OS.gtk_widget_style_get (parentHandle, OS.expander_size, buffer, 0);
		rect.x += buffer [0] + TreeItem.EXPANDER_EXTRA_PADDING;
	}
	OS.gtk_widget_style_get (parentHandle, OS.horizontal_separator, buffer, 0);
	int horizontalSeparator = buffer[0];
	rect.x += horizontalSeparator;

	gtk_tree_view_column_cell_get_position (column, textRenderer, x, null);
	/*
	 * Fix for Eclipse bug 476562, we need to re-adjust the bounds for the text
	 * when the separator value is less than the width of the image. Previously
	 * images larger than 16px in width would be cut off on the right side.
	 * NOTE: this change has been ported to Tables since Tables/Trees both use the
	 * same underlying GTK structure.
	 */
	if (OS.GTK3) {
		Image image = _getImage(index);
		int imageWidth = 0;
		if (image != null) {
			imageWidth = image.getBoundsInPixels ().width;
		}
		if (x [0] < imageWidth) {
			rect.x += imageWidth;
		} else {
			rect.x += x [0];
		}
	} else {
		rect.x += x [0];
	}
	if (parent.columnCount > 0) {
		if (rect.x + rect.width > right) {
			rect.width = Math.max (0, right - rect.x);
		}
	}
	int width = OS.gtk_tree_view_column_get_visible (column) ? rect.width + 1 : 0;
	return new Rectangle (rect.x, rect.y, width, rect.height + 1);
}

@Override
void releaseHandle () {
	if (handle != 0) OS.g_free (handle);
	handle = 0;
	super.releaseHandle ();
	parent = null;
}

@Override
void releaseWidget () {
	super.releaseWidget ();
	font = null;
	cellFont = null;
}

/**
 * Sets the receiver's background color to the color specified
 * by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param color the new color (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 2.0
 */
public void setBackground (Color color) {
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (_getBackground ().equals (color)) return;
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = color != null ? color.handleRGBA : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, Table.BACKGROUND_COLUMN, gdkRGBA, -1);
	} else {
		GdkColor gdkColor = color != null ? color.handle : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, Table.BACKGROUND_COLUMN, gdkColor, -1);
	}
	cached = true;
}

/**
 * Sets the background color at the given column index in the receiver
 * to the color specified by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param index the column index
 * @param color the new color (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public void setBackground (int index, Color color) {
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (_getBackground (index).equals (color)) return;
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return;
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = color != null ? color.handleRGBA : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_BACKGROUND, gdkRGBA, -1);
	} else {
		GdkColor gdkColor = color != null ? color.handle : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_BACKGROUND, gdkColor, -1);
	}
	cached = true;

	if (color != null) {
		boolean customDraw = (parent.columnCount == 0)  ? parent.firstCustomDraw : parent.columns [index].customDraw;
		if (!customDraw) {
			if ((parent.style & SWT.VIRTUAL) == 0) {
				long /*int*/ parentHandle = parent.handle;
				long /*int*/ column = 0;
				if (parent.columnCount > 0) {
					column = parent.columns [index].handle;
				} else {
					column = OS.gtk_tree_view_get_column (parentHandle, index);
				}
				if (column == 0) return;
				long /*int*/ textRenderer = parent.getTextRenderer (column);
				long /*int*/ imageRenderer = parent.getPixbufRenderer (column);
				OS.gtk_tree_view_column_set_cell_data_func (column, textRenderer, display.cellDataProc, parentHandle, 0);
				OS.gtk_tree_view_column_set_cell_data_func (column, imageRenderer, display.cellDataProc, parentHandle, 0);
			}
			if (parent.columnCount == 0) {
				parent.firstCustomDraw = true;
			} else {
				parent.columns [index].customDraw = true;
			}
		}
	}
}

/**
 * Sets the checked state of the checkbox for this item.  This state change
 * only applies if the Table was created with the SWT.CHECK style.
 *
 * @param checked the new checked state of the checkbox
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setChecked (boolean checked) {
	checkWidget();
	if ((parent.style & SWT.CHECK) == 0) return;
	if (_getChecked () == checked) return;
	OS.gtk_list_store_set (parent.modelHandle, handle, Table.CHECKED_COLUMN, checked, -1);
	/*
	* GTK+'s "inconsistent" state does not match SWT's concept of grayed.  To
	* show checked+grayed differently from unchecked+grayed, we must toggle the
	* grayed state on check and uncheck.
	*/
	OS.gtk_list_store_set (parent.modelHandle, handle, Table.GRAYED_COLUMN, !checked ? false : grayed, -1);
	cached = true;
}

/**
 * Sets the font that the receiver will use to paint textual information
 * for this item to the font specified by the argument, or to the default font
 * for that kind of control if the argument is null.
 *
 * @param font the new font (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public void setFont (Font font){
	checkWidget ();
	if (font != null && font.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	Font oldFont = this.font;
	if (oldFont == font) return;
	this.font = font;
	if (oldFont != null && oldFont.equals (font)) return;
	long /*int*/ fontHandle = font != null ? font.handle : 0;
	OS.gtk_list_store_set (parent.modelHandle, handle, Table.FONT_COLUMN, fontHandle, -1);
	cached = true;
}

/**
 * Sets the font that the receiver will use to paint textual information
 * for the specified cell in this item to the font specified by the
 * argument, or to the default font for that kind of control if the
 * argument is null.
 *
 * @param index the column index
 * @param font the new font (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public void setFont (int index, Font font) {
	checkWidget ();
	if (font != null && font.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return;
	if (cellFont == null) {
		if (font == null) return;
		cellFont = new Font [count];
	}
	Font oldFont = cellFont [index];
	if (oldFont == font) return;
	cellFont [index] = font;
	if (oldFont != null && oldFont.equals (font)) return;

	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	long /*int*/ fontHandle  = font != null ? font.handle : 0;
	OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_FONT, fontHandle, -1);
	cached = true;

	if (font != null) {
		boolean customDraw = (parent.columnCount == 0)  ? parent.firstCustomDraw : parent.columns [index].customDraw;
		if (!customDraw) {
			if ((parent.style & SWT.VIRTUAL) == 0) {
				long /*int*/ parentHandle = parent.handle;
				long /*int*/ column = 0;
				if (parent.columnCount > 0) {
					column = parent.columns [index].handle;
				} else {
					column = OS.gtk_tree_view_get_column (parentHandle, index);
				}
				if (column == 0) return;
				long /*int*/ textRenderer = parent.getTextRenderer (column);
				long /*int*/ imageRenderer = parent.getPixbufRenderer (column);
				OS.gtk_tree_view_column_set_cell_data_func (column, textRenderer, display.cellDataProc, parentHandle, 0);
				OS.gtk_tree_view_column_set_cell_data_func (column, imageRenderer, display.cellDataProc, parentHandle, 0);
			}
			if (parent.columnCount == 0) {
				parent.firstCustomDraw = true;
			} else {
				parent.columns [index].customDraw = true;
			}
		}
	}
}

/**
 * Sets the receiver's foreground color to the color specified
 * by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param color the new color (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 2.0
 */
public void setForeground (Color color){
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (_getForeground ().equals (color)) return;
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = color != null ? color.handleRGBA : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, Table.FOREGROUND_COLUMN, gdkRGBA, -1);
	} else {
		GdkColor gdkColor = color != null ? color.handle : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, Table.FOREGROUND_COLUMN, gdkColor, -1);
	}
	cached = true;
}

/**
 * Sets the foreground color at the given column index in the receiver
 * to the color specified by the argument, or to the default system color for the item
 * if the argument is null.
 *
 * @param index the column index
 * @param color the new color (or null)
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the argument has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @since 3.0
 */
public void setForeground (int index, Color color){
	checkWidget ();
	if (color != null && color.isDisposed ()) {
		error (SWT.ERROR_INVALID_ARGUMENT);
	}
	if (_getForeground (index).equals (color)) return;
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return;
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	if (OS.GTK3) {
		GdkRGBA gdkRGBA = color != null ? color.handleRGBA : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_FOREGROUND, gdkRGBA, -1);
	} else {
		GdkColor gdkColor = color != null ? color.handle : null;
		OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_FOREGROUND, gdkColor, -1);
	}
	cached = true;

	if (color != null) {
		boolean customDraw = (parent.columnCount == 0)  ? parent.firstCustomDraw : parent.columns [index].customDraw;
		if (!customDraw) {
			if ((parent.style & SWT.VIRTUAL) == 0) {
				long /*int*/ parentHandle = parent.handle;
				long /*int*/ column = 0;
				if (parent.columnCount > 0) {
					column = parent.columns [index].handle;
				} else {
					column = OS.gtk_tree_view_get_column (parentHandle, index);
				}
				if (column == 0) return;
				long /*int*/ textRenderer = parent.getTextRenderer (column);
				long /*int*/ imageRenderer = parent.getPixbufRenderer (column);
				OS.gtk_tree_view_column_set_cell_data_func (column, textRenderer, display.cellDataProc, parentHandle, 0);
				OS.gtk_tree_view_column_set_cell_data_func (column, imageRenderer, display.cellDataProc, parentHandle, 0);
			}
			if (parent.columnCount == 0) {
				parent.firstCustomDraw = true;
			} else {
				parent.columns [index].customDraw = true;
			}
		}
	}
}

/**
 * Sets the grayed state of the checkbox for this item.  This state change
 * only applies if the Table was created with the SWT.CHECK style.
 *
 * @param grayed the new grayed state of the checkbox;
 *
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setGrayed (boolean grayed) {
	checkWidget();
	if ((parent.style & SWT.CHECK) == 0) return;
	if (this.grayed == grayed) return;
	this.grayed = grayed;
	/*
	* GTK+'s "inconsistent" state does not match SWT's concept of grayed.
	* Render checked+grayed as "inconsistent", unchecked+grayed as blank.
	*/
	int [] ptr = new int [1];
	OS.gtk_tree_model_get (parent.modelHandle, handle, Table.CHECKED_COLUMN, ptr, -1);
	OS.gtk_list_store_set (parent.modelHandle, handle, Table.GRAYED_COLUMN, ptr [0] == 0 ? false : grayed, -1);
	cached = true;
}

/**
 * Sets the receiver's image at a column.
 *
 * @param index the column index
 * @param image the new image
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - if the image has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setImage (int index, Image image) {
	checkWidget ();
	if (image != null && image.isDisposed ()) {
		error(SWT.ERROR_INVALID_ARGUMENT);
	}
	if (image != null && image.type == SWT.ICON) {
		if (image.equals (_getImage (index))) return;
	}
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return;
	long /*int*/ pixbuf = 0;
	if (image != null) {
		ImageList imageList = parent.imageList;
		if (imageList == null) imageList = parent.imageList = new ImageList ();
		int imageIndex = imageList.indexOf (image);
		if (imageIndex == -1) imageIndex = imageList.add (image);
		pixbuf = imageList.getPixbuf (imageIndex);
	}
	/*
	 * Reset size of pixbufRenderer if we have an image being set that is larger
	 * than the current size of the pixbufRenderer. Fix for bug 457196.
	 * We only do this if the size of the pixbufRenderer has not yet been set.
	 * Otherwise, the pixbufRenderer retains the same size as the first image added.
	 * See comment #4, Bug 478560. Note that all columns need to have their
	 * pixbufRenderer set to this size after the initial image is set. NOTE: this
	 * change has been ported to Tables since Tables/Trees both use the same
	 * underlying GTK structure.
	 */
	if (OS.GTK3) {
		long /*int*/parentHandle = parent.handle;
		long /*int*/ column = OS.gtk_tree_view_get_column (parentHandle, index);
		long /*int*/ pixbufRenderer = parent.getPixbufRenderer (column);
		int [] currentWidth = new int [1];
		int [] currentHeight= new int [1];
		OS.gtk_cell_renderer_get_fixed_size (pixbufRenderer, currentWidth, currentHeight);
		if (!parent.pixbufSizeSet) {
			if (image != null) {
				int iWidth = image.getBoundsInPixels ().width;
				int iHeight = image.getBoundsInPixels ().height;
				if (iWidth > currentWidth [0] || iHeight > currentHeight [0]) {
					OS.gtk_cell_renderer_set_fixed_size (pixbufRenderer, iWidth, iHeight);
					parent.pixbufSizeSet = true;
					columnSetHeight = iHeight;
					columnSetWidth = iWidth;
				}
			}
		} else {
			/*
			 * We check to see if the cached value is greater than the size of the pixbufRenderer.
			 * If it is, then we change the size of the pixbufRenderer accordingly.
			 * Bug 489025: There is a corner case where the below is triggered when current(Width|Height) is -1,
			 * which results in icons being set to 0. Fix is to compare only positive sizes.
			 */
			if (columnSetWidth > Math.max(currentWidth [0], 0) || columnSetHeight > Math.max(currentHeight [0], 0)) {
				OS.gtk_cell_renderer_set_fixed_size (pixbufRenderer, columnSetWidth, columnSetHeight);
			}
		}
	}
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_PIXBUF, pixbuf, -1);
	/*
	 * Bug in GTK.  When in fixed height mode, GTK does not recalculate the cell renderer width
	 * when the image is changed in the model.  The fix is to force it to recalculate the width if
	 * more space is required.
	 */
	if ((parent.style & SWT.VIRTUAL) != 0 && parent.currentItem == null) {
		if (image != null) {
			long /*int*/parentHandle = parent.handle;
			long /*int*/ column = OS.gtk_tree_view_get_column (parentHandle, index);
			int [] w = new int [1];
			long /*int*/ pixbufRenderer = parent.getPixbufRenderer(column);
			gtk_tree_view_column_cell_get_position (column, pixbufRenderer, null, w);
			if (w[0] < image.getBoundsInPixels().width) {
				/*
				* There is no direct way to clear the cell renderer width so we
				* are relying on the fact that it is done as part of modifying
				* the style.
				*/
				if (!OS.GTK3) {
					long /*int*/ style = OS.gtk_widget_get_modifier_style (parentHandle);
					parent.modifyStyle (parentHandle, style);
				}
			}
		}
	}
	cached = true;
	/*
	 * Bug 465056: single column Tables have a very small initial width.
	 * Fix: when text or an image is set for a Table, compute its
	 * width and see if it's larger than the maximum of the previous widths.
	 */
	long /*int*/ column;
	if (parent.columnCount == 0) {
		column = OS.gtk_tree_view_get_column (parent.handle, index);
		parent.maxWidth = Math.max(parent.maxWidth, parent.calculateWidth(column, this.handle));
	}
}

@Override
public void setImage (Image image) {
	checkWidget ();
	setImage (0, image);
}

/**
 * Sets the image for multiple columns in the table.
 *
 * @param images the array of new images
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the array of images is null</li>
 *    <li>ERROR_INVALID_ARGUMENT - if one of the images has been disposed</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setImage (Image [] images) {
	checkWidget ();
	if (images == null) error (SWT.ERROR_NULL_ARGUMENT);
	for (int i=0; i<images.length; i++) {
		setImage (i, images [i]);
	}
}

/**
 * Sets the indent of the first column's image, expressed in terms of the image's width.
 *
 * @param indent the new indent
 *
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 *
 * @deprecated this functionality is not supported on most platforms
 */
@Deprecated
public void setImageIndent (int indent) {
	checkWidget ();
	if (indent < 0) return;
	/* Image indent is not supported on GTK */
	cached = true;
}

/**
 * Sets the receiver's text at a column
 * <p>
 * Note: If control characters like '\n', '\t' etc. are used
 * in the string, then the behavior is platform dependent.
 * </p>
 * @param index the column index
 * @param string the new text
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the text is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setText (int index, String string) {
	checkWidget ();
	if (string == null) error (SWT.ERROR_NULL_ARGUMENT);
	if (_getText (index).equals (string)) return;
	int count = Math.max (1, parent.getColumnCount ());
	if (0 > index || index > count - 1) return;
	byte[] buffer = Converter.wcsToMbcs (string, true);
	int modelIndex = parent.columnCount == 0 ? Table.FIRST_COLUMN : parent.columns [index].modelIndex;
	OS.gtk_list_store_set (parent.modelHandle, handle, modelIndex + Table.CELL_TEXT, buffer, -1);
	cached = true;
	/*
	 * Bug 465056: single column Tables have a very small initial width.
	 * Fix: when text or an image is set for a Table, compute its
	 * width and see if it's larger than the maximum of the previous widths.
	 */
	long /*int*/ column;
	if (parent.columnCount == 0) {
		column = OS.gtk_tree_view_get_column (parent.handle, index);
		parent.maxWidth = Math.max(parent.maxWidth, parent.calculateWidth(column, this.handle));
	}
}

@Override
public void setText (String string) {
	checkWidget ();
	setText (0, string);
}

/**
 * Sets the text for multiple columns in the table.
 * <p>
 * Note: If control characters like '\n', '\t' etc. are used
 * in the string, then the behavior is platform dependent.
 * </p>
 * @param strings the array of new strings
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - if the text is null</li>
 * </ul>
 * @exception SWTException <ul>
 *    <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
 *    <li>ERROR_THREAD_INVALID_ACCESS - if not called from the thread that created the receiver</li>
 * </ul>
 */
public void setText (String [] strings) {
	checkWidget ();
	if (strings == null) error (SWT.ERROR_NULL_ARGUMENT);
	for (int i=0; i<strings.length; i++) {
		String string = strings [i];
		if (string != null) setText (i, string);
	}
}
}
