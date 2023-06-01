package folder;

import folder.Element.EColor;

public class Element2d {
    public int x;
    public int y;
    public int id;
    public EColor color;
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
