package amazcart2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUI {
    public ImageUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Sealed Fate");
        frame.add(this.populate(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    JPanel populate(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor=constraints.NORTH;
        constraints.insets=new Insets(10,10,10,10);
        constraints.gridy=0;
        constraints.gridx=1;
        JLabel tempLabel = new JLabel("End Date:");
        tempLabel.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempLabel,constraints);
        constraints.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,panel, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempField,constraints);
        constraints.gridx=1;
        constraints.gridy=1;
        constraints.fill=constraints.CENTER;
        constraints.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, panel,tempField);
            }
        });
        panel.add(tempButton,constraints);
        return panel;
    }
    JPanel populatePic(JFrame frame,Date EndDate) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor=constraints.NORTH;
        constraints.insets=new Insets(10,10,10,10);
        constraints.gridy=0;
        constraints.gridx=1;
        JLabel tempLabel = new JLabel("End Date:");
        tempLabel.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempLabel,constraints);
        constraints.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,panel, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempField,constraints);
        constraints.gridx=1;
        constraints.gridy=1;
        constraints.fill=constraints.CENTER;
        constraints.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, panel,tempField);
            }
        });
        panel.add(tempButton,constraints);
        constraints.gridx=1;
        constraints.gridy=2;
        constraints.fill=constraints.CENTER;
        constraints.gridwidth=2;
        JLabel dateLabel = new JLabel(EndDate.toString(), SwingConstants.CENTER);
        panel.add(dateLabel,constraints);
        return panel;
    }
    JPanel DisplayError(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor=constraints.NORTH;
        constraints.insets=new Insets(10,10,10,10);
        constraints.gridy=0;
        constraints.gridx=1;
        JLabel tempLabel = new JLabel("End Date:");
        tempLabel.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempLabel,constraints);
        constraints.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,panel, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        panel.add(tempField,constraints);
        constraints.gridx=1;
        constraints.gridy=1;
        constraints.fill=constraints.CENTER;
        constraints.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, panel,tempField);
            }
        });
        panel.add(tempButton,constraints);
        constraints.gridx=1;
        constraints.gridy=2;
        constraints.fill=constraints.CENTER;
        constraints.gridwidth=2;
        JLabel incorrectFormatLabel = new JLabel("Incorrect Date Format...Use MM/DD/YYYY", SwingConstants.CENTER);
        incorrectFormatLabel.setFont(new Font("Big text",Font.PLAIN,30));
        panel.add(incorrectFormatLabel,constraints);
        return panel;
    }
    public void DisplayImages(JFrame frame, JPanel p, JTextField field) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date EndDate = formatter.parse(field.getText());
            frame.remove(p);
            frame.add(this.populatePic(frame, EndDate));
            frame.revalidate();
            frame.pack();
            frame.setVisible(true);
            frame.repaint();
        } catch (Exception ex) {
            frame.remove(p);
            frame.add(this.DisplayError(frame));
            frame.revalidate();
            frame.pack();
            frame.setVisible(true);
            frame.repaint();
        }
    }

    public static void main(String[] args) { ImageUI UI = new ImageUI(); }
}
