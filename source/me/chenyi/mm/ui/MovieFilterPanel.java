package me.chenyi.mm.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class description goes here
 *
 * @author $Author:$
 * @version $Revision:$
 */
public class MovieFilterPanel extends CurtainPagePanel
{
    private final JTextField textField;

    public MovieFilterPanel(ICurtainController curtainController)
    {
        super(curtainController);

        setLayout(new GridBagLayout());
        setBackground(Color.black);

        GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0);

        JLabel label = new JLabel("Input name to filter the list:");
        label.setForeground(Color.white);
        add(label, gbc);

        gbc.gridy ++;
        textField = new JTextField(30);
        add(textField, gbc);

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

}
