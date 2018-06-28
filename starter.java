import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.plaf.metal.MetalCheckBoxIcon;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class starter extends JComponent implements MouseListener{
    static final int WIDTH=Toolkit.getDefaultToolkit().getScreenSize().width,HEIGHT=Toolkit.getDefaultToolkit().getScreenSize().height;
    String lastFile = "D&D Character Files\\autoSave.txt";
    private ArrayList<String> searchFilter = new ArrayList<>();
    ArrayList<spellObject> spellBook = new ArrayList<>(), preparedSpells = new ArrayList<>();
    private ArrayList<spellObject> grandSpellList = new ArrayList<>();
    static Icon unpressed = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(Color.white);
            g.fillOval(x,y,this.getIconWidth(),this.getIconHeight());
            g.setColor(Color.black);
            g.drawOval(x,y,this.getIconWidth(),this.getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return (15*WIDTH/1920);
        }

        @Override
        public int getIconHeight() {
            return (15*HEIGHT/1080);
        }
    }, pressed = new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    g.setColor(Color.black);
                    g.fillOval(x,y,this.getIconWidth(),this.getIconHeight());
                }

                @Override
                public int getIconWidth() {
                    return (15*WIDTH/1920);
                }

                @Override
                public int getIconHeight() {
                    return (15*HEIGHT/1080);
                }
    }, pressing = new Icon() {
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(new Color(200,200,200));
            g.fillOval(x,y,this.getIconWidth(),this.getIconHeight());
            g.setColor(Color.black);
            g.drawOval(x,y,this.getIconWidth(),this.getIconHeight());
        }

        @Override
        public int getIconWidth() {
            return (15*WIDTH/1920);
        }

        @Override
        public int getIconHeight() {
            return (15*HEIGHT/1080);
        }
    };
    private ArrayList<JTextArea> abilities;
    private item w1,w2,w3,w4,headItem,amuletItem,cloakItem,armourItem,handsItem,ring1Item,ring2Item,beltItem,bootsItem;
    static private JTextArea inven,notes,racialTraits,languages,otherProficiencies;
    static JTextField cp,sp,ep,gp,pp,lvl1,lvl2,lvl3,lvl4,lvl5,lvl6,lvl7,lvl8,lvl9,hitDiceTotal,hitDiceUsed,speed,saveDC,attackBonus,characterClass,searchName,level,characterName,STR,DEX,CON,INT,WIS,CHA,STRMOD,DEXMOD,CONMOD,INTMOD,WISMOD,CHAMOD,proficiency,invenTitle,notesTitle,abilitiesTitle, spellListTitle,spellBookTitle,maxHealth,currentHealth,tempHealth,AC,initiative,race,background,alignment,exp;
    static JScrollPane spellScroller,noteScroller,invenScroller,racialScroller,otherScroller,languageScroller;
    static JPanel pane,spellPane,abilityPane;
    static JFrame searchFrame, spellSlotFrame;
    static JButton spellSlotButton,filterSearchButton,nameSearchButton,searchSpell,weapon1,weapon2,weapon3,weapon4,head,amulet,cloak,armour,hands,ring1,ring2,belt,boots;
    static private JCheckBox fullCaster,halfCaster,thirdCaster,pactCaster,abjuration,conjuration,divination,enchantment,evocation,illusion,necromancy,transmutation,concentration,ritual,cantrip,level1,level2,level3,level4,level5,level6,level7,level8,level9,artificer,bard,cleric,druid,paladin,ranger,sorcerer,warlock,wizard,simple,martial,shield,light,medium,heavy;
    static private ability strSave,Athletics,dexSave,Acrobatics,Sleight,Stealth,conSave,intSave,Arcana,History,Investigation,Nature,Religion,wisSave,Animal,Insight,Medicine,Perception,Survival,chaSave,Deception,Intimidation,Performance,Persuasion;
    JFrame frame = new JFrame("Character");
    static starter start;
    ActionListener buttonFocus = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            start.requestFocus();
            update();
        }
    }, searchAction =new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name=((JCheckBox)(e.getSource())).getText();
            if(searchFilter.contains(name)){
                searchFilter.remove(name);
            }else searchFilter.add(name);
        }
    };
    KeyListener focus = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode()== KeyEvent.VK_ESCAPE) {
                start.requestFocus();
                update();
                e.consume();
            }
        }
    };

    public static void main(String Args[]) throws InterruptedException {
        start = new starter();
    }

    private starter() throws InterruptedException {
        w1=new item();
        w2=new item();
        w3=new item();
        w4=new item();
        headItem=new item();
        amuletItem=new item();
        cloakItem=new item();
        armourItem=new item();
        handsItem=new item();
        ring1Item=new item();
        ring2Item=new item();
        beltItem=new item();
        bootsItem=new item();
        frame = new JFrame("Title");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH,HEIGHT));
        frame.setResizable(false);
        frame.setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menuBar.setVisible(true);

        JMenu file = new JMenu("File");
        menuBar.add(file);

        JMenuItem load = new JMenuItem("Load");
        load.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
        file.add(load);

        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File("D&D Character Files"));
                fc.showSaveDialog(null);
                save(fc.getSelectedFile().getAbsolutePath());
            }
        });
        file.add(save);

        pane = new JPanel();
        pane.setLayout(null);
        frame.add(pane);
        pane.setBounds(0,0,WIDTH/2,HEIGHT-50);

        pane.add(this);
        this.setBounds(0,0,WIDTH/2,HEIGHT-50);
        new InputHandler(this);
        addMouseListener(this);

        inven = new JTextArea();
        inven.addKeyListener(focus);
        inven.setSize(new Dimension(WIDTH/2-24,HEIGHT-100));
        inven.setLineWrap(true);
        inven.setWrapStyleWord(true);
        inven.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        inven.setAutoscrolls(true);

        invenScroller = new JScrollPane(inven);
        frame.add(invenScroller);
        invenScroller.setBounds(WIDTH/2,50,WIDTH/2,HEIGHT-100);
        invenScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        invenScroller.setVisible(false);

        invenTitle = new JTextField("Inventory");
        frame.add(invenTitle);
        invenTitle.setVisible(false);
        invenTitle.setBounds(WIDTH/2,0,WIDTH/2,50);
        invenTitle.setHorizontalAlignment(SwingConstants.CENTER);
        invenTitle.setForeground(Color.white);
        invenTitle.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        invenTitle.setBackground(Color.black);
        invenTitle.setBorder(null);
        invenTitle.setEditable(false);

        notes = new JTextArea();
        notes.addKeyListener(focus);
        notes.setSize(new Dimension(WIDTH/2-24,HEIGHT-100));
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        notes.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));

        noteScroller = new JScrollPane(notes);
        frame.add(noteScroller);
        noteScroller.setBounds(WIDTH/2,50,WIDTH/2,HEIGHT-100);
        noteScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        noteScroller.setVisible(false);

        notesTitle = new JTextField("Notes");
        frame.add(notesTitle);
        notesTitle.setVisible(false);
        notesTitle.setBounds(WIDTH/2,0,WIDTH/2,50);
        notesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        notesTitle.setForeground(Color.white);
        notesTitle.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        notesTitle.setBackground(Color.black);
        notesTitle.setBorder(null);
        notesTitle.setEditable(false);

        abilityPane = new JPanel();
        abilityPane.setSize(new Dimension(WIDTH/2,HEIGHT-100));
        abilityPane.setLayout(null);
        abilities = new ArrayList<>();
        for(int i=0;i<20;i++) {
            JTextArea ability = new JTextArea();
            ability.addKeyListener(focus);
            abilities.add(ability);
            JScrollPane scroller = new JScrollPane(ability);
            abilityPane.add(scroller);
            ability.setSize(WIDTH/10,HEIGHT*230/1080);
            scroller.setBounds(WIDTH/10*(i%5),(245*HEIGHT/1080)*(i/5)+15,WIDTH/10,HEIGHT*230/1080);
            ability.setLineWrap(true);
            ability.setWrapStyleWord(true);
            ability.setFont(new Font("Garamond", Font.BOLD, 12*WIDTH/1920));

            JTextField label = new JTextField("Level "+(i+1));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setEditable(false);
            abilityPane.add(label);
            label.setBounds(WIDTH/10*(i%5),(245*HEIGHT/1080)*(i/5),WIDTH/10,15*HEIGHT/1080);
            label.setBackground(Color.black);
            label.setForeground(Color.white);
            label.setBorder(new BevelBorder(1));
            label.setFont(new Font("Garamond", Font.BOLD, 15*WIDTH/1920));
        }

        frame.add(abilityPane);
        abilityPane.setVisible(false);
        abilityPane.setBounds(WIDTH/2,50,WIDTH/2,HEIGHT-100);

        abilitiesTitle = new JTextField("Abilities");
        frame.add(abilitiesTitle);
        abilitiesTitle.setVisible(false);
        abilitiesTitle.setBounds(WIDTH/2,0,WIDTH/2,50*WIDTH/1920);
        abilitiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
        abilitiesTitle.setForeground(Color.white);
        abilitiesTitle.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        abilitiesTitle.setBackground(Color.black);
        abilitiesTitle.setBorder(null);
        abilitiesTitle.setEditable(false);

        characterName = new JTextField();
        this.add(characterName);
        characterName.addKeyListener(focus);
        characterName.setBounds(700*WIDTH/1920,65*HEIGHT/1080,200*WIDTH/1920,30*HEIGHT/1080);
        characterName.setFont(new Font("Garamond",Font.BOLD,30*WIDTH/1920));
        characterName.setBorder(null);

        characterClass = new JTextField();
        this.add(characterClass);
        characterClass.addKeyListener(focus);
        characterClass.setBounds(700*WIDTH/1920,100*HEIGHT/1080,200*WIDTH/1920,30*HEIGHT/1080);
        characterClass.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        characterClass.setBorder(null);

        maxHealth=new JTextField();
        this.add(maxHealth);
        maxHealth.setBounds(708*WIDTH/1920,300*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        maxHealth.setHorizontalAlignment(SwingConstants.CENTER);
        maxHealth.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        maxHealth.setBorder(null);
        maxHealth.setHorizontalAlignment(SwingConstants.CENTER);

        currentHealth=new JTextField();
        this.add(currentHealth);
        currentHealth.setBounds(783*WIDTH/1920,300*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        currentHealth.setHorizontalAlignment(SwingConstants.CENTER);
        currentHealth.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        currentHealth.setBorder(null);
        currentHealth.setHorizontalAlignment(SwingConstants.CENTER);

        tempHealth=new JTextField();
        this.add(tempHealth);
        tempHealth.setBounds(858*WIDTH/1920,300*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        tempHealth.setHorizontalAlignment(SwingConstants.CENTER);
        tempHealth.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        tempHealth.setBorder(null);
        tempHealth.setHorizontalAlignment(SwingConstants.CENTER);

        AC=new JTextField();
        this.add(AC);
        AC.setBounds(708*WIDTH/1920,200*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        AC.setHorizontalAlignment(SwingConstants.CENTER);
        AC.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        AC.setBorder(null);
        AC.setHorizontalAlignment(SwingConstants.CENTER);

        initiative=new JTextField();
        this.add(initiative);
        initiative.setBounds(783*WIDTH/1920,200*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        initiative.setHorizontalAlignment(SwingConstants.CENTER);
        initiative.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        initiative.setBorder(null);
        initiative.setHorizontalAlignment(SwingConstants.CENTER);

        speed=new JTextField();
        this.add(speed);
        speed.setBounds(858*WIDTH/1920,200*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        speed.setHorizontalAlignment(SwingConstants.CENTER);
        speed.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        speed.setBorder(null);
        speed.setHorizontalAlignment(SwingConstants.CENTER);

        hitDiceTotal=new JTextField();
        this.add(hitDiceTotal);
        hitDiceTotal.setBounds(821*WIDTH/1920,400*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        hitDiceTotal.setHorizontalAlignment(SwingConstants.CENTER);
        hitDiceTotal.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        hitDiceTotal.setBorder(null);
        hitDiceTotal.setHorizontalAlignment(SwingConstants.CENTER);

        hitDiceUsed=new JTextField();
        this.add(hitDiceUsed);
        hitDiceUsed.setBounds(743*WIDTH/1920,400*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        hitDiceUsed.setHorizontalAlignment(SwingConstants.CENTER);
        hitDiceUsed.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        hitDiceUsed.setBorder(null);
        hitDiceUsed.setHorizontalAlignment(SwingConstants.CENTER);

        attackBonus = new JTextField();
        this.add(attackBonus);
        attackBonus.setBounds(743*WIDTH/1920,500*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        attackBonus.setHorizontalAlignment(SwingConstants.CENTER);
        attackBonus.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        attackBonus.setBorder(null);
        attackBonus.setHorizontalAlignment(SwingConstants.CENTER);

        saveDC = new JTextField();
        this.add(saveDC);
        saveDC.setBounds(821*WIDTH/1920,500*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        saveDC.setHorizontalAlignment(SwingConstants.CENTER);
        saveDC.setFont(new Font("Garamond",Font.BOLD,25*WIDTH/1920));
        saveDC.setBorder(null);
        saveDC.setHorizontalAlignment(SwingConstants.CENTER);

        cp = new JTextField("0");
        this.add(cp);
        cp.setBounds(702*WIDTH/1920,600*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        cp.setHorizontalAlignment(SwingConstants.CENTER);
        cp.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        cp.setBorder(null);
        cp.setHorizontalAlignment(SwingConstants.CENTER);

        sp = new JTextField("0");
        this.add(sp);
        sp.setBounds(742*WIDTH/1920,600*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        sp.setHorizontalAlignment(SwingConstants.CENTER);
        sp.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        sp.setBorder(null);
        sp.setHorizontalAlignment(SwingConstants.CENTER);

        ep = new JTextField("0");
        this.add(ep);
        ep.setBounds(782*WIDTH/1920,600*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        ep.setHorizontalAlignment(SwingConstants.CENTER);
        ep.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        ep.setBorder(null);
        ep.setHorizontalAlignment(SwingConstants.CENTER);

        gp = new JTextField("0");
        this.add(gp);
        gp.setBounds(822*WIDTH/1920,600*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        gp.setHorizontalAlignment(SwingConstants.CENTER);
        gp.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        gp.setBorder(null);
        gp.setHorizontalAlignment(SwingConstants.CENTER);

        pp = new JTextField("0");
        this.add(pp);
        pp.setBounds(862*WIDTH/1920,600*HEIGHT/1080,35*WIDTH/1920,35*HEIGHT/1080);
        pp.setHorizontalAlignment(SwingConstants.CENTER);
        pp.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        pp.setBorder(null);
        pp.setHorizontalAlignment(SwingConstants.CENTER);

        race=new JTextField();
        this.add(race);
        race.setBounds(350*WIDTH/1920,65*HEIGHT/1080,125*WIDTH/1920,20*HEIGHT/1080);
        race.setHorizontalAlignment(SwingConstants.CENTER);
        race.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        race.setBorder(null);
        race.setHorizontalAlignment(SwingConstants.CENTER);

        background=new JTextField();
        this.add(background);
        background.setBounds(476*WIDTH/1920,65*HEIGHT/1080,125*WIDTH/1920,20*HEIGHT/1080);
        background.setHorizontalAlignment(SwingConstants.CENTER);
        background.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        background.setBorder(null);
        background.setHorizontalAlignment(SwingConstants.CENTER);

        alignment=new JTextField();
        this.add(alignment);
        alignment.setBounds(350*WIDTH/1920,86*HEIGHT/1080,125*WIDTH/1920,20*HEIGHT/1080);
        alignment.setHorizontalAlignment(SwingConstants.CENTER);
        alignment.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        alignment.setBorder(null);
        alignment.setHorizontalAlignment(SwingConstants.CENTER);

        exp=new JTextField();
        this.add(exp);
        exp.setBounds(476*WIDTH/1920,86*HEIGHT/1080,125*WIDTH/1920,20*HEIGHT/1080);
        exp.setHorizontalAlignment(SwingConstants.CENTER);
        exp.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        exp.setBorder(null);
        exp.setHorizontalAlignment(SwingConstants.CENTER);

        level = new JTextField("1");
        this.add(level);
        level.setBounds(650*WIDTH/1920,100*HEIGHT/1080,30*WIDTH/1920,30*HEIGHT/1080);
        level.setFont(new Font("Garamond",Font.BOLD,30*WIDTH/1920));
        level.setHorizontalAlignment(SwingConstants.CENTER);
        level.addKeyListener(focus);
        level.setBorder(null);

        proficiency = new JTextField();
        this.add(proficiency);
        proficiency.setBounds(50*WIDTH/1920,65*HEIGHT/1080,30*WIDTH/1920,30*HEIGHT/1080);
        proficiency.setFont(new Font("Garamond",Font.BOLD,30*WIDTH/1920));
        proficiency.setHorizontalAlignment(SwingConstants.CENTER);
        proficiency.setBorder(null);
        proficiency.setEditable(false);

        languages = new JTextArea();
        languageScroller = new JScrollPane(languages);
        this.add(languageScroller);
        languageScroller.setBounds(175*WIDTH/1920,880*HEIGHT/1080,150*WIDTH/1920,60*HEIGHT/1080);
        languageScroller.setBorder(null);
        languages.setSize(new Dimension(150,50*HEIGHT/1080));
        languages.setLineWrap(true);
        languages.setWrapStyleWord(true);
        languages.setBorder(null);
        languages.setFont(new Font("Garamond",Font.BOLD,12*WIDTH/1920));
        languages.addKeyListener(focus);

        otherProficiencies = new JTextArea();
        otherScroller = new JScrollPane(otherProficiencies);
        this.add(otherScroller);
        otherScroller.setBounds(175*WIDTH/1920,960*HEIGHT/1080,150*WIDTH/1920,60*HEIGHT/1080);
        otherScroller.setBorder(null);
        otherProficiencies.setSize(new Dimension(150*WIDTH/1920,50*HEIGHT/1080));
        otherProficiencies.setLineWrap(true);
        otherProficiencies.setWrapStyleWord(true);
        otherProficiencies.setBorder(null);
        otherProficiencies.setFont(new Font("Garamond",Font.BOLD,12*WIDTH/1920));
        otherProficiencies.addKeyListener(focus);

        racialTraits = new JTextArea();
        racialScroller = new JScrollPane(racialTraits);
        this.add(racialScroller);
        racialScroller.setBounds(350*WIDTH/1920,880*HEIGHT/1080,200*WIDTH/1920,140*HEIGHT/1080);
        racialTraits.setSize(new Dimension(200*WIDTH/1920,140*HEIGHT/1080));
        racialTraits.setFont(new Font("Garamond",Font.BOLD,12*WIDTH/1920));
        racialTraits.setLineWrap(true);
        racialTraits.setWrapStyleWord(true);
        racialScroller.setBorder(null);
        racialTraits.setBorder(null);
        racialTraits.addKeyListener(focus);

        STR = new JTextField("10");
        this.add(STR);
        STR.addKeyListener(focus);
        STR.setBounds(40*WIDTH/1920,130*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        STR.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        STR.setHorizontalAlignment(SwingConstants.CENTER);
        STR.setBorder(null);

        STRMOD = new JTextField("0");
        this.add(STRMOD);
        STRMOD.setBounds(55*WIDTH/1920,181*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        STRMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        STRMOD.setHorizontalAlignment(SwingConstants.CENTER);
        STRMOD.setBorder(null);
        STRMOD.setEditable(false);

        DEX = new JTextField("10");
        this.add(DEX);
        DEX.addKeyListener(focus);
        DEX.setBounds(40*WIDTH/1920,260*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        DEX.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        DEX.setHorizontalAlignment(SwingConstants.CENTER);
        DEX.setBorder(null);

        DEXMOD = new JTextField("0");
        this.add(DEXMOD);
        DEXMOD.setBounds(55*WIDTH/1920,311*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        DEXMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        DEXMOD.setHorizontalAlignment(SwingConstants.CENTER);
        DEXMOD.setBorder(null);
        DEXMOD.setEditable(false);

        CON = new JTextField("10");
        this.add(CON);
        CON.addKeyListener(focus);
        CON.setBounds(40*WIDTH/1920,390*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        CON.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        CON.setHorizontalAlignment(SwingConstants.CENTER);
        CON.setBorder(null);

        CONMOD = new JTextField("0");
        this.add(CONMOD);
        CONMOD.setBounds(55*WIDTH/1920,441*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        CONMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        CONMOD.setHorizontalAlignment(SwingConstants.CENTER);
        CONMOD.setBorder(null);
        CONMOD.setEditable(false);

        INT = new JTextField("10");
        this.add(INT);
        INT.addKeyListener(focus);
        INT.setBounds(40*WIDTH/1920,520*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        INT.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        INT.setHorizontalAlignment(SwingConstants.CENTER);
        INT.setBorder(null);

        INTMOD = new JTextField("0");
        this.add(INTMOD);
        INTMOD.setBounds(55*WIDTH/1920,571*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        INTMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        INTMOD.setHorizontalAlignment(SwingConstants.CENTER);
        INTMOD.setBorder(null);
        INTMOD.setEditable(false);

        WIS = new JTextField("10");
        this.add(WIS);
        WIS.addKeyListener(focus);
        WIS.setBounds(40*WIDTH/1920,650*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        WIS.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        WIS.setHorizontalAlignment(SwingConstants.CENTER);
        WIS.setBorder(null);

        WISMOD = new JTextField("0");
        this.add(WISMOD);
        WISMOD.setBounds(55*WIDTH/1920,701*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        WISMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        WISMOD.setHorizontalAlignment(SwingConstants.CENTER);
        WISMOD.setBorder(null);
        WISMOD.setEditable(false);

        CHA = new JTextField("10");
        this.add(CHA);
        CHA.addKeyListener(focus);
        CHA.setBounds(40*WIDTH/1920,780*HEIGHT/1080,50*WIDTH/1920,50*HEIGHT/1080);
        CHA.setFont(new Font("Garamond",Font.BOLD,50*WIDTH/1920));
        CHA.setHorizontalAlignment(SwingConstants.CENTER);
        CHA.setBorder(null);

        CHAMOD = new JTextField("0");
        this.add(CHAMOD);
        CHAMOD.setBounds(55*WIDTH/1920,831*HEIGHT/1080,20*WIDTH/1920,20*HEIGHT/1080);
        CHAMOD.setFont(new Font("Garamond",Font.BOLD,15*WIDTH/1920));
        CHAMOD.setHorizontalAlignment(SwingConstants.CENTER);
        CHAMOD.setBorder(null);
        CHAMOD.setEditable(false);

        weapon1 = new JButton();
        this.add(weapon1);
        weapon1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(w1,weapon1);
            }
        });
        weapon1.setBounds(350*WIDTH/1920,700*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        weapon1.setFont(new Font("Times New Roman",Font.BOLD,10));

        weapon2 = new JButton();
        this.add(weapon2);
        weapon2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(w2,weapon2);
            }
        });
        weapon2.setBounds(350*WIDTH/1920,735*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        weapon2.setFont(new Font("Times New Roman",Font.BOLD,10));

        weapon3 = new JButton();
        this.add(weapon3);
        weapon3.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(w3,weapon3);
            }
        });
        weapon3.setBounds(350*WIDTH/1920,770*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        weapon3.setFont(new Font("Times New Roman",Font.BOLD,10));

        weapon4 = new JButton();
        this.add(weapon4);
        weapon4.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(w4,weapon4);
            }
        });
        weapon4.setBounds(350*WIDTH/1920,805*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        weapon4.setFont(new Font("Times New Roman",Font.BOLD,10));

        head = new JButton();
        this.add(head);
        head.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(headItem,head);
            }
        });
        head.setBounds(700*WIDTH/1920,700*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        head.setFont(new Font("Times New Roman",Font.BOLD,10));

        amulet = new JButton();
        this.add(amulet);
        amulet.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(amuletItem,amulet);
            }
        });
        amulet.setBounds(700*WIDTH/1920,735*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        amulet.setFont(new Font("Times New Roman",Font.BOLD,10));

        cloak = new JButton();
        this.add(cloak);
        cloak.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(cloakItem,cloak);
            }
        });
        cloak.setBounds(700*WIDTH/1920,770*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        cloak.setFont(new Font("Times New Roman",Font.BOLD,10));

        armour = new JButton();
        this.add(armour);
        armour.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(armourItem,armour);
            }
        });
        armour.setBounds(700*WIDTH/1920,805*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        armour.setFont(new Font("Times New Roman",Font.BOLD,10));

        hands = new JButton();
        this.add(hands);
        hands.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(handsItem,hands);
            }
        });
        hands.setBounds(700*WIDTH/1920,840*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        hands.setFont(new Font("Times New Roman",Font.BOLD,10));

        ring1 = new JButton();
        this.add(ring1);
        ring1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(ring1Item,ring1);
            }
        });
        ring1.setBounds(700*WIDTH/1920,875*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        ring1.setFont(new Font("Times New Roman",Font.BOLD,10));

        ring2 = new JButton();
        this.add(ring2);
        ring2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(ring2Item,ring2);
            }
        });
        ring2.setBounds(700*WIDTH/1920,910*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        ring2.setFont(new Font("Times New Roman",Font.BOLD,10));

        belt = new JButton();
        this.add(belt);
        belt.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(beltItem,belt);
            }
        });
        belt.setBounds(700*WIDTH/1920,945*HEIGHT/1080,200*WIDTH/1920,35*HEIGHT/1080);
        belt.setFont(new Font("Times New Roman",Font.BOLD,10));

        boots = new JButton();
        this.add(boots);
        boots.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openItem(bootsItem,boots);
            }
        });
        boots.setBounds(700*WIDTH/1920,HEIGHT-100,200*WIDTH/1920,35*HEIGHT/1080);
        boots.setFont(new Font("Times New Roman",Font.BOLD,10));

        strSave = new ability("Saving Throw",115*WIDTH/1920,130*HEIGHT/1080,false);
        this.add(strSave);
        Athletics = new ability("Athletics",115*WIDTH/1920,145*HEIGHT/1080,true);
        this.add(Athletics);

        dexSave = new ability("Saving Throw",115*WIDTH/1920,260*HEIGHT/1080,false);
        this.add(dexSave);
        Acrobatics = new ability("Acrobatics",115*WIDTH/1920,275*HEIGHT/1080,true);
        this.add(Acrobatics);
        Sleight = new ability("Sleight of Hand",115*WIDTH/1920,290*HEIGHT/1080,true);
        this.add(Sleight);
        Stealth = new ability("Stealth",115*WIDTH/1920,305*HEIGHT/1080,true);
        this.add(Stealth);

        conSave = new ability("Saving Throw",115*WIDTH/1920,390*HEIGHT/1080,false);
        this.add(conSave);

        intSave = new ability("Saving Throw",115*WIDTH/1920,520*HEIGHT/1080,false);
        this.add(intSave);
        Arcana = new ability("Arcana",115*WIDTH/1920,535*HEIGHT/1080,true);
        this.add(Arcana);
        History = new ability("History",115*WIDTH/1920,550*HEIGHT/1080,true);
        this.add(History);
        Investigation = new ability("Investigation",115*WIDTH/1920,565*HEIGHT/1080,true);
        this.add(Investigation);
        Nature = new ability("Nature",115*WIDTH/1920,580*HEIGHT/1080,true);
        this.add(Nature);
        Religion = new ability("Religion",115*WIDTH/1920,595*HEIGHT/1080,true);
        this.add(Religion);

        wisSave = new ability("Saving Throw",115*WIDTH/1920,650*HEIGHT/1080,false);
        this.add(wisSave);
        Animal = new ability("Animal",115*WIDTH/1920,665*HEIGHT/1080,true);
        this.add(Animal);
        Insight = new ability("Insight",115*WIDTH/1920,680*HEIGHT/1080,true);
        this.add(Insight);
        Medicine = new ability("Medicine",115*WIDTH/1920,695*HEIGHT/1080,true);
        this.add(Medicine);
        Perception = new ability("Preception",115*WIDTH/1920,710*HEIGHT/1080,true);
        this.add(Perception);
        Survival = new ability("Survival",115*WIDTH/1920,725*HEIGHT/1080,true);
        this.add(Survival);

        chaSave = new ability("Saving Throw",115*WIDTH/1920,780*HEIGHT/1080,false);
        this.add(chaSave);
        Deception = new ability("Deception",115*WIDTH/1920,795*HEIGHT/1080, true);
        this.add(Deception);
        Intimidation = new ability("Intimidation",115*WIDTH/1920,810*HEIGHT/1080,true);
        this.add(Intimidation);
        Performance = new ability("Performance",115*WIDTH/1920,825*HEIGHT/1080,true);
        this.add(Performance);
        Persuasion = new ability("Persuasion",115*WIDTH/1920,840*HEIGHT/1080,true);
        this.add(Persuasion);

        for(Component c:this.getComponents()){
            if(c instanceof ability && ((ability) c).expertise!=null){
                this.add(((ability) c).expertise);
            }
        }
        light = new JCheckBox("Light");
        this.add(light);
        light.setBounds(30*WIDTH/1920,910*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        light.setHorizontalAlignment(SwingConstants.RIGHT);
        light.setHorizontalTextPosition(SwingConstants.LEFT);
        light.setBorder(null);
        light.setIcon(unpressed);
        light.setSelectedIcon(pressed);
        light.setPressedIcon(pressing);
        light.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        light.setBackground(Color.white);
        light.addActionListener(buttonFocus);

        medium = new JCheckBox("Medium");
        this.add(medium);
        medium.setBounds(30*WIDTH/1920,925*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        medium.setHorizontalAlignment(SwingConstants.RIGHT);
        medium.setHorizontalTextPosition(SwingConstants.LEFT);
        medium.setIcon(unpressed);
        medium.setPressedIcon(pressed);
        medium.setSelectedIcon(pressing);
        medium.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        medium.setBorder(null);
        medium.setBackground(Color.white);
        medium.addActionListener(buttonFocus);

        heavy = new JCheckBox("Heavy");
        this.add(heavy);
        heavy.setBounds(30*WIDTH/1920,940*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        heavy.setHorizontalAlignment(SwingConstants.RIGHT);
        heavy.setHorizontalTextPosition(SwingConstants.LEFT);
        heavy.setBorder(null);
        heavy.setIcon(unpressed);
        heavy.setSelectedIcon(pressed);
        heavy.setPressedIcon(pressing);
        heavy.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        heavy.setBackground(Color.white);
        heavy.addActionListener(buttonFocus);

        simple = new JCheckBox("Simple");
        this.add(simple);
        simple.setBounds(95*WIDTH/1920,910*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        simple.setBorder(null);
        simple.setIcon(unpressed);
        simple.setSelectedIcon(pressed);
        simple.setPressedIcon(pressing);
        simple.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        simple.setBackground(Color.white);
        simple.addActionListener(buttonFocus);

        martial = new JCheckBox("Martial");
        this.add(martial);
        martial.setBounds(95*WIDTH/1920,925*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        martial.setBorder(null);
        martial.setIcon(unpressed);
        martial.setSelectedIcon(pressed);
        martial.setPressedIcon(pressing);
        martial.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        martial.setBackground(Color.white);
        martial.addActionListener(buttonFocus);

        shield = new JCheckBox("Shield");
        this.add(shield);
        shield.setBounds(95*WIDTH/1920,940*HEIGHT/1080,65*WIDTH/1920,15*HEIGHT/1080);
        shield.setBorder(null);
        shield.setIcon(unpressed);
        heavy.setSelectedIcon(pressed);
        heavy.setPressedIcon(pressing);
        shield.setFont(new Font("Garamond",Font.PLAIN,13*starter.WIDTH/1920));
        shield.setBackground(Color.white);
        shield.addActionListener(buttonFocus);

        spellPane = new JPanel();
        spellPane.setPreferredSize(new Dimension(WIDTH/2-5,HEIGHT-50));
        spellPane.setSize(WIDTH/2-5,HEIGHT-50);
        spellPane.setLayout(null);

        spellScroller = new JScrollPane(spellPane);
        frame.add(spellScroller);
        spellScroller.setBounds(WIDTH/2,35*HEIGHT/1080,WIDTH/2-5,HEIGHT-85);
        spellScroller.setPreferredSize(new Dimension(WIDTH/2-5,HEIGHT-85));
        spellScroller.setSize(WIDTH/2-5,HEIGHT-85);
        spellScroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        spellScroller.setVisible(false);



        searchSpell = new JButton("Search");
        searchSpell.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchSpell();
            }
        });
        frame.add(searchSpell);
        searchSpell.setVisible(false);
        searchSpell.setBounds(WIDTH/2,0,100*WIDTH/1920,35*HEIGHT/1080);

        spellListTitle = new JTextField("All Spells");
        frame.add(spellListTitle);
        spellListTitle.setVisible(false);
        spellListTitle.setBounds(WIDTH/2+100*WIDTH/1920,0,WIDTH/2-200*WIDTH/1920,35*HEIGHT/1080);
        spellListTitle.setHorizontalAlignment(SwingConstants.CENTER);
        spellListTitle.setForeground(Color.white);
        spellListTitle.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        spellListTitle.setBackground(Color.black);
        spellListTitle.setBorder(null);
        spellListTitle.setEditable(false);

        spellBookTitle=new JTextField("Spell Book");
        frame.add(spellBookTitle);
        spellBookTitle.setVisible(false);
        spellBookTitle.setBounds(WIDTH/2+100*WIDTH/1920,0,WIDTH/2-200*WIDTH/1920,35*HEIGHT/1080);
        spellBookTitle.setHorizontalAlignment(SwingConstants.CENTER);
        spellBookTitle.setForeground(Color.white);
        spellBookTitle.setFont(new Font("Garamond",Font.BOLD,20*WIDTH/1920));
        spellBookTitle.setBackground(Color.black);
        spellBookTitle.setBorder(null);
        spellBookTitle.setEditable(false);

        searchFrame = new JFrame("Search Filter");
        searchFrame.setSize(new Dimension(550,600));
        searchFrame.setLayout(null);
        searchFrame.setVisible(false);
        searchFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(searchName.getText().equals(""))filterSearchButton.doClick();
                else nameSearchButton.doClick();
                super.windowClosing(e);
            }
        });

        abjuration = new JCheckBox("Abjuration");
        searchFrame.add(abjuration);
        abjuration.setBounds(50,200,150,20);
        abjuration.addActionListener(searchAction);

        conjuration = new JCheckBox("Conjuration");
        searchFrame.add(conjuration);
        conjuration.setBounds(50,220,150,20);
        conjuration.addActionListener(searchAction);

        divination = new JCheckBox("Divination");
        searchFrame.add(divination);
        divination.setBounds(50,240,150,20);
        divination.addActionListener(searchAction);

        enchantment = new JCheckBox("Enchantment");
        searchFrame.add(enchantment);
        enchantment.setBounds(50,260,150,20);
        enchantment.addActionListener(searchAction);

        evocation = new JCheckBox("Evocation");
        searchFrame.add(evocation);
        evocation.setBounds(50,280,150,20);
        evocation.addActionListener(searchAction);

        illusion = new JCheckBox("Illusion");
        searchFrame.add(illusion);
        illusion.setBounds(50,300,150,20);
        illusion.addActionListener(searchAction);

        necromancy = new JCheckBox("Necromancy");
        searchFrame.add(necromancy);
        necromancy.setBounds(50,320,150,20);
        necromancy.addActionListener(searchAction);

        transmutation = new JCheckBox("Transmutation");
        searchFrame.add(transmutation);
        transmutation.setBounds(50,340,150,20);
        transmutation.addActionListener(searchAction);

        concentration = new JCheckBox("Concentration");
        searchFrame.add(concentration);
        concentration.setBounds(50,360,150,20);
        concentration.addActionListener(searchAction);

        ritual = new JCheckBox("Ritual");
        searchFrame.add(ritual);
        ritual.setBounds(50,380,150,20);
        ritual.addActionListener(searchAction);

        cantrip = new JCheckBox("Cantrip");
        searchFrame.add(cantrip);
        cantrip.setBounds(350,200,150,20);
        cantrip.addActionListener(searchAction);

        level1 = new JCheckBox("Level 1");
        searchFrame.add(level1);
        level1.setBounds(350,220,150,20);
        level1.addActionListener(searchAction);

        level2 = new JCheckBox("Level 2");
        searchFrame.add(level2);
        level2.setBounds(350,240,150,20);
        level2.addActionListener(searchAction);

        level3 = new JCheckBox("Level 3");
        searchFrame.add(level3);
        level3.setBounds(350,260,150,20);
        level3.addActionListener(searchAction);

        level4 = new JCheckBox("Level 4");
        searchFrame.add(level4);
        level4.setBounds(350,280,150,20);
        level4.addActionListener(searchAction);

        level5 = new JCheckBox("Level 5");
        searchFrame.add(level5);
        level5.setBounds(350,300,150,20);
        level5.addActionListener(searchAction);

        level6 = new JCheckBox("Level 6");
        searchFrame.add(level6);
        level6.setBounds(350,320,150,20);
        level6.addActionListener(searchAction);

        level7 = new JCheckBox("Level 7");
        searchFrame.add(level7);
        level7.setBounds(350,340,150,20);
        level7.addActionListener(searchAction);

        level8 = new JCheckBox("Level 8");
        searchFrame.add(level8);
        level8.setBounds(350,360,150,20);
        level8.addActionListener(searchAction);

        level9 = new JCheckBox("Level 9");
        searchFrame.add(level9);
        level9.setBounds(350,380,150,20);
        level9.addActionListener(searchAction);

        artificer = new JCheckBox("Artificer");
        searchFrame.add(artificer);
        artificer.setBounds(200,200,150,20);
        artificer.addActionListener(searchAction);

        bard = new JCheckBox("Bard");
        searchFrame.add(bard);
        bard.setBounds(200,220,150,20);
        bard.addActionListener(searchAction);

        cleric = new JCheckBox("Cleric");
        searchFrame.add(cleric);
        cleric.setBounds(200,240,150,20);
        cleric.addActionListener(searchAction);

        druid = new JCheckBox("Druid");
        searchFrame.add(druid);
        druid.setBounds(200,260,150,20);
        druid.addActionListener(searchAction);

        paladin = new JCheckBox("Paladin");
        searchFrame.add(paladin);
        paladin.setBounds(200,280,150,20);
        paladin.addActionListener(searchAction);

        ranger = new JCheckBox("Ranger");
        searchFrame.add(ranger);
        ranger.setBounds(200,300,150,20);
        ranger.addActionListener(searchAction);

        sorcerer = new JCheckBox("Sorcerer");
        searchFrame.add(sorcerer);
        sorcerer.setBounds(200,320,150,20);
        sorcerer.addActionListener(searchAction);

        warlock = new JCheckBox("Warlock");
        searchFrame.add(warlock);
        warlock.setBounds(200,340,150,20);
        warlock.addActionListener(searchAction);

        wizard = new JCheckBox("Wizard");
        searchFrame.add(wizard);
        wizard.setBounds(200,360,150,20);
        wizard.addActionListener(searchAction);

        for(Component c: searchFrame.getContentPane().getComponents()){
            if(c instanceof JCheckBox){
                ((JCheckBox) c).setIcon(unpressed);
                ((JCheckBox) c).setSelectedIcon(pressed);
                ((JCheckBox) c).setPressedIcon(pressing);
            }
        }

        searchName = new JTextField("");
        searchFrame.add(searchName);
        searchName.setBorder(null);
        searchName.setBounds(20,50,500,60);
        searchName.setFont(new Font("Garamond",Font.PLAIN,40));

        nameSearchButton = new JButton("Search By Name");
        searchFrame.add(nameSearchButton);
        nameSearchButton.setBounds(20,125,500,50);
        nameSearchButton.setFont(new Font("Garamond",Font.PLAIN,40));
        nameSearchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.requestFocus();
                searchFrame.setVisible(false);
                if(searchName.getText()==null)return;
                ArrayList<spellObject> temp = new ArrayList<>();
                if(spellListTitle.isVisible()){
                    for(spellObject spell:grandSpellList){
                        if(spell.name.toLowerCase().trim().contains(searchName.getText().toLowerCase().trim())) {
                            start.requestFocus();
                            temp.add(spell);
                        }

                    }
                }else {
                    for (spellObject spell : preparedSpells) {
                        if (spell.name.toLowerCase().trim().contains(searchName.getText().toLowerCase().trim())) {
                            start.requestFocus();
                            temp.add(spell);
                        }
                    }
                    for (spellObject spell : spellBook) {
                        if (spell.name.toLowerCase().trim().contains(searchName.getText().toLowerCase().trim())) {
                            start.requestFocus();
                            temp.add(spell);
                        }
                    }
                }
                spellPane.removeAll();
                spellPane.setPreferredSize(new Dimension(WIDTH/2-5,100*HEIGHT/1080));
                spellPane.repaint();
                int i=0;
                for(spellObject spell:temp){
                    spellPane.add(spell.button);
                    if(spellListTitle.isVisible()) {
                        spellPane.add(spell.known);
                        spell.known.setBounds(0, 40 * i + 10, 20, 20);
                    }else{

                        spellPane.add(spell.prepared);
                        spell.prepared.setBounds(0, 40 * i + 10, 20, 20);
                    }
                    spell.button.setBounds(20,40*i,WIDTH/2-5,40);
                    i++;
                }
                spellPane.setPreferredSize(new Dimension(WIDTH/2,40*i+40));
            }
        });

        filterSearchButton = new JButton("Search By Filter");
        searchFrame.add(filterSearchButton);
        filterSearchButton.setBounds(20,500,500,50);
        filterSearchButton.setFont(new Font("Garamond",Font.PLAIN,40));
        filterSearchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.requestFocus();
                if(spellListTitle.isVisible())openGrandSpellList();
                else openSpellBook();
                searchFrame.setVisible(false);
            }
        });

        spellSlotFrame = new JFrame("Spell Slots");
        spellSlotFrame.setSize(new Dimension(550,600));
        spellSlotFrame.setLayout(null);
        spellSlotFrame.setVisible(false);

        fullCaster = new JCheckBox("Full");
        spellSlotFrame.add(fullCaster);
        fullCaster.setBounds(35,50,100,15);
        fullCaster.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fullCaster.isSelected())casterUpdate("Full");
                else casterUpdate("");
            }
        });
        halfCaster = new JCheckBox("Half");
        spellSlotFrame.add(halfCaster);
        halfCaster.setBounds(160,50,100,15);
        halfCaster.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(halfCaster.isSelected())casterUpdate("Half");
                else casterUpdate("");
            }
        });
        thirdCaster = new JCheckBox("Third");
        thirdCaster.setBounds(285,50,100,15);
        thirdCaster.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(thirdCaster.isSelected())casterUpdate("Third");
                else casterUpdate("");
            }
        });
        spellSlotFrame.add(thirdCaster);
        pactCaster = new JCheckBox("Pact");
        spellSlotFrame.add(pactCaster);
        pactCaster.setBounds(410,50,100,15);
        pactCaster.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(pactCaster.isSelected())casterUpdate("Pact");
                else casterUpdate("");
            }
        });

        for(Component c: spellSlotFrame.getContentPane().getComponents()){
            if(c instanceof JCheckBox){
                ((JCheckBox) c).setIcon(unpressed);
                ((JCheckBox) c).setSelectedIcon(pressed);
                ((JCheckBox) c).setPressedIcon(pressing);
            }
        }

        spellSlotButton = new JButton("Slots");
        spellSlotButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spellSlotFrame.setVisible(true);
            }
        });
        frame.add(spellSlotButton);
        spellSlotButton.setVisible(false);
        spellSlotButton.setBounds(WIDTH-100*WIDTH/1920,0,100*WIDTH/1920,35*HEIGHT/1080);


        update();
        this.setVisible(true);
        pane.setVisible(true);
        spellPane.setVisible(true);
        frame.setVisible(true);
        initializeGrandSpellList();
        openGrandSpellList();
    }

    void searchSpell(){
        searchFrame.setVisible(true);
    }
    void openItem(item i,JButton button){
        start.requestFocus();
        JFrame frame = new JFrame(i.name);
        frame.setLayout(null);
        frame.setSize(567,600);
        frame.setBackground(Color.white);

        JTextField name = new JTextField(i.name);
        frame.add(name);
        name.setBounds(0,0,550,70);
        name.setFont(new Font("Times New Roman",Font.PLAIN,50));
        name.setHorizontalAlignment(SwingConstants.CENTER);
        name.setBorder(null);

        JTextField extra = new JTextField(i.extra);
        frame.add(extra);
        extra.setBounds(0,70,550,40);
        extra.setFont(new Font("Garamond",Font.PLAIN,30));
        extra.setHorizontalAlignment(SwingConstants.CENTER);
        extra.setBorder(null);

        JTextArea description = new JTextArea(i.description);
        frame.add(description);
        description.setBounds(0,110,550,490);
        description.setFont(new Font("Garamond",Font.PLAIN,16));
        description.setWrapStyleWord(true);
        description.setLineWrap(true);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                i.name=name.getText();
                i.extra=extra.getText();
                i.description=description.getText();
                button.setText(i.name);
                super.windowClosing(e);
            }
        });
    }

    public void organizeSpells(ArrayList<spellObject> list){
        if(list.size()<=1)return;
        for(int i=1;i<list.size();i++){
            if(list.get(i).spellLvl<list.get(i-1).spellLvl){
                spellObject temp = list.get(i);
                list.set(i,list.get(i-1));
                list.set(i-1,temp);
                if(i!=1)i-=2;
            }else if(list.get(i).spellLvl==list.get(i-1).spellLvl){
                if(list.get(i).name.compareToIgnoreCase(list.get(i-1).name)<0){
                    spellObject temp = list.get(i);
                    list.set(i,list.get(i-1));
                    list.set(i-1,temp);
                    if(i!=1)i-=2;
                }
            }
        }

    }

    void casterUpdate(String type) {
        for(int i=0;i<spellSlotFrame.getContentPane().getComponentCount();){
            Component c = spellSlotFrame.getContentPane().getComponent(i);
            if(!c.equals(fullCaster) && !c.equals(halfCaster) && !c.equals(thirdCaster) && !c.equals(pactCaster))spellSlotFrame.getContentPane().remove(c);
            else i++;
        }
        spellSlotFrame.repaint();
        switch (type) {
            case "Full":
                pactCaster.setSelected(false);
                halfCaster.setSelected(false);
                thirdCaster.setSelected(false);
                fullCaster.setSelected(true);
                lvl1 = new JTextField("1");
                spellSlotFrame.add(lvl1);
                lvl1.setBounds(70,180,20,20);
                for (int i = 0; i < 4; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(70, i * 50 + 200, 20, 20);
                }
                lvl2 = new JTextField("2");
                spellSlotFrame.add(lvl2);
                lvl2.setBounds(120,180,20,20);
                lvl3 = new JTextField("3");
                spellSlotFrame.add(lvl3);
                lvl3.setBounds(170,180,20,20);
                lvl4 = new JTextField("4");
                spellSlotFrame.add(lvl4);
                lvl4.setBounds(220,180,20,20);
                lvl5 = new JTextField("5");
                spellSlotFrame.add(lvl5);
                lvl5.setBounds(270,180,20,20);
                for (int j = 0; j < 4; j++) {
                    for (int i = 0; i < 3; i++) {
                        JCheckBox slot = new JCheckBox();
                        spellSlotFrame.add(slot);
                        slot.setBounds(120 + j * 50, i * 50 + 200, 20, 20);
                    }
                }
                lvl6 = new JTextField("6");
                spellSlotFrame.add(lvl6);
                lvl6.setBounds(320,180,20,20);
                lvl7 = new JTextField("7");
                spellSlotFrame.add(lvl7);
                lvl7.setBounds(370,180,20,20);
                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < 2; i++) {
                        JCheckBox slot = new JCheckBox();
                        spellSlotFrame.add(slot);
                        slot.setBounds(320 + j * 50, i * 50 + 200, 20, 20);
                    }
                }
                lvl8 = new JTextField("8");
                spellSlotFrame.add(lvl8);
                lvl8.setBounds(420,180,20,20);
                lvl9 = new JTextField("9");
                spellSlotFrame.add(lvl9);
                lvl9.setBounds(470,180,20,20);
                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < 1; i++) {
                        JCheckBox slot = new JCheckBox();
                        spellSlotFrame.add(slot);
                        slot.setBounds(420 + j * 50, i * 50 + 200, 20, 20);
                    }
                }
                lvl1.setBorder(null);
                lvl2.setBorder(null);
                lvl3.setBorder(null);
                lvl4.setBorder(null);
                lvl5.setBorder(null);
                lvl6.setBorder(null);
                lvl7.setBorder(null);
                lvl8.setBorder(null);
                lvl9.setBorder(null);
                lvl1.setHorizontalAlignment(SwingConstants.CENTER);
                lvl2.setHorizontalAlignment(SwingConstants.CENTER);
                lvl3.setHorizontalAlignment(SwingConstants.CENTER);
                lvl4.setHorizontalAlignment(SwingConstants.CENTER);
                lvl5.setHorizontalAlignment(SwingConstants.CENTER);
                lvl6.setHorizontalAlignment(SwingConstants.CENTER);
                lvl7.setHorizontalAlignment(SwingConstants.CENTER);
                lvl8.setHorizontalAlignment(SwingConstants.CENTER);
                lvl9.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Half":
                pactCaster.setSelected(false);
                fullCaster.setSelected(false);
                thirdCaster.setSelected(false);
                halfCaster.setSelected(true);
                lvl1 = new JTextField("1");
                spellSlotFrame.add(lvl1);
                lvl1.setBounds(170,180,20,20);
                for (int i = 0; i < 4; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(170, i * 50 + 200, 20, 20);
                }
                lvl2 = new JTextField("2");
                spellSlotFrame.add(lvl2);
                lvl2.setBounds(220,180,20,20);
                lvl3 = new JTextField("3");
                spellSlotFrame.add(lvl3);
                lvl3.setBounds(270,180,20,20);
                lvl4 = new JTextField("4");
                spellSlotFrame.add(lvl4);
                lvl4.setBounds(320,180,20,20);
                for (int j = 0; j < 3; j++) {
                    for (int i = 0; i < 3; i++) {
                        JCheckBox slot = new JCheckBox();
                        spellSlotFrame.add(slot);
                        slot.setBounds(220 + j * 50, i * 50 + 200, 20, 20);
                    }
                }
                lvl5 = new JTextField("5");
                spellSlotFrame.add(lvl5);
                lvl5.setBounds(370,180,20,20);
                for (int i = 0; i < 2; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(370, i * 50 + 200, 20, 20);
                }
                lvl1.setBorder(null);
                lvl2.setBorder(null);
                lvl3.setBorder(null);
                lvl4.setBorder(null);
                lvl5.setBorder(null);
                lvl1.setHorizontalAlignment(SwingConstants.CENTER);
                lvl2.setHorizontalAlignment(SwingConstants.CENTER);
                lvl3.setHorizontalAlignment(SwingConstants.CENTER);
                lvl4.setHorizontalAlignment(SwingConstants.CENTER);
                lvl5.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Third":
                pactCaster.setSelected(false);
                halfCaster.setSelected(false);
                fullCaster.setSelected(false);
                thirdCaster.setSelected(true);
                lvl1 = new JTextField("1");
                spellSlotFrame.add(lvl1);
                lvl1.setBounds(200,180,20,20);
                for (int i = 0; i < 4; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(200, i * 50 + 200, 20, 20);
                }
                lvl2 = new JTextField("2");
                spellSlotFrame.add(lvl2);
                lvl2.setBounds(250,180,20,20);
                lvl3 = new JTextField("3");
                spellSlotFrame.add(lvl3);
                lvl3.setBounds(300,180,20,20);
                for (int j = 0; j < 2; j++) {
                    for (int i = 0; i < 3; i++) {
                        JCheckBox slot = new JCheckBox();
                        spellSlotFrame.add(slot);
                        slot.setBounds(250 + j * 50, i * 50 + 200, 20, 20);
                    }
                }
                lvl4 = new JTextField("4");
                spellSlotFrame.add(lvl4);
                lvl4.setBounds(350,180,20,20);
                for (int i = 0; i < 1; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(350, i * 50 + 200, 20, 20);
                }
                lvl1.setBorder(null);
                lvl2.setBorder(null);
                lvl3.setBorder(null);
                lvl4.setBorder(null);
                lvl1.setHorizontalAlignment(SwingConstants.CENTER);
                lvl2.setHorizontalAlignment(SwingConstants.CENTER);
                lvl3.setHorizontalAlignment(SwingConstants.CENTER);
                lvl4.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case "Pact":
                fullCaster.setSelected(false);
                halfCaster.setSelected(false);
                thirdCaster.setSelected(false);
                pactCaster.setSelected(true);
                lvl1 = new JTextField();
                spellSlotFrame.add(lvl1);
                lvl1.setBounds(250,180,20,20);
                for (int i = 0; i < 4; i++) {
                    JCheckBox slot = new JCheckBox();
                    spellSlotFrame.add(slot);
                    slot.setBounds(250, i * 50 + 200, 20, 20);
                }
                lvl1.setBorder(null);
                lvl1.setHorizontalAlignment(SwingConstants.CENTER);
                break;

        }

        for(Component c:spellSlotFrame.getContentPane().getComponents()){
            if(c instanceof JCheckBox){
                ((JCheckBox) c).setIcon(unpressed);
                ((JCheckBox) c).setSelectedIcon(pressed);
                ((JCheckBox) c).setPressedIcon(pressing);
            }
        }
    }

    public void update(){
        try {
            int lvl = Integer.parseInt(level.getText());
            if (lvl < 5) proficiency.setText("2");
            else if (lvl < 9) proficiency.setText("3");
            else if (lvl < 13) proficiency.setText("4");
            else if (lvl < 17) proficiency.setText("5");
            else proficiency.setText("6");
        }catch (Exception e){}
        try {
            STRMOD.setText(Integer.toString(Integer.parseInt(STR.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("STRMOD update error");}
        try {
            DEXMOD.setText(Integer.toString(Integer.parseInt(DEX.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("DEXMOD update error");}
        try {
            CONMOD.setText(Integer.toString(Integer.parseInt(CON.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("CONMOD update error");}
        try {
            INTMOD.setText(Integer.toString(Integer.parseInt(INT.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("INTMOD update error");}
        try {
            WISMOD.setText(Integer.toString(Integer.parseInt(WIS.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("WISMOD update error");}
        try{
            CHAMOD.setText(Integer.toString(Integer.parseInt(CHA.getText()) / 2 - 5));
        }catch (Exception e){System.out.println("CHAMOD update error");}
        int prof=Integer.parseInt(proficiency.getText()), STR=Integer.parseInt(STRMOD.getText()),DEX=Integer.parseInt(DEXMOD.getText()),CON=Integer.parseInt(CONMOD.getText()),INT=Integer.parseInt(INTMOD.getText()),WIS=Integer.parseInt(WISMOD.getText()),CHA=Integer.parseInt(CHAMOD.getText());

        strSave.setText(STR+((strSave.isSelected()) ? prof:0)+"  Saving Throw");
        Athletics.setText(STR+((Athletics.isSelected()) ? ((Athletics.expertise.isSelected()) ? prof*2:prof):0)+"  Athletics");
        dexSave.setText(DEX+((dexSave.isSelected()) ? prof:0)+"  Saving Throw");
        Acrobatics.setText(DEX+((Acrobatics.isSelected()) ? ((Acrobatics.expertise.isSelected())?prof*2:prof):0)+"  Acrobatics");
        Sleight.setText(DEX+((Sleight.isSelected()) ? ((Sleight.expertise.isSelected())?prof*2:prof):0)+"  Sleight of Hand");
        Stealth.setText(DEX+((Stealth.isSelected()) ? ((Stealth.expertise.isSelected())?prof*2:prof):0)+"  Stealth");
        conSave.setText(CON+((conSave.isSelected()) ? prof:0)+"  Saving Throw");
        intSave.setText(INT+((intSave.isSelected()) ? prof:0)+"  Saving Throw");
        Arcana.setText(INT+((Arcana.isSelected()) ? ((Arcana.expertise.isSelected())?prof*2:prof):0)+"  Arcana");
        History.setText(INT+((History.isSelected()) ? ((History.expertise.isSelected())?prof*2:prof):0)+"  History");
        Investigation.setText(INT+((Investigation.isSelected()) ? ((Investigation.expertise.isSelected())?prof*2:prof):0)+"  Investigation");
        Nature.setText(INT+((Nature.isSelected()) ? ((Nature.expertise.isSelected())?prof*2:prof):0)+"  Nature");
        Religion.setText(INT+((Religion.isSelected()) ? ((Religion.expertise.isSelected())?prof*2:prof):0)+"  Religion");
        wisSave.setText(WIS+((wisSave.isSelected()) ? prof:0)+"  Saving Throw");
        Animal.setText(WIS+((Animal.isSelected()) ? ((Animal.expertise.isSelected())?prof*2:prof):0)+"  Animal Handling");
        Insight.setText(WIS+((Insight.isSelected()) ? ((Insight.expertise.isSelected())?prof*2:prof):0)+"  Insight");
        Medicine.setText(WIS+((Medicine.isSelected()) ? ((Medicine.expertise.isSelected())?prof*2:prof):0)+"  Medicine");
        Perception.setText(WIS+((Perception.isSelected()) ? ((Perception.expertise.isSelected())?prof*2:prof):0)+"  Perception");
        Survival.setText(WIS+((Survival.isSelected()) ? ((Survival.expertise.isSelected())?prof*2:prof):0)+"  Survival");
        chaSave.setText(CHA+((chaSave.isSelected()) ? prof:0)+"  Saving Throw");
        Deception.setText(CHA+((Deception.isSelected()) ? ((Deception.expertise.isSelected())?prof*2:prof):0)+"  Deception");
        Intimidation.setText(CHA+((Intimidation.isSelected()) ? ((Intimidation.expertise.isSelected())?prof*2:prof):0)+"  Intimidation");
        Performance.setText(CHA+((Performance.isSelected()) ? ((Performance.expertise.isSelected())?prof*2:prof):0)+"  Performance");
        Persuasion.setText(CHA+((Persuasion.isSelected()) ? ((Persuasion.expertise.isSelected())?prof*2:prof):0)+"  Persuasion");
        save(lastFile);
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,WIDTH/2,HEIGHT);
        g.setColor(Color.white);
        g.setFont(new Font("Garamond",Font.BOLD,16*WIDTH/1920));
        g.drawString("Name",characterName.getX(),characterName.getY()-5);
        g.drawString("LvL",level.getX()+2,level.getY()-5);
        g.drawString("Race",race.getX()+40,race.getY()-5);
        g.drawString("Class",characterClass.getX(),characterClass.getY()+43);
        g.drawString("Background",background.getX()+20,background.getY()-5);
        g.drawString("Alignment",alignment.getX()+23,alignment.getY()+32);
        g.drawString("Experience",exp.getX()+22,exp.getY()+32);
        g.drawString("Proficiency",proficiency.getX()-20,proficiency.getY()-5);
        g.drawString("AC",AC.getX()+7,AC.getY()-5);
        g.drawString("Initiative",initiative.getX()-13,initiative.getY()-5);
        g.drawString("Speed",speed.getX()-3,speed.getY()-5);
        g.drawString("Max",maxHealth.getX()+2,maxHealth.getY()-5);
        g.drawString("HP",currentHealth.getX()+7,currentHealth.getY()-5);
        g.drawString("Temp",tempHealth.getX()-3,tempHealth.getY()-5);
        g.drawString("Attack",attackBonus.getX()-4,attackBonus.getY()-20);
        g.drawString("Hit",hitDiceTotal.getX()+5,hitDiceTotal.getY()-20);
        g.drawString("Dice",hitDiceTotal.getX()+2,hitDiceTotal.getY()-5);
        g.drawString("Dice",hitDiceUsed.getX()+2,hitDiceUsed.getY()-20);
        g.drawString("Used",hitDiceUsed.getX(),hitDiceUsed.getY()-5);
        g.drawString("Bonus",attackBonus.getX()-4,attackBonus.getY()-5);
        g.drawString("Save DC",saveDC.getX()-11,saveDC.getY()-5);
        g.drawString("CP",cp.getX()+8,cp.getY()-5);
        g.drawString("SP",sp.getX()+8,sp.getY()-5);
        g.drawString("EP",ep.getX()+8,ep.getY()-5);
        g.drawString("GP",gp.getX()+8,gp.getY()-5);
        g.drawString("PP",pp.getX()+8,pp.getY()-5);
        g.drawString("Languages",languageScroller.getX()+35,languageScroller.getY()-5);
        g.drawString("Other Proficiencies",otherScroller.getX()+10,otherScroller.getY()-3);
        g.drawString("Racial Traits",racialScroller.getX()+55,racialScroller.getY()-5);
        g.drawString("Strength",STR.getX()-4,STR.getY()-5);
        g.drawString("Dexterity",DEX.getX()-7,DEX.getY()-5);
        g.drawString("Constitution",CON.getX()-20,CON.getY()-5);
        g.drawString("Intelligence",INT.getX()-15,INT.getY()-5);
        g.drawString("Wisdom",WIS.getX()-3,WIS.getY()-5);
        g.drawString("Charisma",CHA.getX()-11,CHA.getY()-5);
        g.drawString("Head",head.getX()-60,head.getY()+22);
        g.drawString("Amulet",amulet.getX()-60,amulet.getY()+22);
        g.drawString("Cloak",cloak.getX()-60,cloak.getY()+22);
        g.drawString("Armour",armour.getX()-60,armour.getY()+22);
        g.drawString("Hands",hands.getX()-60,hands.getY()+22);
        g.drawString("Ring",ring1.getX()-60,ring1.getY()+22);
        g.drawString("Ring",ring2.getX()-60,ring2.getY()+22);
        g.drawString("Belt",belt.getX()-60,belt.getY()+22);
        g.drawString("Boots",boots.getX()-60,boots.getY()+22);
        g.drawString("Proficiencies",light.getX()+22,light.getY()-5);
        super.paintComponent(g);
    }
    void initializeGrandSpellList(){
        try{
            BufferedReader in = new BufferedReader(new FileReader("D&D Character Files\\GrandSpellList.txt"));
            String s=in.readLine();
            while(s!=null){
                String name = s;
                s=in.readLine();
                int spellLvl = Integer.parseInt(s);
                s=in.readLine();
                String castTime=s;
                s=in.readLine();
                String duration=s;
                s=in.readLine();
                String range=s;
                s=in.readLine();
                String components = s;
                s=in.readLine();
                String description = s;
                s=in.readLine();
                String tags=s;
                s=in.readLine();
                grandSpellList.add(new spellObject(name,spellLvl,castTime,duration,range,components,description,tags));
            }
        }catch (Exception e){System.out.println("Error creating grand spell list.");}
        System.out.print(grandSpellList.size());
    }

    void openSpellBook(){
        organizeSpells(spellBook);
        organizeSpells(preparedSpells);
        spellPane.removeAll();
        spellPane.setPreferredSize(new Dimension(WIDTH/2-5,100));
        spellPane.repaint();
        int i=0;
        main:
        for(spellObject spell:preparedSpells){
            for(String s:searchFilter){
                if(!spell.tags.contains(s))continue main;
            }
            if(spell.spellLvl==0){
                spellPane.setPreferredSize(new Dimension(WIDTH/2,40*i+40));
                spellPane.add(spell.button);
                spell.button.setBounds(20,40*i,WIDTH/2-5,40);
                i++;
                continue;
            }

            spellPane.add(spell.button);
            spellPane.add(spell.prepared);
            spell.prepared.setBounds(0, 40 * i + 10, 20, 20);
            spell.button.setBounds(20,40*i,WIDTH/2-5,40);
            i++;
        }


        main:
        for(spellObject spell:spellBook){
            for(String s:searchFilter){
                if(!spell.tags.contains(s))continue main;

            }
            spellPane.add(spell.button);
            spellPane.add(spell.prepared);
            spell.prepared.setBounds(0,40*i+10,20,20);
            spell.button.setBounds(20,40*i,WIDTH/2-5,40);
            i++;
        }
        spellPane.setPreferredSize(new Dimension(WIDTH/2,40*i+40));
    }


    void openGrandSpellList(){
        spellPane.removeAll();
        spellPane.setPreferredSize(new Dimension(WIDTH/2-5,100));
        spellPane.repaint();
        int i=0;
        main:
        for(spellObject spell:grandSpellList){
            for(String s:searchFilter){
                if(!spell.tags.contains(s))continue main;
            }
            spellPane.add(spell.button);
            spellPane.add(spell.known);
            spell.known.setBounds(0,40*i+10,20,20);
            spell.button.setBounds(20,40*i,WIDTH/2-5,40);
            i++;
        }
        spellPane.setPreferredSize(new Dimension(WIDTH/2,40*i+40));
    }

    String convertToGrave(String s){
        while (s.contains("\n")){
            s=s.substring(0,s.indexOf("\n"))+"~"+s.substring(s.indexOf("\n")+1);
        }
        return s;
    }
    String convertFromGrave(String s){
        while (s.contains("~")){
            s=s.substring(0,s.indexOf("~"))+"\n"+s.substring(s.indexOf("~")+1);
        }
        return s;
    }

    public void save(String path){
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path));


            out.write(characterName.getText()+"\r\n");
            out.write(level.getText()+"\r\n");
            out.write(characterClass.getText()+"\r\n");
            out.write(race.getText()+"\r\n");
            out.write(background.getText()+"\r\n");
            out.write(alignment.getText()+"\r\n");
            out.write(exp.getText()+"\r\n");
            out.write(AC.getText()+"\r\n");
            out.write(initiative.getText()+"\r\n");
            out.write(speed.getText()+"\r\n");
            out.write(maxHealth.getText()+"\r\n");
            out.write(currentHealth.getText()+"\r\n");
            out.write(tempHealth.getText()+"\r\n");
            out.write(hitDiceTotal.getText()+"\r\n");
            out.write(hitDiceUsed.getText()+"\r\n");
            out.write(attackBonus.getText()+"\r\n");
            out.write(saveDC.getText()+"\r\n");
            out.write(cp.getText()+"\r\n");
            out.write(sp.getText()+"\r\n");
            out.write(ep.getText()+"\r\n");
            out.write(gp.getText()+"\r\n");
            out.write(pp.getText()+"\r\n");
            out.write(convertToGrave(inven.getText())+"\r\n");
            out.write(STR.getText()+"\r\n");
            out.write(DEX.getText()+"\r\n");
            out.write(CON.getText()+"\r\n");
            out.write(INT.getText()+"\r\n");
            out.write(WIS.getText()+"\r\n");
            out.write(CHA.getText()+"\r\n");

            out.write(w1.name+"\r\n"+w1.extra+"\r\n"+convertToGrave(w1.description)+"\r\n");
            out.write(w2.name+"\r\n"+w2.extra+"\r\n"+convertToGrave(w2.description)+"\r\n");
            out.write(w3.name+"\r\n"+w3.extra+"\r\n"+convertToGrave(w3.description)+"\r\n");
            out.write(w4.name+"\r\n"+w4.extra+"\r\n"+convertToGrave(w4.description)+"\r\n");
            out.write(headItem.name+"\r\n"+headItem.extra+"\r\n"+convertToGrave(headItem.description)+"\r\n");
            out.write(amuletItem.name+"\r\n"+amuletItem.extra+"\r\n"+convertToGrave(amuletItem.description)+"\r\n");
            out.write(cloakItem.name+"\r\n"+cloakItem.extra+"\r\n"+convertToGrave(cloakItem.description)+"\r\n");
            out.write(armourItem.name+"\r\n"+armourItem.extra+"\r\n"+convertToGrave(armourItem.description)+"\r\n");
            out.write(handsItem.name+"\r\n"+handsItem.extra+"\r\n"+convertToGrave(handsItem.description)+"\r\n");
            out.write(ring1Item.name+"\r\n"+ring1Item.extra+"\r\n"+convertToGrave(ring1Item.description)+"\r\n");
            out.write(ring2Item.name+"\r\n"+ring2Item.extra+"\r\n"+convertToGrave(ring2Item.description)+"\r\n");
            out.write(beltItem.name+"\r\n"+beltItem.extra+"\r\n"+convertToGrave(beltItem.description)+"\r\n");
            out.write(bootsItem.name+"\r\n"+bootsItem.extra+"\r\n"+convertToGrave(bootsItem.description)+"\r\n");

            out.write(strSave.isSelected()+"\r\n");
            out.write(Athletics.isSelected()+"\r\n" +Athletics.expertise.isSelected()+"\r\n");
            out.write(dexSave.isSelected()+"\r\n");
            out.write(Acrobatics.isSelected()+"\r\n" +Acrobatics.expertise.isSelected()+"\r\n");
            out.write(Sleight.isSelected()+"\r\n" +Sleight.expertise.isSelected()+"\r\n");
            out.write(Stealth.isSelected()+"\r\n" +Stealth.expertise.isSelected()+"\r\n");
            out.write(conSave.isSelected()+"\r\n");
            out.write(intSave.isSelected()+"\r\n");
            out.write(Arcana.isSelected()+"\r\n" +Arcana.expertise.isSelected()+"\r\n");
            out.write(History.isSelected()+"\r\n" +History.expertise.isSelected()+"\r\n");
            out.write(Investigation.isSelected()+"\r\n" +Investigation.expertise.isSelected()+"\r\n");
            out.write(Nature.isSelected()+"\r\n" +Nature.expertise.isSelected()+"\r\n");
            out.write(Religion.isSelected()+"\r\n" +Religion.expertise.isSelected()+"\r\n");
            out.write(wisSave.isSelected()+"\r\n");
            out.write(Animal.isSelected()+"\r\n" +Animal.expertise.isSelected()+"\r\n");
            out.write(Insight.isSelected()+"\r\n" +Insight.expertise.isSelected()+"\r\n");
            out.write(Medicine.isSelected()+"\r\n" +Medicine.expertise.isSelected()+"\r\n");
            out.write(Perception.isSelected()+"\r\n" +Perception.expertise.isSelected()+"\r\n");
            out.write(Survival.isSelected()+"\r\n" +Survival.expertise.isSelected()+"\r\n");
            out.write(chaSave.isSelected()+"\r\n");
            out.write(Deception.isSelected()+"\r\n" +Deception.expertise.isSelected()+"\r\n");
            out.write(Intimidation.isSelected()+"\r\n" +Intimidation.expertise.isSelected()+"\r\n");
            out.write(Performance.isSelected()+"\r\n" +Performance.expertise.isSelected()+"\r\n");
            out.write(Persuasion.isSelected()+"\r\n" +Persuasion.expertise.isSelected()+"\r\n");
            out.write(light.isSelected()+"\r\n");
            out.write(medium.isSelected()+"\r\n");
            out.write(heavy.isSelected()+"\r\n");
            out.write(simple.isSelected()+"\r\n");
            out.write(martial.isSelected()+"\r\n");
            out.write(shield.isSelected()+"\r\n");

            if(fullCaster.isSelected())out.write("Full:");
            else if(halfCaster.isSelected())out.write("Half:");
            else if(thirdCaster.isSelected())out.write("Third:");
            else if(pactCaster.isSelected())out.write("Pact:"+lvl1.getText());

            for (Component c : spellSlotFrame.getContentPane().getComponents()){
                if(c instanceof JCheckBox && ((JCheckBox) c).getText().equals("")){
                    if (((JCheckBox) c).isSelected())out.write("1");
                    else out.write("0");
                }
            }
            out.write("\r\n");

            out.write(convertToGrave(otherProficiencies.getText())+"\r\n");
            out.write(convertToGrave(languages.getText())+"\r\n");
            out.write(convertToGrave(racialTraits.getText())+"\r\n");
            for(spellObject spell:preparedSpells){
                out.write(spell.name+"\r\n"+"true"+"\r\n");
            }
            for(spellObject spell:spellBook){
                out.write(spell.name+"\r\n"+"false"+"\r\n");
            }
            for(JTextArea ability:abilities){
                out.write("Ability"+(abilities.indexOf(ability)+1)+":"+convertToGrave(ability.getText())+"\r\n");
            }
            out.write(convertToGrave(notes.getText())+"\r\n");

            out.close();

        }catch (Exception e){System.out.print("Error Saving");}
    }

    public void load(){
        try {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File("D&D Character Files"));
            fc.showOpenDialog(null);
            BufferedReader in = new BufferedReader(new FileReader(fc.getSelectedFile().getAbsolutePath()));
            lastFile = fc.getSelectedFile().getAbsolutePath();

            characterName.setText(in.readLine());
            level.setText(in.readLine());
            characterClass.setText(in.readLine());
            race.setText(in.readLine());
            background.setText(in.readLine());
            alignment.setText(in.readLine());
            exp.setText(in.readLine());
            AC.setText(in.readLine());
            initiative.setText(in.readLine());
            speed.setText(in.readLine());
            maxHealth.setText(in.readLine());
            currentHealth.setText(in.readLine());
            tempHealth.setText(in.readLine());
            hitDiceTotal.setText(in.readLine());
            hitDiceUsed.setText(in.readLine());
            attackBonus.setText(in.readLine());
            saveDC.setText(in.readLine());
            cp.setText(in.readLine());
            sp.setText(in.readLine());
            ep.setText(in.readLine());
            gp.setText(in.readLine());
            pp.setText(in.readLine());
            inven.setText(convertFromGrave(in.readLine()));

            STR.setText(in.readLine());
            DEX.setText(in.readLine());
            CON.setText(in.readLine());
            INT.setText(in.readLine());
            WIS.setText(in.readLine());
            CHA.setText(in.readLine());

            w1= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            weapon1.setText(w1.name);
            w2= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            weapon2.setText(w2.name);
            w3= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            weapon3.setText(w3.name);
            w4= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            weapon4.setText(w4.name);
            headItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            head.setText(headItem.name);
            amuletItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            amulet.setText(amuletItem.name);
            cloakItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            cloak.setText(cloakItem.name);
            armourItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            armour.setText(armourItem.name);
            handsItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            hands.setText(handsItem.name);
            ring1Item= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            ring1.setText(ring1Item.name);
            ring2Item= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            ring2.setText(ring2Item.name);
            beltItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            belt.setText(beltItem.name);
            bootsItem= new item(in.readLine(),in.readLine(),convertFromGrave(in.readLine()));
            boots.setText(bootsItem.name);

            strSave.setSelected(Boolean.parseBoolean(in.readLine()));
            Athletics.setSelected(Boolean.parseBoolean(in.readLine()));
            Athletics.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            dexSave.setSelected(Boolean.parseBoolean(in.readLine()));
            Acrobatics.setSelected(Boolean.parseBoolean(in.readLine()));
            Acrobatics.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Sleight.setSelected(Boolean.parseBoolean(in.readLine()));
            Sleight.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Stealth.setSelected(Boolean.parseBoolean(in.readLine()));
            Stealth.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            conSave.setSelected(Boolean.parseBoolean(in.readLine()));
            intSave.setSelected(Boolean.parseBoolean(in.readLine()));
            Arcana.setSelected(Boolean.parseBoolean(in.readLine()));
            Arcana.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            History.setSelected(Boolean.parseBoolean(in.readLine()));
            History.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Investigation.setSelected(Boolean.parseBoolean(in.readLine()));
            Investigation.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Nature.setSelected(Boolean.parseBoolean(in.readLine()));
            Nature.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Religion.setSelected(Boolean.parseBoolean(in.readLine()));
            Religion.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            wisSave.setSelected(Boolean.parseBoolean(in.readLine()));
            Animal.setSelected(Boolean.parseBoolean(in.readLine()));
            Animal.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Insight.setSelected(Boolean.parseBoolean(in.readLine()));
            Insight.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Medicine.setSelected(Boolean.parseBoolean(in.readLine()));
            Medicine.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Perception.setSelected(Boolean.parseBoolean(in.readLine()));
            Perception.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Survival.setSelected(Boolean.parseBoolean(in.readLine()));
            Survival.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            chaSave.setSelected(Boolean.parseBoolean(in.readLine()));
            Deception.setSelected(Boolean.parseBoolean(in.readLine()));
            Deception.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Intimidation.setSelected(Boolean.parseBoolean(in.readLine()));
            Intimidation.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Performance.setSelected(Boolean.parseBoolean(in.readLine()));
            Performance.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            Persuasion.setSelected(Boolean.parseBoolean(in.readLine()));
            Persuasion.expertise.setSelected(Boolean.parseBoolean(in.readLine()));
            light.setSelected(Boolean.parseBoolean(in.readLine()));
            medium.setSelected(Boolean.parseBoolean(in.readLine()));
            heavy.setSelected(Boolean.parseBoolean(in.readLine()));
            simple.setSelected(Boolean.parseBoolean(in.readLine()));
            martial.setSelected(Boolean.parseBoolean(in.readLine()));
            shield.setSelected(Boolean.parseBoolean(in.readLine()));

            String s = in.readLine();
            casterUpdate(s.substring(0,s.indexOf(":")));
            if(s.substring(0,s.indexOf(":")).equals("Pact")){
                s=s.substring(s.indexOf(":")+1);
                lvl1.setText(s.substring(0,1));
                s=s.substring(1);
                for(Component c: spellSlotFrame.getContentPane().getComponents()){
                    if (c instanceof JCheckBox && ((JCheckBox) c).getText().equals("")) {
                        if (s.substring(0, 1).equals("1")) ((JCheckBox) c).setSelected(true);
                        s = s.substring(1);
                    }
                }
            }else{
                s=s.substring(s.indexOf(":")+1);
                for(Component c: spellSlotFrame.getContentPane().getComponents()){
                    if (c instanceof JCheckBox && ((JCheckBox) c).getText().equals("")) {
                        if (s.substring(0, 1).equals("1")) ((JCheckBox) c).setSelected(true);
                        s = s.substring(1);
                    }
                }
            }


            otherProficiencies.setText(convertFromGrave(in.readLine()));
            languages.setText(convertFromGrave(in.readLine()));
            racialTraits.setText(convertFromGrave(in.readLine()));

            //Get spellObject list for character
            spellBook= new ArrayList<>();
            preparedSpells= new ArrayList<>();
            s=in.readLine();
            while(!s.contains("Ability1")){
                for(spellObject spell:grandSpellList){
                    if(spell.name.equals(s)){
                        if(spell.spellLvl==0){
                            preparedSpells.add(spell);
                            in.readLine();
                        }
                        else {
                            s=in.readLine();
                            if(Boolean.parseBoolean(s)){
                                preparedSpells.add(spell);
                                spell.prepared.setSelected(true);
                            }else {
                                spellBook.add(spell);
                                spell.prepared.setSelected(false);
                            }
                        }
                        break;
                    }
                }
                s=in.readLine();
            }
            for(int i=0;i<20;i++){
                String a=s.substring(s.indexOf(":")+1);
                abilities.get(i).setText(convertFromGrave(a));
                s=in.readLine();
            }
            notes.setText(convertFromGrave(s));
            update();
            //openGrandSpellList();



        }catch (Exception e){System.out.print("Error loading");}


    }

    void addSpell(){
        start.requestFocus();
        /*spellOut+=JOptionPane.showInputDialog("Name")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("SpellLvl")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("Cast Time")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("Duration")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("Range")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("components")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("description")+"\r\n";
        spellOut+=JOptionPane.showInputDialog("tags")+"\r\n";*/
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        this.requestFocus();
        update();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}