import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class spellObject {
    String name, components, description, tags, castTime,duration,range;
    int spellLvl;
    JButton button;
    JCheckBox known,prepared;
    public spellObject(String name, int spellLvl,String castTime,String duration,String range, String components, String description, String tags){
        this.name=name;
        this.spellLvl=spellLvl;
        this.components=components;
        this.description=description;
        this.tags=tags;
        this.castTime=castTime;
        this.duration=duration;
        this.range=range;
        spellObject reference = this;
        makeButton();
        known = new JCheckBox();
        known.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starter.start.requestFocus();
                if(spellLvl==0){
                    if (starter.start.preparedSpells.contains(reference)) starter.start.preparedSpells.remove(reference);
                    else starter.start.preparedSpells.add(reference);
                }else {
                    if (starter.start.spellBook.contains(reference)) starter.start.spellBook.remove(reference);
                    else starter.start.spellBook.add(reference);
                }
            }
        });
        prepared= new JCheckBox();
        prepared.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starter.start.requestFocus();
                if(starter.start.preparedSpells.contains(reference)){
                    starter.start.preparedSpells.remove(reference);
                    starter.start.spellBook.add(reference);
                }
                else {
                    starter.start.spellBook.remove(reference);
                    starter.start.preparedSpells.add(reference);
                }
                starter.start.openSpellBook();
            }
        });
    }
    void makeButton(){
        button = new JButton(((spellLvl==0)? "Cantrip: ":"Lvl"+spellLvl+": ")+ name);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starter.start.requestFocus();
                JFrame frame = new JFrame(name);
                frame.setSize(new Dimension(567,638));
                frame.setLayout(null);
                frame.setBackground(Color.WHITE);

                JTextArea tag = new JTextArea("Tags: "+tags);
                tag.setEditable(false);
                frame.add(tag);
                tag.setLineWrap(true);
                tag.setWrapStyleWord(true);
                tag.setFont(new Font("Serif",Font.ITALIC,15));
                tag.setBounds(0,0,550,40);

                JTextField popupName = new JTextField(name);
                popupName.setEditable(false);
                popupName.setFont(new Font("Times New Roman",Font.BOLD,40));
                frame.add(popupName);
                popupName.setBackground(Color.WHITE);
                popupName.setBorder(null);
                popupName.setHorizontalAlignment(SwingConstants.CENTER);
                popupName.setBounds(0,40,550,60);

                JTextArea level = new JTextArea((spellLvl==0)?"Cantrip":"Level "+spellLvl);
                level.setEditable(false);
                frame.add(level);
                level.setFont(new Font("Garamond",Font.PLAIN,15));
                level.setBounds(0,100,275,50);

                JTextArea cast = new JTextArea("Cast time: "+castTime);
                cast.setEditable(false);
                frame.add(cast);
                cast.setFont(new Font("Garamond",Font.PLAIN,15));
                cast.setBounds(275,100,275,50);
                cast.setWrapStyleWord(true);
                cast.setLineWrap(true);

                JTextArea popupDuration = new JTextArea("Duration: "+duration);
                popupDuration.setEditable(false);
                frame.add(popupDuration);
                popupDuration.setFont(new Font("Garamond",Font.PLAIN,15));
                popupDuration.setBounds(0,150,275,50);
                popupDuration.setWrapStyleWord(true);
                popupDuration.setLineWrap(true);

                JTextArea popupComponents = new JTextArea("Components: "+components);
                popupComponents.setEditable(false);
                frame.add(popupComponents);
                popupComponents.setFont(new Font("Garamond",Font.PLAIN,15));
                popupComponents.setBounds(275,150,275,50);
                popupComponents.setWrapStyleWord(true);
                popupComponents.setLineWrap(true);

                String temp=description;
                String s="";
                while (temp.contains("~")){
                    s+=temp.substring(0,temp.indexOf("~"))+"\n";
                    temp=temp.substring(temp.indexOf("~")+1);

                }
                s+=temp;
                JTextArea text = new JTextArea(s);
                JScrollPane scroller = new JScrollPane(text);
                scroller.setBorder(null);
                scroller.setBounds(0,200,552,400);
                text.setFont(new Font("Garamond",Font.PLAIN,15));
                text.setEditable(false);
                text.setLineWrap(true);
                text.setWrapStyleWord(true);
                frame.add(scroller);
                frame.setVisible(true);
            }
        });
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()== KeyEvent.VK_ESCAPE) {
                    starter.start.requestFocus();
                    starter.start.update();
                    e.consume();
                }
            }
        });
    }
}
