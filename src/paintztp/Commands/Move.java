package paintztp.Commands;
import paintztp.Shapes.Shape;
import java.util.ArrayList;

public class Move extends Command
{
    int x,y;
    
    Move(Shape target,ArrayList<Shape> lista, int x, int y)
    {
        super(target,lista);
        this.x=x;
        this.y=y;
    }
    
    public void execute(){
        target.move(x, y);
    }
    
    public void undo(){
        target.move(-x, -y);
    }
}