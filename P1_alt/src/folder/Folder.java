package folder;

import java.util.ArrayList;

import folder.Element.Color;
import folder.Element.Direction;

public class Folder {


    private ArrayList<Element>[][] grid = new ArrayList[16][16]; 
    private Element lastInserted = null; // last inserted element ref
    private double fitness = 0.0;
    private ArrayList<Element> checkedAlready = new ArrayList<>();

    // checks if coords inside array bounds
    private boolean inbounds(int x, int y)
    {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length) {
            // x and y values are in bounds
            return true;
        } else {
            // x and y values are out of bounds
            return false;
        }
    }

    public Folder() // konst
    {
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                grid[i][j] = null;
            }
        }
    }

    // needs workover
    public void printme()
    {
        /* *
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                // lol
                Element e = grid[j][i];
                if(e == null)
                {
                    System.out.print("[__]");
                }
                else
                {  
                    System.out.print(e.getStr());
                }


                if(j == grid[i].length-1)
                {
                    System.out.println("");
                }
            }
        }*/
    }

    // Check that only connections to next id don't count
    // checks at position compared to element and updates fitness
    private void checkAt(int x, int y, Element current, Direction direction)
    {
        if(inbounds(x, y)) // Check left
        {
            ArrayList<Element> l = grid[x][y];
            if(l != null) // check if field not empty
            {
                for(Element currentOther : l) // check for all elements at that pos
                {
                    if(currentOther.color== Color.black && !checkedAlready.contains(currentOther) )                //
                    {
                        if(!facing(current, currentOther, direction) && !(current.id + 1 == currentOther.id))
                        {
                            fitness += 1.0;
                            checkedAlready.add(current); // current or other lol
                        }
                    }
                }                            
            }                        
        }
    }

    // insert an element at given position
    public void setCoord(int x, int y, Element e)
    {
        if(!inbounds(x, y))
        {return;}

        if(grid[x][y] == null)
        {
            // create new list
            grid[x][y] = new ArrayList<Element>();
        }
        // append to list
        grid[x][y].add(e);

        if(lastInserted != null) // is gonna be null for first
        {
            lastInserted.next = e;
        }

        lastInserted = e; // update last inserted
    }

    private boolean facing(Element one, Element two, Direction relativeDirection)
    {
        // relative direction is two's rel direction towards one
        // so up means two is above one
        switch(relativeDirection)
        {
            case up:
                if(one.facingDirection == Direction.up || two.facingDirection == Direction.down)
                {
                    return true;
                }
            break;

            case down:
                if(one.facingDirection == Direction.down || two.facingDirection == Direction.up)
                {
                    return true;
                }
            break;

            case left:
                if(one.facingDirection == Direction.left || two.facingDirection == Direction.right)
                {
                    return true;
                }
            break;

            case right:
                if(one.facingDirection == Direction.right || two.facingDirection == Direction.left)
                {
                    return true;
                }
            break;

            default:
                break;
        }

        return false;
    }

    public double berechneFitness() 
    {
        /**
         * 2 Schwarze sind nicht miteinander verbunden wenn:
         * - A nicht auf B zeigt
         * - B nicht auf A zeigt
         * 
         * Wenn dies der Fall ist gibt es stonks
         * 
         * TODO: update
         */
        checkedAlready.clear();
			
        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                ArrayList<Element> fieldList = grid[x][y];
                if(fieldList == null)
                {
                    continue;
                }
                // list exists: 1 or more elements
                //Element currentOther = null;

                for(Element current : fieldList) // loop over all elements at that pos
                { 
                    if(current.color == Color.white)
                    {
                        continue;
                    }
                    // only check black
                    checkAt(x-1, y, current, Direction.left);
                    checkAt(x+1, y, current,Direction.right);
                    checkAt(x, y+1, current,Direction.down);
                    checkAt(x, y-1, current,Direction.up);
                }
                
            }
        }
        System.out.println(checkedAlready.toString());
        return fitness;
    }
}
