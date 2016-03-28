package com.fr.plugin.form.widget.core;

import com.fr.script.Calculator;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLable;
import com.fr.stable.xml.XMLableReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframeSource implements XMLable {

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

    public String getCalculatedUrl(Calculator calculator, HttpServletRequest req) {
        return null;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}