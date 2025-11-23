import java.util.InputMismatchException;
import java.util.Scanner;

public class w2051624_PlanManagement {

    // Define seatList, bookedSeats, and soldTickets arrays as class variables
    private static final String seatList[][]={ new String[14], new String[12], new String[12], new String[14]};
    private static final String[] bookedSeats =new String[52];
    private static final Ticket[] soldTickets = new Ticket[52];
    private static int i = 0;

    // Main method to run the program
    public static void main(String[] args) {

        initializeSeats();

        System.out.println("\nWelcome to the Plane Management Application.");

        Scanner chooseOption = new Scanner(System.in);

        //Menu
        while (true) {
            System.out.println("*******************************************");
            System.out.println("*              MENU  OPTIONS              *");
            System.out.println("*******************************************");
            System.out.println("1) Buy a seat");
            System.out.println("2) Cancel a seat");
            System.out.println("3) Find first available seat");
            System.out.println("4) Show seating plan");
            System.out.println("5) Print ticket information and total sales");
            System.out.println("6) Search ticket");
            System.out.println("0) Quit");
            System.out.println("*******************************************");

            System.out.print("please select an option: ");

            // Switch case to handle menu options
            try {
                int choice = chooseOption.nextInt();

                switch (choice) {
                    case 1:
                        buy_seat();
                        break;
                    case 2:
                        cancel_seat();
                        break;
                    case 3:
                        find_first_available();
                        break;
                    case 4:
                        show_seating_plan();
                        break;
                    case 5:
                        print_ticket_info();
                        break;
                    case 6:
                        search_ticket();
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a valid number.");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid choice. Please enter a valid choice.\n\n");
                break;
            }
        }
    }

