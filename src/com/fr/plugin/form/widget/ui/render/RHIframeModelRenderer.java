package com.fr.plugin.form.widget.ui.render;

import com.fr.design.mainframe.widget.renderer.EncoderCellRenderer;
import com.fr.plugin.form.widget.ui.editor.RHIframeModelWrapper;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframeModelRenderer extends EncoderCellRenderer {
    public RHIframeModelRenderer() {
        super(new RHIframeModelWrapper());
    }
}