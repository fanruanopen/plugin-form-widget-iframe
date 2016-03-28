package com.fr.plugin.form.widget.conf;

import com.fr.design.fun.impl.AbstractFormWidgetOptionProvider;
import com.fr.form.ui.Widget;
import com.fr.general.Inter;
import com.fr.plugin.form.widget.core.RHIframe;
import com.fr.plugin.form.widget.ui.XRHIframe;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframeMarkInForm extends AbstractFormWidgetOptionProvider {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public Class<? extends Widget> classForWidget() {
        return RHIframe.class;
    }

    @Override
    public Class<?> appearanceForWidget() {
        return XRHIframe.class;
    }

    @Override
    public String iconPathForWidget() {
        return "/com/fr/plugin/form/widget/images/rh_iframe.png";
    }

    @Override
    public String nameForWidget() {
        return Inter.getLocText("Plugin-RH_Iframe_Name");
    }
}