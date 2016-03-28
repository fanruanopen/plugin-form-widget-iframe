package com.fr.plugin.form.widget;

import com.fr.stable.fun.impl.AbstractJavaScriptFileHandler;

/**
 * Created by richie on 15/12/2.
 */
public class JavaScriptFileLoader extends AbstractJavaScriptFileHandler {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String[] pathsForFiles() {
        return new String[]{
                "/com/fr/plugin/form/widget/web/widget.rh.iframe.js"
        };
    }
}