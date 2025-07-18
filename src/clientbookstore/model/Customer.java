package clientbookstore.model;

public class Customer {
    private String fullName;
    private int age;
    private String phoneNumber;
    private String email;
    private String address;
    private int customerID;
    private static int countId = 0;

    public Customer() {}

    public Customer(String fullName, int age, String phoneNumber, String email, String address) {
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.customerID = countId;
        countId++;
    }

    public Customer(String fullName, int age, String phoneNumber, String email, String address, int customerID) {
        this.fullName = fullName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.customerID = customerID;
    }

    public static void setCountId(int value) {
        countId = value;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "Клиент #" + customerID +
                "\nФИО: " + fullName +
                "\nВозраст: " + age +
                "\nТелефон: " + phoneNumber +
                "\nEmail: " + (email != null ? email : "не указан") +
                "\nАдрес: " + (address != null ? address : "не указан");
    }
}
