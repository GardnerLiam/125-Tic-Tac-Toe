package Root;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
/*
  @name: singleplayer
  @author: Liam Gardner
  @date: Don't know but I built and finished it in class.
 */
public class Singleplayer extends JFrame {
    ArrayList<JButton[]> btns = new ArrayList<JButton[]>();
    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel[] dimension = {new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()};
    int counter = 0;
    public Singleplayer() {

        for (int i = 0; i < 5; i++) {
            JButton[] b = new JButton[25];
            for (int j = 0; j < 25; j++) {
                JButton k = new JButton();
                k.setFont(new Font("Felix Titling", 0, 100));
                k.setName("D" + i + "B" + j + " " + 0);
                b[j] = k;
            }
            btns.add(b);
        }

        setTitle("Tic Tac Toe Singleplayer");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(3);

        panelBackground(dimension);
        setTabbedPane(tabbedPane, dimension);
        addButtons(dimension);
        buttonAction();

        add(tabbedPane);
        setVisible(true);
    }
    /*
      @name setbuttons
      @purpose sets button properties
     */
    public void setButtons(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 25; j++){
                btns.get(i)[j].setText("");
                btns.get(i)[j].setName("D"+i+"B"+j+" "+0);
                btns.get(i)[j].setEnabled(true);
            }
        }
    }
    /*
    @name panelbackground
    @params array of jpanels
    @purpose: pretty colors
     */
    public void panelBackground(JPanel[] dimension) {
        for (int i = 0; i < 5; i++) {
            dimension[i].setLayout(new GridLayout(5, 5));
        }
        dimension[0].setBackground(Color.RED);
        dimension[1].setBackground(Color.GREEN);
        dimension[2].setBackground(Color.ORANGE);
        dimension[3].setBackground(Color.MAGENTA);
        dimension[4].setBackground(Color.CYAN);
    }
    /*
      @name: settabbedpane
      @params tabbed pane, jpanel array
      @purpose: make all dimensions switchable via a tabbed pane
     */
    public void setTabbedPane(JTabbedPane tabbedPane, JPanel[] dimension) {
        tabbedPane.addTab("Dimension 1", dimension[0]);
        tabbedPane.addTab("Dimension 2", dimension[1]);
        tabbedPane.addTab("Dimension 3", dimension[2]);
        tabbedPane.addTab("Dimension 4", dimension[3]);
        tabbedPane.addTab("Dimension 5", dimension[4]);
    }
    /*
      @name: add buttons
      @params: the thing you add buttons to
      @purpose: look at @name
     */
    public void addButtons(JPanel[] dimension) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 25; j++) {
                dimension[i].add(btns.get(i)[j]);
            }
        }
    }
    /*
     @name: button action
     @purpose: make buttons work
     */
    public void buttonAction() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 25; j++) {
                btns.get(i)[j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton b = (JButton) e.getSource();
                        if (!b.getName().endsWith("0")) {
                            JOptionPane.showMessageDialog(null, "YOU CANNOT SELECT THIS!");
                        } else {
                            String newname = b.getName().substring(0, b.getName().length() - 1) + "1";
                            b.setName(newname);
                            b.setText("X");
                            makeMove();
                            counter ++;
                            if (checkWin() == 1){
                                disableAllButtons();
                                JOptionPane.showMessageDialog(null, "YOU LOSE! turns passed: " + counter);
                                Object[] opts = {"Play Again", "Quit"};
                                if (JOptionPane.showInputDialog(null, "Would you like to play again", "Computer", JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]).equals(opts[0])){
                                    JOptionPane.showMessageDialog(null, "UMU!");
                                    setButtons();
                                    counter = 0;
                                }else{
                                    System.exit(0);
                                }
                            }else if (checkWin() == 2){
                                disableAllButtons();
                                JOptionPane.showMessageDialog(null, "YOU WIN! turns passed: " + counter);
                                Object[] opts = {"Play Again", "Quit"};
                                if (JOptionPane.showInputDialog(null, "Would you like to play again", "Computer", JOptionPane.QUESTION_MESSAGE, null, opts, opts[0]).equals(opts[0])){
                                    JOptionPane.showMessageDialog(null, "UMU!");
                                    setButtons();
                                    counter = 0;
                                }else{
                                    System.exit(0);
                                }
                            }
                        }
                    }
                });
            }
        }
    }
    /*
    @name: make move
    @purpose: AI moves
     */
    public void makeMove() {
        Random r = new Random();
        int dim = r.nextInt(5);
        int ind = r.nextInt(25);
        String name = btns.get(dim)[ind].getName();
        while (!name.endsWith("0")) {
            dim = r.nextInt(5);
            ind = r.nextInt(25);
            name = btns.get(dim)[ind].getName();
        }
        String newname = name.substring(0, name.length() - 1) + "2";
        btns.get(dim)[ind].setName(newname);
        btns.get(dim)[ind].setText("O");
    }
    /*
      @name/purpose disable all buttons
     */
    public void disableAllButtons(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 25; j++){
                btns.get(i)[j].setEnabled(false);
            }
        }
    }
    /*
      @name get player
      @params button
      @purpose check what player played where
      @return: integer player
     */
    public int getPlayer(JButton b) {
        if (b.getName().endsWith("1")) {
            return 1;
        } else if (b.getName().endsWith("2")) {
            return 2;
        }
        return 0;
    }
    /*
     @name/purpose: check win
     @return player that won (0 if no one)
     */
    public int checkWin() {

        //normal 5x5 wins
        for (int i = 0; i < 5; i++) {
            //case 1
            if (getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[1]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[2]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[3]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[4]) && getPlayer(btns.get(i)[0]) != 0) {
                return getPlayer(btns.get(i)[0]);
            }
            if (getPlayer(btns.get(i)[5]) == getPlayer(btns.get(i)[6]) && getPlayer(btns.get(i)[5]) == getPlayer(btns.get(i)[7]) && getPlayer(btns.get(i)[5]) == getPlayer(btns.get(i)[8]) && getPlayer(btns.get(i)[5]) == getPlayer(btns.get(i)[9]) && getPlayer(btns.get(i)[5]) != 0) {
                return getPlayer(btns.get(i)[5]);
            }
            if (getPlayer(btns.get(i)[10]) == getPlayer(btns.get(i)[11]) && getPlayer(btns.get(i)[10]) == getPlayer(btns.get(i)[12]) && getPlayer(btns.get(i)[10]) == getPlayer(btns.get(i)[13]) && getPlayer(btns.get(i)[10]) == getPlayer(btns.get(i)[14]) && getPlayer(btns.get(i)[10]) != 0) {
                return getPlayer(btns.get(i)[10]);
            }
            if (getPlayer(btns.get(i)[15]) == getPlayer(btns.get(i)[16]) && getPlayer(btns.get(i)[15]) == getPlayer(btns.get(i)[17]) && getPlayer(btns.get(i)[15]) == getPlayer(btns.get(i)[18]) && getPlayer(btns.get(i)[15]) == getPlayer(btns.get(i)[19]) && getPlayer(btns.get(i)[15]) != 0) {
                return getPlayer(btns.get(i)[15]);
            }
            if (getPlayer(btns.get(i)[20]) == getPlayer(btns.get(i)[21]) && getPlayer(btns.get(i)[20]) == getPlayer(btns.get(i)[22]) && getPlayer(btns.get(i)[20]) == getPlayer(btns.get(i)[23]) && getPlayer(btns.get(i)[20]) == getPlayer(btns.get(i)[24]) && getPlayer(btns.get(i)[20]) != 0) {
                return getPlayer(btns.get(i)[20]);
            }
            //case 2
            for (int j = 0; j < 5; j++) {
                if (getPlayer(btns.get(i)[0+j]) == getPlayer(btns.get(i)[5+j]) && getPlayer(btns.get(i)[0+j]) == getPlayer(btns.get(i)[10+j]) && getPlayer(btns.get(i)[0+j]) == getPlayer(btns.get(i)[15+j]) && getPlayer(btns.get(i)[0+j]) == getPlayer(btns.get(i)[20+j]) && getPlayer(btns.get(i)[0+j]) != 0) {
                    return getPlayer(btns.get(i)[0+j]);
                }
            }
            //case 3
            if (getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[6]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[12]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[18]) && getPlayer(btns.get(i)[0]) == getPlayer(btns.get(i)[24]) && getPlayer(btns.get(i)[0]) != 0){
                return getPlayer(btns.get(i)[0]);
            }
            if (getPlayer(btns.get(i)[4]) == getPlayer(btns.get(i)[8]) && getPlayer(btns.get(i)[4]) == getPlayer(btns.get(i)[12]) && getPlayer(btns.get(i)[4]) == getPlayer(btns.get(i)[16]) && getPlayer(btns.get(i)[4]) == getPlayer(btns.get(i)[20]) && getPlayer(btns.get(i)[4]) != 0){
                return getPlayer(btns.get(i)[4]);
            }
        }
        //for dimensional wins
        for (int i = 0; i < 25; i++){
            if (getPlayer(btns.get(0)[i]) == getPlayer(btns.get(1)[i]) && getPlayer(btns.get(0)[i]) == getPlayer(btns.get(2)[i]) && getPlayer(btns.get(0)[i]) == getPlayer(btns.get(3)[i]) && getPlayer(btns.get(0)[i]) == getPlayer(btns.get(4)[i]) && getPlayer(btns.get(0)[i]) != 0){
                return getPlayer(btns.get(0)[i]);
            }
        }

        //DIMENSIONAL WINS ARE ONLY SAME SPOT DIFFERENT DEPTH BECAUSE IT'S TOO EASY TO WIN IF IT INCLUDES EVERYTHING ELSE
        //CURRENTLY I SPEND ABOUT 15-30 MINUTES TRYING TO GET THE BOT TO WIN THE WAY IT IS NOW...
        //I PLAYED THIS 20 TIMES, THE AVERAGE AMOUNT OF TURNS FOR THE BOT TO WIN IS 43.8
        return 0;
    }
    /*
      @please run the TICTACTOE_LG.java file
      @or the tictactoe.jar if you have that.
      @it should be in out/artifacts/TicTacToeLG/tictactoe.jar
     */
    public static void main(String[] args) {
        String a = "42";
        new Singleplayer();
    }


}
