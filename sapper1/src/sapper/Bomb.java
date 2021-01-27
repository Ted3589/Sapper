package sapper;

import java.util.Random;

class Bomb {
    private Matrix bombMap;
    private int totalBombs;

    Bomb(int totalBombs){
        this.totalBombs = totalBombs;
        fixBomb();
    }

    void start(){
        bombMap = new Matrix(Box.Zero);
        for (int i = 0; i<totalBombs; i++)
                    placeBomb();
    }

    Box get (Coord coord){
        return bombMap.get(coord);
    }

    private void fixBomb(){
        int maxBomb = Ranges.getSize().x*Ranges.getSize().y/2;
        if(totalBombs>maxBomb)
            totalBombs = maxBomb;
    }

    private void placeBomb(){
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if(Box.Bomb == bombMap.get(coord))
                continue;
            bombMap.set(coord, Box.Bomb);
            incNumbersAround(coord);
            break;
        }
    }

    private void incNumbersAround(Coord coord){
        for(Coord around: Ranges.getCoordsAround(coord)){
            if(Box.Bomb != bombMap.get(around))
            bombMap.set(around, bombMap.get(around).getNextNumberBox());
        }
    }

    int getTotalBomb() {
        return totalBombs;
    }
}
