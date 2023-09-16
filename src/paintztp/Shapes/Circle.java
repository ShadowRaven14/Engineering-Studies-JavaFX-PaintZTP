package paintztp.Shapes;
import java.util.ArrayList;
import paintztp.Painting.Color;

public class Circle extends Shape 
{
    
    public Circle() 
    { 
        name = "Circle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 1;
        obsize = 170; //u koła rozmiar jest jego promieniem
        Point p1 = new Point (50,50);
        points.add(p1);
    }
    
    public Circle(int size) 
    { 
        name = "Circle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        pointcount = 1;
        obsize = size*2; //u koła rozmiar jest jego promieniem
        Point p1 = new Point (50,50);
        points.add(p1);
    }
    
    @Override
    public Shape clone() 
    {
        return new Circle();    
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
