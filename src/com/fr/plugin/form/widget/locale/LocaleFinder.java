package com.fr.plugin.form.widget.locale;

import com.fr.stable.fun.impl.AbstractLocaleFinder;

/**
 * Created by richie on 15/12/2.
 */
public class LocaleFinder extends AbstractLocaleFinder {

    @Override
    public int currentAPILevel() {
        return CURRENT_LEVEL;
    }

    @Override
    public String find() {
        return "com/fr/plugin/form/widget/locale/iframe";
    }
}