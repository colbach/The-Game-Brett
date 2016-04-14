/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegamebrett.game.MADN;

import java.util.ArrayList;
import javafx.scene.paint.Color;
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
    private double r;
    private RelativePoint pos;
    
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
        r = relativateRatio(12.0);
        createNormalFields();
        createInitFields();
        createEndFields();  
        
        return fields;
    }
    public Layout getFLayout(int fieldType){
        Layout fLayout = new Layout();
        fLayout.setFormFactor(Layout.FORM_FACTOR_OVAL); // Farbe von Feldern etc
        if(fieldType == MADN_Field.FIELD_TYPE_START||fieldType==MADN_Field.FIELD_TYPE_END){
            fLayout.setBackgroundColor(Color.BROWN);
        } else {
            fLayout.setBackgroundColor(Color.BEIGE);
        }
        return fLayout;
    }
    
    public void createNormalFields(){
    //1=rechts, 2= oben, 3=unten, 4=links
        int direction = 0;
        pos = new RelativePoint(0, 0);
        
        //Erstes Feld ist das erste nach dem Startfeld von A
        int startX = 1;
        int startY = 5;
            
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
            
            
            int fieldType = MADN_Field.FIELD_TYPE_NORMAL;
            if((fields.isEmpty())||((fields.size()-1)%10==9)){
                fieldType = MADN_Field.FIELD_TYPE_START;
            }
            MADN_Field newField = new MADN_Field(0.05, 0.05, pos, null, getFLayout(fieldType), null, fieldType);
            if(!fields.isEmpty()){
                fields.get(fields.size()-1).addNext(newField);
            }
            if(i==39){
                newField.addNext(fields.get(0));
            }
            fields.add(newField);   
        }
    }
    public void createInitFields(){
    
        //Initfelder
        int startX = 1;
        int startY = 1;
        
        
        for(int i = 0; i<4; i++){
            boolean ersteReiheX = true;
            boolean ersteReiheY = true;
            int startX2 = startX;
            int startY2 = startY;
            for(int j = 0; j<4;j++){
                if(ersteReiheX&&ersteReiheY){
                    drawInitFields(startX2,startY2,j);
                    startX2+=1;
                    ersteReiheX = false;
                }else if(!ersteReiheX&&ersteReiheY){
                    drawInitFields(startX2, startY2,j);
                    startY2+=1;
                    ersteReiheY = false;
                }else if(!ersteReiheX&&!ersteReiheY){
                    drawInitFields(startX2, startY2,j);
                    startX2-=1;
                    ersteReiheX = true;                    
                } else{
                    drawInitFields(startX2, startY2,j);
                }
            }
            
            switch (startX) {
                
                case 1: if(startY==1){
                        startX = 10;
                    }
                    break;
                case 10: if(startY==1){
                        startY =10;
                    } else {
                        startX = 1;
                    }
                    break;
                default: break;
            }
        }
    }
    public void createEndFields(){
        
        int startX = 2;
        int startY = 6;
        
        for(int i = 0; i<4; i++){
                switch (i){
                    
                    case 0: drawEndFields(startX,startY,0);
                            startX=6;
                            startY=2;
                            break;
                    case 1: drawEndFields(startX,startY,1);
                            startX=10;
                            startY=6;
                            break;
                    case 2: drawEndFields(startX,startY,2);
                            startX=6;
                            startY=10;
                            break;    
                    case 3: drawEndFields(startX,startY,3);
                            break;
                    default: break;
                }
        }       
        
    }
    public void drawEndFields(int startX, int startY, int direction){
    
        Layout fLayout1 = new Layout();
        fLayout1.setFormFactor(Layout.FORM_FACTOR_OVAL); // Farbe von Feldern etc
        
        for(int i = 0; i<4;i++){
            pos = new RelativePoint(startX*r, startY*r);
            MADN_Field newEndField = new MADN_Field(0.05, 0.05, pos, null, fLayout1, null, MADN_Field.FIELD_TYPE_END);
            if(fields.get((fields.size()-1)-(direction*4)).getFieldType()!=MADN_Field.FIELD_TYPE_END){
                switch (direction){
                    case 0: fields.get(39).addNext(newEndField);
                            break;
                    case 1: fields.get(9).addNext(newEndField);
                            break;
                    case 2: fields.get(19).addNext(newEndField);
                            break;
                    case 3: fields.get(29).addNext(newEndField);
                            break;
                    default: break;
                }
            } else {
                fields.get(fields.size()-1).addNext(newEndField);   
            }
 //           fields.get(direction).addNext(newEndField);
                
            fields.add(newEndField); 
            
            switch (direction){
                case 0: startX++;
                        break;
                case 1: startY++;
                        break;
                case 2: startX--;
                        break;
                case 3: startY--;
                        break;
                default: break;
            }
        }
        
        
    }
    public void drawInitFields(int startX, int startY, int j){
        Layout fLayout1 = new Layout();
        fLayout1.setFormFactor(Layout.FORM_FACTOR_OVAL); // Farbe von Feldern etc
        
        pos = new RelativePoint(startX*r, startY*r);
        MADN_Field newInitField = new MADN_Field(0.05, 0.05, pos, null, fLayout1, null, MADN_Field.FIELD_TYPE_INIT);
        
        switch (j){
                case 0: newInitField.addNext(fields.get(0));
                        break;
                case 1: newInitField.addNext(fields.get(10));
                        break;
                case 2: newInitField.addNext(fields.get(20));
                        break;
                case 3: newInitField.addNext(fields.get(30));
                        break;
                default: break;
        }
        
        fields.add(newInitField);
        
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
    
    @Override
    public String toString(){
        String s = null;
        for(int i=0;i<fields.size(); i++){
            if(fields.get(i)!=null){
            System.out.println("Aktuelles Feld: "+i+" ist ein "+fields.get(i).getFieldType()+" Feld.\n"
                    +"Nächstes Feld: "+""+"");
            }
        }
        return s;
    }
    
    
}
