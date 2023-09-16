package paintztp.Commands;
import java.util.ArrayList;
import paintztp.Shapes.Shape;

public class ListOfObjects 
{
    public ArrayList<Shape> shapes;
    private ArrayList<Command> commandHistory;
    
    
    public ListOfObjects() 
    {
        this.shapes = new ArrayList<Shape>();
        this.commandHistory = new ArrayList<Command>();
    }
    
    public void addShape(Shape s) 
    {
        //shapes.add(s);
        Add Command=new Add(s,shapes);
        commandHistory.add(Command);
        Command.execute();
        shapes=Command.lista;
    }
    
    public void deleteShape(Shape s) 
    {
        //shapes.remove(s);
        Remove Command=new Remove(s,shapes);
        commandHistory.add(Command);
        Command.execute();
        shapes=Command.lista;
    }
    
    public void moveShape(Shape s, int x, int y) 
    {
        Move Command=new Move(s,shapes,x,y);
        commandHistory.add(Command);
        Command.execute();
        s=Command.target;
        //s.move(x, y);
    }
    
    public void scaleShape(Shape s, int x, int y) 
    { 
        Scale Command=new Scale(s,shapes,x,y);
        commandHistory.add(Command);
        Command.execute();
        s=Command.target;
        //s.scale(factor);
    }
    
    public void undo(Shape s)
    {
        int i=commandHistory.size();
        Command command=commandHistory.get(i-1);
        command.undo();
        shapes=command.backupList;   
        s=command.backup;
        commandHistory.remove(i-1);
    }
    
}