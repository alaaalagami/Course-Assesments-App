package classes.tools;
import java.util.*;

public class Tools {
  
  protected String[][] elementsString;
  protected Double[][] elementsDouble;
  protected String[][] closString;
  protected Double[][] closDouble;
  protected Boolean isDataReady = false;

  public String[][] getElementsString() {
    return this.elementsString;
  }

  public void setElementsString(String[][] elementsString) {
    this.elementsString = elementsString;
  }

  public Double[][] getElementsDouble() {
    return this.elementsDouble;
  }

  public void setElementsDouble(Double[][] elementsDouble) {
    this.elementsDouble = elementsDouble;
  }

  public String[][] getClosString() {
    return this.closString;
  }

  public void setClosString(String[][] closString) {
    this.closString = closString;
  }

  public Double[][] getClosDouble() {
    return this.closDouble;
  }

  public void setClosDouble(Double[][] closDouble) {
    this.closDouble = closDouble;
  }

  public Boolean getIsDataReady() {
    return this.isDataReady;
  }

  public void setIsDataReady(Boolean isDataReady) {
    this.isDataReady = isDataReady;
  }


  public Tools(){

  }
  
 

}