/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.MADN;

import java.util.ArrayList;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Field;

/**
 *
 * @author Koré
 */
public class MADN_Board extends Board {

    private ArrayList<MADN_Field> fields = new ArrayList<>();
    //private ArrayList<MADNstartField> startFields;
    //private ArrayList<MADNendField> endFields;    
    private Layout layout;
    
    private final float ratioX = 1;
    private final float ratioY = 1;
    
    
    public MADN_Board(/*, int fieldLength*/){
        super();
        this.layout = new Layout();
        fields = createFields();
        
    }
    
    //hier ist noch alles zu tun
    public ArrayList<MADN_Field> createFields(){
        
        //koordinate zwischen 1 und 0
        double r = relativateRatio(13.0);
        //1=rechts, 2= oben, 3=unten, 4=links
        int direction = 0;
        RelativePoint pos = new RelativePoint(0, 0);
        
        //Erstes Feld ist das erste nach dem Startfeld von A
        int startX = 2;
        int startY = 6;
            
        for(int i = 1; i<41; i++){
             
            switch(i)
            {
                //richtung rechts
                case 1: case 9: case 15:    
                    direction = 1;
                    break;
                //richtung oben
                case 5: case 31: case 39:    
                    direction = 2;
                    break;
                //richtung unten
                case 11: case 19: case 25:    
                    direction = 3;
                    break;
                //richtung links
                case 21: case 29: case 35:    
                    direction = 4;
                    break;
                default:   
                    break;
                }
            
            
            switch (direction) {
                case 1:
                    pos = new RelativePoint(startX*r, startY*r);
                    startX += 1;
                    System.out.println("rechts");
                    break;
                case 2:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += -1;
                    System.out.println("oben");
                    break;
                case 3:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += 1;
                    System.out.println("unten");
                    break;
                case 4: 
                    pos = new RelativePoint(startX*r, startY*r);
                    startX += -1;
                    System.out.println("links");
                    break;
                default:
                    System.out.println("Keine gültige Richtung");
                    break;
            }
            
            Layout fLayout = new Layout();
            fLayout.setFormFactor(Layout.FORM_FACTOR_OVAL); // Farbe von Feldern etc
            MADN_Field newField = new MADN_Field(0.03, 0.03, pos, null, fLayout, null, 0);
            if(!fields.isEmpty()){
                fields.get(fields.size()-1).addNext(newField);
            }
            fields.add(newField);   
        }
        return fields;
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

    @Override
    public Layout getLayout() {
        return layout;
    }
    
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public ArrayList<MADN_Field> getFields() {
        return fields;
    }

    @Override
    public float getRatioX() {
        return ratioX;
    }

    @Override
    public float getRatioY() {
        return ratioY;
    }
    
    
}