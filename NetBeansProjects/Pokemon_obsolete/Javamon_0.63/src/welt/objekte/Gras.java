package welt.objekte;

import java.awt.Graphics;
import spiel.Javamon;
import spiel.Spielwelt;

public class Gras extends Objekt{

    public Gras(){
      super(true);
      setName("Gras");
    }
    
    @Override
    public void benutzt() {
    }

    @Override
    public void betreten() {
      if((Math.random()*12)<=2)
      Javamon.starteKampf();
    }
    
    @Override
    public void paintComponent(Graphics g,int spot) {
      g.setColor(java.awt.Color.GREEN);
      int b=Spielwelt.SPOT;
      int[] farben={-2949524, -2228628, -2949524, -2949524, -2949524, -2949524,
      -2949524, -2949524, -2949524, -2228628, -2949524, -2949524, -2949524,
      -2949524, -2949524, -2949524, -3342748, -11231724, -3867044, -4915636,
      -3998124, -11231724, -3867044, -4915636, -3867044, -11231724, -3867044,
      -4915636, -3998124, -11231724, -3867044, -4915636, -9125332, -9652700,
      -10180060, -2818460, -10180060, -9652700, -10180060, -3867044, -10180060,
      -9652700, -10180060, -2818460, -10180060, -10178012, -10178012, -3867044,
      -16038652, -5439940, -14120444, -9128404, -13069052, -5439940, -16233468,
      -2818460, -16038652, -5439940, -14120444, -9128404, -13332988, -5439940,
      -16233468, -2818460, -9128404, -13069052, -9642492, -13069052, -12279292,
      -9642492, -15444988, -2163100, -9128404, -13069052, -9642492, -13069052,
      -12279292, -9642492, -15444988, -2163100, -5439940, -16233468, -9642492,
      -9642484, -15444988, -9639420, -15444988, -7015892, -5964236, -16233468,
      -9642492, -9642484, -15444988, -9639420, -15444988, -7015892, -11745788,
      -12281340, -13069052, -9639420, -14917116, -12279292, -12281340, -11088380,
      -11088380, -12281340, -13069052, -9639420, -14917116, -12279292, -12281340, 
      -11088380, -7016908, -11218428, -13332988, -13069052, -16100092, -13069052, 
      -11218428, -7542228, -7542228, -11218428, -13332988, -13069052, -16100092,
      -13069052, -11218428, -7542228, -2294148, -1376644, -1376644, -2294148,
      -1376644, -1769852, -2294148, -2949516, -2294156, -1376644, -2294148, 
      -1376644, -1376644, -1769852, -1376644, -2949516, -3342748, -11231724,
      -3867044, -4915636, -3998124, -11231724, -3867044, -4915636, -3867044,
      -11231724, -3867044, -4915636, -3998124, -11231724, -3867044, -4915636,
      -9125332, -9652700, -10180060, -2818460, -10180060, -9652700, -10180060,
      -3867044, -10180060, -10178012, -10180060, -2818460, -10180060, -10178012,
      -10178012, -3867044, -16038652, -5439940, -14120444, -9128404, -13332988,
      -5439940, -16233468, -2818460, -16038652, -5439940, -14120444, -9128404,
      -13332988, -5439940, -16233468, -2818460, -9128404, -13069052, -9642492,
      -13069052, -12279292, -9642492, -15444988, -2163100, -9128404, -13069052,
      -9642492, -13069052, -12279292, -9642492, -15444988, -2163100, -5439940,
      -16233468, -9642492, -9642484, -15444988, -9639420, -15444988, -7015892,
      -5964236, -16233468, -9642492, -9642484, -15444988, -9639420, -15444988,
      -7015892, -11745788, -12281340, -13069052, -9639420, -14917116, -12279292,
      -12281340, -11088380, -11088380, -12281340, -13069052, -9639420, -14917116,
      -12279292, -12281340, -11088380, -7016908, -11218428, -13332988, -13069052, 
      -16100092, -13069052, -10365948, -7542228, -7542228, -11218428, -13332988, 
      -13069052, -16100092, -13069052, -11218428, -7542228};
      for(int i=0;i<farben.length;i++){
        g.setColor(new java.awt.Color(farben[i]));
        g.fillRect((i%b*(spot/b)),(i/b*(spot/b)),spot/b,spot/b);
      }
    }
}