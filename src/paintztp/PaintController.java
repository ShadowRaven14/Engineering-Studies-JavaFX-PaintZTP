package paintztp;
//import java.awt.Color;
import java.awt.image.BufferedImage;
import paintztp.ComputerGraphics.ViewerUpdateThread;
import paintztp.ComputerGraphics.PpmConverter;
import javafx.scene.paint.Color;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.canvas.Canvas;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.RotateTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import paintztp.Shapes.Shape;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.imageio.ImageIO;

//Klasa obsługująca paintfxml (nazwy w obu plikach muszą być jednakowe)
public class PaintController implements Initializable 
{
    
    //tworzenie zmiennych z pliku FXML
    @FXML public ColorPicker colorpickerID;
    @FXML private TextField brushsizeID;
    @FXML private Canvas canvasID;
    @FXML private Canvas secondcanvasID;
    @FXML private TableView <Shape> tableviewID;
    @FXML private TableColumn <Shape, String> firstColumnID;
    @FXML private TableColumn <Shape, String> secondColumnID;
    //@FXML private AnchorPane anchorpane3ID;
    //@FXML private TextField showcolorID;
    @FXML private TabPane tabpaneID;
    
    //pomoce do zmiennych
    int new_index = 0; //nowy index, przypisywany unikalnie do każdej figury
    final double zoomValue = 0.1; //szybkość powiększania
    int cubeRGB = 0; int cubeR = 0, cubeG = 0, cubeB = 0;
    Boolean max = false;
    
    //nasz rysik
    GraphicsContext paintTool;
    
    //Canvas
    paintztp.Painting.CanvasModelSingleton canvas = paintztp.Painting.CanvasModelSingleton.getInstance();
    private LinkedList<Shape> shapesToClone = new LinkedList<Shape>(); 
    
    //Obecnie zaznaczone figury
    private String currentshape = "";
    
    
    
