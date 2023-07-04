/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package goproject;

/**
 *
 * @author Ángel Marqués García 
 */
class Coordinates {
    public int x;
    public int y;
    
    public Coordinates(){
        x=0;y=0;
    }
    
    public Coordinates(int x,int y){
        this.x = x; this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Coordinates other = (Coordinates) obj;
        if (x != other.x) {
            return false;
        }
        return y == other.y;
    }

    public boolean equals(int xCoord, int yCoord) {
        return (x == xCoord && y == yCoord);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.x;
        hash = 67 * hash + this.y;
        return hash;
    }
    
    public void copy(Coordinates c) {
        x = c.x;
        y = c.y;
    }

    void reset() {
        x = 0;
        y = 0;
    }
    
    @Override
    public Coordinates clone() {
        return new Coordinates(x, y);
    }

    Coordinates up() {
        return new Coordinates(x,y-1);
    }

    Coordinates down() {
        return new Coordinates(x,y+1);
    }

    Coordinates left() {
        return new Coordinates(x-1,y);
    }

    Coordinates right() {
        return new Coordinates(x+1,y);
    }
}
