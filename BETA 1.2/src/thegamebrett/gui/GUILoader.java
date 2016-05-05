package thegamebrett.gui;

import com.sun.javafx.tk.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Element;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

public class GUILoader {
    
    
    public static class Pair<Y, V> {
        private Y y;
        private V v;
        public Pair(Y y, V v) {
            this.y = y;
            this.v = v;
        }
        public Y getFirst() { return y; }
        public V getSecond() { return v; }
    }
    
    private static HashMap<Element, Canvas> drawnElements = new HashMap<>();

    public static void clear() {
        drawnElements.clear();
    }
    
    protected static Pair<Canvas[], HashMap<Canvas, Transition>> createFields(Board board) {
        HashMap<Canvas, Transition> movedFieldsCanvas = new HashMap<>();
        Canvas[] rs = new Canvas[board.getFieldLength()];
        for(int i=0; i<board.getFieldLength(); i++) {
            Field f = board.getField(i);
            Canvas c = drawnElements.get(f);
            
            if(c == null || f.isChangedSinceLastCall()) {
                Canvas newC = createField(f);
                if(c != null && (c.getLayoutX()!=newC.getLayoutX() || c.getLayoutY()!=newC.getLayoutY())) {
                    movedFieldsCanvas.put(newC, new Transition(c.getLayoutX(), c.getLayoutY(), newC.getLayoutX(), newC.getLayoutY()));
                }
                rs[i] = newC;
                drawnElements.put(f, newC);
            } else {
                rs[i] = c;
            }
        }
        return new Pair<Canvas[], HashMap<Canvas, Transition>>(rs, movedFieldsCanvas);
    }
    
    private static Canvas createField(Field field) {
        
        double x = field.getRelativePosition().getXOnScreen();
        double y = field.getRelativePosition().getYOnScreen();
        double w = ScreenResolution.relativeToPixelX(field.getWidthRelative());
        double h = ScreenResolution.relativeToPixelY(field.getHeightRelative());
        
        Canvas c = createCanvas(field.getLayout(), x, y, w, h);
        
        return c;
    }
    
    protected static Canvas[] createFigures(Model model, ObservableList<Node> children) {
        //HashMap<Canvas, Transition> movedFiguresCanvas = new HashMap<>();

        int length = 0;
        for(Player p : model.getPlayers()) {
            length += p.getFigures().length;
        }
        
        Canvas[] rs = new Canvas[length];
        
        HashMap<Field, ArrayList<Canvas>> fieldMap = new HashMap<>();
        
        {
            int i = 0;
            for (Player p : model.getPlayers()) {
                Figure[] fs = p.getFigures();

                for (Figure f : fs) {
                    ////////
                    Canvas c = drawnElements.get(f);
                    if(c == null) {
                        c = createFigure(f);
                        c.setUserData(new Transition(c.getLayoutX(), c.getLayoutY(), c.getLayoutX(), c.getLayoutY()));
                        children.add(c);
                    } else if(f.isChangedSinceLastCall()) {
                        Transition lastT = (Transition)c.getUserData();
                        children.remove(c);
                        c = createFigure(f);
                        c.setUserData(new Transition(lastT.getNewX(), lastT.getNewY(), c.getLayoutX(), c.getLayoutY()));
                        c.setLayoutX(lastT.getNewX());
                        c.setLayoutY(lastT.getNewY());
                        children.add(c);
                    } else {
                        Canvas tmp = createFigure(f); // aendern!!!!!
                        Transition lastT = (Transition)c.getUserData();
                        c.setUserData(new Transition(lastT.getNewX(), lastT.getNewY(), tmp.getLayoutX(), tmp.getLayoutY()));
                    }
                    rs[i] = c;
                    drawnElements.put(f, c);            
                    
                    if (fieldMap.containsKey(f.getField())) {
                        fieldMap.get(f.getField()).add(rs[i]);
                    } else {
                        ArrayList<Canvas> al = new ArrayList<>();
                        al.add(rs[i]);
                        fieldMap.put(f.getField(), al);
                    }
                    
                    i++;
                }
            }
        }
        {
            final float[][] shifts = new float[][]{{-1, -1}, {1, 1}, {1, -1}, {-1, 1}};
            for (ArrayList<Canvas> al : fieldMap.values()) {
                if (al.size() > 1) {
                    double shift = al.get(0).getWidth() / 4;
                    for (int i = 0; i < al.size(); i++) {
                        Canvas critical = al.get(i);
                        Transition transition = (Transition)critical.getUserData();
                        double newX = transition.getNewX() + shifts[i%4][0]*shift;
                        double newY = transition.getNewY() + shifts[i%4][1]*shift;
                        double oldX;
                        double oldY;
                        //System.out.println("newX="+newX + " transition.getNewX()="+transition.getNewX());
                        critical.setUserData(new Transition(transition.getOldX(), transition.getOldY(), newX, newY));
                        
                    }
                }
            }
        }
        return rs;
    }
    
    private static Canvas createFigure(Figure figure) {
        double w = ScreenResolution.relativeToPixelX(figure.getRelativeWidth());
        double h = ScreenResolution.relativeToPixelY(figure.getRelativeHeight());
        double x = figure.getField().getRelativePosition().getXOnScreen() + ScreenResolution.relativeToPixelX(figure.getField().getWidthRelative())/2 - w/2;
        double y = figure.getField().getRelativePosition().getYOnScreen() + ScreenResolution.relativeToPixelY(figure.getField().getHeightRelative())/2 - h/2;
        
        Canvas c = createCanvas(figure.getLayout(), x, y, w, h);
        
        return c;
    }
    
