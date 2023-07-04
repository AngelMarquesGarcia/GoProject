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
    
    private int xSize;
    private int ySize;
    private int width;
    private int height;
    private int circleSize;
    private int halfCircle;
    private List<Coordinates> blackPieces;
    private List<Coordinates> whitePieces;
    private int pieceSize;
    
    private final Coordinates selectedSpace = new Coordinates();
    private boolean blackToPlay = true;
    private boolean highlightSelected = true;

    public GoBoardPanel() {
        updateSize();
        blackPieces = new ArrayList<>();
        whitePieces = new ArrayList<>();
    }
    
    @Override
    public void paint(Graphics g){
        updateSize();
        super.paint(g);
        g.setColor(Color.DARK_GRAY);
        int k;
        for (int i=0;i<19;i++){
            k = i+1;
            g.drawLine(width, k*height, width*19, k*height); //horizontal
            g.drawLine(k*width, height, k*width, height*19); //vertical
        }
        g.fillOval(4*width-halfCircle, 4*height-halfCircle, circleSize, circleSize);
        g.fillOval(10*width-halfCircle, 4*height-halfCircle, circleSize, circleSize);
        g.fillOval(16*width-halfCircle, 4*height-halfCircle, circleSize, circleSize);
        g.fillOval(4*width-halfCircle, 10*height-halfCircle, circleSize, circleSize);
        g.fillOval(10*width-halfCircle, 10*height-halfCircle, circleSize, circleSize);
        g.fillOval(16*width-halfCircle, 10*height-halfCircle, circleSize, circleSize);
        g.fillOval(4*width-halfCircle, 16*height-halfCircle, circleSize, circleSize);
        g.fillOval(10*width-halfCircle, 16*height-halfCircle, circleSize, circleSize);
        g.fillOval(16*width-halfCircle, 16*height-halfCircle, circleSize, circleSize);
        
        int half = pieceSize/2;
        //Draw Pieces
        g.setColor(Color.BLACK);
        for (Coordinates c: blackPieces){
            g.fillOval(c.x*width-half,c.y*height-half, pieceSize, pieceSize);
        }
        g.setColor(Color.WHITE);
        for (Coordinates c: whitePieces){
            g.fillOval(c.x*width-half,c.y*height-half, pieceSize, pieceSize);
        }
        if (blackToPlay){
            g.setColor(Color.BLACK);
        } else {
        }
        g.setColor(blackToPlay ? Color.BLACK : Color.WHITE);
        if (selectedSpace==null || selectedSpace.equals(0,0)) return;
        int x = selectedSpace.x;
        int y = selectedSpace.y;
        g.fillOval(width*x-half,height*y-half, pieceSize, pieceSize);
        if (highlightSelected){
            g.setColor(Color.ORANGE);
            g.fillOval(width*x-halfCircle,height*y-halfCircle, circleSize, circleSize);
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
            } else {
                whitePieces.add(selectedSpace.clone());
            }
        }
        blackToPlay = !blackToPlay;
        selectedSpace.reset();
        highlightSelected = false;
        
    }
}
