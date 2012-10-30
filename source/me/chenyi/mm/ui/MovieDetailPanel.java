package me.chenyi.mm.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.moviejukebox.themoviedb.model.MovieDb;
import me.chenyi.jython.Script;
import me.chenyi.jython.ScriptTriggerType;
import me.chenyi.jython.ScriptUtilities;
import me.chenyi.jython.action.ScriptAction;
import me.chenyi.mm.model.Node;
import me.chenyi.mm.model.NodeUtil;
import me.chenyi.mm.service.ImageType;

/**
 * Created by IntelliJ IDEA.
 * User: yichen1976
 * Date: 14/08/12
 * Time: 9:12 pm
 * To change this template use File | Settings | File Templates.
 */
public class MovieDetailPanel extends AlphaPanel{

    public static final int POSTER_WIDTH = 160;
    public static final int POSTER_HEIGHT = 240;

    public static final int BACKDROP_WIDTH = 780;
    public static final int BACKDROP_HEIGHT = 439;


    private final JLabel posterLabel;
    private final JLabel detailLabel;
    private final JPanel pluginPanel;
//    private final JTextPane detailArea;

    public MovieDetailPanel() {
        super(new GridBagLayout());

        setBackground(Color.black);

        GridBagConstraints gbc =
            new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
                                   new Insets(15, 15, 15, 15), 0, 0);
        posterLabel = new JLabel();
//        posterLabel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        posterLabel.setOpaque(false);
        add(posterLabel, gbc);

//        detailArea = new JTextPane();
//        detailArea.setContentType("html/text");
//        detailArea.setEditorKit(new HTMLEditorKit());
//        detailArea.setEditable(false);
        detailLabel = new JLabel();
        detailLabel.setVerticalAlignment(SwingConstants.TOP);
        StringBuffer movieDetail = new StringBuffer();
//        movieDetail.append("<html><table>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("<td>Title:</td><td>This is test</td><tr>");
//        movieDetail.append("</table></html>");
//        detailArea.setText(movieDetail.toString());
//        detailArea.setOpaque(false);
//        add(detailArea, gbc);
        
        detailLabel.setText(movieDetail.toString());
        detailLabel.setOpaque(false);

        gbc.gridx ++;
        gbc.weightx = 1;
//        gbc.weighty = 1;
        add(detailLabel, gbc);

        pluginPanel = createPluginPanel();
        gbc.gridy ++;
        gbc.weighty = 1;
        add(pluginPanel, gbc);

