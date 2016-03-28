package com.fr.plugin.form.widget.ui;

import com.fr.design.beans.FurtherBasicBeanPane;
import com.fr.design.dialog.BasicDialog;
import com.fr.design.dialog.DialogActionAdapter;
import com.fr.design.gui.ibutton.UIButton;
import com.fr.design.gui.itextfield.UITextField;
import com.fr.design.gui.itree.filetree.ReportletPane;
import com.fr.plugin.form.widget.core.TemplateSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by richie on 15/12/2.
 */
public class TemplateSourcePane extends FurtherBasicBeanPane<TemplateSource> {

    private UITextField textField;

    public TemplateSourcePane() {
        setLayout(new BorderLayout());
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(30, 20));
        add(contentPane, BorderLayout.NORTH);

        textField = new UITextField();
        UIButton btn = new UIButton("...");
        btn.setPreferredSize(new Dimension(30, 20));
        contentPane.add(textField, BorderLayout.CENTER);
        contentPane.add(btn, BorderLayout.EAST);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ReportletPane reportletPane = new ReportletPane();
                reportletPane.setSelectedReportletPath(textField.getText());
                BasicDialog reportletDialog = reportletPane.showWindow(SwingUtilities.getWindowAncestor(TemplateSourcePane.this));

                reportletDialog.addDialogActionListener(new DialogActionAdapter() {
                    public void doOk() {
                        textField.setText(reportletPane.getSelectedReportletPath());
                    }
                });
                reportletDialog.setVisible(true);
            }
        });
    }

    @Override
    public boolean accept(Object ob) {
        return ob instanceof TemplateSource;
    }

    @Override
    public String title4PopupWindow() {
        return "模板路径";
    }

    @Override
    public void reset() {

    }

    @Override
    public void populateBean(TemplateSource ob) {
        textField.setText(ob.getPath());
    }

    @Override
    public TemplateSource updateBean() {
        return new TemplateSource(textField.getText());
    }
}