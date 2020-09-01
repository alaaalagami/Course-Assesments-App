package classes.map;

public abstract class Map {


  public Map(){
  }
  protected String[][] mapString;
  protected Double[][] mapDouble;
  protected Boolean isDataReady = false;

 
  public String[][] getMapString() {
    return this.mapString;
}

public void setMapString(String[][] mapString) {
    this.mapString = mapString;
}

public Double[][] getMapDouble() {
    return this.mapDouble;
}

public void setMapDouble(Double[][] mapDouble) {
    this.mapDouble = mapDouble;
}

public Boolean getIsDataReady() {
    return isDataReady;
}

public void setIsDataReady(Boolean isDataReady) {
    this.isDataReady = isDataReady;
}
}