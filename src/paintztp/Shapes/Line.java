package paintztp.Shapes;
import java.util.ArrayList;
import paintztp.Painting.Color;

public class Line extends Shape
{

    public Line() 
    { 
        name = "Line";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 2;
        Point p1 = new Point (50,50);
        Point p2 = new Point (300,10);
        points.add(p1);
        points.add(p2);
    }
    
    public Line(int size) 
    { 
        name = "Line";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 2;
        Point p1 = new Point (50,50);
        Point p2 = new Point (50+size,10);
        points.add(p1);
        points.add(p2);
    }

    @Override
    public Shape clone() 
    {
        return new Line();
    }

    @Override
    public void move(int x, int y) 
    {
        points.get(0).setX(x);
        points.get(0).setY(y);
    }

    @Override
    public void scale(int x, int y) 
    {
        for(int i=1; i< pointcount; i++)
        {
            points.get(i).setX(x);
        }
    }
    
    
}
