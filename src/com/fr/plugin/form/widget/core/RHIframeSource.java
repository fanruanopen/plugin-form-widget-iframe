package com.fr.plugin.form.widget.core;

import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.DependenceProvider;
import com.fr.stable.ParameterProvider;
import com.fr.stable.UtilEvalError;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by richie on 15/12/2.
 */
public abstract class RHIframeSource implements XMLable, DependenceProvider {

    public static final String XML_TAG = "RHIframeSource";


    public String toString() {
        return "source";
    }

    @Override
    public void readXML(XMLableReader reader) {

    }

    @Override
    public void writeXML(XMLPrintWriter writer) {

    }

    public abstract String getSourceType();

    public String getCalculatedUrl(Calculator calculator, HttpServletRequest req) {
        return null;
    }

    public abstract void mixCalculatedParameters(Calculator c, JSONArray ja, ParameterProvider[] parameters) throws JSONException, UtilEvalError;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}