    protected static Canvas createBoardBackground(Layout layout) {
        double w = ScreenResolution.relativeToPixelX(1);
        double h = ScreenResolution.relativeToPixelY(1);
        
        Canvas c = new Canvas(w, h);
        c.setLayoutX(ScreenResolution.getContentXOff()+0);
        c.setLayoutY(ScreenResolution.getContentYOff()+0);
        
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(layout.getBackgroundColor());
        gc.fillRect(0, 0, w, h);
        
        return c;
    }
    
    private static Canvas createCanvas(Layout layout, double x, double y, double w, double h) {
        
        // Done:  backgroundColor border borderColor formFactor backgroundImage
        // To Do: 
        
        if(layout == null) {
            layout = new Layout();
            System.err.println("Layout ist null");
        }
        Canvas c = new Canvas(w+layout.getBorder()*4, h+layout.getBorder()*4);
        c.setLayoutX(ScreenResolution.getContentXOff()+x-layout.getBorder()*2);
        c.setLayoutY(ScreenResolution.getContentYOff()+y-layout.getBorder()*2);
        
        GraphicsContext gc = c.getGraphicsContext2D();
        
        // --- Formfaktor ---
        if(layout.getFormFactor() == Layout.FORM_FACTOR_OVAL) {
            setFill(gc, layout, w, h);
            gc.fillOval(layout.getBorder()*2, layout.getBorder()*2, w, h);
            gc.setLineWidth(layout.getBorder());
            gc.setStroke(layout.getBorderColor());
            gc.strokeOval(layout.getBorder()*2, layout.getBorder()*2, w, h);
        } else if(layout.getFormFactor() == Layout.FORM_FACTOR_SQUARE) {
            setFill(gc, layout, w, h);
            gc.fillRect(layout.getBorder()*2, layout.getBorder()*2, w, h);
            gc.setLineWidth(layout.getBorder());
            gc.setStroke(layout.getBorderColor());
            gc.strokeRect(layout.getBorder()*2, layout.getBorder()*2, w, h);
        } else {
            System.err.println("Unbekannter Formfaktor");
        }
        
        // --- Icon ---
        
        double down = 2+layout.getBorder();
        
        if(layout.getIconImage() != null) {
            
            down += 2+layout.getBorder();
           
            Canvas test = new Canvas(20, 20);
            GraphicsContext ctest = test.getGraphicsContext2D();
            ctest.setFill(Color.RED);
            ctest.fillOval(0, 0, 20, 20);
            WritableImage wi = new WritableImage(20, 20);
            test.snapshot(null, wi);

            gc.drawImage(wi, (w-wi.getWidth())/2d, down);
        
            down += wi.getHeight();
        }
        
        
        // --- Titel ---
                
        gc.setFill(layout.getTitleColor());
        final double titelSize = 15*(1+layout.getTitleScaleFactor());
        gc.setFont(Font.font("Arial", FontWeight.BOLD, titelSize));
        
        String line = "";
        for(String word : layout.getTitle().split(" ")) {
            if(textWidth(line + " " + word, gc) > w-2 && line.length() != 0) {
                down += textHeight(line, gc);
                double off = 2;
                if(layout.isCenterTitle()) {
                    off = (w - textWidth(line, gc))/2d;
                }
                gc.fillText(line, off, down);
                line = "";
            } else {
                line += " " + word;
            }
        }
        if(line.length() != 0) {
            down += textHeight(line, gc);
            double off = 2;
            if(layout.isCenterTitle()) {
                off = (w - textWidth(line, gc))/2d;
            }
            gc.fillText(line, off, 2+down);
            line = "";
        }
        
        // --- Subtext ---
                
        gc.setFill(layout.getTitleColor());
        final double subtitelSize = 13*(1+layout.getTitleScaleFactor());
        gc.setFont(Font.font("Arial", FontWeight.LIGHT, titelSize));
        
        for(String word : layout.getSubtext().split(" ")) {
            if(textWidth(line + " " + word, gc) > w-2 && line.length() != 0) {
                down += textHeight(line, gc);
                double off = 2;
                if(layout.isCenterSubtext()) {
                    off = (w - textWidth(line, gc))/2d;
                }
                gc.fillText(line, off, 2+down);
                line = "";
            } else {
                line += " " + word;
            }
        }
        if(line.length() != 0) {
            down += textHeight(line, gc);
            double off = 2;
            if(layout.isCenterSubtext()) {
                off = (w - textWidth(line, gc))/2d;
            }
            gc.fillText(line, off, 2+down);
            line = "";
        }
        
        return c;
    }
    
    private static float textWidth(String s, GraphicsContext gc) {
        return Toolkit.getToolkit().getFontLoader().computeStringWidth(s, gc.getFont());
    }
    
    private static float textHeight(String s, GraphicsContext gc) {
        return Toolkit.getToolkit().getFontLoader().getFontMetrics(gc.getFont()).getLineHeight();
    }
    
    private static void setFill(GraphicsContext gc, Layout l, double w, double h) {
        if(l.getBackgroundImage() != null) {
            Image image = l.getBackgroundImage();
            Image bg = l.getBackgroundImage();
            ImagePattern imagePattern;
            if(l.getBackgroundImageFillFactor() == Layout.BACKGROUND_IMAGE_FILL_FACTOR_REPEAT)
                imagePattern = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
            else if(l.getBackgroundImageFillFactor() == Layout.BACKGROUND_IMAGE_FILL_FACTOR_FILL) {
                imagePattern = new ImagePattern(image, 0, 0, w, h, false);
            } else { // default
                imagePattern = new ImagePattern(image, 0, 0, w, h, false);
            }
            gc.setFill(imagePattern);
        } else {
            gc.setFill(l.getBackgroundColor());
        }
    }
}