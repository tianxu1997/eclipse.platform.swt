/*******************************************************************************
* Copyright (c) 2000, 2004 IBM Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Common Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/cpl-v10.html
* 
* Contributors:
*     IBM Corporation - initial API and implementation
*******************************************************************************/

#ifdef NATIVE_STATS
int OS_nativeFunctionCallCount[];
char* OS_nativeFunctionNames[];
#define OS_NATIVE_ENTER(env, that, func) OS_nativeFunctionCallCount[func]++;
#define OS_NATIVE_EXIT(env, that, func) 
#else
#define OS_NATIVE_ENTER(env, that, func) 
#define OS_NATIVE_EXIT(env, that, func) 
#endif

typedef enum {
	CODESET_FUNC,
	Call_FUNC,
	ConnectionNumber_FUNC,
	FD_1ISSET_FUNC,
	FD_1SET_FUNC,
	FD_1ZERO_FUNC,
	LC_1CTYPE_FUNC,
	MB_1CUR_1MAX_FUNC,
	XAllocColor_FUNC,
	XBell_FUNC,
	XBlackPixel_FUNC,
	XChangeActivePointerGrab_FUNC,
	XChangeProperty_FUNC,
	XChangeWindowAttributes_FUNC,
	XCheckIfEvent_FUNC,
	XCheckMaskEvent_FUNC,
	XCheckWindowEvent_FUNC,
	XClearArea_FUNC,
	XClipBox_FUNC,
	XCloseDisplay_FUNC,
	XCopyArea_FUNC,
	XCopyPlane_FUNC,
	XCreateBitmapFromData_FUNC,
	XCreateFontCursor_FUNC,
	XCreateGC_FUNC,
	XCreateImage_FUNC,
	XCreatePixmap_FUNC,
	XCreatePixmapCursor_FUNC,
	XCreateRegion_FUNC,
	XCreateWindow_FUNC,
	XDefaultColormap_FUNC,
	XDefaultColormapOfScreen_FUNC,
	XDefaultDepthOfScreen_FUNC,
	XDefaultGCOfScreen_FUNC,
	XDefaultRootWindow_FUNC,
	XDefaultScreen_FUNC,
	XDefaultScreenOfDisplay_FUNC,
	XDefaultVisual_FUNC,
	XDefineCursor_FUNC,
	XDestroyImage_FUNC,
	XDestroyRegion_FUNC,
	XDestroyWindow_FUNC,
	XDisplayHeight_FUNC,
	XDisplayHeightMM_FUNC,
	XDisplayWidth_FUNC,
	XDisplayWidthMM_FUNC,
	XDrawArc_FUNC,
	XDrawLine_FUNC,
	XDrawLines_FUNC,
	XDrawPoint_FUNC,
	XDrawRectangle_FUNC,
	XEmptyRegion_FUNC,
	XEventsQueued_FUNC,
	XFillArc_FUNC,
	XFillPolygon_FUNC,
	XFillRectangle_FUNC,
	XFilterEvent_FUNC,
	XFlush_FUNC,
	XFontsOfFontSet_FUNC,
	XFree_FUNC,
	XFreeColors_FUNC,
	XFreeCursor_FUNC,
	XFreeFont_FUNC,
	XFreeFontNames_FUNC,
	XFreeGC_FUNC,
	XFreePixmap_FUNC,
	XFreeStringList_FUNC,
	XGetGCValues_FUNC,
	XGetGeometry_FUNC,
	XGetIconSizes_FUNC,
	XGetImage_FUNC,
	XGetInputFocus_FUNC,
	XGetModifierMapping_FUNC,
	XGetWindowAttributes_FUNC,
	XGetWindowProperty_FUNC,
	XGrabKeyboard_FUNC,
	XGrabPointer_FUNC,
	XInitThreads_FUNC,
	XInternAtom_FUNC,
	XIntersectRegion_FUNC,
	XKeysymToKeycode_FUNC,
	XKeysymToString_FUNC,
	XListFonts_FUNC,
	XListProperties_FUNC,
	XLocaleOfFontSet_FUNC,
	XLookupString_FUNC,
	XLowerWindow_FUNC,
	XMapWindow_FUNC,
	XMoveResizeWindow_FUNC,
	XOpenDisplay_FUNC,
	XPointInRegion_FUNC,
	XPolygonRegion_FUNC,
	XPutImage_FUNC,
	XQueryBestCursor_FUNC,
	XQueryColor_FUNC,
	XQueryPointer_FUNC,
	XQueryTree_FUNC,
	XRaiseWindow_FUNC,
	XReconfigureWMWindow_FUNC,
	XRectInRegion_FUNC,
	XReparentWindow_FUNC,
	XResizeWindow_FUNC,
	XRootWindowOfScreen_FUNC,
	XSelectInput_FUNC,
	XSendEvent_FUNC,
	XSetBackground_FUNC,
	XSetClipMask_FUNC,
	XSetClipRectangles_FUNC,
	XSetDashes_FUNC,
	XSetErrorHandler_FUNC,
	XSetFillStyle_FUNC,
	XSetForeground_FUNC,
	XSetFunction_FUNC,
	XSetGraphicsExposures_FUNC,
	XSetIOErrorHandler_FUNC,
	XSetInputFocus_FUNC,
	XSetLineAttributes_FUNC,
	XSetRegion_FUNC,
	XSetStipple_FUNC,
	XSetSubwindowMode_FUNC,
	XSetWMNormalHints_FUNC,
	XShapeCombineMask_FUNC,
	XShapeCombineRegion_FUNC,
	XSubtractRegion_FUNC,
	XSync_FUNC,
	XSynchronize_FUNC,
	XTestFakeButtonEvent_FUNC,
	XTestFakeKeyEvent_FUNC,
	XTestFakeMotionEvent_FUNC,
	XTranslateCoordinates_FUNC,
	XUndefineCursor_FUNC,
	XUngrabKeyboard_FUNC,
	XUngrabPointer_FUNC,
	XUnionRectWithRegion_FUNC,
	XUnionRegion_FUNC,
	XUnmapWindow_FUNC,
	XWarpPointer_FUNC,
	XWhitePixel_FUNC,
	XWithdrawWindow_FUNC,
	XineramaIsActive_FUNC,
	XineramaQueryScreens_FUNC,
	XmAddWMProtocolCallback_FUNC,
	XmChangeColor_FUNC,
	XmClipboardCopy_FUNC,
	XmClipboardEndCopy_FUNC,
	XmClipboardEndRetrieve_FUNC,
	XmClipboardInquireCount_FUNC,
	XmClipboardInquireFormat_FUNC,
	XmClipboardInquireLength_FUNC,
	XmClipboardRetrieve_FUNC,
	XmClipboardStartCopy_FUNC,
	XmClipboardStartRetrieve_FUNC,
	XmComboBoxAddItem_FUNC,
	XmComboBoxDeletePos_FUNC,
	XmComboBoxSelectItem_FUNC,
	XmCreateArrowButton_FUNC,
	XmCreateCascadeButtonGadget_FUNC,
	XmCreateComboBox_FUNC,
	XmCreateDialogShell_FUNC,
	XmCreateDrawingArea_FUNC,
	XmCreateDrawnButton_FUNC,
	XmCreateErrorDialog_FUNC,
	XmCreateFileSelectionDialog_FUNC,
	XmCreateForm_FUNC,
	XmCreateFrame_FUNC,
	XmCreateInformationDialog_FUNC,
	XmCreateLabel_FUNC,
	XmCreateList_FUNC,
	XmCreateMainWindow_FUNC,
	XmCreateMenuBar_FUNC,
	XmCreateMessageDialog_FUNC,
	XmCreatePopupMenu_FUNC,
	XmCreatePulldownMenu_FUNC,
	XmCreatePushButton_FUNC,
	XmCreatePushButtonGadget_FUNC,
	XmCreateQuestionDialog_FUNC,
	XmCreateScale_FUNC,
	XmCreateScrollBar_FUNC,
	XmCreateScrolledList_FUNC,
	XmCreateScrolledText_FUNC,
	XmCreateSeparator_FUNC,
	XmCreateSeparatorGadget_FUNC,
	XmCreateTextField_FUNC,
	XmCreateToggleButton_FUNC,
	XmCreateToggleButtonGadget_FUNC,
	XmCreateWarningDialog_FUNC,
	XmCreateWorkingDialog_FUNC,
	XmDestroyPixmap_FUNC,
	XmDragCancel_FUNC,
	XmDragStart_FUNC,
	XmDropSiteRegister_FUNC,
	XmDropSiteUnregister_FUNC,
	XmDropSiteUpdate_FUNC,
	XmDropTransferAdd_FUNC,
	XmDropTransferStart_FUNC,
	XmFileSelectionBoxGetChild_FUNC,
	XmFontListAppendEntry_FUNC,
	XmFontListCopy_FUNC,
	XmFontListEntryFree_FUNC,
	XmFontListEntryGetFont_FUNC,
	XmFontListEntryLoad_FUNC,
	XmFontListFree_FUNC,
	XmFontListFreeFontContext_FUNC,
	XmFontListInitFontContext_FUNC,
	XmFontListNextEntry_FUNC,
	XmGetAtomName_FUNC,
	XmGetDragContext_FUNC,
	XmGetFocusWidget_FUNC,
	XmGetPixmap_FUNC,
	XmGetPixmapByDepth_FUNC,
	XmGetXmDisplay_FUNC,
	XmImMbLookupString_FUNC,
	XmImRegister_FUNC,
	XmImSetFocusValues_FUNC,
	XmImSetValues_FUNC,
	XmImUnregister_FUNC,
	XmImUnsetFocus_FUNC,
	XmInternAtom_FUNC,
	XmListAddItemUnselected_FUNC,
	XmListDeleteAllItems_FUNC,
	XmListDeleteItemsPos_FUNC,
	XmListDeletePos_FUNC,
	XmListDeletePositions_FUNC,
	XmListDeselectAllItems_FUNC,
	XmListDeselectPos_FUNC,
	XmListGetKbdItemPos_FUNC,
	XmListGetSelectedPos_FUNC,
	XmListItemPos_FUNC,
	XmListPosSelected_FUNC,
	XmListReplaceItemsPosUnselected_FUNC,
	XmListSelectPos_FUNC,
	XmListSetKbdItemPos_FUNC,
	XmListSetPos_FUNC,
	XmListUpdateSelectedList_FUNC,
	XmMainWindowSetAreas_FUNC,
	XmMessageBoxGetChild_FUNC,
	XmParseMappingCreate_FUNC,
	XmParseMappingFree_FUNC,
	XmProcessTraversal_FUNC,
	XmRenderTableAddRenditions_FUNC,
	XmRenderTableFree_FUNC,
	XmRenditionCreate_FUNC,
	XmRenditionFree_FUNC,
	XmStringBaseline_FUNC,
	XmStringCompare_FUNC,
	XmStringComponentCreate_FUNC,
	XmStringConcat_FUNC,
	XmStringCreate_FUNC,
	XmStringCreateLocalized_FUNC,
	XmStringDraw_FUNC,
	XmStringDrawImage_FUNC,
	XmStringDrawUnderline_FUNC,
	XmStringEmpty_FUNC,
	XmStringExtent_FUNC,
	XmStringFree_FUNC,
	XmStringGenerate_FUNC,
	XmStringHeight_FUNC,
	XmStringParseText_FUNC,
	XmStringUnparse_FUNC,
	XmStringWidth_FUNC,
	XmTabCreate_FUNC,
	XmTabFree_FUNC,
	XmTabListFree_FUNC,
	XmTabListInsertTabs_FUNC,
	XmTextClearSelection_FUNC,
	XmTextCopy_FUNC,
	XmTextCut_FUNC,
	XmTextDisableRedisplay_FUNC,
	XmTextEnableRedisplay_FUNC,
	XmTextFieldPaste_FUNC,
	XmTextGetInsertionPosition_FUNC,
	XmTextGetLastPosition_FUNC,
	XmTextGetMaxLength_FUNC,
	XmTextGetSelection_FUNC,
	XmTextGetSelectionPosition_FUNC,
	XmTextGetString_FUNC,
	XmTextGetSubstring_FUNC,
	XmTextGetSubstringWcs_FUNC,
	XmTextInsert_FUNC,
	XmTextPaste_FUNC,
	XmTextPosToXY_FUNC,
	XmTextReplace_FUNC,
	XmTextScroll_FUNC,
	XmTextSetEditable_FUNC,
	XmTextSetHighlight_FUNC,
	XmTextSetInsertionPosition_FUNC,
	XmTextSetMaxLength_FUNC,
	XmTextSetSelection_FUNC,
	XmTextSetString_FUNC,
	XmTextShowPosition_FUNC,
	XmUpdateDisplay_FUNC,
	XmWidgetGetDisplayRect_FUNC,
	XmbTextListToTextProperty_FUNC,
	XmbTextPropertyToTextList_FUNC,
	XpCancelJob_FUNC,
	XpCreateContext_FUNC,
	XpDestroyContext_FUNC,
	XpEndJob_FUNC,
	XpEndPage_FUNC,
	XpFreePrinterList_FUNC,
	XpGetOneAttribute_FUNC,
	XpGetPageDimensions_FUNC,
	XpGetPrinterList_FUNC,
	XpGetScreenOfContext_FUNC,
	XpSetAttributes_FUNC,
	XpSetContext_FUNC,
	XpStartJob_FUNC,
	XpStartPage_FUNC,
	XtAddCallback_FUNC,
	XtAddEventHandler_FUNC,
	XtAddExposureToRegion_FUNC,
	XtAppAddInput_FUNC,
	XtAppAddTimeOut_FUNC,
	XtAppCreateShell_FUNC,
	XtAppGetSelectionTimeout_FUNC,
	XtAppNextEvent_FUNC,
	XtAppPeekEvent_FUNC,
	XtAppPending_FUNC,
	XtAppProcessEvent_FUNC,
	XtAppSetErrorHandler_FUNC,
	XtAppSetFallbackResources_FUNC,
	XtAppSetSelectionTimeout_FUNC,
	XtAppSetWarningHandler_FUNC,
	XtBuildEventMask_FUNC,
	XtCallActionProc_FUNC,
	XtClass_FUNC,
	XtConfigureWidget_FUNC,
	XtCreateApplicationContext_FUNC,
	XtCreatePopupShell_FUNC,
	XtDestroyApplicationContext_FUNC,
	XtDestroyWidget_FUNC,
	XtDispatchEvent_FUNC,
	XtDisplay_FUNC,
	XtDisplayToApplicationContext_FUNC,
	XtFree_FUNC,
	XtGetMultiClickTime_FUNC,
	XtGetValues_FUNC,
	XtInsertEventHandler_FUNC,
	XtIsManaged_FUNC,
	XtIsRealized_FUNC,
	XtIsSubclass_FUNC,
	XtIsTopLevelShell_FUNC,
	XtLastTimestampProcessed_FUNC,
	XtMalloc_FUNC,
	XtManageChild_FUNC,
	XtMapWidget_FUNC,
	XtMoveWidget_FUNC,
	XtNameToWidget_FUNC,
	XtOpenDisplay_FUNC,
	XtOverrideTranslations_FUNC,
	XtParent_FUNC,
	XtParseTranslationTable_FUNC,
	XtPopdown_FUNC,
	XtPopup_FUNC,
	XtQueryGeometry_FUNC,
	XtRealizeWidget_FUNC,
	XtRegisterDrawable_FUNC,
	XtRemoveEventHandler_FUNC,
	XtRemoveInput_FUNC,
	XtRemoveTimeOut_FUNC,
	XtResizeWidget_FUNC,
	XtResizeWindow_FUNC,
	XtSetLanguageProc_FUNC,
	XtSetMappedWhenManaged_FUNC,
	XtSetValues_FUNC,
	XtToolkitInitialize_FUNC,
	XtToolkitThreadInitialize_FUNC,
	XtTranslateCoords_FUNC,
	XtUnmanageChild_FUNC,
	XtUnmapWidget_FUNC,
	XtUnregisterDrawable_FUNC,
	XtWindow_FUNC,
	XtWindowToWidget_FUNC,
	_1XmSetMenuTraversal_FUNC,
	close_FUNC,
	fd_1set_1sizeof_FUNC,
	getenv_FUNC,
	iconv_FUNC,
	iconv_1close_FUNC,
	iconv_1open_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XButtonEvent_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XClientMessageEvent_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XConfigureEvent_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XExposeEvent_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XImage_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XKeyEvent_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XmDragProcCallbackStruct_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XmTextBlockRec_2I_FUNC,
	memmove__ILorg_eclipse_swt_internal_motif_XmTextVerifyCallbackStruct_2I_FUNC,
	memmove__I_3BI_FUNC,
	memmove__I_3CI_FUNC,
	memmove__I_3II_FUNC,
	memmove__I_3SI_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_Visual_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XButtonEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XCharStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XClientMessageEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XConfigureEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XCreateWindowEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XCrossingEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XDestroyWindowEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XExposeEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XFocusChangeEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XFontStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XIconSize_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XImage_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XKeyEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XModifierKeymap_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XMotionEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XPropertyEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XReparentEvent_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XineramaScreenInfo_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmAnyCallbackStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmDragProcCallbackStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmDropFinishCallbackStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmDropProcCallbackStruct_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmTextBlockRec_2II_FUNC,
	memmove__Lorg_eclipse_swt_internal_motif_XmTextVerifyCallbackStruct_2II_FUNC,
	memmove___3BII_FUNC,
	memmove___3CII_FUNC,
	memmove___3III_FUNC,
	nl_1langinfo_FUNC,
	overrideShellWidgetClass_FUNC,
	pipe_FUNC,
	read_FUNC,
	select_FUNC,
	setResourceMem_FUNC,
	setlocale_FUNC,
	shellWidgetClass_FUNC,
	strlen_FUNC,
	topLevelShellWidgetClass_FUNC,
	transientShellWidgetClass_FUNC,
	write_FUNC,
	xmMenuShellWidgetClass_FUNC,
} OS_FUNCS;
