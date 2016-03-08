package thegamebrett.gui;

import com.sun.beans.util.Cache;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import thegamebrett.model.Layout;
import thegamebrett.model.Model;
import thegamebrett.model.Player;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Field;
import thegamebrett.model.elements.Figure;

public class GUILoader {

    protected static Canvas[] createFields(Board board) {
        
        Canvas[] rs = new Canvas[board.getFieldLength()];
        for(int i=0; i<board.getFieldLength(); i++) {
            rs[i] = createField(board.getField(i));
        }
        
        return rs;
    }
    
    private static Canvas createField(Field field) {
        
        double x = field.getRelativePosition().getXOnScreen();
        double y = field.getRelativePosition().getYOnScreen();
        double w = ScreenResolution.relativeToPixelX(field.getWidthRelative());
        double h = ScreenResolution.relativeToPixelY(field.getHeightRelative());
        
        Layout layout = field.getLayout();
        
        Canvas c = new Canvas(w, h);
        c.setLayoutX(ScreenResolution.getContentXOff()+x);
        c.setLayoutY(ScreenResolution.getContentYOff()+y);
        
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(layout.getBackgroundColor());
        gc.fillRect(0, 0, w, h);
        gc.setLineWidth(layout.getBorder());
        gc.setStroke(layout.getBorderColor());
        gc.strokeRect(0, 0, w, h);
        
        
        //group.getChildren().add(rect);
        //group.addField(rect);
        
        
        return c;
    }
    
    protected static Canvas[] createFigures(Model model) {
        
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
                    rs[i] = createFigure(f);
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
                        al.get(i).setLayoutX(al.get(i).getLayoutX() + shifts[i%4][0]*shift);
                        al.get(i).setLayoutY(al.get(i).getLayoutY() + shifts[i%4][1]*shift);
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
        
        //System.out.println(x + " " + y + " " + w + " " + h + " ");
        
        Layout layout = figure.getLayout();
        
        Canvas c = new Canvas(w+layout.getBorder()*4, h+layout.getBorder()*4);
        c.setLayoutX(ScreenResolution.getContentXOff()+x-layout.getBorder()*2);
        c.setLayoutY(ScreenResolution.getContentYOff()+y-layout.getBorder()*2);
        
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.setFill(layout.getBackgroundColor());
        gc.fillOval(layout.getBorder()*2, layout.getBorder()*2, w, h);
        gc.setLineWidth(layout.getBorder());
        gc.setStroke(layout.getBorderColor());
        gc.strokeOval(layout.getBorder()*2, layout.getBorder()*2, w, h);
        
        //group.getChildren().add(rect);
        //group.addField(rect);
        
        c.toFront();
        
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
}
