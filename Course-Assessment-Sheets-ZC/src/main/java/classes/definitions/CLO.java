package classes.definitions;

import java.util.List;

public class CLO extends Definitions{
    

    /**
     *
     */
    private static final long serialVersionUID = 4432043877499154162L;

    public CLO(final List<String> newCloList) {
        super(newCloList);
	}
	public CLO() {
        super();
    }
    public List<String> getDefList() {
        return defList;
    }

    public void setDefList(List<String> defList) {
    this.defList = defList;
    }
    


}