
import java.awt.*;
    /*import java.awt.BorderLayout;
    import java.awt.Color;
    import java.awt.Font;
    import java.awt.GridLayout;*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
    /*import javax.swing.ImageIcon;
    import javax.swing.JButton;
    import javax.swing.JFrame;
    import javax.swing.JLabel;
    import javax.swing.JOptionPane;
    import javax.swing.JPanel;
    import javax.swing.SwingConstants;*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sumit Kumar
 */
public class TicTacToeGUI extends JFrame implements ActionListener {

    JLabel heading;     
    JLabel clock;
    JPanel mainpanel;

    int gameChance[] = {2, 2, 2, 2, 2, 2, 2, 2, 2}; // give info., buttons are already clicked or not.
                            // if clicked then stores which player was clicked
    
    int activePlayer = 0;       // player '0' play first

    // winning possible position 
    int[][] wpp = {
        {0, 1, 2},
        {3, 4, 5},
        {6, 7, 8},
        {0, 3, 6},
        {1, 4, 7},
        {2, 5, 8},
        {0, 4, 8},
        {2, 4, 6}
    };

    int winner = 2;     // it means no any wins the game...

    boolean gameOver = false;   // check for game over or not
    
    int c = 0;      // count numbers of success clicks

    Font font = new Font("", Font.BOLD, 40);    // set font for all text

    public TicTacToeGUI() {

        setTitle("Tic Tac Toe");
        setSize(700, 600);  // set default JFrame dimension

        ImageIcon img_icon = new ImageIcon("src/img/TicTacToc.jpg");    // put image icon 

        setIconImage(img_icon.getImage());  //set icon for title

        // when close your jframe then it also close program
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //call method GUI for set gui components
        setGUI();

        setVisible(true);

    }

    private void setGUI() {

        // add background color
        this.getContentPane().setBackground(Color.blue);  // you can also give hexa value of your color code Color.decode("#....") 

        // set layout as border layout
        this.setLayout(new BorderLayout());

                // ------------ set title in north section ---------------
        heading = new JLabel("Tic Tac Toe");
        heading.setFont(font);
        
        heading.setForeground(Color.white);     // set forground color for all texts

        heading.setHorizontalAlignment(SwingConstants.CENTER);      // heading (JLabel) set as center
        this.add(heading, BorderLayout.NORTH);  // everything done... so add this component in layout

                // --------------  set clock in south section  -----------------
        clock = new JLabel();
        clock.setFont(font);

        clock.setForeground(Color.white);   // set forground color

        clock.setHorizontalAlignment(SwingConstants.CENTER);    // set as center
        this.add(clock, BorderLayout.SOUTH);    // everything done... so add this component in layout

        // for current time use thread...
        Thread t = new Thread() {
            @Override
            public void run()   // override run method
            {
                try {
                    while (true) {
                        // fetch current date and time
                        String datetime = new Date().toLocaleString();  // new Date() return current date & time so converting into string
                        clock.setText(datetime);    // set text into clock (JLabel)
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();      // thread start

        // ---------- Now add panel and insert into all elements inside this panel -----------
        
        mainpanel = new JPanel();   // create JPanel's object

        mainpanel.setLayout(new GridLayout(3, 3));  // grid layout for 3 rows and 3 columns (for set, 9 buttons)

        // set 9 buttons
        for (int i = 0; i < 9; i++) 
        {
            JButton btn = new JButton();
            btn.setFont(font);

            btn.setBackground(Color.decode("#90caf9"));     // set background color each & every button

            mainpanel.add(btn);     // add button in JPanel

            btn.addActionListener(this);    //now add listener for all buttons
            btn.setName(String.valueOf(i));  // uniqely identified of button with it's name
        }

        this.add(mainpanel, BorderLayout.CENTER);   // now add JPanel (mainpanel)

    }

    // listener code here, for each and every button
    @Override
    public void actionPerformed(ActionEvent ae) {
        
        int s;  // stores the value which is returning by confirm box

                // -------------  checked gameOver or not  -----------------------
        if (gameOver)  
        {
            // game over message
            JOptionPane.showMessageDialog(null, "     Game Already Over...\n Player " + (winner+1) + " has won the game");

            // play more message
            s = JOptionPane.showConfirmDialog(this, " Do you want to play more???");

            //method call for checks user want to play again or not
            isRunAgain(s);

        }
            // ---------------- if gameOver== false then check for game draw ----------------
        else if(gameOver == false && c==9)      // check success clicks 9 and no any player winner then game must be drawn
        {
            // call method for game draw
            isDraw(c, gameOver);
        }
        else    // no any winner found, even not game drawn. so button can be click
        {
            
            // variable 'ae' gives the object which will clicks the button
            JButton currentButton = (JButton) ae.getSource();   //actionPerformed(ActionEvent ae) it gives object so typecast required

            int selectButton = Integer.parseInt(currentButton.getName());  // it returns name of the clicked button

            if (gameChance[selectButton] == 2) // ==2 means, button clicked as first time
            {
                
                c++;    // increase the value of success click
                
                if (activePlayer == 0)      // for player zero
                {
                    currentButton.setIcon(new ImageIcon("img/zero.jpg"));   // set image on button
                    gameChance[selectButton] = 0;   // set player zero clicks this button

                    // now give chance to next player 
                    activePlayer = 1;
                }
                else
                {
                    currentButton.setIcon(new ImageIcon("img/x.jpg"));  // set image on button
                    gameChance[selectButton] = 1;   // set player zero clicks this button

                    // now give chance to next player 
                    activePlayer = 0;
                }
            } 
            else    // button are already clicked
                JOptionPane.showMessageDialog(null, "Position already occupied...");
            

                    // --------------------- check winner by hashing technique  --------------------------
            // for taking each and every row from possible winning position array
            for (int[] temp : wpp) 
            {
                // gameChance[temp[0]] != 2)    for checking button are clicked or not by the player
                                        // because initial value of gameChance array is 2 for all element
                // in other two condition, checks all three buttons are clicked by same player or not
                if ((gameChance[temp[0]] != 2) && (gameChance[temp[0]] == gameChance[temp[1]]) && (gameChance[temp[1]] == gameChance[temp[2]])) 
                {
                    winner = gameChance[temp[0]];       // set for winning player

                    //wining message
                    JOptionPane.showMessageDialog(null, "Player " + (winner+1) + " has won the game");

                    gameOver = true;    // winner find so gaveOver...

                    break;  // break for each loop
                }
            }

            // we can write also this before 'break', without using if condition
            if (gameOver) // after wining message this code will execute...   because "gameOver==true"
            {
                // play more message
                s = JOptionPane.showConfirmDialog(this, "Do you want to play more???");

                //function for checks user want to play again or not
                isRunAgain(s);
            }
            
            // ---------------- game draw ----------------
            isDraw(c, gameOver);    // if button count 9 and game win by player

        }

    }

    // method for checking player wants to play again or not...
    public void isRunAgain(int k) 
    {
        if (k == 0) 
        {
            this.setVisible(false);

            // open new instance
            new TicTacToeGUI();
        } 
        else if (k == 1)
        {
            System.exit(0);
        }
    }
    
    // method for checking game draw or not
    public void isDraw(int c, boolean f)
    {
        if(c == 9 && f == false)
        {
            // game over message
            JOptionPane.showMessageDialog(null, "Game Draw!!!");

            // play more message
            int m = JOptionPane.showConfirmDialog(this, " Do you want to play more???");

            //function for checks user want to play more or not
            isRunAgain(m);

        }
    }

}
