package classes.definitions;

import java.util.*;

import classes.actions.*;

public class SO extends Definitions {
  /**
   *
   */
  private static final long serialVersionUID = -2061633601449973623L;
  private List<Boolean> Linked;
  private Actions soActions;

  public SO(List<String> newSoList, List<Boolean> newSoLinkedList) {
    super(newSoList);
    Linked = new ArrayList<>(newSoLinkedList);
  }

  public List<String> getDefList() {
    return defList;
  }

  public void setDefList(List<String> defList) {
    this.defList = defList;
  }

  public SO() {
    super();
    Linked = new ArrayList<>();
}

public List<Boolean> getLinked() {
    return Linked;
  }

  public void setLinked(List<Boolean> linked) {
    this.Linked = linked;
  }

  @Override
  public String toString() {
    return super.toString() + " SO [Linked=" + Linked + ", soActions=" + soActions + "]";
  }

  public Actions getSoActions() {
    return soActions;
  }

  public void setSoActions(Actions soActions) {
    this.soActions = soActions;
  }

  // public Vector myCLO-SO Map;
  // public Vector mySO action;
  

}