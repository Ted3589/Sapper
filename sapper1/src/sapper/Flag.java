package sapper;

class Flag {
    private Matrix flagMap;
    private int ccb;

    void start(){
        flagMap = new Matrix(Box.Closed);
        ccb = Ranges.getSize().x*Ranges.getSize().y;
    }

    Box get (Coord coord){
        return flagMap.get(coord);
    }

    void setOpened(Coord coord) {
        flagMap.set(coord, Box.Opened);
        ccb --;
    }

    void setFlaged(Coord coord) {
        flagMap.set(coord, Box.Flaged);
    }

    void toggleFlaged(Coord coord) {
        switch (flagMap.get(coord)){
            case Flaged : setClosed(coord); break;
            case Closed : setFlaged(coord); break;
        }
    }

    private void setClosed(Coord coord) {
        flagMap.set(coord, Box.Closed);
    }

    int getCountClosedBox() {
        return ccb;
    }

    void setBombed(Coord coord){
        flagMap.set(coord, Box.Bombed);
    }

    void setOpenedClosedBombBox(Coord coord) {
        if(flagMap.get(coord) == Box.Closed)
            flagMap.set(coord, Box.Opened);
    }

    void setNoBombSafeBox(Coord coord) {
        if(flagMap.get(coord) == Box.Flaged)
            flagMap.set(coord, Box.Nobomb);
    }



    int getCountBoxes(Coord coord) {
        int count = 0;
        for(Coord around: Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.Flaged)
                count++;
        return count;
    }
}
