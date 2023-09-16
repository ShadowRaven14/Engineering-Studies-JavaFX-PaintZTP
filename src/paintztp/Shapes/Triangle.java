package paintztp.Shapes;
import java.util.ArrayList;
import paintztp.Painting.Color;

public class Triangle extends Shape
{

    public Triangle() 
    { 
        name = "Triangle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 3;
        Point p1 = new Point (50,0);
        Point p2 = new Point (0,50);
        Point p3 = new Point (50,50);
        points.add(p1);
        points.add(p2);
        points.add(p3);
    }
    
    public Triangle(int size) 
    { 
        name = "Triangle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 3;
        Point p1 = new Point (size,0);
        Point p2 = new Point (0,size);
        Point p3 = new Point (size,size);
        points.add(p1);
        points.add(p2);
        points.add(p3);
    }

    @Override
    public Shape clone() 
    {
        return new Triangle();
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
            points.get(i).setY(y);
        }
    }
    
    
}
