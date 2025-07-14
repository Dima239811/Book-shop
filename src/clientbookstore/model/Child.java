package clientbookstore.model;

public class Child extends Customer{
    private String nameParent;
    private boolean parentConsent; // разрешение родителя на покупку
    private String schoolName;


    public Child(String fullName, int age, String phoneNumber, String email, String address, String nameParent,
                 boolean parentConsent, String schoolName) {
        super(fullName, age, phoneNumber, email, address);
        this.nameParent = nameParent;
        this.parentConsent = parentConsent;
        this.schoolName = schoolName;
    }

    public String getNameParent() {
        return nameParent;
    }

    public void setNameParent(String nameParent) {
        this.nameParent = nameParent;
    }

    public boolean isParentConsent() {
        return parentConsent;
    }

    public void setParentConsent(boolean parentConsent) {
        this.parentConsent = parentConsent;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
