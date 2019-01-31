package amazcart2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageUI {
    JPanel populate() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor=c.NORTH;
        c.insets=new Insets(10,10,10,10);
        c.gridy=0;
        c.gridx=1;
        p.add(new JLabel("From:"),c);
        c.gridx=2;
        p.add(new JTextField(10),c);
        c.gridx=4;
        p.add(new JLabel("To: "),c);
        c.gridx=5;
        p.add(new JTextField(10),c);
        c.gridy=1;
        c.gridx=3;
        c.anchor=c.CENTER;
        JButton temp = new JButton("Search");
        temp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DisplayImages(p);
            }
        });
        p.add(temp,c);
        return p;
    }
    public void DisplayImages(JPanel p) {

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
