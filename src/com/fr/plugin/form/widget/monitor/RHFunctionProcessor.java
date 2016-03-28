package com.fr.plugin.form.widget.monitor;

import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;

/**
 * Created by richie on 15/12/3.
 */
public class RHFunctionProcessor extends AbstractFunctionProcessor {

    private static RHFunctionProcessor instance = new RHFunctionProcessor();

    public static RHFunctionProcessor getInstance() {
        return instance;
    }

    @Override
    public int getId() {
        return FunctionHelper.generateFunctionID("com.fr.plugin.form.widget.rh.iframe");
    }

    @Override
    public String getLocaleKey() {
        return "Plugin-RH_Iframe_Name";
    }
}