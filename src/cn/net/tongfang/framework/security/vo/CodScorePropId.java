package cn.net.tongfang.framework.security.vo;



/**
 * CodScorePropId entity. @author MyEclipse Persistence Tools
 */

public class CodScorePropId  implements java.io.Serializable {


    // Fields    

     private String name;
     private String prop;


    // Constructors

    /** default constructor */
    public CodScorePropId() {
    }

    
    /** full constructor */
    public CodScorePropId(String name, String prop) {
        this.name = name;
        this.prop = prop;
    }

   
    // Property accessors

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getProp() {
        return this.prop;
    }
    
    public void setProp(String prop) {
        this.prop = prop;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof CodScorePropId) ) return false;
		 CodScorePropId castOther = ( CodScorePropId ) other; 
         
		 return ( (this.getName()==castOther.getName()) || ( this.getName()!=null && castOther.getName()!=null && this.getName().equals(castOther.getName()) ) )
 && ( (this.getProp()==castOther.getProp()) || ( this.getProp()!=null && castOther.getProp()!=null && this.getProp().equals(castOther.getProp()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getName() == null ? 0 : this.getName().hashCode() );
         result = 37 * result + ( getProp() == null ? 0 : this.getProp().hashCode() );
         return result;
   }   





}