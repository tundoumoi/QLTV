/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import java.util.Scanner;

/**
 *
 * @author LENOVO Ideapad 3
 */
public class view {
    private Scanner sc = new Scanner(System.in);

    public view() {
    }
    
    public int getchoice(int min, int max){
        int choice = 0;
        do{
            try{
                System.out.println("enter your choice : ");
                choice = sc.nextInt();
            }catch(Exception e){
                System.out.println("Error "+e.getMessage());
                sc.nextLine();
            }
        }while(choice<min&&choice>max);
        return choice;
    }
    
    public String getString(){
        return sc.nextLine();
    }
    
    public void message(String x){
        System.out.println(x);
    }
}