    //inicjalizacja i kod główny, obsługa w czasie rzeczywistym
    @Override public void initialize(URL url, ResourceBundle rb) 
    {
        //Posiadamy wczesną inicjalizację
        
        //Prototyp
        /*
        Shape protCircle = new paintztp.Shapes.Circle();
        Shape protLine = new paintztp.Shapes.Line();
        Shape protTriangle = new paintztp.Shapes.Triangle();
        Shape protRectangle = new paintztp.Shapes.Rectangle();
        shapesToClone.add(protCircle);
        shapesToClone.add(protLine);
        shapesToClone.add(protTriangle);
        shapesToClone.add(protRectangle);
        */
        paintTool = canvasID.getGraphicsContext2D(); //ustawienie rysika na płótnie
        firstColumnID.setCellValueFactory(new PropertyValueFactory <Shape, String>("name")); //obsługa TableView
        
        
      //OBSŁUGA ZACHOWAŃ MYSZKI - INTEFEJS UŻYTKOWNIKA
        //KLIKNIĘCIE MYSZKĄ NA KOMÓRKĘ TABLEVIEW
        tableviewID.setOnMousePressed(e -> 
        {
            //LEWY PRZYCISK
            if (e.getButton() == MouseButton.PRIMARY) 
            {
                //POJEDYNCZE KLIKNIĘCIE - USTAWIENIE BIEŻĄCEJ FIGURY
                if (e.getClickCount() == 1) 
                {
                    //wypisywanie danych komórki do konsoli
                    System.out.println(tableviewID.getSelectionModel().getSelectedItem());
                    System.out.println(tableviewID.getSelectionModel().getSelectedItem().getName());
                    
                    //zaznaczenie którą figurą chcemy operować
                    currentshape = tableviewID.getSelectionModel().getSelectedItem().getName();
                } 
                //PODWÓJNE KLIKNIĘCIE - ZMIANA NAZWY FIGURY
                else if (e.getClickCount() == 2) 
                {
                    RenameShapeWindow(tableviewID.getSelectionModel().getSelectedItem());
                    refresh();
                }
            }
            //PRAWY PRZYCISK, POJEDYNCZE KLIKNIĘCIE - USUWANIE
            else if (e.getButton() == MouseButton.SECONDARY) 
            {
                currentshape = "";
                canvas.objects.shapes.remove(tableviewID.getSelectionModel().getSelectedItem());
                refresh();
            }
            
        });
      
        //PRZECIĄGANIE MYSZKĄ PO CANVAS
        canvasID.setOnMouseDragged(e -> 
        {
            double size = Double.parseDouble(brushsizeID.getText());
            double x = e.getX() - size/2;
            double y = e.getY() - size/2;
            
            //LEWY PRZYCISK - PORUSZANIE
            if (e.getButton() == MouseButton.PRIMARY) 
            {
                for (Shape s : canvas.objects.shapes)
                    {
                        if(currentshape == s.getName()) 
                        {
                            s.move((int)x, (int)y);
                            refresh();   
                        }
                            
                    }
            }
            //PRAWY PRZYCISK - SKALOWANIE FIGURY
            else if (e.getButton() == MouseButton.SECONDARY) 
            {
                for (Shape s : canvas.objects.shapes)
                    {
                        if(currentshape == s.getName()) 
                        {
                            s.scale((int)x, (int)y);
                            s.setObSize((int)x);
                            refresh();
                        }
                    }
            }          
        }); 
        
        ////KLIKNIĘCIE MYSZKĄ NA CANVAS
        canvasID.setOnMouseClicked(e -> 
        {
            //ZMIANA PRZYCISK - ZMIANA KOLORU
            if (e.getButton() == MouseButton.PRIMARY) 
            {
                for (Shape s : canvas.objects.shapes)
                    {
                        //zmień kolor
                        if(currentshape == s.getName()) 
                        {
                            s.setColor(
                            (int)(colorpickerID.getValue().getRed() * 255), 
                            (int)(colorpickerID.getValue().getGreen() * 255),
                            (int)(colorpickerID.getValue().getBlue() * 255)); 
                            
                            refresh();
                        }     
                    }
            }
            
            //PRAWY PRZYCISK
            if (e.getButton() == MouseButton.SECONDARY) 
                {
                    //PODWÓJNE KLIKNIĘCIE - KLONOWANIE
                    if (e.getClickCount() == 2) 
                    {
                            for (Shape s : canvas.objects.shapes)
                            {
                                if(currentshape == s.getName()) 
                                {
                                    
                                    //tableviewID.getItems().add(s.clone());
                                    //shapesToClone.add(s.clone());
                                    Shape newShape = s.clone();
                                    canvas.objects.addShape(newShape);
                                    tableviewID.getItems().add(newShape);
                                    //shapesToClone.add(newShape);
                                    refresh();
                                }
                            }
                    }
                        
                }
            
            //ŚRODKOWY PRZYCISK
            if (e.getButton() == MouseButton.MIDDLE) 
            {
                double size = Double.parseDouble(brushsizeID.getText());
                double x = e.getX() - size/2;
                double y = e.getY() - size/2;
                for (Shape s : canvas.objects.shapes)
                            {
                                if(currentshape == s.getName()) 
                                {                                
                                    Shape newShape = s.clone();
                                    canvas.objects.addShape(newShape);
                                    tableviewID.getItems().add(newShape);
                                    newShape.setObSize(Integer.parseInt(brushsizeID.getText()));
                                    newShape.setColor(s.getColorR(),s.getColorG(),s.getColorB()); 
                                    newShape.move((int)x, (int)y);
                                    canvas.objects.shapes.remove(s);
                                    refresh();
                                }
                            }
            }
        });
        
        //SCROLLOWANIE PO CANVAS
        canvasID.setOnScroll(e -> 
        {
            //ODDALENIE OBRAZU
            if (e.isAltDown()) 
            {
                System.out.println("---");
                Scale newScale = new Scale();
                newScale.setX(canvasID.getScaleX() - zoomValue);
                newScale.setY(canvasID.getScaleY() - zoomValue);
                newScale.setPivotX(canvasID.getScaleX());
                newScale.setPivotY(canvasID.getScaleY());
                canvasID.getTransforms().add(newScale);
            }
            //POWIĘKSZANIE OBRAZU
            else
            {
                System.out.println("+++");
                Scale newScale = new Scale();
                newScale.setX(canvasID.getScaleX() + zoomValue);
                newScale.setY(canvasID.getScaleY() + zoomValue);
                newScale.setPivotX(canvasID.getScaleX());
                newScale.setPivotY(canvasID.getScaleY());
                canvasID.getTransforms().add(newScale);   
            }
            
        });
        
        //OBRACANIE FIGURY 3D
        tabpaneID.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Rotate xRotation = new Rotate(25, Rotate.X_AXIS);
                Rotate yRotation = new Rotate(25, Rotate.Y_AXIS);
                boxID.getTransforms().addAll(xRotation, yRotation);
                RotateTransition rt = new RotateTransition(Duration.millis(1000), boxID);
                switch (event.getCode()) {
                    case UP:
                            System.out.println("Kręcę figurą");
                            rt.setAxis(Rotate.X_AXIS);
                            rt.setByAngle(360);
                            rt.setCycleCount(5);
                            rt.play();
                        break;
                    case DOWN:  
                            System.out.println("Kręcę figurą");
                            rt.setAxis(Rotate.X_AXIS);
                            rt.setByAngle(180);
                            rt.setCycleCount(5);
                            rt.play();
                        break;
                    case LEFT:  
                            System.out.println("Kręcę figurą");
                            rt.setAxis(Rotate.Y_AXIS);
                            rt.setByAngle(360);
                            rt.setCycleCount(5);
                            rt.play();
                        break;
                    case RIGHT: 
                            System.out.println("Kręcę figurą");
                            rt.setAxis(Rotate.Y_AXIS);
                            rt.setByAngle(180);
                            rt.setCycleCount(5);
                            rt.play();
                        break;
                }
            }
        });
        
        //PRZECIĄGANIE MYSZKĄ PO CANVAS
        tabpaneID.setOnMouseDragged(e -> 
        {
            //PRAWY PRZYCISK - SKALOWANIE FIGURY
            if (e.getButton() == MouseButton.SECONDARY) 
            {
                //Color c = new Color(1,1,1);
                PhongMaterial mat = new PhongMaterial();
                boxID.setMaterial(mat);
                
                colorpickerID.setValue(Color.rgb(cubeR, cubeG, cubeB));
                flashingRGB();

                mat.setDiffuseColor(colorpickerID.getValue());
            }          
        }); 
    }
    
    
    public void flashingRGB()
    {
       System.out.println(cubeR + ", " + cubeG + ", " + cubeB);

       if(max == false)
       {
           if(cubeR<255) cubeR++;
            else
            {
                 if(cubeG<255) cubeG++;
                 else
                 {
                     if(cubeB<255) cubeB++;
                     else max = true;
                 }
            }
       }
       else
       {
           if(cubeR>0) cubeR--;
            else
            {
                 if(cubeG>0) cubeG--;
                 else
                 {
                     if(cubeB>0) cubeB--;
                     else max = false;
                 }
            }
       }
       
        

    }
    
    
    //Odświeżanie stanu CanvasModelSingleton (rysowanie)
    public void refresh() 
    {
        //Czyszczenie płótna i tabeli
        paintTool.clearRect(0, 0, 2000, 2000);
        tableviewID.getItems().clear();
        
        //PRZEJŚCIE PO LIŚCIE I RYSOWANIE ELEMENTÓW ZGODNIE Z ATRYBUTAMI
        for (Shape s : canvas.objects.shapes)
        {
            //RYSOWANIE KÓŁKO
            if (s.getPointCount() == 1)
            {
                paintTool.setFill(javafx.scene.paint.Color.rgb(s.getColorR(), s.getColorG(), s.getColorB()));
                paintTool.fillOval(s.getPointsX(0), s.getPointsY(0), s.getObSize(), s.getObSize());
                if (s.getBorderWidth() > 0)
                    paintTool.strokeOval(s.getPointsX(0), s.getPointsY(0), s.getObSize(), s.getObSize());

                paintTool.setStroke(colorpickerID.getValue());

                tableviewID.getItems().add(s); //Dodanie obiektu do tabeli figur
            }

            //RYSOWNIE LINIA
            if (s.getPointCount() == 2) 
            {
                paintTool.setFill(javafx.scene.paint.Color.rgb(s.getColorR(), s.getColorG(), s.getColorB()));
                paintTool.fillRect(s.getPointsX(0), s.getPointsY(0), s.getPointsX(1), s.getPointsY(1));
                if (s.getBorderWidth() > 0)
                    paintTool.strokeLine(s.getPointsX(0), s.getPointsY(0), s.getPointsX(1), s.getPointsY(1));

                tableviewID.getItems().add(s); //Dodanie obiektu do tabeli figur
            }

            //RYSOWANIE TRÓJKĄT
            if (s.getPointCount() == 3) 
            {
                paintTool.setFill(javafx.scene.paint.Color.rgb(s.getColorR(), s.getColorG(), s.getColorB()));
                //paintTool.fillArc(s.getPointsX(0), s.getPointsY(0), s.getPointsX(1), s.getPointsY(1), null, null);
                //if (s.getBorderWidth() > 0)
                    //paintTool.strokeArc(s.getPointsX(0), s.getPointsY(0), s.getPointsX(1), s.getPointsY(1), null, null);
                
                tableviewID.getItems().add(s); //Dodanie obiektu do tabeli figur
            }

            //RYSOWANIE PROSTOKĄT
            if (s.getPointCount() == 4) 
            {
                paintTool.setFill(javafx.scene.paint.Color.rgb(s.getColorR(), s.getColorG(), s.getColorB()));
                paintTool.fillRect(s.getPointsX(0), s.getPointsY(0), s.getPointsX(2), s.getPointsY(2));
                if (s.getBorderWidth() > 0)
                    paintTool.strokeRect(s.getPointsX(0), s.getPointsY(0), s.getPointsX(2), s.getPointsY(2));

                tableviewID.getItems().add(s); //Dodanie obiektu do tabeli figur
            }
        }
    }
    
    

    
 
