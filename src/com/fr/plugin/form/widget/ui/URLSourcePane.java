package com.fr.plugin.form.widget.ui;

import com.fr.design.beans.FurtherBasicBeanPane;
import com.fr.design.formula.TinyFormulaPane;
import com.fr.plugin.form.widget.core.URLSource;

import java.awt.*;

/**
 * Created by richie on 15/12/2.
 */
public class URLSourcePane extends FurtherBasicBeanPane<URLSource> {

    private TinyFormulaPane formulaPane;

    public URLSourcePane() {
        setLayout(new BorderLayout());
        formulaPane = new TinyFormulaPane();
        add(formulaPane, BorderLayout.CENTER);
    }

    @Override
    public boolean accept(Object ob) {
        return ob instanceof URLSource;
    }

    @Override
    public String title4PopupWindow() {
        return "网络路径";
    }

    @Override
    public void reset() {

    }

    @Override
    public void populateBean(URLSource ob) {
        if (ob != null) {
            formulaPane.populateBean(ob.getUrl());
        }
    }

    @Override
    public URLSource updateBean() {
        return new URLSource(formulaPane.updateBean());
    }
}