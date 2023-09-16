package paintztp.Painting;
import paintztp.Commands.ListOfObjects;
import paintztp.Painting.ShapeColorAdjustment;
import paintztp.Shapes.Shape;

//Model i Singleton
public class CanvasModelSingleton 
{
    private static CanvasModelSingleton instance = new CanvasModelSingleton();
    private int width, height;
    Color col=new Color(255, 0, 0);
    
    // nie wiem czy public i static, czy jakoś inaczej kminić
    public static ListOfObjects objects = new ListOfObjects();
    private ShapeColorAdjustment currentShapeColorAdjustment;
    
    public static CanvasModelSingleton getInstance() 
    {
        return instance;
    }
    
    private CanvasModelSingleton() {};
    
    public void create(int width, int height) 
    {
        // TODO: sprawdzić czy liczby są poprawne
        this.width = width;
        this.height = height;
    }
    
    public void setShapeColorAdjustmentStrategy(ShapeColorAdjustment s) 
    {
        this.currentShapeColorAdjustment = s;
    }
    
    //czy na pewno void?
    public void executeShapeColorAdjustment(int value) 
    {
        this.currentShapeColorAdjustment.execute(col,value);
        int size=objects.shapes.size();
        for(int i = 0;i<size;i++)
        {
            Shape test=objects.shapes.get(i);
            test.setColor(col.r,col.g, col.b);
            objects.shapes.set(i, test);
        }
    }
}