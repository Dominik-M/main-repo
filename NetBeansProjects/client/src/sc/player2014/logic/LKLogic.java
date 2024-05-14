package sc.player2014.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import sc.player2014.Starter;
import sc.plugin2014.GameState;
import sc.plugin2014.entities.*;
import sc.plugin2014.moves.*;
import sc.plugin2014.util.GameUtil;
import sc.shared.GameResult;

public class LKLogic implements sc.plugin2014.IGameHandler {

    private final Starter client;
    private GameState gameState;
    private Player currentPlayer;
    /** Blauer oder Roter Spieler */
    private boolean blue=true;
    private ArrayList<Field>relevanteFields;
    private Board board;
    private LayMove besterMove;
    private int bestePunkte;

    public LKLogic(Starter client) {
      this.client = client;
      relevanteFields=new ArrayList();  
    }

    @Override
    public void onRequestAction() {
        System.out.println("*** Es wurde ein Zug angefordert");
        if (isOurStartLayMove()) {
            blue=false;
            if (tryToDoStartLayMove() == false) {
                exchangeStones(currentPlayer.getStones());
            }
        } else {
          if(tryToDoValidLayMove() == false){
            exchangeStones(currentPlayer.getStones());  
          }
        }
    }

    private boolean isOurStartLayMove() {
        return (!gameState.getBoard().hasStones())
                && (gameState.getStartPlayer() == currentPlayer);
    }

    private boolean tryToDoStartLayMove() {
        ArrayList<Stone> myStones=new ArrayList();
        for(Stone s : currentPlayer.getStones()){
            myStones.add(s);
            for(Stone s2:myStones) {
                if(s2!=s && s2.getColor()==s.getColor() && s2.getShape()==s.getShape()) {
                    myStones.remove(s);
                }
            }
        }
        StoneColor bestStoneColor = GameUtil.getBestStoneColor(myStones);
        StoneShape bestStoneShape = GameUtil.getBestStoneShape(myStones);
        int colorAnz=0;
        int shapeAnz=0;
        for(Stone st : myStones){
          if(st.getColor()==bestStoneColor) {
                colorAnz++;
            }
          if(st.getShape()==bestStoneShape) {
                shapeAnz++;
            }
        }
        LayMove layMove = new LayMove();
        int fieldX = 6;
        int fieldY = 7;
        if(colorAnz>=shapeAnz) {
          for (Stone stone : myStones) {
              if (stone.getColor() == bestStoneColor) {
                  layMove.layStoneOntoField(stone, gameState.getBoard()
                          .getField(fieldX, fieldY));
                  fieldX++;
              }
          }
        }else{
               for (Stone stone : myStones) {
                 if (stone.getShape() == bestStoneShape) {
                   layMove.layStoneOntoField(stone, gameState.getBoard()
                            .getField(fieldX, fieldY));
                   fieldX++;
                 }
               }
         }
         if (GameUtil.checkIfLayMoveIsValid(layMove, gameState.getBoard())) {
             layMove.addHint("Sending Start LayMove");
             sendAction(layMove);
             return true;
        }return false;
    }

    private boolean tryToDoValidLayMove() {
        for (Field field : relevanteFields) {
          for (Stone stone : currentPlayer.getStones()) {
            LayMove layMove = new LayMove();
            layMove.layStoneOntoField(stone, field);
            if (GameUtil.checkIfLayMoveIsValid(layMove,board)){
              
            }
            layMove.addHint("Sending LayMove");
            sendAction(layMove);
            return true;
          }
        }
        return false;
    }
    /** Fordert einen Spielsteinaustausch vom Server an.
     * Damit wird die Aktion des Spielers beendet.
    */
    private void exchangeStones(List<Stone> stones) {
        ExchangeMove exchangeMove = new ExchangeMove(stones);
        exchangeMove.addHint("Sending Exchange Move");
        sendAction(exchangeMove);
    }

    @Override
    public void onUpdate(Player player, Player otherPlayer) {
        currentPlayer = player;
        System.out.println("*** Spielerwechsel: " + player.getPlayerColor());
    }

    @Override
    public void onUpdate(GameState gameState) {
        this.gameState = gameState;
        currentPlayer = gameState.getCurrentPlayer();
        this.board = gameState.getBoard();
        findRelevanteFields();
    }

    @Override
    public void sendAction(Move move) {
        client.sendMove(move);
    }

    @Override
    public void gameEnded(GameResult data, PlayerColor color,
            String errorMessage) {
        System.out.println("*** Das Spiel ist beendet");
    }
    
    private void findRelevanteFields(){
      for(Field f: board.getFields()){
        if(!f.isFree()){
          for(Field nachbar: getNachbarn(f)){
            if(nachbar==null || relevanteFields.contains(f))continue;
            if(nachbar.isFree()) relevanteFields.add(nachbar);
          }
        }
      }
    }
    
    public Field[] getNachbarn(Field f){
      int x=f.getPosX();
      int y=f.getPosY();
      Field[] nachbarn=new Field[4];
      // oben
      if(y>0)nachbarn[0]=board.getField(x,y-1);
      // rechts
      if(x<15) nachbarn[1]=board.getField(x+1,y);
      // unten
      if(y<15) nachbarn[2]=board.getField(x,y+1);
      // links
      if(x>0) nachbarn[3]=board.getField(x-1,y);
      return nachbarn;
    }
    
    public int zählePunkteFürLaymove(LayMove layMove){
      int punkte=0;
      Map<Stone,Field> map=layMove.getStoneToFieldMapping();
      Stone[] gelegteSt=(Stone[]) map.keySet().toArray();
      
      return punkte;
    }
}