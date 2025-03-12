package Model;

import java.util.Date;

public class Promotion {
    private String pid;
    private String adid;
    private String pname;
    private float discountRate;
    private Date startDate;
    private Date endDate;
    private String pdescription;

    // Constructor
    public Promotion(String pid, String adid, String pname, float discountRate, Date startDate, Date endDate, String pdescription) {
        this.pid = pid;
        this.adid = adid;
        this.pname = pname;
        this.discountRate = discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pdescription = pdescription;
    }

    // Getters
    public String getPid() {
        return pid;
    }

    public String getAdid() {
        return adid;
    }

    public String getPname() {
        return pname;
    }

    public float getDiscountRate() {
        return discountRate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getPdescription() {
        return pdescription;
    }

    // Setters
    public void setPid(String pid) {
        this.pid = pid;
    }

    public void setAdid(String adid) {
        this.adid = adid;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }
}