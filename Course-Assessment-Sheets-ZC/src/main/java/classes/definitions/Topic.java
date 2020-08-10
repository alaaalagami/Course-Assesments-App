package classes.definitions;

import java.util.List;

public class Topic extends Definitions{

	/**
     *
     */
    private static final long serialVersionUID = -632295232635802989L;

    public Topic(List<String> newTopicList) {
        super(newTopicList);
	}

	public Topic() {
        super();
    }
    public List<String> getDefList() {
        return defList;
    }

    public void setDefList(List<String> defList) {
    this.defList = defList;
    }

    //public Vector  myTopic-CLO Map;

}