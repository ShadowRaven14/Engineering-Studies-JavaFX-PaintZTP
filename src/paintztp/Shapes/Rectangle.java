package paintztp.Shapes;
import java.util.ArrayList;
import paintztp.Painting.Color;

public class Rectangle extends Shape 
{

    public Rectangle() 
    { 
        name = "Rectangle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        this.pointcount = 4;
        this.obsize = 100;
        
        int basestart = 50;
        Point p1 = new Point (basestart,basestart);
        Point p2 = new Point (this.obsize+basestart,basestart);
        Point p3 = new Point (this.obsize+basestart,this.obsize+basestart);
        Point p4 = new Point (basestart,this.obsize+basestart);
        
        
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        
        System.out.println("Tworzę figurę");
    }
    
    public Rectangle(int size) 
    { 
        name = "Rectangle";
        
        this.points = new ArrayList<Point>();
        this.color = new Color(0, 0, 0);
        this.borderWidth = 0;
        
        this.pointcount = 4;
        this.obsize = size;
        
        int basestart = 50;
        Point p1 = new Point (basestart,basestart);
        Point p2 = new Point (this.obsize+basestart,basestart);
        Point p3 = new Point (this.obsize+basestart,this.obsize+basestart);
        Point p4 = new Point (basestart,this.obsize+basestart);
        
        
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        
        System.out.println("Tworzę figurę");
    }

    @Override
    public Shape clone() 
    {
        return new Rectangle();
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
