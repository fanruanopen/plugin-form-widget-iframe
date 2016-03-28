package com.fr.plugin.form.widget.ui.editor;

import com.fr.design.dialog.BasicDialog;
import com.fr.design.dialog.DialogActionAdapter;
import com.fr.design.mainframe.widget.accessibles.UneditableAccessibleEditor;
import com.fr.plugin.form.widget.core.RHIframeAttr;
import com.fr.plugin.form.widget.ui.RHFramePane;

import javax.swing.*;

/**
 * Created by richie on 15/12/2.
 */
public class AccessibleRHIframeModelEditor extends UneditableAccessibleEditor {

    private RHFramePane rhFramePane;

    public AccessibleRHIframeModelEditor() {
        super(new RHIframeModelWrapper());
    }

    @Override
    protected void showEditorPane() {
        if (rhFramePane == null) {
            rhFramePane = new RHFramePane();
        }
        BasicDialog dlg = rhFramePane.showWindow(SwingUtilities.getWindowAncestor(this));
        rhFramePane.populate(getValue());
        dlg.addDialogActionListener(new DialogActionAdapter() {
            @Override
            public void doOk() {
                RHIframeAttr zTreeNode = rhFramePane.update();
                setValue(zTreeNode);
                fireStateChanged();
            }
        });
        dlg.setVisible(true);

    }

    @Override
    public RHIframeAttr getValue() {
        return (RHIframeAttr) super.getValue();
    }
}