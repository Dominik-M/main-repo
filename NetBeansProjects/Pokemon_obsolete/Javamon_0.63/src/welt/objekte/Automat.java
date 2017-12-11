package welt.objekte;

import java.awt.Graphics;
import spiel.Javamon;
import spiel.OutputListener;

public class Automat extends Objekt implements OutputListener{
    private int delay;
    
    public Automat(){
      super(false);
      setName("Automat");
      delay=0;
    }
    
    public Automat(int d){
      super(false);
      delay=d;
      setName("Automat");
    }
    
    public void setDelay(int d){
        delay=d;
    }

    @Override
    public void benutzt() {
      Javamon.sout("Ein einarmiger Bandit!");
      Javamon.addListener(this);
    }

    @Override
    protected void betreten() {}

    @Override
    public void paintComponent(Graphics g, int spot) {
      int b=spiel.Spielwelt.SPOT;
      int[] farben={-14144714, -372433, -373723, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373723, -372433, -13420993, -14144458, -44242, -14142143, -14208969, -14208969, -13289664, -14208969, -14208969, -14208969, -14208969, -13289664, -14208969, -14208969, -14142143, -44242, -13420993, -14144458, -44242, -14208969, -24625, -24625, -14341331, -24625, -26169, -26169, -24625, -14341331, -24625, -24625, -14208969, -44242, -13420993, -14144458, -44242, -14144714, -331814, -331814, -14147028, -331814, -1055535, -1055535, -331814, -14147028, -331814, -331814, -14144714, -44242, -13420993, -14144458, -44242, -14144714, -2590, -2590, -14212821, -2590, -267559, -267559, -2590, -14212821, -2590, -2590, -14144714, -44242, -13420993, -14144458, -44242, -13355200, -13423563, -13423563, -12568256, -13423563, -13423307, -13423307, -13423563, -12568256, -13423563, -13423563, -13355200, -44242, -13420993, -14144458, -44242, -14208969, -13422026, -13422026, -13422026, -13422026, -13422026, -13422026, -13422026, -13422026, -13422026, -13422026, -14208969, -44242, -13420993, -14144458, -439260, -22565, -23855, -23855, -23855, -23855, -23855, -23855, -23855, -23855, -23855, -23855, -22565, -439260, -13420993, -14144714, -44242, -373723, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373466, -373723, -44242, -13420993, -13292234, -14274505, -14208969, -14142143, -13355457, -13420993, -13420993, -13420993, -13420993, -13420993, -13420993, -13355457, -14142143, -14208969, -14274505, -12568513, -14211283, -23344, -23855, -45532, -13420993, -13420993, -13420993, -13420993, -13420993, -13420993, -13420993, -13420993, -45532, -23855, -23344, -13487818, -14211283, -24888, -25399, -373466, -14142400, -44242, -373723, -373466, -373466, -373723, -44242, -14142400, -373466, -25399, -24888, -13422026, -14211283, -23344, -23855, -45532, -14207936, -45533, -22565, -23855, -23855, -22565, -45533, -14207936, -45532, -23855, -23344, -13487818, -13292234, -14274505, -14208969, -14142143, -13289921, -14142143, -14208969, -14208969, -14208969, -14208969, -14142143, -13289921, -14142143, -14208969, -14274505, -12568513, -14210250, -43987, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -44242, -43987, -13486528, -14147028, -14144714, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144458, -14144714, -13357771};
      for(int i=0;i<farben.length;i++){
        g.setColor(new java.awt.Color(farben[i]));
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
    }

    @Override
    public void ausgabeFertig() {
      if(delay<=0) Javamon.display(new spiel.Spielautomat(100));
      else Javamon.display(new spiel.Spielautomat(delay));
    }   
}