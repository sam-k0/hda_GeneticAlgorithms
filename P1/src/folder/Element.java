package folder;

public class Element {

    private int id;
    private Element next;
    private Direction nextDirection;
    private Color color;

    public enum Direction {
        STRAIGHT,
        RIGHT,
        LEFT
    }

    public enum Color{
        WHITE,
        BLACK
    }

    public Element(int id, Direction dir, Color col) {
        this.id = id;
        this.next = null;
        this.nextDirection = dir;
        this.color = col;
    }

    public int getId() {
        return id;
    }

    public Element getNext() {
        return next;
    }

    public void setNext(Element next, Direction direction) {
        this.next = next;
        this.nextDirection = direction;
    }

    public Direction getNextDirection() {
        return this.nextDirection;
    }

    public void setNextDirection(Direction direction) {
        this.nextDirection = direction;
    }
}
