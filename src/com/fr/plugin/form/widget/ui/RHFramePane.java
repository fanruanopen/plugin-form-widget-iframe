package com.fr.plugin.form.widget.ui;

import com.fr.design.dialog.BasicPane;
import com.fr.design.gui.frpane.UIRadioPane;
import com.fr.design.gui.itableeditorpane.ParameterTableModel;
import com.fr.design.gui.itableeditorpane.UITableEditorPane;
import com.fr.general.Inter;
import com.fr.plugin.form.widget.core.RHIframeAttr;
import com.fr.plugin.form.widget.core.RHIframeSource;
import com.fr.stable.ParameterProvider;

import java.awt.*;

/**
 * Created by richie on 15/12/2.
 */
public class RHFramePane extends BasicPane {

    private UIRadioPane<RHIframeSource> radioPane;
    private UITableEditorPane<ParameterProvider> editorPane;

    @Override
    protected String title4PopupWindow() {
        return Inter.getLocText("Plugin-RH_Iframe_Value");
    }

    public RHFramePane() {
        setLayout(new BorderLayout());
        radioPane = new SourceRadioPane();
        radioPane.setPreferredSize(new Dimension(200, 60));
        add(radioPane, BorderLayout.NORTH);
        ParameterTableModel model = new ParameterTableModel();
        editorPane = new UITableEditorPane<ParameterProvider>(model);
        add(editorPane, BorderLayout.CENTER);

    }

    public RHIframeAttr update() {
        RHIframeAttr attr = new RHIframeAttr();
        attr.setSource(radioPane.updateBean());
        java.util.List<ParameterProvider> list = editorPane.update();
        attr.setParameters(list.toArray(new ParameterProvider[list.size()]));
        return attr;
    }

    public void populate(RHIframeAttr attr) {
        if (attr != null) {
            radioPane.populateBean(attr.getSource());
            editorPane.populate(attr.getParameters());
        }
    }
}