        showExample();
    }

    public JPanel createPluginPanel()
    {
        JPanel pluginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pluginPanel.setOpaque(false);
        Map<String,Script>
            menuScriptMap = ScriptUtilities.getScriptsByTriggerType(ScriptTriggerType.ItemDetailButton);
        for (Script script : menuScriptMap.values()) {
            JButton button = new JButton(new ScriptAction(script));
            button.setOpaque(false);
            button.setToolTipText(script.getName());
            button.setText("");
            button.setIcon(script.getScriptIcon());
            button.setPreferredSize(new Dimension(48, 48));
            button.setMinimumSize(new Dimension(48, 48));
            pluginPanel.add(button);
        }
        return pluginPanel;
    }
    
    public void setPostAndDesc(Image poster, String description)
    {
        posterLabel.setIcon(new ImageIcon(poster));
//        detailArea.setText(description);
        detailLabel.setText(description);
    }

    public void setNode(Node node)
    {
        if (node == null)
        {
            //clear the UI.
            return;
        }
        String title = String.valueOf(node.getAttributeValue(MovieDb.ATTR_TITLE));
        String overview = String.valueOf(node.getAttributeValue(MovieDb.ATTR_OVERVIEW));
        StringBuffer movieDetail = new StringBuffer();
        movieDetail.append("<html><p style=\"color:white;font-size:18px;\">");
        movieDetail.append(title).append("</p><hr>");
        movieDetail.append("<p style=\"color:white;font-size:12px;\">").append(overview);
        movieDetail.append("</p></html>");
        detailLabel.setText(movieDetail.toString());
        File file = NodeUtil.getImageFile(node, ImageType.poster.toString());
        if (file != null)
        {
            try {
                BufferedImage image = ImageIO.read(file);
                Image scaledInstance = image.getScaledInstance(POSTER_WIDTH, POSTER_HEIGHT, BufferedImage.SCALE_SMOOTH);

                posterLabel.setIcon(new ImageIcon(scaledInstance));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File backdropFile = NodeUtil.getImageFile(node, ImageType.backdrop.toString());
        if (backdropFile != null)
        {
            try {
                BufferedImage image = ImageIO.read(backdropFile);
                Image scaledInstance = image.getScaledInstance(BACKDROP_WIDTH, BACKDROP_HEIGHT, BufferedImage.SCALE_SMOOTH);

                setBackgroundImage(scaledInstance);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return calculateSize();
    }

    @Override
    public Dimension getMinimumSize()
    {
        return calculateSize();
    }

    private Dimension calculateSize() {
        if (super.getParent() == null)
        {
            Dimension size = super.getSize();
            if (size == null || size.getWidth() == 0 || size.getHeight() == 0)
                return new Dimension(800, 300);
            return new Dimension((int) size.getWidth(), (int) size.getWidth() * 9 / 16);
        }
        else
        {
            Dimension size = super.getParent().getMaximumSize();
            if (size == null || size.getWidth() == 0 || size.getHeight() == 0)
                return new Dimension(800, 300);
            int flowPanelHeight = (int)(size.getWidth() * 2 / 9);
            int height = (int)size.getHeight() - flowPanelHeight;
            return new Dimension((int) size.getWidth(), height);
        }

    }

    public void showExample()
    {
        setAlpha(0.3f);

        String imageFilePath = getClass().getResource("/posters/p1_1.jpg").getPath();
        ImageIcon icon = new ImageIcon(imageFilePath);
        setBackgroundImage(icon.getImage());

        String posterPath = getClass().getResource("/posters/p1.jpg").getPath();
        ImageIcon poster = new ImageIcon(posterPath);

        StringBuffer movieDetail = new StringBuffer();
        movieDetail.append("<html><br><br><div style=\"color:white;font:font-size:30px;\"><b>Game of Thrones</b><br><br><table cellpadding=\"10\">");
        movieDetail.append("<td>Title:</td><td>Game of Thrones</td><tr>");
        movieDetail.append("<td>Year:</td><td>2001</td><tr>");
        movieDetail.append("<td>IMDB Score:</td><td>9.4/10</td><tr>");
        movieDetail.append("<td>Release Date:</td><td>17 April 2011 (USA)</td><tr>");
        movieDetail.append("<td>Creators:</td><td>David Benioff, D.B. Weiss</td><tr>");
        movieDetail.append("<td>Genre:</td><td>Adventure | Drama | Fantasy</td><tr>");
        movieDetail.append("<td>Description:</td><td>Seven noble families fight for control of the mythical land of Westeros.</td><tr>");
        movieDetail.append("<td>Quotes:</td><td>Tyrion Lannister: A Lannister always pays his debts. <br>Eddard Stark: Winter is coming. </td><tr>");
        movieDetail.append("<td>Storyline:</td><td>Seven noble families fight for control of the mythical land of Westeros. " +
                "Political and sexual intrigue is pervasive. Robert Baratheon, King of Westeros, asks his old friend Eddard, " +
                "Lord Stark, to serve as Hand of the King, or highest official. Secretly warned that the previous Hand was assassinated, " +
                "Eddard accepts in order to investigate further. Meanwhile the Queen's family, the Lannisters, may be hatching a plot to take power. " +
                "Across the sea, the last members of the previous and deposed ruling family, the Targaryens, are also scheming to regain the throne. " +
                "The friction between the houses Stark, Lannister and Baratheon, and with the remaining great houses Greyjoy, Tully, Arryn," +
                " and Tyrell, leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war and political confusion, " +
                "a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond. </td>");
        movieDetail.append("</table></div></html>");

        setPostAndDesc(poster.getImage(), movieDetail.toString());
    }

}
