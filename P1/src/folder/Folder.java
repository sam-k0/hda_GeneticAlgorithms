package folder;

import java.util.ArrayList;

import folder.Element.Color;
import folder.Element.Direction;

public class Folder {


    private Element[][] grid = new Element[16][16]; 

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

    

    public void printme()
    {
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
        }
    }

    public void setCoord(int x, int y, Element e)
    {
        if(!inbounds(x, y))
        {return;}

        grid[x][y] = e;
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
         */
        double leFitness = 0.0;
        ArrayList<Element> checkedAlready = new ArrayList<Element>();

        for(int x = 0; x < grid.length; x++)
        {
            for(int y = 0; y < grid[0].length; y++)
            {
                Element current = grid[x][y];
                Element currentOther = null;
                if(current == null || current.color == Color.white)
                {
                    continue;
                }

                

                // Only blacks remain
                if(inbounds(x-1, y)) // Check left
                {
                    currentOther = grid[x-1][y];
                    if(currentOther != null && currentOther.color== Color.black && !checkedAlready.contains(currentOther))                
                    {
                        if(!facing(current, currentOther, Direction.left))
                        {
                            leFitness += 1.0;
                            checkedAlready.add(currentOther);
                        }
                    }
                }

                if(inbounds(x+1, y)) // Check right
                {
                    currentOther = grid[x+1][y];
                    if(currentOther != null && currentOther.color== Color.black && !checkedAlready.contains(currentOther))                
                    {
                        if(!facing(current, currentOther, Direction.right))
                        {
                            leFitness += 1.0;
                            checkedAlready.add(currentOther);
                        }
                    }
                }

                if(inbounds(x, y+1)) // Check below
                {
                    currentOther = grid[x][y+1];
                    if(currentOther != null && currentOther.color== Color.black && !checkedAlready.contains(currentOther))                
                    {
                        if(!facing(current, currentOther, Direction.down))
                        {
                            leFitness += 1.0;
                            checkedAlready.add(currentOther);
                        }
                    }
                }

                if(inbounds(x, y+1)) // Check above
                {
                    currentOther = grid[x][y-1];
                    if(currentOther != null && currentOther.color== Color.black && !checkedAlready.contains(currentOther))                
                    {
                        if(!facing(current, currentOther, Direction.up))
                        {
                            leFitness += 1.0;
                            checkedAlready.add(currentOther);
                        }
                    }
                }
            }
        }

        return leFitness;
    }
}
