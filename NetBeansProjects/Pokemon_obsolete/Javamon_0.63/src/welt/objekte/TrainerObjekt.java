package welt.objekte;

import spiel.Javamon;
import spiel.OutputListener;
import spiel.Trainer;

public class TrainerObjekt extends Person implements OutputListener{
    private spiel.Trainer trainer;
    private int sichtweite;

    public TrainerObjekt(){
        this(new Trainer("Trainer",100,Trainer.DOWN));
    }
    
    public TrainerObjekt(Trainer t){
      super();
      trainer=t;
      richtung=t.getRichtung();
      setColor(java.awt.Color.ORANGE);
      setSkinColor(java.awt.Color.WHITE);
      setTrainer(this);
    }
    
    @Override
    public void benutzt() {
      richtung=-Javamon.getSpieler().getRichtung();
      if(Javamon.getSpieler().isBesiegt())Javamon.sout("Haha, Du hast verloren!");
      else{
        if(trainer.isBesiegt()){
          Javamon.sout(trainer.getDialog2());
          if(trainer.equals(Javamon.getRivale()))Javamon.addListener(this);
        }
        else{
          Javamon.sout(trainer.getDialog1());
          Javamon.sout(trainer+" will kämpfen!");
          Javamon.addListener(this);
        }
      }
    }
    
    public boolean isBesiegt(){
      return trainer.isBesiegt();
    }
    
    public int getRichtung(){
      return trainer.getRichtung();
    }
    
    @Override
    public void ausgabeFertig() {
      if(trainer.isBesiegt()){
        Javamon.rivaleBsg();
      }else{
        Javamon.starteKampf(trainer);  
      }
    }
    
    @Override
    public void setDialog(String... texte){
        if(texte!=null && texte.length>0){
            trainer.setDialog1(texte[0]);
            trainer.setDialog2("");
            for(int i=1;i<texte.length;i++)trainer.setDialog2(trainer.getDialog2()+texte[i]);
        }
    }
    
    @Override
    public void addDialog(String... texte){
        for(String s:texte)trainer.setDialog2(trainer.getDialog2()+s);
    }
    
    @Override
    public String[] getDialog(){
        return new String[]{trainer.getDialog1(),trainer.getDialog2()};
    }
    
    public void setRange(int range){
        sichtweite=range;
    }
    
    public int getRange(){
        return sichtweite;
    }
    
    public void setTrainer(Trainer t){
        trainer=t;
    }
    
    public Trainer trainer(){
        return trainer;
    }
    
    @Override
    public String toString(){
        return trainer.toString();
    }
}