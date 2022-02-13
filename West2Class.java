/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author arunkumar
 */
package W2C;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JOptionPane;

public class West2Class extends JPanel {

    public West2Class() {

        String[] wscale = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
        String[] classnotes = {"S", "r", "R", "g", "G", "m", "M", "P", "d", "D", "n", "N"};

        JLabel label = new JLabel("Western Notes");
        label.setBounds(10, 15, 500, 27);

        JLabel labelS = new JLabel("Select Scale");
        labelS.setBounds(230, 25, 98, 27);

        JComboBox<String> jComboBox = new JComboBox<>(wscale);
        jComboBox.setBounds(240, 45, 48, 27);
        jComboBox.setSelectedIndex(3);

        final JTextArea txt = new JTextArea("", 10, 10);
        txt.setBounds(40, 80, 400, 200);
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        setLayout(null);

        JLabel labelc = new JLabel("Classical notes");
        labelc.setBounds(10, 280, 500, 27);

        final JTextArea txtc = new JTextArea("", 10, 10);
        txtc.setBounds(40, 310, 400, 200);
        txtc.setLineWrap(true);
        txtc.setWrapStyleWord(true);
        setLayout(null);
        JLabel label2 = new JLabel("song title (saves a text file)");
        label2.setBounds(10, 510, 500, 27);

        final JTextArea txt2 = new JTextArea("", 1, 1);
        txt2.setBounds(40, 535, 400, 30);
        txt2.setLineWrap(true);
        txt2.setWrapStyleWord(true);

        JButton button = new JButton("convert");
        button.setBounds(210, 575, 98, 27);

        add(txt);
        add(label);
        add(txtc);
        add(labelc);
        add(labelS);
        add(txt2);
        txt2.append("notes");
        add(label2);
        add(button);
        add(jComboBox);

        txt2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                txt2.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("Lost Focus");
            }
        });

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String lines[];
                String key;
                int i, j = 99, k;
                int no = 0, te = 0;
                char c, c2;
                BufferedWriter bw = null;
                txtc.setText("");
                String scale = "" + jComboBox.getSelectedItem();

                int iS = 0;

                int tmp = 99;

                iS = Arrays.asList(wscale).indexOf(scale);
             

                HashMap<String, String> map = new HashMap<>();

                for (no = iS; no < 12 && no < tmp; no++, te++) {

                    map.put(wscale[no], classnotes[te]);
                    if (no == 11) {

                        tmp = iS;
                        no = -1;
                    }

                }
           

                try {
                    String wnotes = txt.getText();
                    if (wnotes.equals("") == true) {
                        String message = "Please enter notes and then click :)";
                        JOptionPane.showMessageDialog(new JFrame(), message, "Wake up!", JOptionPane.ERROR_MESSAGE);
                    } else {

                        File f;
                        if (txt2.getText().equalsIgnoreCase("") || txt2.getText().equals("notes")) {
                            f = new File("notes.txt");
                        } else {
                            f = new File(txt2.getText() + ".txt");
                        }

                        bw = new BufferedWriter(new FileWriter(f));
                        bw.newLine();
                        bw.write("Notes for the song : " + txt2.getText());
                        bw.newLine();
                        bw.write("---------------------------------------");
                        bw.newLine();
                        bw.write("western notes");

                        bw.newLine();
                        bw.newLine();
                        bw.write(wnotes);
                        bw.newLine();
                        bw.newLine();
                        bw.write("classical notes");
                        bw.newLine();

                        lines = wnotes.split("\\r?\\n");
                        //take lines 

                        for (i = 0; i < lines.length; i++) {

                            StringTokenizer st = new StringTokenizer(lines[i], " ", false);
                            bw.newLine();
                            txtc.append("\n");
                            while (st.hasMoreTokens()) {
                                bw.write("  ");
                                txtc.append(" ");
                                String note = st.nextToken();

                                for (k = 0, j = 1; j < note.length(); j++, k++) {
                                    //process each token for syllable    

                                    c = note.charAt(k);
                                    key = Character.toString(c);
                                    key = key.toUpperCase();

                                    c2 = note.charAt(j);

                                    if (c2 == '#') {
                                        bw.write(map.get(key + "#"));
                                        txtc.append(map.get(key + "#"));
                                    } else if (c != '#') {
                                        bw.write(map.get(key));
                                        txtc.append(map.get(key));
                                    }

                                }

                                c = note.charAt(j - 1);

                                if (c != '#' && j != 99) {

                                    key = Character.toString(c);
                                    key = key.toUpperCase();

                                    bw.write(map.get(key));
                                    txtc.append(map.get(key));
                                }

                            }
                        }
                        bw.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(West2Class.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(West2Class.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Western to Classical Notes Generator");
        frame.getContentPane().add(new West2Class());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
