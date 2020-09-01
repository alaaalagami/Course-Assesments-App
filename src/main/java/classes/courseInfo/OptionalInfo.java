package classes.courseInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OptionalInfo implements Serializable{

  /**
   *
   */
  private static final long serialVersionUID = -1626431643374569702L;

  private Required required;

  private Integer credit;

  private String description;

  private String prerequisites;

  private String relatedTopics;
  private String instructorOffice;
  private String taOffice;
  private String DiscussinGroup;
  private List<String> scheduleLines;
  private List<String> textBooks;
  private List<String> references;
  private List<String> notes;

  public OptionalInfo() {
    required = Required.REQUIRED;
    credit = Integer.valueOf(0);
    description = "";
    prerequisites = "";
    relatedTopics = "";
    instructorOffice = "";
    taOffice = "";
    DiscussinGroup = "";
    setScheduleLines(new ArrayList<String>());
    setTextBooks(new ArrayList<String>());
    setReferences(new ArrayList<String>());
    setNotes(new ArrayList<String>());
  }

  public String getTaOffice() {
    return taOffice;
  }

  public void setTaOffice(String taOffice) {
    this.taOffice = taOffice;
  }

  public String getInstructorOffice() {
    return instructorOffice;
  }

  public void setInstructorOffice(String instructorOffice) {
    this.instructorOffice = instructorOffice;
  }

  public List<String> getNotes() {
    return notes;
  }

  public void setNotes(List<String> notes) {
    this.notes = notes;
  }

  public List<String> getReferences() {
    return references;
  }

  public void setReferences(List<String> references) {
    this.references = references;
  }

  public List<String> getTextBooks() {
    return textBooks;
  }

  public void setTextBooks(List<String> textBooks) {
    this.textBooks = textBooks;
  }

  public List<String> getScheduleLines() {
    return scheduleLines;
  }

  public void setScheduleLines(List<String> scheduleLines) {
    this.scheduleLines = scheduleLines;
  }

  public Required getRequired() {
    return required;
  }

  public void setRequired(Required required) {
    this.required = required;
  }

  public Integer getCredit() {
    return credit;
  }

  public void setCredit(Integer credit) {
    this.credit = credit;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPrerequisites() {
    return prerequisites;
  }

  public void setPrerequisites(String prerequisites) {
    this.prerequisites = prerequisites;
  }

  public String getRelatedTopics() {
    return this.relatedTopics;
  }

  public void setRelatedTopics(String relatedTopics) {
    this.relatedTopics = relatedTopics;
  }

  @Override
  public String toString() {
    return "OptionalInfo [DiscussinGroup=" + DiscussinGroup + ", credit=" + credit + ", description=" + description
        + ", instructorOffice=" + instructorOffice + ", notes=" + notes + ", prerequisites=" + prerequisites
        + ", references=" + references + ", relatedTopics=" + relatedTopics + ", required=" + required
        + ", scheduleLines=" + scheduleLines + ", taOffice=" + taOffice + ", textBooks=" + textBooks + "]";
  }

  public String getDiscussinGroup() {
    return DiscussinGroup;
  }

  public void setDiscussinGroup(String discussinGroup) {
    DiscussinGroup = discussinGroup;
  }

  
}