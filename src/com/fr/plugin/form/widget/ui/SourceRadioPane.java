package com.fr.plugin.form.widget.ui;

import com.fr.design.beans.FurtherBasicBeanPane;
import com.fr.design.gui.frpane.UIRadioPane;
import com.fr.plugin.form.widget.core.RHIframeSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richie on 15/12/2.
 */
public class SourceRadioPane extends UIRadioPane<RHIframeSource>{

    @Override
    protected List<FurtherBasicBeanPane<? extends RHIframeSource>> initPaneList() {
        List<FurtherBasicBeanPane<? extends RHIframeSource>> list = new ArrayList<FurtherBasicBeanPane<? extends RHIframeSource>>();
        list.add(new TemplateSourcePane());
        list.add(new URLSourcePane());
        return list;
    }

    @Override
    protected String title4PopupWindow() {
        return "Source";
    }

}