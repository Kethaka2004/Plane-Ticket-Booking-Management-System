import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    //fields
    private String row;
    private int seat;
    private double price;
    private Person person;

    //constructing
    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    //getters and setters
    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    //method for print ticket information
    public void printTicketInfo() {
        System.out.println("*Ticket Details*");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: " + price);
        System.out.println();
        System.out.println("*Person Information*");
        person.printInfo();
        System.out.println();
    }


    //method to save ticket information to a file
    public void save(){
        //create file name
        String fileName = row + seat + ".txt";
        try(FileWriter writer = new FileWriter(fileName)){
            //write ticket details
            writer.write("*Ticket Details*\n\n");
            writer.write("Seat: " + row + seat + "\n");
            writer.write("Full name: " + getPerson().getName() + getPerson().getSurname() + "\n");
            writer.write("Email: " + getPerson().getEmail());
            writer.close();
        }
        catch (IOException e){
            System.out.println("Something went wrong...");
        }
    }
}
