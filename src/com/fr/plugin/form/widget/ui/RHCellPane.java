package com.fr.plugin.form.widget.ui;

import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.widget.ui.AbstractDataModify;
import com.fr.general.Inter;
import com.fr.plugin.form.widget.core.RHIframe;

import java.awt.*;

/**
 * Created by richie on 16/3/28.
 */
public class RHCellPane extends AbstractDataModify<RHIframe> {

    private RHFramePane attrPane;
    private RHIframe iframe;

    public RHCellPane() {
        this.setLayout(FRGUIPaneFactory.createBorderLayout());
        attrPane = new RHFramePane();
        this.add(attrPane, BorderLayout.CENTER);
    }

    @Override
    public void populateBean(RHIframe rhIframe) {
        this.iframe = rhIframe;
        attrPane.populate(rhIframe.getAttr());
    }

    @Override
    public RHIframe updateBean() {
        if (iframe != null) {
            iframe.setAttr(attrPane.update());
        }
        return iframe;
    }

    @Override
    protected String title4PopupWindow() {
        return Inter.getLocText("Plugin-RH_Iframe_Name");
    }
}
