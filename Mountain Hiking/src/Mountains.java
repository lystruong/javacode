public class Mountains {
    private String mountainCode;
    private String moutain;
    private String province;
    private String description;

    public Mountains() {
    }

    public Mountains(String mountainCode, String moutain, String province, String description) {
        this.mountainCode = mountainCode;
        this.moutain = moutain;
        this.province = province;
        this.description = description;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = mountainCode;
    }

    public String getMoutain() {
        return moutain;
    }

    public void setMoutain(String moutain) {
        this.moutain = moutain;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
