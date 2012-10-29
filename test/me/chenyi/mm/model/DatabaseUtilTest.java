package me.chenyi.mm.model;

import me.chenyi.mm.model.DatabaseUtil;
import me.chenyi.mm.model.Attribute;
import me.chenyi.mm.model.ModelUtils;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yichen1976
 * Date: 26/08/12
 * Time: 09:01
 */
public class DatabaseUtilTest {

    @Test
    public void testInitDatabase() throws Exception {
        DatabaseUtil.initDatabase();
    }

    @Test
    public void testAddAttribute() throws Exception {
        Connection connection = DatabaseUtil.openConnection();
        Attribute attribute = ModelUtils.getOrAddAttribute(connection, "test", Attribute.AttributeType.String);
        System.out.println("Name = " + attribute.getName());
        System.out.println("ID = " + attribute.getId());
        System.out.println("Type = " + attribute.getType());
        DatabaseUtil.closeConnection(connection);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(800, 600));

        frame.getContentPane().setLayout(new BorderLayout());

        try
        {
            Connection connection = DatabaseUtil.openConnection();
            AttributeTableModel tableModel = new AttributeTableModel((List<Attribute>) ModelUtils.getAllAttributes(connection));
            DatabaseUtil.closeConnection(connection);
            JTable table = new JTable(tableModel);
            frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    public static class AttributeTableModel extends AbstractTableModel
    {

        private List<Attribute> attributes;

        public AttributeTableModel(List<Attribute> attributes) {
            this.attributes = attributes;
        }

        @Override
        public int getRowCount() {
            return attributes.size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex < 0 || rowIndex >= attributes.size() )
                return null;

            Attribute attribute = attributes.get(rowIndex);
            if (columnIndex == 0)
                return attribute.getId();
            else if (columnIndex == 1)
                return attribute.getName();
            else if (columnIndex == 2)
                return attribute.getType();

            return null;
        }

        @Override
        public String getColumnName(int column) {
            if (column == 0)
                return "ID";
            else if (column == 1)
                return "Name";
            else if (column == 2)
                return "Type";
            return null;
        }
    }

}
