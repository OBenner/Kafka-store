package kafka.streams.store;


import java.util.Objects;

/**
 * The type Account.
 */

public class Ac {
    private int id;
    private String firstName;
    private String lastName;
    private int phone;
    private double amount;


    public Ac() {
    }

    public Ac(int id, String firstName, String lastName, int phone, double amount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.amount = amount;
    }

    public Ac(int i) {
        this.id=i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ac ac = (Ac) o;
        return getId() == ac.getId() &&
                getPhone() == ac.getPhone() &&
                Double.compare(ac.getAmount(), getAmount()) == 0 &&
                Objects.equals(getFirstName(), ac.getFirstName()) &&
                Objects.equals(getLastName(), ac.getLastName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getFirstName(), getLastName(), getPhone(), getAmount());
    }

    @Override
    public String toString() {
        return "Ac{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone=" + phone +
                ", amount=" + amount +
                '}';
    }
}
