package thegamebrett.gui;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Element;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

public class GUILoader {
    
    public static class Transition {
        public final double oldX, oldY, newX, newY;
        public Transition(double oldX, double oldY, double newX, double newY) {
            this.oldX = oldX;
            this.oldY = oldY;
            this.newX = newX;
            this.newY = newY;
        }
        public double getOldX() {  return oldX;  }
        public double getOldY() {  return oldY;  }
        public double getNewX() {  return newX;  }
        public double getNewY() {  return newY;  }
    }
    
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
    
    protected static Pair<Canvas[], HashMap<Canvas, GUILoader.Transition>> createFields(Board board) {
        HashMap<Canvas, GUILoader.Transition> movedFieldsCanvas = new HashMap<>();
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
        return new Pair<Canvas[], HashMap<Canvas, GUILoader.Transition>>(rs, movedFieldsCanvas);
    }
    
    private static Canvas createField(Field field) {
        
        double x = field.getRelativePosition().getXOnScreen();
        double y = field.getRelativePosition().getYOnScreen();
        double w = ScreenResolution.relativeToPixelX(field.getWidthRelative());
        double h = ScreenResolution.relativeToPixelY(field.getHeightRelative());
        
        Canvas c = createCanvas(field.getLayout(), x, y, w, h);
        
        return c;
    }
    
    protected static Pair<Canvas[], HashMap<Canvas, GUILoader.Transition>> createFigures(Model model) {
        HashMap<Canvas, GUILoader.Transition> movedFiguresCanvas = new HashMap<>();
       
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
                    if(c == null || f.isChangedSinceLastCall()) {
                        Canvas newC = createFigure(f);
                        if(c != null && (c.getLayoutX()!=newC.getLayoutX() || c.getLayoutY()!=newC.getLayoutY())) {
                            movedFiguresCanvas.put(newC, new Transition(c.getLayoutX(), c.getLayoutY(), newC.getLayoutX(), newC.getLayoutY()));
                        }
                        rs[i] = newC;
                        drawnElements.put(f, newC);
                    } else {
                        rs[i] = c;
                    }
                    ////////                    
                    
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
                        Transition correctedTransition = movedFiguresCanvas.get(critical);
                        double newX = al.get(i).getLayoutX() + shifts[i%4][0]*shift;
                        double newY = al.get(i).getLayoutY() + shifts[i%4][1]*shift;
                        double oldX;
                        double oldY;
                        if(correctedTransition == null) {
                            oldX = critical.getLayoutX();
                            oldY = critical.getLayoutY();
                        } else {
                            oldX = correctedTransition.getOldX();
                            oldY = correctedTransition.getOldY();
                            movedFiguresCanvas.remove(correctedTransition);
                        }
                        movedFiguresCanvas.put(critical, new Transition(oldX, oldY, newX, newY));
                        critical.setLayoutX(newX);
                        critical.setLayoutY(newY);
                    }
                }
            }
        }
        return new Pair<Canvas[], HashMap<Canvas, GUILoader.Transition>>(rs, movedFiguresCanvas);
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
        
        Canvas c = new Canvas(w+layout.getBorder()*4, h+layout.getBorder()*4);
        c.setLayoutX(ScreenResolution.getContentXOff()+x-layout.getBorder()*2);
        c.setLayoutY(ScreenResolution.getContentYOff()+y-layout.getBorder()*2);
        
        GraphicsContext gc = c.getGraphicsContext2D();
        
        System.out.println(layout.getFormFactor());
        
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
        
        
        return c;
    }
    
    private static void setFill(GraphicsContext gc, Layout l, double w, double h) {
        if(l.getBackgroundImage() != null) {
            Image image = l.getBackgroundImage();
            Image bg = l.getBackgroundImage();
            ImagePattern imagePattern;
            if(l.getBackgroundImageFillFactor() == Layout.BACKGROUND_IMAGE_FILL_FACTOR_REPEAT)
                imagePattern = new ImagePattern(image, 0, 0, image.getWidth(), image.getHeight(), false);
            else if(l.getBackgroundImageFillFactor() == Layout.BACKGROUND_IMAGE_FILL_FACTOR_FILL) {
                imagePattern = new ImagePattern(image, 0, 0, w, h, false); // nicht getestet
            } else {
                imagePattern = new ImagePattern(image, 0, 0, w, h, false); // falsch
            }
            gc.setFill(imagePattern);
        } else {
            gc.setFill(l.getBackgroundColor());
        }
    }
}
