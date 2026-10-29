import java . io .∗;
import java .time . LocalDate ;
import java .time . format . DateTimeParseException ;
import java . util .∗;
public class PersonalExpenseTracker {
s tatic Scanner sc = new Scanner(System. in );
s tatic ArrayList<Expense> expenses = new ArrayList <>();
s tatic final String FILE NAME = ”expenses. txt”;
s tatic class Expense {
i nt id ;
String date ;
String category ;
String description ;
double amount;
Expense( int id , String date , String category ,
String description , double amount) {
t his . id = id ;
t his . date = date;
t his . category = category ;
t his . description = description ;
t his .amount = amount;
}
}
public static void main(String [] args) {
loadExpenses ();
while (true) {
System. out . println (”\n===== PERSONAL EXPENSE TRACKER =====”);
System. out . println (”1. Add Expense”);
System. out . println (”2. View All Expenses”);
System. out . println (”3. View Total Expenses”);
System. out . println (”4. Category−wise Summary”);
System. out . println (”5. Delete Expense”);
System. out . println (”6. Exit ”);
System. out . print (”Enter your choice : ”);
i nt choice = readInt ();
switch (choice) {
case 1:
addExpense ();
break ;
case 2:
viewExpenses ();
break ;
case 3:
showTotal ();
break ;
case 4:
5
categorySummary ();
break ;
case 5:
deleteExpense ();
break ;
case 6:
saveExpenses ();
System. out . println (”Expenses saved . Thank you!”);
return ;
default :
System. out . println (” Invalid choice . Try again .”);
}
}
}
s tatic int readInt () {
while (true) {
try {
return Integer . parseInt(sc . nextLine (). trim ());
} catch (NumberFormatException e) {
System. out . print (”Enter a valid whole number: ”);
}
}
}
s tatic double readAmount() {
while (true) {
try {
double amount =
Double . parseDouble(sc . nextLine (). trim ());
i f (Double. isFinite (amount) && amount > 0) {
return amount;
}
} catch (NumberFormatException e) {
// Ask again if input is not a number.
}
System. out . print (”Enter a valid amount greater than zero : ”);
}
}
s tatic void addExpense() {
System. out . println (”\n−−− Add New Expense −−−”);
System. out . print (”Enter date (YYYY−MM−DD): ”);
String date = sc.nextLine (). trim ();
try {
LocalDate . parse(date );
} catch (DateTimeParseException e) {
System. out . println (” Invalid date format .”);
6
return ;
}
System. out . println (”Categories : Food, Transport , Education , Shopping , Other”);
System. out . print (”Enter category : ”);
String category = sc .nextLine (). trim ();
i f (category .isEmpty()) {
System. out . println (”Category cannot be empty.”);
return ;
}
System. out . print (”Enter description : ”);
String description = sc . nextLine (). trim ();
System. out . print (”Enter amount (Rs.): ”);
double amount = readAmount();
i nt id = 1;
f or (Expense e : expenses) {
i f (e. id >= id) {
i d = e. id + 1;
}
}
// Avoid the file delimiter in saved text .
category = category . replace (”|” , ”/”);
description = description . replace (”|” , ”/”);
expenses .add(new Expense(
id , date , category , description , amount
) );
saveExpenses ();
System. out . println (”Expense added successfully !”);
}
s tatic void viewExpenses() {
System. out . println (”\n−−− All Expenses −−−”);
i f (expenses .isEmpty()) {
System. out . println (”No expenses recorded .”);
return ;
}
System. out . printf(”%−5s %−12s %−15s %−22s %10s%n”,
”ID”, ”Date”, ”Category”, ”Description”, ”Amount”);
System. out . println(”−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−−
f or (Expense e : expenses) {
System. out . printf(”%−5d %−12s %−15s %−22s %10.2f%n”,
7
e . id , e.date , e. category ,
e . description , e.amount);
}
}
s tatic void showTotal() {
double total = 0;
f or (Expense e : expenses) {
t otal += e.amount;
}
System. out . printf (”Total Expenses : Rs. %.2f%n”, total );
}
s tatic void categorySummary() {
i f (expenses .isEmpty()) {
System. out . println (”No expenses recorded .”);
return ;
}
Map<String , Double> summary = new TreeMap<>();
f or (Expense e : expenses) {
summary. put(
e . category ,
summary. getOrDefault(e . category , 0.0) + e.amount
) ;
}
System. out . println (”\n−−− Category−wise Summary −−−”);
f or (Map.Entry<String , Double> entry : summary. entrySet ()) {
System. out . printf(”%−20s Rs. %.2f%n”,
entry . getKey() , entry . getValue ());
}
}
s tatic void deleteExpense () {
System. out . print (”Enter the expense ID to delete : ”);
i nt id = readInt ();
f or (int i = 0; i < expenses. size (); i++) {
i f (expenses . get( i ). id == id) {
expenses .remove( i );
saveExpenses ();
System. out . println (”Expense deleted successfully !”);
return ;
}
}
8
System. out . println (”Expense ID not found .”);
}
s tatic void saveExpenses() {
try (PrintWriter writer =
new PrintWriter(new FileWriter(FILE NAME))) {
f or (Expense e : expenses) {
writer . println (e . id + ”|” + e.date + ”|” +
e . category + ”|” + e. description + ”|” + e.amount);
}
} catch (IOException e) {
System. out . println (”Error saving expenses : ” + e.getMessage ());
}
}
s tatic void loadExpenses() {
File file = new File(FILE NAME);
i f (! file . exists ()) {
return ;
}
try (BufferedReader reader =
new BufferedReader(new FileReader( file ))) {
String line ;
while (( line = reader . readLine ()) != null) {
String [] data = line . split (”\\|” , 5);
i f (data. length == 5) {
try {
expenses .add(new Expense(
Integer . parseInt (data [0]) ,
data [1] ,
data [2] ,
data [3] ,
Double . parseDouble(data [4])
) );
} catch (NumberFormatException e) {
System. out . println (”Skipping an invalid saved record .”);
}
}
}
} catch (IOException e) {
System. out . println (”Error loading expenses : ” + e.getMessage ());
}
}
9
}