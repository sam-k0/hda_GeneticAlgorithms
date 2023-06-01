package folder;

public class Element {

    private int id;
    private Element next;
    private Direction direction; // pointing to next element 
    private EColor color;

    public CDir d2Dir;
    
    public enum Direction {
        STRAIGHT,
        RIGHT,
        LEFT
    }

    public enum EColor{
        WHITE,
        BLACK
    }

    enum CDir {
        up,
        down,
        left,
        right
    }

    public Element(int id, Direction dir, EColor col) {
        this.id = id;
        this.next = null;
        this.direction = dir;
        this.color = col;
    }

    public int getId() {
        return id;
    }

    public Element getNext() {
        return next;
    }

    public EColor getColor()
    {return this.color;}

    public void setNext(Element next) {
        this.next = next;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
