package com.fr.plugin.form.widget.core;

import com.fr.base.Formula;
import com.fr.base.Parameter;
import com.fr.base.ParameterHelper;
import com.fr.base.TemplateUtils;
import com.fr.general.FArray;
import com.fr.general.FRLogger;
import com.fr.general.GeneralUtils;
import com.fr.general.Inter;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;
import com.fr.script.Calculator;
import com.fr.stable.CodeUtils;
import com.fr.stable.ParameterProvider;
import com.fr.stable.StableUtils;
import com.fr.stable.UtilEvalError;
import com.fr.stable.js.WidgetName;
import com.fr.stable.script.CalculatorProvider;
import com.fr.stable.xml.XMLPrintWriter;
import com.fr.stable.xml.XMLableReader;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String result = null;
        if (StableUtils.canBeFormula(url)) {
            try {
                result = GeneralUtils.objectToString(calculator.eval(new Formula(url)));
            } catch (UtilEvalError u) {
                FRLogger.getLogger().error(u.getMessage(), u);
            }
        } else {
            try {
                result = TemplateUtils.render(url, calculator);
            } catch (Exception e) {
                FRLogger.getLogger().error(e.getMessage(), e);
            }
        }

        if (result != null && !result.toLowerCase().startsWith("http")) {
            result = "http://" + result;
        }
        return result;
    }

    @Override
    public String[] dependence(CalculatorProvider ca) {
        Parameter[] parameters = ParameterHelper.analyze4Parameters(url, false);
        List<String> collections = new ArrayList<String>();
        for (Parameter parameter : parameters) {
            collections.addAll(Arrays.asList(parameter.dependence(ca)));
        }
        return collections.toArray(new String[collections.size()]);
    }

    @Override
    public void mixCalculatedParameters(Calculator c, JSONArray ja, ParameterProvider[] parameters) throws JSONException, UtilEvalError  {
        for (int i = 0; i < (parameters == null ? 0 : parameters.length); i++) {
            Object obj = parameters[i].getValue();
            if (obj instanceof Formula) {
                String content = ((Formula) obj).getContent();
                obj = c.evalValue(content);
            }
            JSONObject jo = JSONObject.create();
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
    public String getSourceType() {
        return "url";
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