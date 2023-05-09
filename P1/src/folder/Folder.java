package folder;

import java.util.ArrayList;
import java.util.List;
import folder.Element.CDir;
import folder.Element.Direction;

public class Folder {

    private Element lastInserted = null;
    private Element head = null; // first inserted (id 0)
    private int numElements = 0;

    public Folder()
    {}

    public void addElement(Element newe)
    {
        numElements ++;
        if(lastInserted == null && head == null)
        {
            head = newe;
            lastInserted = newe;
            return;
        }

        lastInserted.setNext(newe);
    }

    public ArrayList<Element2d> convertToElement2dList(Element head) {
        // Initialize the 2D array of ArrayLists
        ArrayList<Element2d> returnList = new ArrayList<>();
        // Start at the center of the grid
        int x = 0;
        int y = 0;
        CDir currentDir2d = CDir.up;
        // Traverse the linked list and add each element to the appropriate position in the 2D array
        Element current = head;
       

        while(current != null)
        {
            returnList.add(new Element2d(x, y, current));
            Direction currentDir = current.getDirection();
            // Rotate based on direction
            if(currentDir == Direction.LEFT)
            {
                currentDir2d = rotateLeft(currentDir2d);
            }
            else if(currentDir == Direction.RIGHT)
            {
                currentDir2d = rotateRight(currentDir2d);
            }
            else if(currentDir == Direction.STRAIGHT)
            {
                // nix machen
            }

            current.d2Dir = currentDir2d;

            if(currentDir2d == CDir.down)
            {
                y++;
            }
            else if(currentDir2d == CDir.up)
            {
                y--;
            }
            else if(currentDir2d == CDir.right)
            {
                x++;
            }
            else if(currentDir2d == CDir.left)
            {
                x--;
            }

            // Go to next element
            current = current.getNext();
        }

        return returnList;
    }

    CDir rotateLeft(CDir topDir)
    {
        if(topDir == CDir.left)
        {
            return CDir.down;
        }
        if(topDir == CDir.right)
        {
            return CDir.up;
        }
        if(topDir == CDir.down)
        {
            return CDir.right;
        }
        if(topDir == CDir.up)
        {
            return CDir.left;
        }
        System.out.println("ROTATE LEFT IST SUS");
        return CDir.down;
    }

    CDir rotateRight(CDir topDir)
    {
        if(topDir == CDir.left)
        {
            return CDir.up;
        }
        if(topDir == CDir.right)
        {
            return CDir.down;
        }
        if(topDir == CDir.down)
        {
            return CDir.left;
        }
        if(topDir == CDir.up)
        {
            return CDir.right;
        }
        System.out.println("ROTATE LEFT IST SUS");
        return CDir.down;
    }

    double distance(Element2d one, Element2d two) {
        int x1, x2, y1, y2;
        x1 = one.x;
        y1 = one.y;
        x2 = two.x;
        y2 = two.y;
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public double getFitness()
    {
        double fit = 0.0;
        ArrayList<Element2d> list = convertToElement2dList(this.head);
        Element2d outer;
        Element2d inner;
        return 0.0;
        /* 
        for(int i = 0; i < list.size(); i++)
        {
            //outer = list.
            for(int j = 0; j < list.size(); j++)
            {

            }
        }*/

    }
}


