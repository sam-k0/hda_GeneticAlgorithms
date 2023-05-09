package folder;

import folder.Element.Color;

public class Element2d {
    public int x;
    public int y;
    public int id;
    public Color color;
    public Element Element3d;

    public Element2d(int x, int y, Element ele)
    {
        this.x = x;
        this.y = y;
        this.id = ele.getId();
        this.color = ele.getColor();
        this.Element3d = ele;
    }
}
