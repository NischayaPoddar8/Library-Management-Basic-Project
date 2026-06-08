import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class book{
    public int id;
    public String title;
    public String author;
    public boolean isIssued;
}


class library{

    ArrayList<book>books = new ArrayList<>();

    public void addBook(){ // 1
        book b = new book();
        System.out.println("Add id of the book : ");
        b.id = Integer.parseInt(IO.readln()); 
        System.out.println("Add title of the book : ");
        b.title = IO.readln();
        System.out.println("Add author of the book : ");
        b.author = IO.readln();
        b.isIssued = false;
        books.add(b);
    }

    book searchBook(int bookId){ // 2
        int libSize = books.size();
        for(int i=0;i<libSize;i++){
            book b = books.get(i);
            if(b.id == bookId){
                System.out.println(
                    "Book id is: " + b.id + " " +
                    "Book title is : " + b.title + " " +
                    "Book author is : " + b.author + " " +
                    "Issued: " + b.isIssued
                ); // To get details in single line
                return b;
            }
        }
        System.out.println("No such book exists in record");
        return null;
    }

    public void issueBook(int bookId){ // 3
        book b = searchBook(bookId);
        if(b==null) {
            System.out.println("Cannot issue book");
        }
        else{
            if(b.isIssued == true) System.out.println("Already issued");
            else{
                b.isIssued = true; // Book issued
                System.out.println("Book issued successfully");
            }
        }
    }

    public void returnBook(int bookId){ // 4
       
        book b = searchBook(bookId);
        // check if book was of library
        if(b==null) {
            System.out.println("Book does not belong to library"); 
            return;
        }
        
        if(b.isIssued==false){
            System.out.println("This book was never issued");
        }
        else{
            b.isIssued = false; // book returned
            System.out.println("Book returned");
        }
    }

    public void load_data(){ // 5 loads the saved data when this program is closed all memory would be wiped but data saved in text file could be loaded with this function helps
        books.clear(); // so no copies are loaded
        File myFile = new File("library.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(myFile))){
            String line;
            while((line = reader.readLine()) != null){
                String [] parts  = line.split(",");
                book b = new book();
                b.id = Integer.parseInt(parts[0]);
                b.title = parts[1];
                b.author = parts[2];
                b.isIssued = Boolean.parseBoolean(parts[3]);
                books.add(b);
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Could not locate file");
        }
        catch(IOException e){
            System.out.println("Something went wrong");
        }
    }

    public void save_data(){ // 6 to save data to file
        File myFile = new File("library.txt"); // file to be written on

        try(FileWriter writer = new FileWriter(myFile)){
            String textContent = "";
            for(book b : books){
                textContent = textContent + Integer.toString(b.id) + ","+ b.title + "," + b.author + ","+ b.isIssued + "\n";
                writer.write(textContent);
                textContent = "";
            }
        }
        catch(FileNotFoundException e){
            System.out.println("Could not locate file location");
        }
        catch(IOException e){
            System.out.println("Could not write file");
        }
    }

}

void main(){
    library thapar = new library();
    System.out.println("Welcome to Thapar library system ");

    while (true) { 

        System.out.println("Choose an option from the menu : 0.Exit 1.Add a book 2.Search a Book 3.Issue a book 4.Return a Book 5.To load data 6.To save data");

        Scanner sc = new Scanner(System.in);
        int optionNo = sc.nextInt();

        try{
            if(optionNo < 0 || optionNo > 6){
                throw new IllegalArgumentException("Invalid menu option");
            }
        }
        catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }

        switch(optionNo){

            case 0 : return; // To stop the code

            case 1 : 
                thapar.addBook();
                break;

            case 2 :
                System.out.println("Enter book id to search for ");
                Scanner scSearch = new Scanner(System.in);
                int searchBookId = scSearch.nextInt();
                thapar.searchBook(searchBookId);
                break;

            case 3 : 
                System.out.println("Enter book id to issue ");
                Scanner scIssue = new Scanner(System.in);
                int issueBookId = scIssue.nextInt();
                thapar.issueBook(issueBookId);
                break;

            case 4 :
                System.out.println("Enter book id to return ");
                Scanner scReturn = new Scanner(System.in);
                int returnBookId = scReturn.nextInt();
                thapar.returnBook(returnBookId);
                break;
            
            case 5 : 
                thapar.load_data();
                System.out.println("Data has been loaded");
                break;

            case 6 :
                thapar.save_data();
                System.out.println("Data saved successfully");
                break;

            default: System.out.println("Wrong option selected");
        }
    }
}