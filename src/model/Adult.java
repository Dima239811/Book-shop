package model;

public class Adult extends Customer{
    private String numberPassword;
    private String workplace;
    private boolean newsletterOptIn;

    public Adult(String fullName, int age, String phoneNumber, String email, String address, String numberPassword,
                 String workplace, boolean newsletterOptIn) {
        super(fullName, age, phoneNumber, email, address);
        this.numberPassword = numberPassword;
        this.workplace = workplace;
        this.newsletterOptIn = newsletterOptIn;
    }

    public String getNumberPassword() {
        return numberPassword;
    }

    public void setNumberPassword(String numberPassword) {
        this.numberPassword = numberPassword;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public boolean isNewsletterOptIn() {
        return newsletterOptIn;
    }

    public void setNewsletterOptIn(boolean newsletterOptIn) {
        this.newsletterOptIn = newsletterOptIn;
    }
}
