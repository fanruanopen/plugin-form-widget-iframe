package com.fr.plugin.form.widget.core;

import com.fr.base.TemplateUtils;
import com.fr.data.NetworkHelper;
import com.fr.general.Inter;
import com.fr.script.Calculator;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by richie on 15/12/2.
 */
public class TemplateSource extends RHIframeSource {


    private String path;

    public TemplateSource() {

    }

    public TemplateSource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getCalculatedUrl(Calculator calculator, HttpServletRequest req) {
        try {
            String realPath = TemplateUtils.render(path, calculator);
            if (realPath.contains(".cpt")) {
                return NetworkHelper.createServletURL(req) + "?reportlet=" + realPath;
            } else if (realPath.contains(".frm")) {
                return NetworkHelper.createServletURL(req) + "?formlet=" + realPath;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @Override
    public String toString() {
        return Inter.getLocText("Plugin_RH_Iframe_Template_Path");
    }

    @Override
    public void readXML(XMLableReader reader) {
        super.readXML(reader);
        if (reader.isChildNode()) {
            String nodeName = reader.getTagName();
            if ("Attr".equals(nodeName)) {
                this.path = reader.getAttrAsString("path", null);
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);
        writer.startTAG("Attr");
        writer.attr("path", path);
        writer.end();
    }
}