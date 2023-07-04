/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package goproject;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author burak
 */
public class GoLogicalBoardTest {
    
    private GoLogicalBoard instance;
    private int[] xCoordsW = new int[]{2,3, 7,7, 11, 15,15,  2,3, 5,7,6,5,7, 10};
    private int[] yCoordsW = new int[]{2,2, 2,3, 2,  2, 3,   6,6, 5,5,6,7,7, 6};
    private int[] xCoordsB = new int[]{1,1,2,2,2,2,3,3,4,5,6,6,6,6,7,7,7,8,8,9,10,10,11,11,11,12,14,15,16};
    private int[] yCoordsB = new int[]{2,6,1,3,4,7,1,3,2,6,2,3,5,7,1,4,6,2,3,6,2,7,1,3,6,2,2,1,2};

    
    
    public GoLogicalBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.out.println("Hola");
        instance = new GoLogicalBoard();
        for (int i=0;i<xCoordsW.length;i++){
            int x = xCoordsW[i];            
            int y = yCoordsW[i];
            instance.set(x,y, GoState.w);
        }
        for (int i=0;i<xCoordsB.length;i++){
            int x = xCoordsB[i];            
            int y = yCoordsB[i];
            instance.set(x,y, GoState.b);
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of set method, of class GoLogicalBoard.
     */
    @Test
    public void testSet() {
        System.out.println("set");
        Coordinates intersection = null;
        GoState st = null;
        GoLogicalBoard instance = new GoLogicalBoard();
        instance.set(intersection, st);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of at method, of class GoLogicalBoard.
     */
    @Test
    public void testAt() {
        System.out.println("at");
        Coordinates intersection = null;
        GoLogicalBoard instance = new GoLogicalBoard();
        GoState expResult = null;
        GoState result = instance.at(intersection);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isAlive method, of class GoLogicalBoard.
     */
    @Test
    public void testIsAlive() {
        System.out.println("isAlive");
        Coordinates intersection;
        boolean expResult;
        boolean result;
        
        //test 1 horizontal
        intersection = new Coordinates(2,2);
        expResult = false;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        assertEquals(expResult, instance.isAlive(new Coordinates(3,2)));
        
        //test 2 vertical
        intersection = new Coordinates(7,2);
        expResult = false;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        assertEquals(expResult, instance.isAlive(new Coordinates(7,3)));
        
        //test 3 una sola pieza
        intersection = new Coordinates(11,2);
        expResult = false;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        
        //test 4 vertical una libre
        intersection = new Coordinates(15,2);
        expResult = true;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        assertEquals(expResult, instance.isAlive(new Coordinates(15,3)));
        
        //test 5 horizontal una libre
        intersection = new Coordinates(2,6);
        expResult = true;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        assertEquals(expResult, instance.isAlive(new Coordinates(3,6)));
        
        //test 6 diagonal
        intersection = new Coordinates(6,6);
        expResult = false;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        
        //test 7 una pieza libre
        intersection = new Coordinates(10,6);
        expResult = true;
        result = instance.isAlive(intersection);
        assertEquals(expResult, result);
        
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
