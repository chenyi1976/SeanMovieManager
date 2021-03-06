package me.chenyi.jython.editor;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import me.chenyi.jython.Script;
import me.chenyi.jython.ScriptTriggerType;
import me.chenyi.jython.ScriptUtilities;
import me.chenyi.mm.util.ImageController;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptEditorFrame extends JFrame
{
    private final String SAVE_ICON = "images/16x16/save.png";
    private final String NEW_ICON = "images/16x16/add.png";
    private final String DELETE_ICON = "images/16x16/delete.png";
    private final String VALIDATE_ICON = "images/16x16/validate.png";

    private static ScriptEditorFrame instance = null;

    private JList scriptList;
    private DefaultListModel scriptListModel;
    private JComboBox typeComboBox;
    private JTextField nameTextField;
    private JTextArea scriptTextArea;

    private final JButton validateButton;
    private final JButton closeButton;
    private final JButton saveButton;
    private final JButton newButton;
    private final JButton deleteButton;

    private Script selectedScript = null;

    private EventAdapter ea;

    public static ScriptEditorFrame getInstance()
    {
        if(instance == null)
            instance = new ScriptEditorFrame();
        return instance;
    }

    public ScriptEditorFrame()
        throws HeadlessException
    {
        super("Script Editor");

        ea = new EventAdapter();

        setSize(800, 600);

        getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.2, 0, GridBagConstraints.NORTHWEST,
                                                        GridBagConstraints.BOTH, new Insets(2, 5, 2, 5), 0, 0);

        JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.LEADING));

        newButton = new JButton("", ImageController.loadIcon(NEW_ICON));
        newButton.addActionListener(ea);
        buttonPanel1.add(newButton);
        deleteButton = new JButton("", ImageController.loadIcon(DELETE_ICON));
        deleteButton.addActionListener(ea);
        buttonPanel1.add(deleteButton);

        getContentPane().add(buttonPanel1, gbc);

        scriptListModel = new DefaultListModel();
        scriptList = new JList(scriptListModel);
        scriptList.getSelectionModel().addListSelectionListener(ea);

        gbc.gridy ++;
        gbc.weighty = 1;
        gbc.gridheight = 3;
        getContentPane().add(new JScrollPane(scriptList), gbc);

        typeComboBox = new JComboBox(ScriptTriggerType.values());

        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0;
        gbc.gridx++;
        getContentPane().add(typeComboBox, gbc);

        nameTextField = new JTextField();

        gbc.gridy ++;
        getContentPane().add(nameTextField, gbc);

        scriptTextArea = new JTextArea();
        gbc.gridy ++;
        gbc.weighty = 1;
        getContentPane().add(new JScrollPane(scriptTextArea), gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        validateButton = new JButton("", ImageController.loadIcon(VALIDATE_ICON));
        validateButton.addActionListener(ea);
        buttonPanel.add(validateButton);

        saveButton = new JButton("", ImageController.loadIcon(SAVE_ICON));
        saveButton.addActionListener(ea);
        buttonPanel.add(saveButton);

        gbc.gridy++;
        gbc.weighty = 0;
        getContentPane().add(buttonPanel, gbc);

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        closeButton = new JButton("Close");
        closeButton.addActionListener(ea);
        buttonPanel2.add(closeButton);

        gbc.gridy++;
        gbc.weighty = 0;
        getContentPane().add(buttonPanel2, gbc);

        loadScriptList();
    }

    private void loadScriptList()
    {
        scriptListModel.removeAllElements();
        Map<ScriptTriggerType, Map<String, Script>> scripts = ScriptUtilities.getScripts();
        for(Map<String, Script> scriptMap : scripts.values())
        {
            for(Script script : scriptMap.values())
            {
                scriptListModel.addElement(script);
            }
        }
        if (scriptListModel.getSize() > 0)
            scriptList.setSelectedIndex(0);
    }

    private class EventAdapter implements ActionListener, ListSelectionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == closeButton)
            {
                ScriptEditorFrame.this.setVisible(false);
            }
            else if(e.getSource() == saveButton)
            {
                if(selectedScript == null)
                {
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Please select the script to save.");
                    return;
                }
                String newName = nameTextField.getText().trim();
                if(newName.length() == 0)
                {
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Please give a name for script.");
                    return;
                }
                String scriptContent = scriptTextArea.getText().trim();
                if(scriptContent.length() == 0)
                {
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Please put some content for script.");
                    return;
                }
                Object item = typeComboBox.getSelectedItem();
                if(!(item instanceof ScriptTriggerType))
                {
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Please choose a script trigger.");
                    return;
                }
                ScriptTriggerType type = (ScriptTriggerType)item;
                boolean result = selectedScript.updateScript(type, newName, scriptContent);
                JOptionPane.showMessageDialog(ScriptEditorFrame.this, result ? "Saved." : "Failed!");
            }
            else if(e.getSource() == validateButton)
            {
                try
                {
                    ScriptUtilities.compileEditRule(scriptTextArea.getText());
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "OK.");
                }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, ex.toString());
                }
            }
            else if(e.getSource() == newButton)
            {
                Script script = new Script(ScriptTriggerType.OnAppStart, "newScript" + System.currentTimeMillis());
                scriptListModel.addElement(script);
            }
            else if (e.getSource() == deleteButton)
            {
                Object[] selectedValues = scriptList.getSelectedValues();
                if (selectedValues.length == 0)
                    return;
                int option = JOptionPane.showConfirmDialog(ScriptEditorFrame.this,
                                                            "Do you want to delete selected " + selectedValues.length + " script(s) ?",
                                                            "Confirm", JOptionPane.YES_NO_OPTION);

                java.util.List<String> deletedScripts = new ArrayList();
                if (option == JOptionPane.YES_OPTION)
                {
                    for(Object value : selectedValues)
                    {
                        if(value instanceof Script)
                        {
                            Script script = (Script)value;
                            boolean delete = new File(script.getScriptFileLocation()).delete();
                            if (delete)
                                deletedScripts.add(script.getName());
                        }
                    }
                }
                if (deletedScripts.size() > 0)
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Successfully delete " + deletedScripts.size() + "scripts");
                else
                    JOptionPane.showMessageDialog(ScriptEditorFrame.this, "Nothing get deleted");
                ScriptUtilities.reloadScripts();
                loadScriptList();
            }
        }

        @Override
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
                return;
            if(e.getSource() == scriptList.getSelectionModel())
            {
                Object value = scriptList.getSelectedValue();
                if(value instanceof Script)
                {
                    selectedScript = (Script)value;
                    nameTextField.setText(selectedScript.getName());
                    typeComboBox.setSelectedItem(selectedScript.getTriggerType());
                    scriptTextArea.setText(selectedScript.getScriptContent());
                }
                else
                {
                    selectedScript = null;
                }
            }
        }
    }
}
