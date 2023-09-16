package paintztp.Commands;
import paintztp.Shapes.Shape;
import java.util.ArrayList;

public abstract class Command 
{
    Shape target;
    Shape backup;
    ArrayList<Shape> lista;
    ArrayList<Shape> backupList;
    Command(Shape target,ArrayList<Shape> lista){
        this.target=target;
        backup=target;
        this.lista=lista;
        backupList=lista;
    }
    public abstract void execute();
    public abstract void undo();
}