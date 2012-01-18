/**Spela 21 spel
 * Innehåller klasserna Spela21, GameOn, TryckMig, Stanna, Utbetalning och kort
 * man lägger in en viss summa pengar 
 * och lägger sats och spelar spelet.
 * Author Sam Almendwi-företag (The Daily Solutions Sweden)
 * All kod som tillhör BlackJackGUI, är fri för granskning och användning, 
 * vill man ändra på koden måste man få ett godkännande från mig personligen 
 * kontaktas via: sam.almen@gmail.com
 */

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.*;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class Spela21 extends JFrame{
   int kash = 0;
   int nuSats = 0;
   ArrayList<Kort> kortLek;
   Kort[] spelareKort = new Kort[10];
   Kort[] datornsKort = new Kort[10];
   private JButton buttonSlå, buttonAvstå, buttonDela, buttonUtbetalning;
   private JPanel spelarePanel, datornsPanel, spelareKortPanel;
   private JLabel labelPengar, labelSats;
   BufferedImage img;
   final int kortBred = 79;
   final int kortHöjd = 123;
   boolean spelaPå = true;

    public Spela21() throws FileNotFoundException, IOException {

       setTitle("Spela21");
       setSize(800,600);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLayout(new BorderLayout());
       setVisible(true);
       underLag();
       spelInit();
       repaint();
       getContentPane().setBackground(Color.GREEN);
       String AudioFile = "junkiexl.wav";
        InputStream in = new FileInputStream(AudioFile);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
    }


        @Override
    public void paint(Graphics g) {

       super.paint(g);
       g.drawString("Datorns hand", 20, 80);
       g.drawString ("Summa: " + getDatornsSumma(), 120, 80);
       g.drawString("Din hand", 20, 240);
       g.drawString("Summa: " + getSpelareSumma(), 90, 240);
       int x1 = 10;
       int y1 = 250;
       int x2 = x1 + kortBred;
       int y2 = y1 + kortHöjd;
       int dx1 = 10;
       int dy1 = 100;
       int dx2 = dx1 + kortBred;
       int dy2 = dy1 + kortHöjd;

       // Skapar datorns kort
       for(int i = 1; i<=getNumDatornsKort(); i++){
            g.drawImage(img, dx1+(kortBred *(i-1)), dy1, dx2+(kortBred*(i-1)), dy2, datornsKort[i-1].getX(),
            datornsKort[i-1].getY(), datornsKort[i-1].getX()+kortBred , datornsKort[i-1].getY() + kortHöjd,null);
       }

       // Skapar spelarens kort
       for(int i = 1; i<=getNumSpelareKort(); i++){
          g.drawImage(img, x1+(kortBred *(i-1)), y1, x2 + (kortBred *(i-1)) , y2, spelareKort[i-1].getX(),
             spelareKort[i-1].getY(), spelareKort[i-1].getX() + kortBred ,spelareKort[i-1].getY() + kortHöjd,null);
       }
    }

    public int getNumSpelareKort(){

          int numKort = 0;
          while(spelareKort[numKort]!=null) {
                numKort++;
            }
          return numKort;
    }

    public int getNumDatornsKort() {
       int numKort = 0;

       while(datornsKort[numKort]!= null) {
                numKort++;
            }
       return numKort;
    }

    public int getSpelareSumma(){

        int numKort = 0;
        int summa = 0;
        int kortValör = 0;
        while (spelareKort[numKort] != null) {
            kortValör = spelareKort[numKort].getValör();
            if (kortValör == 1 && summa <= 10) {
                kortValör = 11;
            } else if (kortValör == 11) {
                kortValör = 10;
            } else if (kortValör == 12) {
                kortValör = 10;
            } else if (kortValör == 13) {
                kortValör = 10;
            }
            summa += kortValör;
            numKort++;
        }
        return summa;
    }

    public int getDatornsSumma(){

        int numKort = 0;
        int summa = 0;
        int kortValör = 0;
        while (datornsKort[numKort] != null) {
            kortValör = datornsKort[numKort].getValör();
            if (kortValör == 1 && summa <= 10) {
                kortValör = 11;
            } else if (kortValör == 11) {
                kortValör = 10;
            } else if (kortValör == 12) {
                kortValör = 10;
            } else if (kortValör == 13) {
                kortValör = 10;
            }
            summa += kortValör;
            numKort++;
        }
        return summa;
    }

    public int setSats(int sats){

        try {
            sats = Integer.parseInt(JOptionPane.showInputDialog("Ange sats: "));
        } catch (NumberFormatException ex) {
            sats = 0;
        }
        if (sats <= 0 || sats > kash) {
            sats = setSats(0);
        } else {
            labelSats.setText(Integer.toString(sats));
        }
        return sats;
    }

    private void underLag(){
        
        // Övre knappar

        buttonDela = new JButton("Dela!");
        buttonUtbetalning = new JButton("Utbetalning");

        // Datorns panel

        datornsPanel = new JPanel();
        datornsPanel.setBackground(Color.gray);
        datornsPanel.add(buttonUtbetalning);
        datornsPanel.add(buttonDela);
        buttonDela.addActionListener(new GameOn());
        buttonUtbetalning.addActionListener(new Utbetalning());
        
        // Nedre knappar
        buttonSlå = new JButton("Mera kort!");
        buttonAvstå = new JButton("Avstå");

        // Spelarens panel

        spelareKortPanel = new JPanel();
        spelarePanel = new JPanel(new GridLayout(3, 2));
        Border money = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Dina pengar");
        spelarePanel.setBorder(money);
        spelarePanel.setBackground(Color.gray);        
        labelSats = new JLabel("0");
        labelPengar = new JLabel("0");
        spelarePanel.add(new JLabel("Pengar totalt: "));
        spelarePanel.add(labelPengar);
        spelarePanel.add(new JLabel("Din sats: "));
        spelarePanel.add(labelSats);
        spelarePanel.add(buttonSlå);
        spelarePanel.add(buttonAvstå);
        buttonSlå.addActionListener(new TryckMig());
        buttonAvstå.addActionListener(new Stanna());
        add(datornsPanel, BorderLayout.NORTH);
        add(spelarePanel, BorderLayout.SOUTH);       
        

        try {
            img = ImageIO.read(new File("Kort.png"));
        } catch (IOException e) {
        }
        kash = getStartKapital();
        labelPengar.setText(Integer.toString(kash));
    }

    
    // Ny runda
    
    private void spelInit(){

        nuSats = setSats(0);
        spelareKort = new Kort[10];
        datornsKort = new Kort[10];
        kortLek = new ArrayList<Kort>(52);

        for (int i = 0; i < 52; i++) {
            kortLek.add(i, new Kort(i % 4, (i % 13) + 1));
        }

    // Båda spelarna får 2 kort var
        
        spelareKort[0] = draRandomKort();
        spelareKort[1] = draRandomKort();
        datornsKort[0] = draRandomKort();
        datornsKort[1] = draRandomKort();
        repaint();
    }


    public int getStartKapital(){

        int kapital = 0;
        while (kapital <= 0) {
            try {
                JOptionPane.showMessageDialog(null, "Instruktioner:"
                        + "\n-Starta med ett kapital." + "\n-Ange hur mycket du vill satsa per spel."
                        + "\n-Tryck på Mera kort för att få mera kort eller avstå för att få resultatet"
                        + "\n-När spelet är slut tryck på dela för att börja om"
                        + "-Tryck på Utbetalning för att avsluta spelet och få pengarna som är kvar."
                        + "-Lycka till");
                kapital = Integer.parseInt(JOptionPane.showInputDialog("Hur mycket pengar vill du starta med: "));
                
            } 
            catch (NumberFormatException r) {
                kapital = 0;
            }
        }
        return kapital;
    }

    public Kort draRandomKort(){

        Random rand = new Random();
        return kortLek.remove(rand.nextInt(kortLek.size()));
    }

    //Dela, skapa om kortlek och ge 2 kort för varje spelare

    class GameOn implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            spelaPå = true;
            spelInit();
        }
    }

    /** Tryck mig knapp
    * skapar slump kort från kortlek
    * Kollar den nya summan
    * om 21 spelare vinner
    * om mer förlorar spelaren
    * om mindre än 21 spelare kan dra fler kort
    */

    class TryckMig implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            if (spelaPå) {
                spelareKort[getNumSpelareKort()] = draRandomKort();
                repaint();
                if (getSpelareSumma() > 21) {
    // Du förlorar
                    JOptionPane.showMessageDialog(null, "Du förlorade!");
                    spelaPå = false;
                    kash -= nuSats;
                    labelPengar.setText(Integer.toString(kash));
                }
                if (kash <= 0) {
                    JOptionPane.showMessageDialog(null, "Du har inga pengar kvar!");
                    System.exit(0);
                }
            }
        }
    }

    /**
    * Kollar vem är vinnaren
    */

    class Stanna implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            if (spelaPå) {
                spelaPå = false;

                // Du förlorar      
                if (getSpelareSumma() > 21) {
                    JOptionPane.showMessageDialog(null, "Du förlorade!");
                    kash -= nuSats;
                    labelPengar.setText(Integer.toString(kash));
                } 
                
                // Ai
                else {
                    while (getDatornsSumma() < 21) {
                        datornsKort[getNumDatornsKort()] = draRandomKort();
                    }

                    // Du vinner
                    if (getDatornsSumma() > 21) {

                        JOptionPane.showMessageDialog(null, "Du vann, grattis!");
                        kash += nuSats;
                        labelPengar.setText(Integer.toString(kash));
                    } else {
                        if (21 - getSpelareSumma() < 21 - getDatornsSumma()) {
                            // Du vinner
                            JOptionPane.showMessageDialog(null, "Du vann, grattis!!");
                            kash += nuSats;
                            labelPengar.setText(Integer.toString(kash));
                        } else if (getSpelareSumma() == getDatornsSumma()) {
                            JOptionPane.showMessageDialog(null, "Lika");
                        } else // Du förlorar
                        {
                            JOptionPane.showMessageDialog(null, "Du förlorade!");
                            kash -= nuSats;
                            labelPengar.setText(Integer.toString(kash));
                        }
                    }
                }
                
                repaint();
                
                if (kash <= 0) {
                    JOptionPane.showMessageDialog(null, "Du har inga pengar kvar!");
                    System.exit(0);
                }
            }
        }
    }


    class Utbetalning implements ActionListener{

        public void actionPerformed(ActionEvent ae) {
            JOptionPane.showMessageDialog(null, "Dina kvarvarande pengar efter spel är " + kash);
            System.exit(0);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        new Spela21();
    }

}