//Funkcje do obsługi zaznaczonego przycisku
//DZIAŁALNOŚCI PRZYCISKÓW
    //stwórz nowe płótno
    @FXML public void NewCanvas(ActionEvent e)
    {
        paintTool.clearRect(0, 0, 2000, 2000);
        tableviewID.getItems().clear();
    }
    
    //wybierz narzędzie linia
    @FXML public void SelectCircle()
    {
        System.out.println("kółko");
        //Shape s = shapesToClone.get(0).clone(); //Tworzenie obiektu z Prototypu
        Shape s = new paintztp.Shapes.Circle(Integer.parseInt(brushsizeID.getText()));
        currentshape = s.getName();
        
        s.setColor(
                (int)(colorpickerID.getValue().getRed() * 255), 
                (int)(colorpickerID.getValue().getGreen() * 255),
                (int)(colorpickerID.getValue().getBlue() * 255));
        
        canvas.objects.addShape(s); //Dodanie nowego obiektu do listy
        refresh();
    }
    
    //wybierz narzędzie linia
    @FXML public void SelectLine()
    {    
        System.out.println("linia");
        //Shape s = shapesToClone.get(1).clone(); //Tworzenie obiektu z Prototypu
        Shape s = new paintztp.Shapes.Line(Integer.parseInt(brushsizeID.getText()));
        currentshape = s.getName();
        
        s.setColor(
                (int)(colorpickerID.getValue().getRed() * 255), 
                (int)(colorpickerID.getValue().getGreen() * 255),
                (int)(colorpickerID.getValue().getBlue() * 255));
        
        canvas.objects.addShape(s); //Dodanie nowego obiektu do listy
        refresh();
    }
    
    //wybierz narzędzie linia
    @FXML public void SelectTriangle()
    {
        System.out.println("trójkąt");
        //Shape s = shapesToClone.get(2).clone(); //Tworzenie obiektu z Prototypu
        Shape s = new paintztp.Shapes.Triangle(Integer.parseInt(brushsizeID.getText()));
        
        s.setColor(
                (int)(colorpickerID.getValue().getRed() * 255), 
                (int)(colorpickerID.getValue().getGreen() * 255),
                (int)(colorpickerID.getValue().getBlue() * 255));
        
        currentshape = s.getName(); //Dodanie nowego obiektu do listy
        refresh();
    }
    
    //wybierz narzędzie linia
    @FXML public void SelectRectangle()
    {  
        System.out.println("prostokąt");
        //Shape s = shapesToClone.get(3).clone(); //Tworzenie obiektu z Prototypu
        Shape s = new paintztp.Shapes.Rectangle(Integer.parseInt(brushsizeID.getText()));
        currentshape = s.getName();
        
        s.setColor(
                (int)(colorpickerID.getValue().getRed() * 255), 
                (int)(colorpickerID.getValue().getGreen() * 255),
                (int)(colorpickerID.getValue().getBlue() * 255));
        
        
        canvas.objects.addShape(s); //Dodanie nowego obiektu do listy
        refresh();
    }
    
    
    
    
    //ZAPISYWANIE DO PLIKU
    @FXML private void SaveToFile(ActionEvent e)
    {
            FileChooser savefile = new FileChooser();
            savefile.setTitle("Save File");
            Stage saveStage = new Stage();
            File file = savefile.showSaveDialog(saveStage);
            if (file != null) 
            {
                try 
                {
                    WritableImage writableImage = new WritableImage(1080, 790);
                    canvasID.snapshot(null, writableImage);
                    RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                    ImageIO.write(renderedImage, "png", file);
                } 
                catch (IOException ex) 
                {
                    System.out.println("Error!");
                }
            }
    }
    
    
    //WCZYTYWANIE DO PLIKU
    @FXML private void OpenFromFile(ActionEvent e)
    {
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open File");
        Stage openStage = new Stage();
        File file = openFile.showOpenDialog(openStage);
        if (file != null) 
        {
                try 
                {
                    InputStream io = new FileInputStream(file);
                    Image img = new Image(io);
                    paintTool.drawImage(img, 0, 0);
                } 
                catch (IOException ex) 
                {
                    System.out.println("Error!");
                }
        }
    }
    
    
    
    
    //Zmiana nazwy figury
    private void RenameShapeWindow(Shape shape)
    {
        TextField newName = new TextField();
        newName.setPromptText("Name");
        newName.setPrefWidth(150);
        newName.setPrefHeight(20);
        newName.setAlignment(Pos.CENTER);
        
        Button applyButton = new Button();
        applyButton.setText("Apply");
        
        //nowe pole widoku
        VBox viewBox = new VBox();
        viewBox.setSpacing(5);
        viewBox.setAlignment(Pos.CENTER);
        viewBox.getChildren().addAll(newName, applyButton);
        
        //nowa instancja (scena)
        Stage renameShapeStage = new Stage();
        AnchorPane root  = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefWidth(200);
        root.getChildren().add(viewBox);
        Scene renameShapeScene = new Scene(root);
        renameShapeStage.setTitle("Rename shape");
        renameShapeStage.setScene(renameShapeScene);
        renameShapeStage.show();
        
        //Funkcjonalność
        applyButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) 
            {
                shape.setName(newName.getText());
                currentshape = shape.getName();
                renameShapeStage.close();
            }
        });  
    }
    
    
    //Zmiana nazwa figury
    @FXML private void TransformColor(ActionEvent e)
    {
        TextField newValue = new TextField();
        newValue.setPromptText("Value");
        newValue.setPrefWidth(150);
        newValue.setPrefHeight(20);
        newValue.setAlignment(Pos.CENTER);
        
        Button applyButton = new Button();
        applyButton.setText("Apply");
        
        //nowe pole widoku
        VBox viewBox = new VBox();
        viewBox.setSpacing(5);
        viewBox.setAlignment(Pos.CENTER);
        viewBox.getChildren().addAll(newValue, applyButton);
        
        //nowa instancja (scena)
        Stage renameShapeStage = new Stage();
        AnchorPane root  = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefWidth(200);
        root.getChildren().add(viewBox);
        Scene renameShapeScene = new Scene(root);
        renameShapeStage.setTitle("Transform Color");
        renameShapeStage.setScene(renameShapeScene);
        renameShapeStage.show();
        
        //Funkcjonalność
        applyButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t) 
            {
                
            }
        });  
    }
    
    //GRAFIKA NR2
    //FORMAT PPM P3
    @FXML private ImageView imageViewID;
    private Stage stage2;
    private ViewerUpdateThread viewerUpdateThread;
    
    @FXML private void OpenP3FromFile(ActionEvent e)
    {
        File ppmFile = getPpmFile();
            if (ppmFile != null)
                bindImageFileToViewer(ppmFile, imageViewID);
    }
    
     private File getPpmFile() 
     {
        FileChooser imageChooser = new FileChooser();
        imageChooser.setTitle("Open Image");
        imageChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PPM Image", "*.ppm")
        );
        return imageChooser.showOpenDialog(stage2);
    }

    private void bindImageFileToViewer(File imageFile, ImageView imageView) 
    {
        // Halt update thread if one exists
        if (viewerUpdateThread != null) {
            viewerUpdateThread.interrupt();
        }
        // Set the current image to the new image
        imageView.setImage(PpmConverter.convert(imageFile));

        // Create a new thread for this image
        viewerUpdateThread = new ViewerUpdateThread(imageView, imageFile);
        viewerUpdateThread.start();
    }
    
    //ZADANIE NR3
    //KOSTKA 3D
    @FXML private Group cubegroupID;
    @FXML private Box boxID;
    
    
    public void select3DCube() {
        boxID = null;
        boxID = createCube();
        cubegroupID.getChildren().add(boxID);
    }
    
    private Box createCube()
    {
        Box box = new Box();
        box.setWidth(Integer.parseInt(brushsizeID.getText())*10); //x size
        box.setHeight(Integer.parseInt(brushsizeID.getText())*10); //y size
        box.setDepth(Integer.parseInt(brushsizeID.getText())*10); //z size
        
        box.setTranslateX(300);
        box.setTranslateY(300);
        box.setTranslateZ(0);
        
        PhongMaterial mat = new PhongMaterial();
        mat.setSpecularColor(Color.BLACK);
        mat.setDiffuseColor(colorpickerID.getValue());
        System.out.println(colorpickerID.getValue());
        box.setMaterial(mat);
               
        Rotate xRotation = new Rotate(25, Rotate.X_AXIS);
        Rotate yRotation = new Rotate(25, Rotate.Y_AXIS);
        box.getTransforms().addAll(xRotation, yRotation);
        
       RotateTransition rt = new RotateTransition(Duration.millis(1000), box);
       rt.setAxis(Rotate.Y_AXIS);
       rt.setByAngle(360);
       rt.setCycleCount(3);
       rt.play();
       
       System.out.println("Tworzę figurę 3D");
       return box;
    }
    
    @FXML public void changeCubeColor()
    {
        PhongMaterial mat = new PhongMaterial();
        mat.setSpecularColor(Color.BLACK);
        mat.setDiffuseColor(colorpickerID.getValue());
        System.out.println(colorpickerID.getValue());
        boxID.setMaterial(mat);
    }
    
    //MALOWANIE
    @FXML public TextField rID, gID, bID;
    @FXML public TextField cID, mID, yID, kID;
    
    @FXML public void changeColor()
    {
        rID.setText(String.valueOf((int)(colorpickerID.getValue().getRed() * 255)));
        gID.setText(String.valueOf((int)(colorpickerID.getValue().getGreen() * 255)));
        bID.setText(String.valueOf((int)(colorpickerID.getValue().getBlue() * 255)));
        RGBtoCMYK();
        changeCubeColor();
    }
    
    
    @FXML public void transformCurrentColor()
    {
        int r = Integer.parseInt(rID.getText());
        int g = Integer.parseInt(gID.getText());
        int b = Integer.parseInt(bID.getText()); 
        colorpickerID.setValue(Color.rgb(r, g, b));
        changeCubeColor();
    }
    
    @FXML public void RGBtoCMYK()
    {
        int[] cmyk = rgbToCmyk(
            Integer.parseInt(rID.getText()),
            Integer.parseInt(gID.getText()),
            Integer.parseInt(bID.getText())
        );
        cID.setText(String.valueOf(cmyk[0]));
        mID.setText(String.valueOf(cmyk[1]));
        yID.setText(String.valueOf(cmyk[2]));
        kID.setText(String.valueOf(cmyk[3]));
        System.out.println(Arrays.toString(cmyk));
        changeCubeColor();
    }
    private static int[] rgbToCmyk(int r, int g, int b) {
        double percentageR = r / 255.0 * 100;
        double percentageG = g / 255.0 * 100;
        double percentageB = b / 255.0 * 100;

        double k = 100 - Math.max(Math.max(percentageR, percentageG), percentageB);

        if (k == 100) {
            return new int[]{ 0, 0, 0, 100 };
        }

        int c = (int)((100 - percentageR - k) / (100 - k) * 100);
        int m = (int)((100 - percentageG - k) / (100 - k) * 100);
        int y = (int)((100 - percentageB - k) / (100 - k) * 100);

        return new int[]{ c, m, y, (int)k };
    }
    
    @FXML public void CMYKtoRGB()
    {
        int[] rgb = cmykToRgb(
            Integer.parseInt(cID.getText()),
            Integer.parseInt(mID.getText()),
            Integer.parseInt(yID.getText()),
            Integer.parseInt(kID.getText())
        );
        rID.setText(String.valueOf(rgb[0]));
        gID.setText(String.valueOf(rgb[1]));
        bID.setText(String.valueOf(rgb[2]));
        System.out.println(Arrays.toString(rgb));
        changeCubeColor();
    }
    private static int[] cmykToRgb(int c, int m, int y, int k) {
        int r = 255 * (1 - c/100) * (1 - k/100);
        int g = 255 * (1 - m/100) * (1 - k/100);
        int b = 255 * (1 - y/100) * (1 - k/100);
        int[] rgb = new int[]{ r, g, b };

        
        //rID.setText("0");
        //rID.textProperty() = String.valueOf(r);
        //rID.tex
        
        return rgb;
    }
    
    
    //ZADANIE NR4
    public BufferedImage imageOrginal;
    @FXML private ImageView imageViewTransID;
    @FXML public BufferedImage OpenFromFileV2(ActionEvent e){
        FileChooser openFile = new FileChooser();
        openFile.setTitle("Open File");
        Stage openStage = new Stage();
        
        openFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png"));
        File file = openFile.showOpenDialog(openStage);

        try {imageOrginal = ImageIO.read(file);}
        catch (IOException ex) {ex.printStackTrace();}
        
        imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(imageOrginal));
        return imageOrginal;
    }
    @FXML protected void onAddBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.add(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    @FXML protected void onSubtractBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.subtract(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    @FXML protected void onMultiplyBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.multiply(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    @FXML protected void onDivideBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.divide(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    @FXML protected void onGrayscaleBtnClick1() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.grayscale1(imageOrginal);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    @FXML protected void onGrayscaleBtnClick2() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage addImage = paintztp.FilterAlgorithms.BasicAlgorithms.grayscale2(imageOrginal);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(addImage));
        }
    }
    
    @FXML protected void onMedianBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage medianedImage = paintztp.FilterAlgorithms.MedianAlgorithm.median(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(medianedImage));
        }
    }
    @FXML protected void onAverageBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage medianedImage = paintztp.FilterAlgorithms.AveragingAlgorithm.average(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(medianedImage));
        }
    }
    
    @FXML protected void onEdgesBtnClick() {
        if(brushsizeID.getText() != "") {
            int PIX_SIZE = Integer.parseInt(brushsizeID.getText());
            BufferedImage medianedImage = paintztp.FilterAlgorithms.EdgeDetectorAlgorithm.detect(imageOrginal, PIX_SIZE);
            imageViewTransID.setImage(paintztp.FilterAlgorithms.FileHandler.convertToFxImage(medianedImage));
        }
    }
    
}