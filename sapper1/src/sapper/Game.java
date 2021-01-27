package sapper;

public class Game {

    private Bomb bomb;
    private Flag flag;
    private GameState state;

    public GameState getState() {
        return state;
    }

    public Game(int cols, int rows, int bombs){
        Ranges.setSize(new Coord(cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start(){
       bomb.start();
       flag.start();
       state = GameState.Played;
    }

    public Box getBox(Coord coord){
        if(flag.get(coord) == Box.Opened)
            return bomb.get(coord);
        else
            return flag.get(coord);
    }

    public void pressLeftBtn(Coord coord) {
        if(gameOver()) return;
       openBox (coord);
       checkWinner();
    }

    private void checkWinner(){
        if(state == GameState.Played){
            if(flag.getCountClosedBox() == bomb.getTotalBomb())
                state = GameState.Winner;
        }
    }

    private void openBox(Coord coord){
        switch (flag.get(coord)){
            case Opened: setOpenedClosedBoxesAroundNum(coord); return;
            case Flaged: return;
            case Closed:
            switch (bomb.get(coord)){
                case Zero: openBoxAround(coord); return;
                case Bomb: openBombs(coord); return;
                default: flag.setOpened(coord); return;
            }
        }
    }
    private void setOpenedClosedBoxesAroundNum(Coord coord){
        if(bomb.get(coord) != Box.Bomb)
            if(flag.getCountBoxes(coord) == bomb.get(coord).getNumber())
            for(Coord around: Ranges.getCoordsAround(coord))
                if(flag.get(around) == Box.Closed)
                    openBox(around);
    }

    private void openBombs(Coord bombed) {
        state = GameState.Bombed;
        flag.setBombed(bombed);
        for(Coord coord: Ranges.getAllCoords())
            if(bomb.get(coord) == Box.Bomb)
                flag.setOpenedClosedBombBox(coord);
            else
                flag.setNoBombSafeBox(coord);
    }

    private void openBoxAround(Coord coord){
        flag.setOpened(coord);
        for(Coord around: Ranges.getCoordsAround(coord))
            openBox(around);
    }

    public void pressRightBtn(Coord coord) {
        if(gameOver()) return;
        flag.toggleFlaged(coord);
    }

    private boolean gameOver() {
        if(state == GameState.Played)
            return false;
        start();
        return true;
    }
}
