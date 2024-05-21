package RentalSys;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cycle {
    private String id;
    private String brand;
    private String type;// elctrical or manual
    private boolean isAvailable;
    private int pricePerDay;

    public Cycle(String id, String brand, String type, int pricePerDay) {
        this.id = id;
        this.brand = brand;
        this.type = type;
        this.isAvailable = true;
        this.pricePerDay = pricePerDay;
    }

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public Boolean isAvailable(){
       return isAvailable;
    }
    public int CalPriceforDay(int day){
        return pricePerDay*day;
    }

    public void rented(){
        isAvailable=false;
    }
    public void returned(){
        isAvailable=true;
    }
}


class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}

class GetRent {
    private Cycle cycle;
    private Customer customer;
    private int days;

    public GetRent(Cycle cycle, Customer customer, int days) {
        this.cycle = cycle;
        this.customer = customer;
        this.days = days;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}


class CycleRentalCystem{
    private List<Cycle> cycleList;
    private List<Customer> customerList;
    private List<GetRent> rentList;

    public CycleRentalCystem() {
        this.cycleList = new ArrayList<>();
        this.customerList = new ArrayList<>();
        this.rentList = new ArrayList<>();
    }

    public void addCycle(Cycle cycle){
        cycleList.add(cycle);
    }

    public void addCustomer(Customer customer){
        customerList.add(customer);
    }

    public void wantToRent(Cycle cycle,Customer customer,int days){
        if(cycle.isAvailable()){
            cycle.rented();
            rentList.add(new GetRent(cycle,customer,days));
            System.out.println("Cycle is Rented for "+days);
        }else {
            System.out.println("Sorry .....");
            System.out.println("Cycle is Not Available");
        }
    }

    public void TorReturn(Cycle cycle){
      cycle.returned();
      GetRent rentCycle=null;
        for (GetRent removeCycle:rentList) {
            if (removeCycle.getCycle()==cycle){
               rentCycle=removeCycle;
               break;
            }// here I am checking for a cycle which is not rented and  you are returning   this cycle
        }

        if(rentCycle!=null){
            rentList.remove(rentCycle);
        }else {
            System.out.println("OOP's  cycle is Not Rented Here");
        }
    }

    public void Manu(){
        Scanner scanner=new Scanner(System.in);
        while (true){
            System.out.println("++++++++++++ Welcome to Cycle Rental System ++++++++++");
            System.out.println("1. Rent ");
            System.out.println("2.Return Cycle");
            System.out.println("3. Exit ");
            int  choice =scanner.nextInt();
            scanner.nextLine();
            if (choice==1){
                System.out.println("++++++++++ Rent a Cycle ++++++");
                System.out.println("Enter your Name ");
                String CustomerName =  scanner.nextLine();

                System.out.println("Here are some Cycle are available ");
                for (Cycle cycle: cycleList) {
                    if (cycle.isAvailable()){
                        System.out.println("Id  "+cycle.getId()+ "  Brand is  "+ cycle.getBrand()+"  Cycle is  "+cycle.getType()+"  type");

                    }
                }//if cycle is available then print the details of cycle

                System.out.println("Emter the Cycle id you want ");
              String cycleId=scanner.nextLine();
                System.out.println("Enter the Number of days ");
               int days =scanner.nextInt();
                scanner.nextLine();
              Customer newCustomer=new Customer(CustomerName.substring(0,3).strip()+customerList.size()+1,CustomerName);
              addCustomer(newCustomer);// added to the customer list
                Cycle selectedCycle=null;
                for (Cycle cycle: cycleList) {
                    if (cycleId.equals(cycle.getId())&& cycle.isAvailable()){
                        selectedCycle=cycle;
                        break;
                    }
                }
                if (selectedCycle!=null){
                    System.out.println("Customer Id  is "+newCustomer.getId()+"  Custommer name is "+ newCustomer.getName());
                    System.out.println("Selrcted Cycle Details are  ");
                    System.out.println("Cycle id "+selectedCycle.getId()+"  Brand is   "+selectedCycle.getBrand()+"  Cycle is type of "+selectedCycle.getType());
                    System.out.println(selectedCycle.getBrand()+" is rented for "+days+"days");
                    int total=selectedCycle.CalPriceforDay(days);
                   if (selectedCycle.getType().equals("Electrical")){
                       System.out.println("Total Rupais to pay  for Electrical Cycls "+total+"");
                   }else {
                       System.out.println("Total Rupais to pay for Manual cycle "+total);
                   }

                }
                System.out.println("For Conform Cycle (Y/N) ");
                String conform=scanner.nextLine();
                if (conform.equalsIgnoreCase("Y")){
                  wantToRent(selectedCycle,newCustomer,days);
                    System.out.println("Cycle i is rented success fully");
                }  else {
                    System.out.println("Cycle rental Canceled ");
                } // Rented
            } else if (choice==2) {
                System.out.println("++++++++++ Return Cycle+++++++++");
                System.out.println("Enter the cycle Id ");
            String cycleId=scanner.nextLine();
            Cycle returnedCycle=null;
                for (Cycle cycle:cycleList) {
                    if (cycle.getId().equals(cycleId)&&!cycle.isAvailable()){
                        returnedCycle=cycle;
                        break;
                    }//find the cycle int list
                }
                if(returnedCycle!=null){
                    Customer customer=null;
                    for (GetRent rent:rentList) {
                        if(rent.getCycle().equals(returnedCycle)){
                            customer =rent.getCustomer();
                            break;
                        }
                    }

                    if (customer!=null){
                     TorReturn(returnedCycle);
                        System.out.println("Cycle is Returned SuccessFully");
                    }else {
                        System.out.println("Cycle is Not Rented or Customer  is Not found  ");
                    }
                }else {
                    System.out.println("Cycle is Not rented or Invalid cycle ID ");
                }
            } else if (choice==3) {
                break;
            }else {
                System.out.println("please Enter Valid Number ");
            }
            System.out.println("+++++++++++++++++++++ Thank You For Comming ++++++++++++++++++++++");


        }
        }


    public static void main(String[] args) {
        CycleRentalCystem system=new CycleRentalCystem();
        Cycle cycle1=new Cycle("001","Hero","Manual",10);
        Cycle cycle2=new Cycle("002","Hero","Electrical",20);
        Cycle cycle3=new Cycle("003","Atlas","Manual",15);
        Cycle cycle4=new Cycle("004","Atlas ","Electrical",25);
        system.addCycle(cycle1);
        system.addCycle(cycle2);
        system.addCycle(cycle3);
        system.addCycle(cycle4);
        system.Manu();
    }
    }











