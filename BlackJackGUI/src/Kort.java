/**Kort.java klass
 * Author Sam Almendwi-företag (The Daily Solutions Sweden)
 * All kod som tillhör BlackJackGUI, är fri för granskning och användning, 
 * vill man ändra på koden måste man få ett godkännande från mig personligen 
 * kontaktas via: sam.almen@gmail.com
 */

public class Kort {
   private int kortValör;
   private int kortTyp;
   public Kort()
   {
      kortValör = 0;
      kortTyp = 0;
   }
   public Kort(int typ, int val)
   {
      kortValör = val;
      kortTyp = typ;
   }
   public void setValör(int val)
   {
      kortValör = val;
   }
   public void setTyp(int typ)
   {
      kortTyp = typ;
   }
   public int getValör()
   {
      return kortValör;
   }
   public int getTyp()
   {
      return kortTyp;
   }
   public int getY()
   {
      return 123*kortTyp;
   }
   public int getX()
   {
      return 79*(kortValör-1);
   }
}