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

    public Element(Direction d, Color w)
    {   
        color = w;
        facingDirection = d;
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
