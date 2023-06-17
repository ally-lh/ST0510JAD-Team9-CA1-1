package models;
import java.sql.Date;
public class Order {
    public Order(String iSBN, String title, int quantity, Date orderDate) {
        super();
        ISBN = iSBN;
        this.title = title;
        this.quantity = quantity;
        this.orderDate = orderDate;
    }
    private String ISBN;
    private String title;
    private int quantity;
    private Date orderDate;
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

}