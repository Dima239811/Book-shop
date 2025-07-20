package clientbookstore.repo.dto;

public class BookDTO {
    private int id;
    private String name;
    private String author;
    private int year;
    private double price;
    private String status;

    public BookDTO() {}

    public BookDTO(int id, String name, String author, int year, double price, String status) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
