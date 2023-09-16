package paintztp.Commands;
import paintztp.Shapes.Shape;
import java.util.ArrayList;

public class Remove extends Command
{
    
    Remove(Shape target,ArrayList<Shape> lista)
    {
        super(target,lista);
    }
    
    public void execute()
    {
        lista.remove(target);
    }
    
    public void undo(){
        lista.add(target);
    }
}