package com.fr.plugin.form.widget.core;

import com.fr.base.Formula;
import com.fr.base.ParameterMapNameSpace;
import com.fr.general.FArray;
import com.fr.general.xml.GeneralXMLTools;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.*;
import com.fr.stable.js.WidgetName;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.script.NameSpace;
import com.fr.stable.xml.*;
import com.fr.web.core.SessionIDInfor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframeAttr implements XMLable, DependenceProvider  {
    public static final String XML_TAG = "RHIframeAttr";

    private RHIframeSource source;

    private ParameterProvider[] parameters;

    public RHIframeSource getSource() {
        return source;
    }

    public void setSource(RHIframeSource source) {
        this.source = source;
    }

    public ParameterProvider[] getParameters() {
        return parameters;
    }

    public void setParameters(ParameterProvider[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public String[] dependence(CalculatorProvider ca) {
        List<String> data = new ArrayList<String>();
        data.addAll(Arrays.asList(source.dependence(ca)));
        if (parameters != null) {
            for (ParameterProvider parameter : parameters) {
                data.addAll(Arrays.asList(parameter.dependence(ca)));
            }
        }
        return data.toArray(new String[data.size()]);
    }

    public void mixConfig(JSONObject jo, Calculator c, HttpServletRequest req) throws JSONException {
        if (source != null) {
            jo.put("src", source.getCalculatedUrl(c, req));
        }
        String[] dependence = dependence(c);
        if (!ArrayUtils.isEmpty(dependence)) {
            jo.put("dependence", dependence);
            jo.put("valueDependence", dependence);
        }
    }

    public void mixJSONData(JSONArray ja, SessionIDInfor sessionIDInfor, Calculator c) throws JSONException, UtilEvalError {
        NameSpace ns = ParameterMapNameSpace.create(sessionIDInfor.getParameterMap4Execute());
        c.pushNameSpace(ns);
        for (int i = 0; i < (parameters == null ? 0 : parameters.length); i++) {
            Object obj = parameters[i].getValue();
            if (obj instanceof Formula) {
                String content = ((Formula) obj).getContent();
                obj = c.evalValue(content);
            }
            JSONObject jo = new JSONObject();
            if (obj instanceof String) {
                obj = CodeUtils.cjkEncode((String) obj);
                jo.put(parameters[i].getName(), obj);
            } else if (obj instanceof FArray) {
                obj = ((FArray) obj).cjkEncode();
                jo.put(parameters[i].getName(), obj);
            } else if (obj instanceof WidgetName) {
                jo.put("widgetName", ((WidgetName) obj).getName());
            } else {
                // 还可以是数字啊什么的
                jo.put(parameters[i].getName(), obj);
            }

            ja.put(jo);
        }
    }

    @Override
    public void readXML(XMLableReader reader) {
        if (reader.isChildNode()) {
            String tmpName = reader.getTagName();
            if (ParameterProvider.ARRAY_XML_TAG.equals(tmpName)) {//读取Parameters.
                final List<ParameterProvider> tmpParameterList = new ArrayList<ParameterProvider>();

                reader.readXMLObject(new XMLReadable() {
                    public void readXML(XMLableReader reader) {
                        if (ParameterProvider.XML_TAG.equals(reader.getTagName())) {
                            tmpParameterList.add(StableXMLUtils.readParameter(reader));
                        }
                    }
                });

                //转换数组.
                if (!tmpParameterList.isEmpty()) {
                    this.parameters = tmpParameterList.toArray(new ParameterProvider[tmpParameterList.size()]);
                }
            } else if (tmpName.equals(RHIframeSource.XML_TAG)) {
                this.source = (RHIframeSource) GeneralXMLTools.readXMLable(reader);
            }
        }
    }

    @Override
    public void writeXML(XMLPrintWriter writer) {
        if (source != null) {
            GeneralXMLTools.writeXMLable(writer, source, RHIframeSource.XML_TAG);
        }
        StableXMLUtils.writeParameters(writer, parameters);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        RHIframeAttr cloned = (RHIframeAttr)super.clone();
        cloned.source = source;
        cloned.parameters = parameters;
        return cloned;
    }
}