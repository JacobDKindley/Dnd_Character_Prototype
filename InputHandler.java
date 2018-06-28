
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;

public class InputHandler extends KeyAdapter{
    public InputHandler(starter frame){
        frame.getInputMap().put(KeyStroke.getKeyStroke("I"),"openInventory");
        frame.getInputMap().put(KeyStroke.getKeyStroke("S"),"openSpellBook");
        frame.getInputMap().put(KeyStroke.getKeyStroke("G"),"openGrandSpellList");
        frame.getInputMap().put(KeyStroke.getKeyStroke("N"),"openNotes");
        frame.getInputMap().put(KeyStroke.getKeyStroke("A"),"openAbilities");


        frame.getActionMap().put("openInventory", openInventory);
        frame.getActionMap().put("openSpellBook", openSpellBook);
        frame.getActionMap().put("openGrandSpellList", openGrandSpellList);
        frame.getActionMap().put("openNotes",openNotes);
        frame.getActionMap().put("openAbilities",openAbilites);
    }
    private Action openInventory = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if(starter.invenScroller.isVisible()){
                starter.invenScroller.setVisible(false);
                starter.invenTitle.setVisible(false);
            }else {
                close();
                starter.invenScroller.setVisible(true);
                starter.invenTitle.setVisible(true);
            }
        }
    };
    private Action openSpellBook = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            starter.start.openSpellBook();
            if(starter.spellBookTitle.isVisible()){
                starter.searchSpell.setVisible(false);
                starter.spellBookTitle.setVisible(false);
                starter.spellScroller.setVisible(false);
                starter.spellSlotButton.setVisible(false);
            }else {
                close();
                starter.spellBookTitle.setVisible(true);
                starter.spellScroller.setVisible(true);
                starter.searchSpell.setVisible(true);
                starter.spellSlotButton.setVisible(true);
            }
        }
    };
    private Action openGrandSpellList = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if(starter.spellListTitle.isVisible()){
                starter.searchSpell.setVisible(false);
                starter.spellListTitle.setVisible(false);
                starter.spellScroller.setVisible(false);
                starter.spellSlotButton.setVisible(false);
            }else {
                close();
                starter.spellListTitle.setVisible(true);
                starter.start.openGrandSpellList();
                starter.spellScroller.setVisible(true);
                starter.searchSpell.setVisible(true);
                starter.spellSlotButton.setVisible(true);
            }
        }
    };
    private Action openNotes = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if(starter.noteScroller.isVisible()){
                starter.noteScroller.setVisible(false);
                starter.notesTitle.setVisible(false);
            }else {
                close();
                starter.noteScroller.setVisible(true);
                starter.notesTitle.setVisible(true);
            }
        }
    };
    private Action openAbilites = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            if(starter.abilityPane.isVisible()){
                starter.abilityPane.setVisible(false);
                starter.abilitiesTitle.setVisible(false);
            }else {
                close();
                starter.abilityPane.setVisible(true);
                starter.abilitiesTitle.setVisible(true);
            }
        }
    };
    public void close(){
        starter.spellScroller.setVisible(false);
        starter.searchSpell.setVisible(false);
        starter.spellListTitle.setVisible(false);
        starter.spellBookTitle.setVisible(false);
        starter.invenScroller.setVisible(false);
        starter.invenTitle.setVisible(false);
        starter.noteScroller.setVisible(false);
        starter.notesTitle.setVisible(false);
        starter.abilityPane.setVisible(false);
        starter.abilitiesTitle.setVisible(false);
    }


}
