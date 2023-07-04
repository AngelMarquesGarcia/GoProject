/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package goproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Ángel Marqués García 
 */
public class GoBoardPanel extends JPanel {
    private Coordinates pressedOn;
    
    public void mousePressed(MouseEvent evt) {
        pressedOn = determineCoordinates(evt);
        if (blackPieces.contains(pressedOn) || whitePieces.contains(pressedOn)){
            pressedOn.reset();
        }
        System.out.println(Integer.toString(pressedOn.x) + ", " + Integer.toString(pressedOn.y));

    }

    public void mouseReleased(MouseEvent evt) {
        Coordinates releasedOn = determineCoordinates(evt);
        if (releasedOn == null ||!releasedOn.equals(pressedOn)){
            return;
        }
        selectedSpace.copy(releasedOn);
        repaint();
        highlightSelected = false;
    }
    
    public static GoBoardPanel goBoardPanel;
    
    
    private int xSize;
    private int ySize;
    private int width;
    private int height;
    private int circleSize;
    private int halfCircle;
    private List<Coordinates> blackPieces;
    private List<Coordinates> whitePieces;
    private final GoLogicalBoard board;
    private int pieceSize;
    
    private final Coordinates selectedSpace = new Coordinates();
    private boolean blackToPlay = true;
    private boolean highlightSelected = true;

    public GoBoardPanel() {
        updateSize();
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();
        board = new GoLogicalBoard();
        GoBoardPanel.goBoardPanel = this;
    }
    
    @Override
    public void paint(Graphics g){
        updateSize();
        super.paint(g);
        g.setColor(Color.DARK_GRAY);
        int k;
        for (int i=0;i<19;i++){
            k = i+1;
            g.drawLine(width/2, k*height-height/2, width*19-width/2, k*height-height/2); //horizontal
            g.drawLine(k*width-width/2, height/2, k*width-width/2, height*19-height/2); //vertical
        }
        int additionWidth = -halfCircle + width/2;
        int additionHeight = -halfCircle + height/2;
        g.fillOval(4*width+additionWidth, 4*height+additionHeight, circleSize, circleSize);
        g.fillOval(10*width+additionWidth, 4*height+additionHeight, circleSize, circleSize);
        g.fillOval(16*width+additionWidth, 4*height+additionHeight, circleSize, circleSize);
        g.fillOval(4*width+additionWidth, 10*height+additionHeight, circleSize, circleSize);
        g.fillOval(10*width+additionWidth, 10*height+additionHeight, circleSize, circleSize);
        g.fillOval(16*width+additionWidth, 10*height+additionHeight, circleSize, circleSize);
        g.fillOval(4*width+additionWidth, 16*height+additionHeight, circleSize, circleSize);
        g.fillOval(10*width+additionWidth, 16*height+additionHeight, circleSize, circleSize);
        g.fillOval(16*width+additionWidth, 16*height+additionHeight, circleSize, circleSize);
        
        int halfW = -width/2 - pieceSize/2;
        int halfH = -height/2 - pieceSize/2;
        
        //Draw Pieces
        g.setColor(Color.BLACK);
        for (Coordinates c: blackPieces){
            g.fillOval(c.x*width+halfW,c.y*height+halfH, pieceSize, pieceSize);
        }
        g.setColor(Color.WHITE);
        for (Coordinates c: whitePieces){
            g.fillOval(c.x*width+halfW,c.y*height+halfH, pieceSize, pieceSize);
        }

        //Draw Selected Piece
        g.setColor(blackToPlay ? Color.BLACK : Color.WHITE);
        if (selectedSpace==null || selectedSpace.equals(0,0)) return;
        int x = selectedSpace.x;
        int y = selectedSpace.y;
        g.fillOval(width*x+halfW,height*y+halfH, pieceSize, pieceSize);
        if (highlightSelected){
            halfW = -width/2 - halfCircle;
            halfH = -height/2 - halfCircle;
            g.setColor(Color.ORANGE);
            g.fillOval(width*x+halfW,height*y+halfH, circleSize, circleSize);
        }
    }

    private void updateSize() {
        Dimension size = getSize();
        xSize = size.width;
        ySize = size.height;
        width = xSize/19;
        height = ySize/19;
        circleSize = height/4;
        halfCircle = circleSize/2;
        pieceSize = height;
    }

    private Coordinates determineCoordinates(MouseEvent evt) {
        int x = evt.getX();
        for (int i=1; i<=19;i++){
            x -= width;
            if (x<=0){
                x = i;
                break;
            }
        }
        int y = evt.getY();
        for (int i=1; i<=19;i++){
            y -= height;
            if (y<=0){
                y = i;
                break;
            }
        }
        return new Coordinates(x,y);

        //previous version
        /*int x = evt.getX();
        for (int i = 1; i<=19; i++){
            if (x>width*i-width/2 && x<width*i+width/2){
                x = -i;
                break;
            }
        }
        int y = evt.getY();
        for (int i = 1; i<=19; i++){
            if (y>height*i-height/2 && y<height*i+height/2){
                y = -i;
                break;
            }
        }
        if (x>0 || y>0){
            throw new RuntimeException();
        }
        x = -x;
        y = -y;
        return new Coordinates(x,y);
    */
    }

    public void setHighlightSelected(boolean b) {
        highlightSelected = b;
        repaint();
    }

    void removePiece() {
        selectedSpace.reset();
        repaint();
    }

    void sendMoves() {
        if (!selectedSpace.equals(0,0)){
            if (blackToPlay){
                blackPieces.add(selectedSpace.clone());
                board.set(selectedSpace, GoState.b );
            } else {
                whitePieces.add(selectedSpace.clone());
                board.set(selectedSpace, GoState.w );
            }
        }
        
        considerCaptures();
        
        blackToPlay = !blackToPlay;
        selectedSpace.reset();
        highlightSelected = false;
        repaint();
        
    }

    private void considerCaptures() {
        Coordinates[] foobar = new Coordinates[4];
        foobar[0] = selectedSpace.up();
        foobar[1] = selectedSpace.down();
        foobar[2] = selectedSpace.left();
        foobar[3] = selectedSpace.right();
        for (Coordinates cell: foobar){
            if (blackToPlay && board.at(cell) == GoState.w)  {
                if (!board.isAlive(cell)){
                    board.flagForDeath(cell);
                    //whitePieces.remove(cell);
                }
            } else if (!blackToPlay && board.at(cell) == GoState.b){
                if (!board.isAlive(cell)){
                    board.flagForDeath(cell);
                    //blackPieces.remove(cell);
                }
            }
        }
        board.killFlagged();
    }
    
    public void removePiece(GoState st, Coordinates cell){
        if (st==GoState.b){
            blackPieces.remove(cell);
        } else {
            whitePieces.remove(cell);
        }
    }

    void showCaptures() {
        int white = board.getCapturedByWhite();
        int black = board.getCapturedByBlack();
        JOptionPane.showMessageDialog(GoProject.frame, 
                "White: " + Integer.toString(white) + "\nBlack: " + Integer.toString(black), 
                "Game Over", 
                1);
    }
}
