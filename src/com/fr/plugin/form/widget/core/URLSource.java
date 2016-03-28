package com.fr.plugin.form.widget.core;

import com.fr.base.TemplateUtils;
import com.fr.general.Inter;
import com.fr.script.Calculator;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by richie on 15/12/2.
 */
public class URLSource extends RHIframeSource {
    private String url;

    public URLSource() {

    }

    public URLSource(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getCalculatedUrl(Calculator calculator, HttpServletRequest req) {
        try {
            return TemplateUtils.render(url, calculator);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return Inter.getLocText("Plugin_RH_Iframe_Url");
    }

    @Override
    public void readXML(XMLableReader reader) {
        super.readXML(reader);
        if (reader.isChildNode()) {
            String nodeName = reader.getTagName();
            if ("Attr".equals(nodeName)) {
                this.url = reader.getAttrAsString("url", null);
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);
        writer.startTAG("Attr");
        writer.attr("url", url);
        writer.end();
    }
}