//Author: Jack Bragg
//CS141 JAVA Final Project
//Compiler used: netbeans
//Final project: password creator

package cs141finalproject;
import javax.swing.JOptionPane;
import java.io.*;
import java.util.regex.Pattern;

public class CS141FinalProject {
   
    public static void main(String[] args) throws IOException
    { 
        // first window explaining the program to the user and allowing them to
        // continue or quit
        String[] buttons = {"Start", "Cancel"};
        int choice = JOptionPane.showOptionDialog(null, "This program will help"
                + " you create a password \nby asking you a series of questions"
                + " and then \nwill apply one of two algorithms to create a "
                + "\nunique password based on your input and save"
                + "\nit in a file." , "Password helper", 
                0, -1, null, buttons, buttons[0]);
        // cancle button = quit;
        if (choice == 1){System.exit(0);}
        //passes to the main program enableing recursion
        passwordPrompt();
    }
    
    public static void passwordPrompt() throws FileNotFoundException,IOException
    {
        
        int choice;
        // prompts the user for input and validates that it has at least two 
        // characters
        Prompt promptUser = new Prompt();
        choice = promptUser.begin();
        Password p = new Password(promptUser.getPass(), choice);
        choice = JOptionPane.showConfirmDialog(null, "your password is: " +
                p.getPassword() + "\nwould you like to go again?", "Password "
                + "helper", JOptionPane.YES_NO_OPTION);
        p.writeToFile();
        if (choice == 0) {passwordPrompt();}
    } // end of 'passwordPrompt()'
    
}

class Prompt{
    private String[] password = {"thing", "color", "action", "category", "number"}, 
                buttons = { "First", "Last", "Cancel" };
    private int i = 0, choice;
    private boolean valid = false;
    
    public int begin(){
        thing();
        color();
        action();
        category();
        numbers();
        alg();
        return choice;
    };
    
    public String[] getPass(){
        return password;
    }
    
    private void thing() {
        password[0] = JOptionPane.showInputDialog(null, "Thing: (cat, car, key,"
                + " etc)", "Password Helper", 3);
        if (password[0].length() < 2)
        {
             JOptionPane.showMessageDialog(null, "You must use at least two "
                        + "characters");
            thing();
        }   
    }
    
    private void color() {
     password[1] = JOptionPane.showInputDialog(null, "Color of the " 
                + password[0] + ":", "Password helper", 3);
     if (password[1].length() < 2)
     {
         JOptionPane.showMessageDialog(null, "You must use at least two "
                    + "characters");
         color();
     }
           
    }
    
    private void action(){
        password[2] = JOptionPane.showInputDialog(null, "An action the " 
                + password[1] + " " + password[0] + " would perform:", 
                "Password helper", 3);
        if (password[2].length() < 2)
        {
            JOptionPane.showMessageDialog(null, "You must use at least two "
                    + "characters");
            action();
        }
    }
    
    private void category(){
        password[3] = JOptionPane.showInputDialog(null, "A category it belongs "
                + "to: (mammal, lizard, machine, etc)", "Password helper", 3);
        if (password[3].length() < 2)
        {
            JOptionPane.showMessageDialog(null, "You must use at least two "
                    + "characters");
            category();
        }
    }
    
    private void numbers(){
        password[4] = JOptionPane.showInputDialog(null, "Number of " 
                    + password[1] + " " + password[0] + "'s you would like to "
                    + "have: ", "Password helper", 3);
        if (Pattern.matches("[0-9]+", password[4]) && password[4].length() >= 2)
        {
            
        } else {
            JOptionPane.showMessageDialog(null, "That is not a valid number."
                       + "\nplease use two numeric digits");
            numbers();
        }
           
    }
    
    private void alg(){
        String[] buttons = { "First", "Last", "Cancel" };
        choice = JOptionPane.showOptionDialog(null, "Would you like the first "
                + "letters used in the password or the final letters?"
                , "Algorithem", 0, -1, null, buttons, buttons[0]);
        if (choice == 2){System.exit(0);}        
    }
    
} // end of class prompt

class Password{
    // all the variables are strings because the return type will be a string 
    // even through it contains numbers and symbols.
    private String[] password = new String[5];
    private String finalPassword = "";
    private File file;

    public static String ManipulatePhrase(String inPhrase){
        String buff = "";
        char io;
        int numOfWhiteSpaces = 0;
        for (int i = 0; i < inPhrase.length(); i++){
            if(inPhrase.charAt(i) == ' '){
                numOfWhiteSpaces++;
                if(numOfWhiteSpaces >= 10){
                    numOfWhiteSpaces = 1;
                }
                buff += Integer.toString(numOfWhiteSpaces);
            } else {
                io = inPhrase.charAt(i);
                io += 1;
                buff += io;
            }
        }
        return buff;
    }
    
    //constructor
    Password(String[] passwordVar, int myChoice){
        //assigns variables
        int choice = myChoice;

        System.arraycopy(passwordVar, 0, password, 0, passwordVar.length);

        //choice of which algorithm to appyly
        if (choice == 0){ 
            firstAlgorithm();
        } else { 
            secondAlgorithm();
        }
    } // end of constructor
    
    public String getPassword(){
        return finalPassword;
    }
    
    public void writeToFile() throws IOException 
    {
        file = new File("Passwords.txt");
        if (!file.exists()){
            System.out.println("File not found");
            System.exit(0);
        }
        try (FileWriter ifile = new FileWriter(file, true)) {
            ifile.write("\t" + finalPassword);
        } catch (FileNotFoundException e) {System.out.println("file not found");}
    }
    
    private void firstAlgorithm() {
        //since i am pulling char data from an array of strings which are 
        //themselves arrays, I need to use a similar algorithm to a 
        //multidimentional array to load the data I need.
        for (int i = 0; i < 5; i++){
            for (int l = 0; l < 2; l++){
                finalPassword += password[i].charAt(l);
            }
        }
    }
    
    private void secondAlgorithm() {
        //since i am pulling char data from an array of strings which are 
        //themselves arrays, I need to use a similar algorithm to a 
        //multidimentional array to load the data I need.
        for (int i = 0; i < 5; i++){
            for (int l = password[i].length() - 2;l < password[i].length();l++){
                finalPassword += password[i].charAt(l);
            }
        }
    }

} // end of 'Password'
