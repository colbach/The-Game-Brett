/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.PSS;

import java.util.ArrayList;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Field;

/**
 *
 * @author Kore
 */
public class PSS_Board extends Board{

    private ArrayList<PSS_Field> fields = new ArrayList<>();
    private String[] fieldContent;
    private double r;
    private RelativePoint pos;
    private Layout layout;
    
    private final float ratioX = 1;
    private final float ratioY = 1;
    
    public PSS_Board(String[] fieldContent){
        super();
        this.layout = new Layout();
        this.fieldContent = fieldContent;
        createFields();
    }
    
    private void createFields() {
        
        r = relativateRatio(9.0);
        //1=rechts, 2= oben, 3=unten, 4=links
        int direction = 0;
        pos = new RelativePoint(0, 0);
        
        int startX = 0;
        int startY = 7;
            
        for(int i = 0; i<72; i++){
             
            switch(i)
            {
                //richtung rechts
                case 0: case 29: case 51: case 65:    
                    direction = 1;
                    break;
                //richtung oben
                case 8: case 36: case 56: case 68:    
                    direction = 2;
                    break;
                //richtung links
                case 15: case 41: case 59: case 69:    
                    direction = 4;
                    break;
                //richtung unten
                case 23: case 47: case 63:    
                    direction = 3;
                    break;
                
                default:   
                    break;
                }
            
            
            switch (direction) {
                case 1:
                    pos = new RelativePoint(startX*r, startY*r);
                    startX += 1;
                    break;
                case 2:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += -1;
                    break;
                case 3:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += 1;
                    break;
                case 4: 
                    pos = new RelativePoint(startX*r, startY*r);
                    startX += -1;
                    break;
                default:
                    break;
            }
            
            Layout fieldLayout = new Layout(i+"", fieldContent[i]);
            fieldLayout.setFormFactor(Layout.FORM_FACTOR_SQUARE);
            
            float rat = 1.0f/9.0f;
            PSS_Field newField = new PSS_Field(i,ratioX/9,ratioY/9,pos,null,fieldLayout,null);
            if(!fields.isEmpty()){
                fields.get(fields.size()-1).addNext(newField);
            }
            
            fields.add(newField);   
        }        
    }
    
    @Override
    public int getFieldLength() {
        return fields.size();
    }

    //speziell auf das Feld, nicht allgemein
    public double relativateRatio(double i){
        return 1/i;
    }
    
    @Override
    public Field getField(int i) {
        if(fields == null){
            fields = new ArrayList<>();
            return null;
        }
        //abfragen ob index out of bound?
        return fields.get(i);

    }
    
    
    public ArrayList<PSS_Field> getFields() {
        return fields;
    }

    @Override
    public Layout getLayout() {
        return layout;
    }

    @Override
    public float getRatioX() {
        return ratioX;
    }

    @Override
    public float getRatioY() {
        return ratioY;
    }
    
    public void setFieldContent(String[] content){
        this.fieldContent = content;
    }
}
