package sapper;

public enum Box {
    Zero,
    Num1,
    Num2,
    Num3,
    Num4,
    Num5,
    Num6,
    Num7,
    Num8,
    Bomb,
    Opened,
    Closed,
    Flaged,
    Bombed,
    Nobomb;

    public Object img;

    Box getNextNumberBox(){
        return Box.values()[this.ordinal()+1];
    }

    int getNumber(){
         return this.ordinal();
    }
}
