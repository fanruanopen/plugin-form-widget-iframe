package com.fr.plugin.form.widget.ui;

import com.fr.design.designer.creator.CRPropertyDescriptor;
import com.fr.design.designer.creator.XWidgetCreator;
import com.fr.design.form.util.XCreatorConstants;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.layout.FRGUIPaneFactory;
import com.fr.design.mainframe.widget.editors.WidgetValueEditor;
import com.fr.general.Inter;
import com.fr.plugin.form.widget.core.RHIframe;
import com.fr.plugin.form.widget.ui.editor.RHIframeModelEditor;
import com.fr.plugin.form.widget.ui.render.RHIframeModelRenderer;
import com.fr.stable.ArrayUtils;

import javax.swing.*;
import java.awt.*;
import java.beans.IntrospectionException;

/**
 * Created by richie on 15/12/2.
 */
public class XRHIframe extends XWidgetCreator {

    public XRHIframe(RHIframe widget, Dimension initSize) {
        super(widget, initSize);
    }

    @Override
    public CRPropertyDescriptor[] supportedDescriptor() throws IntrospectionException {
        return (CRPropertyDescriptor[]) ArrayUtils.addAll(super.supportedDescriptor(), new CRPropertyDescriptor[]{
                new CRPropertyDescriptor("attr", this.toData().getClass())
                        .setI18NName(Inter.getLocText("Plugin-RH_Iframe_Value"))
                        .setEditorClass(RHIframeModelEditor.class)
                        .setRendererClass(RHIframeModelRenderer.class),
                new CRPropertyDescriptor("overflowX", this.data.getClass()).setI18NName(
                        Inter.getLocText("Preference-Horizontal_Scroll_Bar_Visible")).putKeyValue(
                        XCreatorConstants.PROPERTY_CATEGORY, "Advanced"),
                new CRPropertyDescriptor("overflowY", this.data.getClass()).setI18NName(
                        Inter.getLocText("Preference-Vertical_Scroll_Bar_Visible")).putKeyValue(
                        XCreatorConstants.PROPERTY_CATEGORY, "Advanced")});
    }

    @Override
    protected JComponent initEditor() {
        if (editor == null) {
            editor = FRGUIPaneFactory.createBorderLayout_S_Pane();
            UITextField address = new UITextField();
            editor.add(address, BorderLayout.NORTH);
            JPanel contentPane = FRGUIPaneFactory.createNormalFlowInnerContainer_S_Pane();
            contentPane.setBackground(Color.white);
            editor.add(contentPane, BorderLayout.CENTER);
        }
        return editor;
    }

    @Override
    public Dimension initEditorSize() {
        return new Dimension(160, 80);
    }

    /**
     * 该组件是否可以拖入参数面板
     * 这里控制 网页预定义控件在工具栏不显示
     *
     * @return 是则返回true
     */
    public boolean canEnterIntoParaPane() {
        return false;
    }

    @Override
    public String getIconPath() {
        return "/com/fr/plugin/form/widget/images/rh_iframe.png";
    }
}