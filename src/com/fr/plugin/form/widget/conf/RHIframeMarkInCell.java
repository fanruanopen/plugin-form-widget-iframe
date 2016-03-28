package com.fr.plugin.form.widget.conf;

import com.fr.design.beans.BasicBeanPane;
import com.fr.design.fun.impl.AbstractCellWidgetOptionProvider;
import com.fr.form.ui.Widget;
import com.fr.general.Inter;
import com.fr.plugin.form.widget.core.RHIframe;
import com.fr.plugin.form.widget.ui.RHCellPane;

/**
 * Created by richie on 16/3/28.
 */
public class RHIframeMarkInCell extends AbstractCellWidgetOptionProvider {

    public int currentAPILevel() {
        return 1;
    }

    @Override
    public Class<? extends Widget> classForWidget() {
        return RHIframe.class;
    }

    @Override
    public String iconPathForWidget() {
        return "/com/fr/plugin/form/widget/images/rh_iframe.png";
    }

    @Override
    public String nameForWidget() {
        return Inter.getLocText("Plugin-RH_Iframe_Name");
    }

    @Override
    public Class<? extends BasicBeanPane<? extends Widget>> appearanceForWidget() {
        return RHCellPane.class;
    }
}
