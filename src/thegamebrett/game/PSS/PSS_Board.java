package thegamebrett.game.PSS;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import thegamebrett.model.Layout;
import thegamebrett.model.RelativePoint;
import thegamebrett.model.elements.Board;
import thegamebrett.model.elements.Field;

/**
 * THE GAMEBRETT - Teamprojekt 2015-2016 - Hochschule Trier
 *
 * @author Kore Kaluzynski, Cenk Saatci, Christian Colbach
 */
public class PSS_Board extends Board {

    private ArrayList<PSS_Field> fields = new ArrayList<>();
    private String[] fieldContent;
    private float rX, rY;
    private RelativePoint pos;
    private Layout layout;

    private final float ratioX = 1.2f;
    private final float ratioY = 1;

    public PSS_Board(String[] fieldContent) {
        super();
        this.layout = new Layout();
        this.layout.setBackgroundColor(Color.LIGHTGREY);
        this.fieldContent = fieldContent;
        createFields();
    }

    private void createFields() {

        rX = relativateRatioX(9.0f);
        rY = relativateRatioY(10.0f);
        //1=rechts, 2= oben, 3=unten, 4=links
        int direction = 0;
        pos = new RelativePoint(0, 0);

        int startX = 0;
        int startY = 8;

        for (int i = 0; i < 72; i++) {

            switch (i) {
                //richtung rechts
                case 0:
                case 29:
                case 51:
                case 65:
                    direction = 1;
                    break;
                //richtung oben
                case 8:
                case 36:
                case 56:
                case 68:
                    direction = 2;
                    break;
                //richtung links
                case 15:
                case 41:
                case 59:
                case 69:
                    direction = 4;
                    break;
                //richtung unten
                case 23:
                case 47:
                case 63:
                    direction = 3;
                    break;

                default:
                    break;
            }

            switch (direction) {
                case 1:
                    pos = new RelativePoint(startX * rX, startY * rY);
                    startX += 1;
                    break;
                case 2:
                    pos = new RelativePoint(startX * rX, startY * rY);
                    startY += -1;
                    break;
                case 3:
                    pos = new RelativePoint(startX * rX, startY * rY);
                    startY += 1;
                    break;
                case 4:
                    pos = new RelativePoint(startX * rX, startY * rY);
                    startX += -1;
                    break;
                default:
                    break;
            }

            Layout fieldLayout = new Layout(i + "", fieldContent[i]);
            fieldLayout.setFormFactor(Layout.FORM_FACTOR_SQUARE);

            PSS_Field newField = new PSS_Field(i, relativateRatioX(9.0f), relativateRatioY(10.0f), pos, null, fieldLayout, null);
            if (!fields.isEmpty()) {
                fields.get(fields.size() - 1).addNext(newField);
            }
            fieldLayout.setSubtextScaleFactor(-0.2f);

            fields.add(newField);
        }
    }

    @Override
    public int getFieldLength() {
        return fields.size();
    }

    //speziell auf das Feld, nicht allgemein
    public float relativateRatioX(float i) {
        return 1.0f / i;
    }

    public float relativateRatioY(float i) {
        return 1.0f / i;
    }

    @Override
    public Field getField(int i) {
        if (fields == null) {
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

    public void setFieldContent(String[] content) {
        this.fieldContent = content;
    }
}
