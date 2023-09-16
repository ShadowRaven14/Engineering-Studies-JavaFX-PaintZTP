package paintztp.Shapes;
import java.io.Serializable;

public class Point implements Serializable
{
    public int x;
    public int y;

    public Point() { this(0, 0); }

    public Point(int x, int y) 
    {
        setX(x);
        setY(y);
    }

    public double getX() { return x; }

    public void setX(int x) { if (x >= 0) this.x = x;}

    public double getY() { return y; }

    public void setY(int y) { if (y >= 0) this.y = y; }

    public String toString() 
    {
        return "(" + this.x + "," + this.y + ")";
    }
    
    public double distance(Point p2) 
    {
        int dx = this.x - p2.x;
        int dy = this.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean equals(Object other) 
    {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Point))return false;

        Point p2 = (Point)other;
        return (this.x == p2.x) && (this.y == p2.y);
    }
    
    public void setLocation(double x, double y) 
    {
        this.x = (int) Math.floor(x + 0.5);
        this.y = (int) Math.floor(y + 0.5);    
    }
    
    public void setLocation(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void translate(int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    
    
}
