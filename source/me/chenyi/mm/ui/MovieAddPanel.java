package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.chenyi.mm.service.ServiceUtilities;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieAddPanel extends CurtainPagePanel
{

    private final JTextField textField;
    private EventAdapter ea;

    public MovieAddPanel(ICurtainController curtainController)
    {
        super(curtainController);

        ea = new EventAdapter();

        setLayout(new GridBagLayout());
        setBackground(Color.gray);
        setAlpha(0.5f);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0);

        JLabel label = new JLabel("Input name to add new movie:");
        label.setForeground(Color.white);
        add(label, gbc);

        gbc.gridy ++;
        textField = new JTextField(30);
        add(textField, gbc);

        textField.addActionListener(ea);

        setOpaque(false);
        setBackground(Color.black);
        setAlpha(0.9f);
    }

    public JTextField getTextField()
    {
        return textField;
    }

    @Override
    public void curtainActivate()
    {
        textField.requestFocus();
    }

    private class EventAdapter implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == textField)
            {
                if (textField.getText().trim().length() == 0)
                    return;

                ServiceUtilities.addMovieInfoToDatabase(textField.getText(), 1, true);

                controller.pullUpAllComponent(false);
            }
        }
    }

}
