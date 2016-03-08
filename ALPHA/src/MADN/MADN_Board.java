/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MADN;

import java.util.ArrayList;
import Model.Layout;
import Model.RelativePoint;
import Model.Elements.Board;
import Model.Elements.Field;

/**
 *
 * @author Koré
 */
public class MADN_Board extends Board{

    private ArrayList<MADN_Field> fields = new ArrayList<>();
    //private ArrayList<MADNstartField> startFields;
    //private ArrayList<MADNendField> endFields;    
    private Layout layout;
    private final double ratioX = 1;
    private final double ratioY = 1;
    
    
    public MADN_Board(/*Layout layout/*, int fieldLength*/){
        
        this.layout = layout;
        fields = createFields();
        System.out.println("MADN_Board wurde erstellt ");
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
                    System.out.println("rechts " + pos.getXRelative());
                    break;
                case 2:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += -1;
                    System.out.println("oben " + pos.getXRelative());
                    break;
                case 3:
                    pos = new RelativePoint(startX*r, startY*r);
                    startY += 1;
                    System.out.println("unten " + pos.getXRelative());
                    break;
                case 4: 
                    pos = new RelativePoint(startX*r, startY*r);
                    startX += -1;
                    System.out.println("links " + pos.getXRelative());
                    break;
                default:
                    System.out.println("Keine gültige Richtung");
                    break;
            }
            
            MADN_Field newField = new MADN_Field(1, 1, pos, null, layout, null, 0);
                if(!fields.isEmpty()){
                    fields.get(fields.size()-1).addNext(newField);
                }

//            System.out.println(newField.getPosition().getXRelative()); ////
//            System.out.println(newField.getPosition().getYRelative()); ////
            fields.add(newField); 
        }

        return null;
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
    public MADN_Field getField(int i) {
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
    public double getRatioX() {
        return ratioX;
    }

    @Override
    public double getRatioY() {
        return ratioY;
    }
    
    
}