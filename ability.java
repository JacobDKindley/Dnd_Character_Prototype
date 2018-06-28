import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ability extends JCheckBox{
    JCheckBox expertise;
    public ability(String title,int x,int y,boolean hasExpertise){
        this.setText(title);
        this.setBounds(x,y,125*starter.WIDTH/1920,15*starter.HEIGHT/1080);
        this.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        this.setBorder(null);
        this.setIcon(starter.unpressed);
        this.setSelectedIcon(starter.pressed);
        this.setPressedIcon(starter.pressing);
        this.setBackground(Color.white);
        this.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starter.start.requestFocus();
                starter.start.update();
            }
        });
        if(hasExpertise){
            expertise = new JCheckBox();
            expertise.setBounds(100*starter.WIDTH/1920,y,15*starter.WIDTH/1920,15*starter.HEIGHT/1080);
            expertise.setBackground(Color.white);
            expertise.setBorder(null);
            expertise.setIcon(starter.unpressed);
            expertise.setSelectedIcon(starter.pressed);
            expertise.setPressedIcon(starter.pressing);
            expertise.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    starter.start.requestFocus();
                    starter.start.update();
                }
            });
        }
    }
}