    // Method to initialize all seats as available
    private static void initializeSeats() {
        // Set all elements in all seat rows to O
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[i].length; j++) {
                seatList[i][j] = "O";
            }
        }
    }
    // Method to make sure if a seat is available
    private static boolean isAvailable(String rowLetter,int seatNumber){
        String currentValue = "O"; // Default value for an available seat
        if(rowLetter.equals("A")){
            currentValue = seatList[0][seatNumber-1];
        } else if (rowLetter.equals("B")) {
            currentValue = seatList[1][seatNumber-1];
        }else if (rowLetter.equals("C")) {
            currentValue = seatList[2][seatNumber-1];
        }else if (rowLetter.equals("D")) {
            currentValue = seatList[3][seatNumber-1];
        }

        return currentValue.equals("O"); // Return true if the seat is available
    }

    //Checking seat which is valid
    private static boolean isValidSeatNumber(String rowLetter,int seatNumber){
        return rowLetter.equals("A")  && seatNumber > 0 && seatNumber < 15 ||
                rowLetter.equals("B")  && seatNumber > 0 && seatNumber < 13 ||
                rowLetter.equals("C")  && seatNumber > 0 && seatNumber < 13 ||
                rowLetter.equals("D")  && seatNumber > 0 && seatNumber < 15;
    }

    //Buy seat method
    private static void buy_seat() {
        Scanner buySeat = new Scanner(System.in);

        System.out.print("Please enter a row letter: ");
        String rowLetter = buySeat.next().toUpperCase();

        System.out.print("Please enter a seat number: ");
        int seatNumber = buySeat.nextInt();

        // Check if the seat number is validation
        if(isValidSeatNumber(rowLetter,seatNumber)){
            if(isAvailable(rowLetter,seatNumber)){

                //get person's information
                System.out.println("1st name?");
                String name = buySeat.next();

                System.out.println("surname?");
                String sName = buySeat.next();

                System.out.println("email?");
                String email = buySeat.next();

                Person person = new Person(name, sName, email);

                double price = calculatePrice(seatNumber);

                Ticket ticket = new Ticket(rowLetter, seatNumber, price, person);

                //add the ticket to the soldTickets array
                soldTickets[i] = ticket;
                i++;
                ticket.save();

                //mark seats as sold
                switch (rowLetter){
                    case "A":
                        seatList[0][seatNumber-1] = "X";
                        System.out.println("Your seat is "+rowLetter +seatNumber);
                        addToBookedList(rowLetter+seatNumber);
                        break;
                    case "B":
                        seatList[1][seatNumber-1] = "X";
                        System.out.println("Your seat is "+rowLetter +seatNumber);
                        addToBookedList(rowLetter+seatNumber);
                        break;
                    case "C":
                        seatList[2][seatNumber-1] = "X";
                        System.out.println("Your seat is "+rowLetter+ seatNumber);
                        addToBookedList(rowLetter+seatNumber);
                        break;
                    case "D":
                        seatList[3][seatNumber-1] = "X";
                        System.out.println("Your seat is "+rowLetter+ seatNumber);
                        addToBookedList(rowLetter+seatNumber);
                        break;
                }
            }else {
                System.out.println(rowLetter+ seatNumber+ " is already bought :(");
            }
        }else{
            System.out.println("Invalid row letter or seat number");
        }

    }
    //Cancel seat method
    private static void cancel_seat() {
        Scanner cancelSeat = new Scanner(System.in);

        System.out.print("Please enter a row letter: ");
        String rowLetter = cancelSeat.next().toUpperCase();

        System.out.print("Please enter a seat number: ");
        int seatNumber = cancelSeat.nextInt();

        if(isValidSeatNumber(rowLetter, seatNumber)){
            if(!isAvailable(rowLetter, seatNumber)){
                switch (rowLetter) {
                    case "A":
                        seatList[0][seatNumber - 1] = "O";
                        System.out.println("Seat " + rowLetter + seatNumber + " has been canceled. Now it is available.");
                        removeFromBookedList(rowLetter + seatNumber);
                        break;
                    case "B":
                        seatList[1][seatNumber - 1] = "O";
                        System.out.println("Seat " + rowLetter + seatNumber + " has been canceled. Now it is available.");
                        removeFromBookedList(rowLetter + seatNumber);
                        break;
                    case "C":
                        seatList[2][seatNumber - 1] = "O";
                        System.out.println("Seat " + rowLetter + seatNumber + " has been canceled. Now it is available.");
                        removeFromBookedList(rowLetter + seatNumber);
                        break;
                    case "D":
                        seatList[3][seatNumber - 1] = "O";
                        System.out.println("Seat " + rowLetter + seatNumber + " has been canceled. Now it is available.");
                        removeFromBookedList(rowLetter + seatNumber);
                        break;
                }
                System.out.println("Seat " + rowLetter + seatNumber + " has been canceled. It is now available.");

            }else {
                System.out.println(rowLetter+" "+seatNumber+ " is already available");
            }
        }else{
            System.out.println("Invalid row letter or seat number");
        }

    }
    //Find first available method
    private static void find_first_available() {
        boolean found = false;
        // Iterate through seatList to find the first available seat
        for (int i = 0; i < seatList.length; i++) {
            for (int j = 0; j < seatList[i].length; j++) {
                if (seatList[i][j].equals("O")) {

                    // Determine the row letter based on the current index
                    String seatLetter = i == 0 ? "A" : i == 1 ? "B" : i == 2 ? "C" : "D";
                    System.out.println("First available seat is " + seatLetter  + (j + 1));
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        // If no available seat is found, print a message
        if (!found) {
            System.out.println("No available seats!");
        }
    }

    //Show seating plan method
    private static void show_seating_plan() {
        System.out.println("\n\nHere the seating plan");

        // Print the seatList
        for (String[] seatRow : seatList) {
            for (String seat : seatRow) {
                System.out.print(seat);
            }
            System.out.println();
        }
        System.out.println();
    }

    //Print ticket information method
    private static void print_ticket_info(){
        double totalPrice = 0.0;
        System.out.println("Ticket information\n");
        for (int j = 0; j < i; j++) {
            Ticket ticket = soldTickets[j];
            if (ticket != null) {
                ticket.printTicketInfo();
                System.out.println();
                totalPrice += ticket.getPrice();
            }
        }
        System.out.println("Total price is £ "+ totalPrice);
    }

    //calculating price
    public static double calculatePrice(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= 5) {
            return 200.0;
        } else if (seatNumber >= 6 && seatNumber <= 9) {
            return 150.0;
        } else {
            return 180.0;
        }
    }

    // Method to add a seat to the booked seats list
    public static void addToBookedList(String selectedSeats){
        bookedSeats[i]=selectedSeats;
        i++;
    }

    // Method to remove a seat from the booked seats list
    public static void removeFromBookedList(String selectedSeats){
        for (int j = 0; j < i; j++) {
            if (bookedSeats[j] != null && bookedSeats[j].equals(selectedSeats)) {
                bookedSeats[j] = null;
                System.out.println("And it has been removed from the booked list.");
                break;
            }
        }
    }

    //Search ticket method
    public static void search_ticket(){
        Scanner searchTicket = new Scanner(System.in);

        System.out.println("Enter row letter: ");
        String rowLetter = searchTicket.next().toUpperCase();

        System.out.println("Enter seat number: ");
        int seatNumber = searchTicket.nextInt();

        if (isValidSeatNumber(rowLetter, seatNumber)){
            if (!isAvailable(rowLetter, seatNumber)) {

                // Iterate through soldTickets to find the matching ticket
                for (int j = 0; j < i; j++) {
                    Ticket ticket = soldTickets[j];
                    if (ticket.getRow().equals(rowLetter) && ticket.getSeat() == seatNumber) {
                        System.out.println("Here the information");
                        ticket.printTicketInfo();
                        break;
                    }
                }
            }else{
                System.out.println("This seat is available");
            }
        }else{
            System.out.println("Invalid row letter or seat number");
        }
    }
}

