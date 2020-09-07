package classes.map;

public class TopicCLOMap extends Map{

    
    private Double[] nOfweeksPerTopic;
    private Double totalnOfweeks;
    private Double[] nOfWeeksPerClo;

   

    TopicCLOMap()
    {

    }
    TopicCLOMap(int nOftopic, int nofClo)
    {
        mapString = new String[nOftopic][nofClo];
        mapDouble = new Double[nOftopic][nofClo];
        nOfweeksPerTopic = new Double[nOftopic];
    }


    public Double[] getNOfweeksPerTopic() {
        return this.nOfweeksPerTopic;
    }

    public void setNOfweeksPerTopic(Double[] nOfweeksPerTopic) {
        this.nOfweeksPerTopic = nOfweeksPerTopic;
    }

    public Double getTotalnOfweeks() {
        return this.totalnOfweeks;
    }

    public void setTotalnOfweeks(Double totalnOfweeks) {
        this.totalnOfweeks = totalnOfweeks;
    }
    public void setTotalnOfweeks() {
        double value = 0;
        for (Double i: nOfweeksPerTopic)
        {
            value = value + i.doubleValue();
        }
        this.totalnOfweeks = new Double(value);
    }
    public Double[] getNOfWeeksPerClo() {
        return this.nOfWeeksPerClo;
    }

    public void setNOfWeeksPerClo(Double[] nOfweeksPerClo) {
        this.nOfWeeksPerClo = nOfweeksPerClo;
    }
}