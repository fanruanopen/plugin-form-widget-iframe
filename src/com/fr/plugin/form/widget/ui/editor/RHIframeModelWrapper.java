package com.fr.plugin.form.widget.ui.editor;

import com.fr.design.Exception.ValidationException;
import com.fr.design.designer.properties.Decoder;
import com.fr.design.designer.properties.Encoder;
import com.fr.plugin.form.widget.core.RHIframeAttr;
import com.fr.plugin.form.widget.core.RHIframeSource;

/**
 * Created by richie on 15/12/2.
 */
public class RHIframeModelWrapper implements Encoder<RHIframeAttr>, Decoder<RHIframeAttr> {
    @Override
    public RHIframeAttr decode(String txt) {
        return null;
    }

    @Override
    public void validate(String txt) throws ValidationException {

    }

    @Override
    public String encode(RHIframeAttr v) {
        if (v != null) {
            RHIframeSource source = v.getSource();
            if (source != null) {
                return source.toString();
            }
        }
        return null;
    }
}