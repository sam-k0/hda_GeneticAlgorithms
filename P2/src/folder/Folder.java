package folder;

import java.util.ArrayList;
import java.util.List;
import folder.Element.CDir;
import folder.Element.EColor;
import folder.Element.Direction;

public class Folder implements Comparable<Folder>{

    private Element lastInserted = null;
    private Element head = null; // first inserted (id 0)
    private double fitness = 0.0;
    private int overlaps = 0;
    private int contacts = 0;

    public Folder()
    {
        
    }

    public Folder(String bitString) // For using the benchmark
    {
        for(int i = 0; i < bitString.length(); i++)
        {
            EColor nCol = EColor.WHITE;
            char currChar = bitString.charAt(i);
            // decide color
            if(currChar == '0')
            {
                nCol = EColor.WHITE;
            }
            else if(currChar == '1')
            {
                nCol = EColor.BLACK;
            }

            this.addElement(new Element(i, Direction.STRAIGHT, nCol));
        }

        System.out.println("Constructed a folder from bitString");
    }

    // use this constructor for crossover
    public Folder(List<Element> elementsFromCrossover)
    {
        for(Element e : elementsFromCrossover)
        {
            this.addElement(e);
        }
    }

    public Element getHead()
    {
        return head;
    }

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
                y--;
            }
            else if(currentDir2d == CDir.up)
            {
                y++;
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

    public int getOverlaps()
    {
        return overlaps;
    }

    public int getContacts()
    {
        return contacts;
    }

    public double getFitness()
    {
        calcFitnessAndOverlaps(false);
        return fitness;
    }

    public void calcFitnessAndOverlaps(boolean doPrint)
    {
        int tcontacts = 0;
        int toverlaps = 0;
        ArrayList<Element2d> list = convertToElement2dList(this.head);
        Element2d outer;
        Element2d inner;

        ArrayList<Element2d> blacklist = new ArrayList<>();
        
        for(int i = 0; i < list.size(); i++)
        {
            outer = list.get(i);
            for(int j = 0; j < list.size(); j++)
            {
                inner = list.get(j);

                double dist = calculateDistance(inner.x, inner.y, outer.x, outer.y);
               
                //if(outer.x == inner.x && outer.y == inner.y && inner != outer )
                if(dist == 0.0 && inner != outer && !blacklist.contains(inner))
                {
                    toverlaps += 1;
                    if(doPrint)
                    {
                        System.out.println("Überlappung mit "+ outer.id + " und " + inner.id);
                    }                  
                }

                if(dist == 1.0 && inner.color == EColor.BLACK && outer.color == EColor.BLACK)
                { // Nur adjazente & schwarze behalten
                    if(inner.Element3d.getNext() == outer.Element3d || outer.Element3d.getNext() == inner.Element3d)
                    { // Keine direkt verbundenen behalten
                        //System.out.println("Sus");
                    }
                    else if(!blacklist.contains(inner))
                    {
                        if(doPrint)
                        {
                            System.out.println("Kontakt mit "+ outer.id + " und " + inner.id);
                        }
                        
                        tcontacts += 1;
                    }
                } 
            }

            blacklist.add(outer);
        }
        this.contacts = tcontacts;
        this.overlaps = toverlaps;
        
        if(doPrint)
        {
            System.out.println("Contacts:"+tcontacts);
            System.out.println("Overlaps"+this.overlaps);
        }
        

        this.fitness = ((double)this.contacts / (double)(this.overlaps+1));
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

    public String getPrintDirections()
    {
        String complete = "";
        Element curr = head;
        while(curr != null)
        {
            if(curr.getDirection() == Direction.LEFT)
            {
                complete += "L-";
            }
            else if(curr.getDirection() == Direction.RIGHT)
            {
                complete += "R-";
            }
            else if(curr.getDirection() == Direction.STRAIGHT)
            {
                complete += "S-";
            }
            curr = curr.getNext();
        }
        return complete;
    }

    public int getLength()
    {
        int length = 0;
        Element curr = head;
        while(curr != null)
        {
            length++;
            curr = curr.getNext();
        }
        return length;
    }

    public int compareTo(Folder other) {
        // Compare individuals based on their fitness values
        return Double.compare(other.getFitness(), this.getFitness());
    }

}


