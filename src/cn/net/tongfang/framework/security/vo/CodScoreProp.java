package cn.net.tongfang.framework.security.vo;



/**
 * CodScoreProp entity. @author MyEclipse Persistence Tools
 */

public class CodScoreProp  implements java.io.Serializable {


    // Fields    

     private CodScorePropId id;
     private String propname;
     private String propother;


    // Constructors

    /** default constructor */
    public CodScoreProp() {
    }

	/** minimal constructor */
    public CodScoreProp(CodScorePropId id, String propname) {
        this.id = id;
        this.propname = propname;
    }
    
    /** full constructor */
    public CodScoreProp(CodScorePropId id, String propname, String propother) {
        this.id = id;
        this.propname = propname;
        this.propother = propother;
    }

   
    // Property accessors

    public CodScorePropId getId() {
        return this.id;
    }
    
    public void setId(CodScorePropId id) {
        this.id = id;
    }

    public String getPropname() {
        return this.propname;
    }
    
    public void setPropname(String propname) {
        this.propname = propname;
    }

    public String getPropother() {
        return this.propother;
    }
    
    public void setPropother(String propother) {
        this.propother = propother;
    }
   








}