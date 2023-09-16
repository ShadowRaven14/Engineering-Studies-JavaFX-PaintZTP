package paintztp.Shapes;
import java.security.Guard;
import java.util.ArrayList;
import paintztp.Painting.Color;

//interfejs
abstract public class Shape 
{
    protected String name;
    protected int obsize;
    protected ArrayList<Point> points;
    protected int pointcount;
    protected Color color;
    protected Boolean hasBorder;
    protected Color borderColor;
    protected int borderWidth;
    protected int opacity;
    
    public Shape() 
    {
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
    }
    
    //Tworzenie kopii obiektu
    public abstract Shape clone();
    
    // przemieszczanie figury
    public abstract void move(int x, int y);
    
    //zmiana rozmiaru figury
    public abstract void scale(int x, int y);
    
    
    //Ustawianie kolorów
    public void setColor(int nr, int ng, int nb) 
    {
        this.color.r = nr;
        this.color.g = ng;
        this.color.b = nb;
    }
    
    //Pobieranie kolorów
    public int getColorR() { return this.color.r; }
    public int getColorG() { return this.color.g; }
    public int getColorB() { return this.color.b; }

    //Pobieranie liczby wierzchołków
    public int getPointCount() { return this.pointcount; }
    
    //Pobieranie grubości linii
    public int getBorderWidth() { return this.borderWidth; }

    //Pobieranie xy danego punktu
    public int getPointsX(int index) { return this.points.get(index).x; }
    public int getPointsY(int index) { return this.points.get(index).y; }
    
    //Pobieranie i zmiana wymiarów obiektu
    public int getObSize() { return this.obsize; }
    public void setObSize(int obsize) { this.obsize = obsize; }
    
    //Pobieranie i zmiana nazwy
    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
  
    
}
