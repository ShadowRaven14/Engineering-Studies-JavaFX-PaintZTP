package paintztp.Commands;
import paintztp.Shapes.Shape;
import java.util.ArrayList;

public class Add extends Command
{
    Add(Shape target, ArrayList<Shape> lista)
    {
        super(target,lista);
    }
    public void execute()
    {
        lista.add(target);
    }
    public void undo(){
        lista.remove(target);
    }
}