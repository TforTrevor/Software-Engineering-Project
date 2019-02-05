package amazcart2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUI {
    JPanel populate() {
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
            public void actionPerformed(ActionEvent e) {DisplayImages(p, tempField);}
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
                DisplayImages(p, tempField);
            }
        });
        p.add(tempButton,c);
        return p;
    }
    public void DisplayImages(JPanel p, JTextField field) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            //formatter.setLenient(false);
            Date EndDate = formatter.parse(field.getText());
            System.out.println(EndDate);
        } catch (Exception ex) {
            System.err.println("Unformatted Date");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new ImageUI().populate());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
