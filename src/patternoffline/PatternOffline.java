/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternoffline;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author masud
 */
public class PatternOffline {

    //static ArrayList<Integer>[] trainFeatures = new ArrayList<Integer>()[78];
    static List<Double> [] trainFeatures;
    static List<Double> [] testFeatures;
    static double[] weight;
    static double[] error;
    static int numberOfFeatures;
    static int sizeOfData;
    static int sizeOfTestData=0;
    static double learningRate=0.005;
    
    public static void traindata() throws FileNotFoundException, IOException{
        
        BufferedReader topics = new BufferedReader(new FileReader("Train.txt"));
        String files;
        files = topics.readLine();
        String[]measures=files.split(" ");
        numberOfFeatures=Integer.parseInt(measures[0])+1;
        sizeOfData=Integer.parseInt(measures[2]);
        
        trainFeatures=new List[numberOfFeatures+1];
        testFeatures=new List[numberOfFeatures+1];
        weight=new double[numberOfFeatures+1];
        error=new double[numberOfFeatures+1];
        for (int i =0 ; i<numberOfFeatures+1;i++) {
            trainFeatures[i] = new ArrayList<>();
            testFeatures[i] = new ArrayList<>();
        }
        
        System.out.println("FIles "+sizeOfData);
        for(int i=0;i<sizeOfData;i++){
            trainFeatures[0].add(1.0);
        }
        while((files = topics.readLine()) !=null)
        {
            //System.out.println("files "+files);
            files=files.replaceAll("\\s+"," ");
            files=files.trim();
            String[]parts=files.split(" ");
//            double t1=Double.parseDouble(parts[0]);
//            for (String retval: files.split(" ")) {
//                System.out.println(retval);
//            }
            
            for(int i=0;i<parts.length;i++){
                //System.out.println(parts[i]);
                double t1=Double.parseDouble(parts[i]);
                trainFeatures[i+1].add(t1);
                
            }

        }
        for(int i=0;i<trainFeatures.length;i++){
            ArrayList<Double> temp = (ArrayList<Double>) trainFeatures[i];
            for(int j=0;j<temp.size();j++){
                System.out.print(temp.get(j)+" ");
            }
            System.out.println("");
        }
    }
    
    public static void testdata() throws FileNotFoundException, IOException{
        
        BufferedReader topics = new BufferedReader(new FileReader("Test.txt"));
        String files;
        for(int i=0;i<sizeOfData;i++){
            
            testFeatures[0].add(1.0);
        }
        while((files = topics.readLine()) !=null)
        {
            //System.out.println("files "+files);
            files=files.replaceAll("\\s+"," ");
            files=files.trim();
            String[]parts=files.split(" ");
//            double t1=Double.parseDouble(parts[0]);
//            for (String retval: files.split(" ")) {
//                System.out.println(retval);
//            }
            for(int i=0;i<parts.length;i++){
                //System.out.println(parts[i]);
                double t1=Double.parseDouble(parts[i]);
                testFeatures[i+1].add(t1);
                
            }

        }
        ArrayList<Double> temp = (ArrayList<Double>) testFeatures[0];
        sizeOfTestData=temp.size();
        for(int i=0;i<testFeatures.length;i++){
            temp = (ArrayList<Double>) testFeatures[i];
            
            for(int j=0;j<temp.size();j++){
                System.out.print(temp.get(j)+" ");
            }
            System.out.println("");
        }
        
        System.out.println("SIze "+sizeOfTestData);
    }
    
    public static void Normaltest(){
        double y=0;
        int totalCorrect=0;
        double Class=1.0;
        for(int i=0;i<sizeOfTestData;i++){
            y=0;
            for(int j=0;j<numberOfFeatures;j++){
                y=y+(weight[j]*testFeatures[j].get(i));
            }
            if(y>0){
                Class=1.0;
                //System.out.println("1");
            }
            else {
                Class=2.0;
                //System.out.println("2");
            }
            if(testFeatures[numberOfFeatures].get(i)==Class){
                totalCorrect++;
            }
        }
        
        double Accuracy=(double)totalCorrect/sizeOfTestData;
        System.out.println("Accuracy "+Accuracy);
    }
    
    
    public static void Normal(){
        
        double Class=1.0;
        double y;
        double add=0;
        int K=0;
        while(K++<=100){
            for(int i=0;i<numberOfFeatures+1;i++){
                error[i]=0;
            }
            for(int i=0;i<sizeOfData;i++){
                y=0.0;

                for(int j=0;j<numberOfFeatures;j++){
                    y=y+(weight[j]*trainFeatures[j].get(i));
                }
                if(y>0){
                    Class=1.0;
                    //System.out.println("1");
                }
                else {
                    Class=2.0;
                    //System.out.println("2");
                }
                add=0;
                if(trainFeatures[numberOfFeatures].get(i)!=Class){
                    System.out.println("Original Class "+trainFeatures[numberOfFeatures].get(i));
                    if(trainFeatures[numberOfFeatures].get(i)==1.0)add=-1;
                    else add=1;
                }

                for(int j=0;j<numberOfFeatures;j++){
                    error[j]+=(add*trainFeatures[j].get(i));
                }

            }
            System.out.println("For Iteration: "+K);
            for(int j=0;j<weight.length;j++){
                System.out.println(" weight[j] "+ weight[j]);
                weight[j]=weight[j]-(learningRate*error[j]);
            }
            System.out.println("");
            int Count=0;
            for(int j=0;j<error.length;j++){
                if(error[j]!=0.0)Count++;
            }
            if(Count==0)break;
        }
        Normaltest();
    }
    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        
        traindata();
        testdata();
        for(int i=0;i<numberOfFeatures+1;i++){
            weight[i]=Math.random();
            //weight[i]=0;
            error[i]=0;
        }
        
        System.out.println("Before Train ");
        for(int j=0;j<weight.length;j++){
            System.out.println(" weight[j] "+ weight[j]);
        }
        System.out.println("\n\n");
        
        System.out.println("Number of "+numberOfFeatures );
        //Normal();
        //RewardAndPunishment.RewardAndPunishment();
        Pocket.Pocket();
                
       
    }
    
}
