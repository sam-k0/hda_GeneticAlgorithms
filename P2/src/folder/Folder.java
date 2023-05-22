package folder;

import java.util.ArrayList;
import java.util.List;
import folder.Element.CDir;
import folder.Element.Color;
import folder.Element.Direction;

public class Folder {

    private Element lastInserted = null;
    private Element head = null; // first inserted (id 0)
    private int numElements = 0;

    public Folder()
    {}

    public void addElement(Element newe)
    {
        if(this.head == null)
        {
            head = newe;
            lastInserted = newe;
        }
        else
        {
            lastInserted.setNext(newe);
            lastInserted = newe;
        }
    
        
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

    public static double calculateDistance(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getFitness()
    {
        double fit = 0.0;
        ArrayList<Element2d> list = convertToElement2dList(this.head);
        Element2d outer;
        Element2d inner;

        ArrayList<Element2d> blacklist = new ArrayList<>();

        System.out.println(list.size());
        
        
        
        for(int i = 0; i < list.size(); i++)
        {
            outer = list.get(i);
            for(int j = 0; j < list.size(); j++)
            {
                inner = list.get(j);

                double dist = calculateDistance(inner.x, inner.y, outer.x, outer.y);
                //System.out.println(dist);

                if(dist == 1.0 && inner.color == Color.BLACK && outer.color == Color.BLACK)
                { // Nur adjazente & schwarze behalten
                    if(inner.Element3d.getNext() == outer.Element3d || outer.Element3d.getNext() == inner.Element3d)
                    { // Keine direkt verbundenen behalten
                        System.out.println("Sus");
                    }
                    else if(!blacklist.contains(inner))
                    {
                        fit += 1.0;
                    }
                } 
            }
            blacklist.add(outer);
        }
        return fit;
    }

    public int calculateHammingDistance(int num1, int num2) {
        int xor = num1 ^ num2;
        int distance = 0;
        
        while (xor != 0) {
            if ((xor & 1) == 1) {
                distance++;
            }
            xor >>= 1;
        }
        
        return distance;
    }
}


