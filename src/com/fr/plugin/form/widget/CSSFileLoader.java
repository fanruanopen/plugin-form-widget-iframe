package com.fr.plugin.form.widget;

import com.fr.stable.fun.impl.AbstractCssFileHandler;

/**
 * Created by richie on 15/12/2.
 */
public class CSSFileLoader extends AbstractCssFileHandler {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String[] pathsForFiles() {
        return new String[0];
    }
}