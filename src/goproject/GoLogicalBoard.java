/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package goproject;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Ángel Marqués García 
 */
class GoLogicalBoard {
    private final GoState[][] board;
    private Set<Coordinates> studyGroup;
    int capturedByWhite = 0;
    int capturedByBlack = 0;
    private Set<Coordinates> dying;

    public GoLogicalBoard() {
        this.board = new GoState[19][19];
        for (GoState[] row : board){
            for (GoState cell : row){
                cell = GoState.e;
            }
        }
        studyGroup = new HashSet<>();
        dying = new HashSet<>();
    }
    
    public void set(Coordinates intersection, GoState st){
        int x = intersection.x;
        int y = intersection.y;
        board[y][x] = st;
    }
    public void set(int x, int y, GoState st){
        board[y][x] = st;
    }
    
    public GoState at(Coordinates intersection){
        try { 
            return board[intersection.y][intersection.x];
        } catch (ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
    private boolean isAliveAux(Coordinates intersection, Coordinates studying){
        GoState st = at(intersection);
        GoState studSt = at(studying);
        /*if (studSt == GoState.e){ //should not be neccesary
            studyGroup.remove(intersection);
            return true;
        }*/
        if (studSt == st && !studyGroup.contains(studying) && !dying.contains(studying) && isAlive(studying)){
            return true;
        }
        return false;
    }
    
    /**
     * Checks if the piece at the given intersection is alive.
     * <p> To do that, it checks first that the intersection exists.
     * Then it checks if it's empty. If so, it's considered alive. </p> 
     * <p>If it's not empty, we check the pieces adjacent to it with isAliveAux. 
     * If any of the adjacent pieces are found to be empty, the piece is alive. 
     * Otherwise, it is alive so long as at least one of its adjacent pieces is alive</p>
     * @param intersection
     * @return true if the piece at the intersection is alive, false otherwise
     */
    public boolean isAlive(Coordinates intersection){
        if (dying.contains(intersection)) return false;
         GoState st = at(intersection);
        if (st == null){return true;}
        if (st == GoState.e){return true;}
        
        studyGroup.add(intersection);
        Coordinates[] foobar = new Coordinates[4];
        foobar[0] = intersection.up();
        foobar[1] = intersection.down();
        foobar[2] = intersection.left();
        foobar[3] = intersection.right();
        for (Coordinates cell: foobar){
            if (at(cell) == GoState.e || at(cell) == null){
                studyGroup.remove(intersection);
                return true;
            }
        }
        for (Coordinates cell: foobar){
            if (isAliveAux(intersection,cell)){
                studyGroup.remove(intersection);
                return true;
            }
        }
        studyGroup.remove(intersection);
        return false;
    }

    void flagForDeath(Coordinates cell) {
        dying.add(cell);
        Coordinates[] foobar = new Coordinates[4];
        foobar[0] = cell.up();
        foobar[1] = cell.down();
        foobar[2] = cell.left();
        foobar[3] = cell.right();
        for (Coordinates c: foobar){
            if (!dying.contains(c) && !isAlive(c)){
                flagForDeath(c);
            }
        }
        
    }
    
    void killFlagged(){
        for (Coordinates cell: dying){
            GoState st = at(cell);
            set(cell, GoState.e);
            GoBoardPanel.goBoardPanel.removePiece(st, cell);
            if (st == GoState.b){
                capturedByBlack++;
            } else {
                capturedByWhite++;
            }
        }
    }
    
    
    public int getCapturedByWhite() {
        return capturedByWhite;
    }

    public void setCapturedByWhite(int capturedByWhite) {
        this.capturedByWhite = capturedByWhite;
    }

    public int getCapturedByBlack() {
        return capturedByBlack;
    }

    public void setCapturedByBlack(int capturedByBlack) {
        this.capturedByBlack = capturedByBlack;
    }
}
