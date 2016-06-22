package com.fr.plugin.form.widget.core;

import com.fr.form.ui.DataControl;
import com.fr.form.ui.FieldEditor;
import com.fr.form.ui.Interactive;
import com.fr.form.ui.WidgetValue;
import com.fr.general.FRLogger;
import com.fr.general.Inter;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.plugin.ExtraClassManager;
import com.fr.plugin.form.widget.monitor.RHFunctionProcessor;
import com.fr.script.Calculator;
import com.fr.stable.ArrayUtils;
import com.fr.stable.DependenceProvider;
import com.fr.stable.core.NodeVisitor;
import com.fr.stable.fun.FunctionHelper;
import com.fr.stable.fun.FunctionProcessor;
import com.fr.stable.fun.impl.AbstractFunctionProcessor;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.web.Repository;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;
import com.fr.web.core.SessionIDInfor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframe extends FieldEditor implements Interactive {

    private static final FunctionProcessor RH = new AbstractFunctionProcessor() {
        @Override
        public int getId() {
            return FunctionHelper.generateFunctionID(Constants.PLUGIN_ID);
        }

        public String getLocaleKey() {
            return Inter.getLocText("Plugin-RH_Iframe_Name");
        }
    };


    private boolean overflowX = true;
    private boolean overflowY = true;

    private RHIframeAttr attr = new RHIframeAttr();


    public RHIframe() {

    }

    public RHIframeAttr getAttr() {
        return attr;
    }

    public void setAttr(RHIframeAttr attr) {
        this.attr = attr;
    }

    public boolean isOverflowX() {
        return overflowX;
    }

    public void setOverflowX(boolean overflowX) {
        this.overflowX = overflowX;
    }

    public boolean isOverflowY() {
        return overflowY;
    }

    public void setOverflowY(boolean overflowY) {
        this.overflowY = overflowY;
    }

    @Override
    public JSONObject createJSONConfig(Repository repo, Calculator c, NodeVisitor nodeVisitor) throws JSONException {
        JSONObject jo = super.createJSONConfig(repo, c, nodeVisitor);
        attr.mixConfig(jo, c, repo.getHttpServletRequest());
        jo.put("showOverFlowX", overflowX);
        jo.put("showOverFlowY", overflowY);
        FunctionProcessor processor = ExtraClassManager.getInstance().getFunctionProcessor();
        if (processor != null){
            processor.recordFunction(RHFunctionProcessor.getInstance());
        }
        return jo;
    }

    @Override
    public JSONArray createJSONData(SessionIDInfor sessionIDInfor, Calculator c, HttpServletRequest req) throws Exception {
        JSONArray ja = super.createJSONData(sessionIDInfor, c, req);
        attr.mixJSONData(ja, sessionIDInfor, c);
        return ja;
    }

    @Override
    public String getXType() {
        return "rh.iframe";
    }

    @Override
    public boolean isEditor() {
        return false;
    }

    @Override
    public String[] supportedEvents() {
        return new String[0];
    }

    @Override
    public String[] dependence(CalculatorProvider ca) {
        if (attr == null) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        return attr.dependence(ca);
    }

    @Override
    public void readXML(XMLableReader reader) {
        super.readXML(reader);
        if (reader.isChildNode()) {
            String nodeName = reader.getTagName();
            if ("Attr".equals(nodeName)) {
                this.overflowX = reader.getAttrAsBoolean("scrollX", true);
                this.overflowY = reader.getAttrAsBoolean("scrollY", true);
            } else if (RHIframeAttr.XML_TAG.equals(nodeName)) {
                this.attr = (RHIframeAttr) GeneralXMLTools.readXMLable(reader);
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        super.writeXML(writer);
        writer.startTAG("Attr");
        writer.attr("scrollX", overflowX);
        writer.attr("scrollY", overflowY);
        writer.end();
        if (attr != null) {
            GeneralXMLTools.writeXMLable(writer, attr, RHIframeAttr.XML_TAG);
        }
    }

    @Override
    public void mixinReturnData(HttpServletRequest req, Calculator ca, JSONObject jo) throws JSONException {
        if (widgetName != null) {
            RHIframeSource source = attr.getSource();
            if (source != null) {
                String url = source.getCalculatedUrl(ca, req);
                jo.put(widgetName.toUpperCase(), url);
            }
        }
    }
}