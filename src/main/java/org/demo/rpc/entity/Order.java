package org.demo.rpc.entity;

public class Order {
    private String code;
    private String name;
    private String count;
    private String price;
    private String address;

    private String status;

    @Override
    public String toString() {
        return "Order{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", count='" + count + '\'' +
                ", price='" + price + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
