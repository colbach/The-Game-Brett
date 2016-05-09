/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.dummy;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Field;

public class D_Board extends Board{

    private ArrayList<D_Field> fields = new ArrayList<>();
    //private ArrayList<MADNstartField> startFields;
    //private ArrayList<MADNendField> endFields;    
    private Layout boardLayout;
    
    
    public D_Board(){
        this.boardLayout = new Layout();
        boardLayout.setBackgroundColor(Color.ALICEBLUE);
        fields = createFields();
    }
    
    public ArrayList<D_Field> createFields(){
        
        ArrayList<D_Field> al = new ArrayList<>();
        
        Layout fieldLayout = new Layout();
        fieldLayout.setFormFactor(Layout.FORM_FACTOR_SQUARE);
        fieldLayout.setBackgroundColor(Color.LIGHTGRAY);
        
        {
            int offX = 150;
            for(int i=0; i<4; i++) {
            al.add(new D_Field(0.15f, 0.1f, new RelativePoint(offX, 150), fieldLayout, i));
            offX += 200;
            }
        }
        {
            al.add(new D_Field(0.15f, 0.1f, new RelativePoint(750, 450), fieldLayout, 4));
        }
        {
            int offX = 750;
            for(int i=0; i<4; i++) {
                al.add(new D_Field(0.15f, 0.1f, new RelativePoint(offX, 700), fieldLayout, 5 + i));
                offX -= 200;
            }
        }
        {
            al.add(new D_Field(0.15f, 0.1f, new RelativePoint(150, 450), fieldLayout, 9));
        }
        for(int i=0; i<al.size(); i++) {
            al.get(i).setNext(al.get((i+1)%al.size()));
        }
                    
        return al;
    }
    
    @Override
    public int getFieldLength() {
        return fields.size();
    }
    
    @Override
    public Field getField(int i) {
        if(fields == null){
            throw new RuntimeException("fields not initialized");
        }
        return fields.get(i);
    }

    @Override
    public Layout getLayout() {
        return boardLayout;
    }
    
    public void setLayout(Layout layout) {
        registerChange();
        this.boardLayout = layout;
    }

    public ArrayList<D_Field> getFields() {
        return fields;
    }

    public static float RATIO_X = 1.2f;
    @Override
    public float getRatioX() {
        return RATIO_X;
    }

    @Override
    public float getRatioY() {
        return 1f;
    }
    
    
}
