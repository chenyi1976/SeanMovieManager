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

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class ScriptEditorFrame extends JFrame
{
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

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 3, 0.2, 1, GridBagConstraints.NORTHWEST,
                                                        GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0);

        scriptListModel = new DefaultListModel();
        scriptList = new JList(scriptListModel);
        scriptList.getSelectionModel().addListSelectionListener(ea);

        getContentPane().add(new JScrollPane(scriptList), gbc);

        JPanel buttonPanel1 = new JPanel();

        newButton = new JButton("New");//an icon should be better
        newButton.addActionListener(ea);
        buttonPanel1.add(newButton);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(ea);
        buttonPanel1.add(deleteButton);

        gbc.gridy += gbc.gridheight;
        gbc.weighty = 0;
        gbc.gridheight = 1;
        getContentPane().add(buttonPanel1, gbc);

        typeComboBox = new JComboBox(ScriptTriggerType.values());

        gbc.gridy = 0;
        gbc.weightx = 0.8;
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

        validateButton = new JButton("Check Python Syntax");
        validateButton.addActionListener(ea);
        buttonPanel.add(validateButton);

        saveButton = new JButton("Save Script");
        saveButton.addActionListener(ea);
        buttonPanel.add(saveButton);

        closeButton = new JButton("Close");
        closeButton.addActionListener(ea);
        buttonPanel.add(closeButton);

        gbc.gridy++;
        gbc.weighty = 0;
        getContentPane().add(buttonPanel, gbc);

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
                    return;
                String newName = nameTextField.getText().trim();
                if(newName.length() == 0)
                    return;
                String scriptContent = scriptTextArea.getText().trim();
                if(scriptContent.length() == 0)
                    return;
                Object item = typeComboBox.getSelectedItem();
                if(!(item instanceof ScriptTriggerType))
                    return;
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
