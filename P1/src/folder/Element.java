package folder;

public class Element {
    public enum Direction{
        up,
        down,
        left,
        right,
        empty
    }

    public enum Color{
        white,
        black
    }

    public Direction facingDirection = Direction.up; 

    public Color color = Color.white;

    public Element next = null;

    public int id;

    public Element(Direction d, Color w, int id)
    {   
        color = w;
        facingDirection = d;
        this.id = id;
    }

    public String getStr()
    {
        String mystr= "[";
        switch(facingDirection)
        {
            case up:
            mystr += '^';
            break;

            case down:
            mystr += 'v';
            break;

            case left:
            mystr += '<';
            break;

            case right:
            mystr += '>';
            break;

            default:
                break;
        }
        
        switch(color)
        {
            case white:
            mystr += 'W';
            break;

            case black:
            mystr += "B";
            break;

        }

        return mystr+"]";
    }
}
