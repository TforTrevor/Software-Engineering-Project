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
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor=c.NORTH;
        c.insets=new Insets(10,10,10,10);
        c.gridy=0;
        c.gridx=1;
        JLabel temp = new JLabel("End Date:");
        temp.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(temp,c);
        c.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,p, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(tempField,c);
        c.gridx=1;
        c.gridy=1;
        c.fill=c.CENTER;
        c.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, p,tempField);
            }
        });
        p.add(tempButton,c);
        return p;
    }
    JPanel populatePic(JFrame frame,Date EndDate) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor=c.NORTH;
        c.insets=new Insets(10,10,10,10);
        c.gridy=0;
        c.gridx=1;
        JLabel temp = new JLabel("End Date:");
        temp.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(temp,c);
        c.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,p, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(tempField,c);
        c.gridx=1;
        c.gridy=1;
        c.fill=c.CENTER;
        c.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, p,tempField);
            }
        });
        p.add(tempButton,c);
        c.gridx=1;
        c.gridy=2;
        c.fill=c.CENTER;
        c.gridwidth=2;
        JLabel l = new JLabel(EndDate.toString(), SwingConstants.CENTER);
        p.add(l,c);
        return p;
    }
    JPanel DisplayError(JFrame frame) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor=c.NORTH;
        c.insets=new Insets(10,10,10,10);
        c.gridy=0;
        c.gridx=1;
        JLabel temp = new JLabel("End Date:");
        temp.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(temp,c);
        c.gridx=2;
        JTextField tempField = new JTextField(7);
        tempField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {DisplayImages(frame,p, tempField);}
        });
        tempField.setFont(new Font("Big text",Font.PLAIN,50));
        p.add(tempField,c);
        c.gridx=1;
        c.gridy=1;
        c.fill=c.CENTER;
        c.gridwidth=2;
        JButton tempButton = new JButton("Search");
        tempButton.setFont(new Font("Big text",Font.PLAIN,50));
        tempButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(frame, p,tempField);
            }
        });
        p.add(tempButton,c);
        c.gridx=1;
        c.gridy=2;
        c.fill=c.CENTER;
        c.gridwidth=2;
        JLabel l = new JLabel("Incorrect Date Format...Use MM/DD/YYYY", SwingConstants.CENTER);
        l.setFont(new Font("Big text",Font.PLAIN,30));
        p.add(l,c);
        return p;
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

    public static void main(String[] args) {
        ImageUI UI = new ImageUI();
    }
}
