package classes.definitions;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Definitions implements Serializable{

  /**
   *
   */
  private static final long serialVersionUID = 2900528981503346559L;
  protected List<String> defList;
  protected Boolean dataReady = false;

  public Definitions(){
    defList = new ArrayList<String>();
  }

  public Definitions(List<String> newdefList){
    defList = new ArrayList<String>();
    newdefList.forEach((String s ) -> defList.add(s));
  }

  public Boolean isDataReady() {
    return dataReady;
  }

  public void setDataReady(Boolean dataReady) {
  this.dataReady = dataReady;
  }

  @Override
  public String toString() {
    return "Definitions [dataReady=" + dataReady + ", defList=" + defList + "]";
  }
}