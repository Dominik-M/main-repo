/*
 * Copyright (C) 2015 Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package panzers;

import graphic.SpielfeldPanel;
import graphic.Delay;
import utils.Vektor;

/**
 *
 * @author Dominik Messerschmidt <dominik_messerschmidt@yahoo.de>
 */
public class TankKI {
    public final Tank mTank;
    final int level;
    public final boolean friend;
    
    public TankKI(Tank t, int difficulty){
        mTank=t;
        level=difficulty;
        friend=mTank.playersTeam;
    }
    
    public Projectile[] signal(SpielfeldPanel game,Delay delay){
        // spend bonus points if available
            mTank.randomUpgrades();
        // move
        double phi=mTank.direction;
        if(mTank.x-mTank.width<10 || mTank.x+mTank.width>=game.BOUNDS.getWidth() 
                || mTank.y-mTank.height<10 || mTank.y+mTank.height>=game.BOUNDS.getHeight()){
            if(mTank.x-mTank.height<0 && (phi>Vektor.RIGHT_DOWN || phi<Vektor.RIGHT_UP) ||
                  mTank.x+mTank.height>=game.BOUNDS.getWidth() && (phi<Vektor.LEFT_DOWN || phi>Vektor.LEFT_UP) || 
                  mTank.y-mTank.height<10 && (phi<Vektor.RIGHT_DOWN || phi>Vektor.LEFT_DOWN) || 
                  mTank.y+mTank.height>=game.BOUNDS.getHeight() && (phi>Vektor.RIGHT_UP || phi<Vektor.LEFT_UP))
                mTank.rotate(true, delay);
            mTank.accelerate(true, delay);
        }else if(Math.random()*10<5)
            mTank.accelerate(true, delay);
        // aim on closest target
        mTank.aimX=-1;
        mTank.aimY=-1;
        Vektor[] enemysCoords=game.getEnemysCoords(friend);
        Vektor aim;
        if(enemysCoords.length>0)aim=enemysCoords[0];
        else aim=null;
        if(enemysCoords.length>1){
          Vektor distance=new Vektor(-1*mTank.getX(),-1*mTank.getY());
          distance.addVektor(aim);
          double minDist=distance.getBetrag();
          for(int i=1; i<enemysCoords.length; i++){
            distance=new Vektor(-1*mTank.getX(),-1*mTank.getY());
            distance.addVektor(enemysCoords[i]);
            if(distance.getBetrag()<minDist)aim=enemysCoords[i];
          }
        }
        if(aim!=null){
            mTank.aimX=aim.getX();
            mTank.aimY=aim.getY();
        }
        // fire
        if(Math.random()*1000/delay.MS<level && mTank.aimX>=0){
            Vektor distance=new Vektor(-1*mTank.getX(),-1*mTank.getY());
            distance.addVektor(aim);
            double dist=distance.getBetrag();
            if(dist<Math.sqrt(game.getWidth()*game.getWidth()+game.getHeight()*game.getHeight())){
              Projectile[] shots1;
              if(mTank.getPrimWeapon()!=null && mTank.getPrimWeapon().getRange()>dist)
                  shots1=mTank.fire(0);
              else shots1=new Projectile[0];
              Projectile[] shots2;
              if(mTank.getSecWeapon()!=null && mTank.getSecWeapon().getRange()>dist)
                  shots2=mTank.fire(1);
              else shots2=new Projectile[0];
              Projectile[] allShots=new Projectile[shots1.length+shots2.length];
              System.arraycopy(shots1, 0, allShots, 0, shots1.length);
              System.arraycopy(shots2, 0, allShots, shots1.length, shots2.length);
              return allShots;
            }
        }
        return null;
    }
}
