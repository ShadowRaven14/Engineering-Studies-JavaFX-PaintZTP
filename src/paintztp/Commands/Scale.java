package paintztp.Commands;
import paintztp.Shapes.Shape;
import java.util.ArrayList;

public class Scale extends Command
{
    //int factor;
    int x, y;
    
    Scale(Shape target,ArrayList<Shape> lista, int x, int y)
    {
        super(target,lista);
        //this.factor=factor;
        this.x = x;
        this.y = y;
    }
    
    public void execute()
    {
        target.scale(x,y);
    }
    
    public void undo(){
        target=backup;
    }
}