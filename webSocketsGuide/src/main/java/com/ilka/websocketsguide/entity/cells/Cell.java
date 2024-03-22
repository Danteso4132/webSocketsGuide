package com.ilka.websocketsguide.entity.cells;

import com.ilka.websocketsguide.entity.Matrix;
import org.springframework.beans.factory.annotation.Autowired;

public class Cell {

    private Element element;

    public Cell(){
    }

    public Cell(Element e){
        this.element = e;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "element=" + element +
                '}';
    }
}
