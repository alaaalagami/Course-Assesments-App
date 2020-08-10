package classes.courseInfo;

import java.io.Serializable;

public class CourseInfo implements Serializable {

  /**
	 *
	 */
	private static final long serialVersionUID = -239521776987533164L;

  private MandatoryInfo MandInfo;

  private OptionalInfo opInfo;

  private Boolean dataReady = false;

  public CourseInfo() {
    MandInfo = new MandatoryInfo();
    opInfo = new OptionalInfo();
  }

  public Boolean isDataReady() {
    return dataReady;
  }

  public void setDataReady(Boolean dataReady) {
    this.dataReady = dataReady;
  }

  public MandatoryInfo getMandInfo() {
    return MandInfo;
  }

  public void setMandInfo(MandatoryInfo mandInfo) {
    MandInfo = mandInfo;
  }

  public OptionalInfo getOpInfo() {
    return opInfo;
  }

  public void setOpInfo(OptionalInfo opInfo) {
    this.opInfo = opInfo;
  }

  @Override
  public String toString() {
    return "CourseInfo [MandInfo=" + MandInfo + ", dataReady=" + dataReady + ", opInfo=" + opInfo + "]";
  }

}