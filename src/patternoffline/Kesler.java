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
import static patternoffline.PatternOffline.numberOfFeatures;

/**
 *
 * @author masud
 */
public class Kesler {
    //static List<Double> [] trainFeatures;
    static ArrayList< ArrayList<Double> > testFeatures = new ArrayList<ArrayList<Double>>();
    static ArrayList<FeatureVector> featureArray=new ArrayList<>();
    static List<Double> [] testData;
    static List<Double> [] trainData;
    static double[] weight;
    static double[] error;
    static int numberOfFeatures;
    static int sizeOfData;
    static int sizeOfTestData=0;
    static double learningRate=0.005;
    static int numberOfClass;
    int vectorLength;
    
    public class FeatureVector{
        ArrayList<Double>[]trainFeatures;
        public FeatureVector(int i,int j, int k,ArrayList<Double>arr){
            trainFeatures = new ArrayList[numberOfClass+1];
            for(int I=0;I<numberOfClass+1;I++){
                trainFeatures[I] = new ArrayList<>();
            }
            trainFeatures[i]=arr;
            ArrayList<Double> temp1=new ArrayList<>();
            for(int I=0;I<arr.size();I++){
                temp1.add(0.0);
            }
            ArrayList<Double> temp2=new ArrayList<>();
            for(int J=0;J<arr.size();J++){
                temp2.add(-1*arr.get(J));
            }
            trainFeatures[j]=temp2;
            trainFeatures[k]=temp1;
            
        }
        
        public ArrayList<Double>[] getTrainFeatures(){
            return trainFeatures;
        }
    }
    
    
    public void traindata() throws FileNotFoundException, IOException{
        
        BufferedReader topics = new BufferedReader(new FileReader("Train_Kesler.txt"));
        String files;
        files = topics.readLine();
        String[]measures=files.split(" ");
        numberOfFeatures=Integer.parseInt(measures[0])+1;
        sizeOfData=Integer.parseInt(measures[2]);
        numberOfClass=Integer.parseInt(measures[1]);
//        System.out.println("Class Number "+numberOfClass);
        trainData=new List[(2*sizeOfData)+1];
        testData=new List[numberOfFeatures+1];
        weight=new double[(numberOfFeatures*numberOfClass)+1];
        error=new double[(numberOfFeatures*numberOfClass)+1];
        for (int i =0 ; i<numberOfFeatures+1;i++) {
            testData[i] = new ArrayList<>();
        }
        
        for(int i=0;i<(2*sizeOfData)+1;i++){
            trainData[i] = new ArrayList<>();
        }
        
        System.out.println("FIles "+sizeOfData);
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
            ArrayList<Double> temp=new ArrayList<>();
            temp.add(1.0);
            for(int i=0;i<parts.length-1;i++){
                //System.out.println(parts[i]);
                
                double t1=Double.parseDouble(parts[i]);
                temp.add(t1);
                //trainData[i+1].add(t1);
            }
            double t1=Double.parseDouble(parts[parts.length-1]);
            if(t1==1.0){
                FeatureVector featurClass= new FeatureVector(1,2,3,temp);
                featureArray.add(featurClass);
                FeatureVector featurClass1= new FeatureVector(1,3,2,temp);
                featureArray.add(featurClass1);
            }
            if(t1==2.0){
                FeatureVector featurClass= new FeatureVector(2,1,3,temp);
                featureArray.add(featurClass);
                FeatureVector featurClass1= new FeatureVector(2,3,1,temp);
                featureArray.add(featurClass1);
            }
            if(t1==3.0){
                FeatureVector featurClass= new FeatureVector(3,2,1,temp);
                featureArray.add(featurClass);
                FeatureVector featurClass1= new FeatureVector(3,1,2,temp);
                featureArray.add(featurClass1);
            }

        }
        for(int i=0;i<featureArray.size();i++){
            FeatureVector featurClass=featureArray.get(i);
            ArrayList<Double>[] tempArray=featurClass.getTrainFeatures();
            
            for(int j=0;j<tempArray.length;j++){
                ArrayList<Double> temp=tempArray[j];
                //System.out.println("Size "+tempArray[j].size());
                for(int k=0;k<temp.size();k++){
                    //System.out.print(temp.get(k)+" ");
                    trainData[i].add(temp.get(k));
                }
            }
            //System.out.println("");
        }
        
        System.out.println("Size "+featureArray.size());
    }
    
    public static void testdata() throws FileNotFoundException, IOException{
        
        BufferedReader topics = new BufferedReader(new FileReader("Test_Kesler.txt"));
        String files;
        for(int i=0;i<sizeOfData;i++){
            
            testData[0].add(1.0);
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
                testData[i+1].add(t1);
                
            }

        }
        ArrayList<Double> temp = (ArrayList<Double>) testData[0];
        sizeOfTestData=temp.size();
        for(int i=0;i<testData.length;i++){
            temp = (ArrayList<Double>) testData[i];
            
            for(int j=0;j<temp.size();j++){
                System.out.print(temp.get(j)+" ");
            }
            System.out.println("");
        }
        
        System.out.println("SIze "+testData.length);
    }
    
    public static void Keslertest(){
        double y=0;
        int totalCorrect=0;
        double Class=1.0;
        double MAXIMUM=0;
        for(int i=0;i<sizeOfTestData;i++){
            y=0;
            ArrayList<Double> temp=new ArrayList<>();
            MAXIMUM=0;
            for(int k=0;k<testFeatures.size();k++){
                temp=testFeatures.get(k);
                y=0;
                for(int j=0;j<temp.size();j++){
                    y=y+(temp.get(j)*testData[j].get(i));
                }
                if(y>MAXIMUM){
                    MAXIMUM=y;
                    Class=k+1;
                }
            }
            System.out.println("CLASS "+Class);
            if(testData[numberOfFeatures].get(i)==Class){
                totalCorrect++;
            }
        }
        
        double Accuracy=(double)totalCorrect/sizeOfTestData;
        System.out.println("Accuracy "+Accuracy);
    }
    
    
    public void getFeatureVector(int length){
        double Class=1.0;
        double y;
        double add=0;
        int K=0;
        //Kesler kesler=new Kesler();
        //System.out.println("qweqwewqe "+length);
        ArrayList<Double> temp=new ArrayList<>();
        while(K++<=100){
            for(int i=0;i<length+1;i++){
                error[i]=0;
            }
            for(int i=0;i<trainData.length-1;i++){
                y=0.0;
                temp = (ArrayList<Double>) trainData[i];
//                System.out.println("dsfsdfdsffds "+temp.size());
//                for(int j=0;j<trainData[i].size();j++){
//                    System.out.print(temp.get(j)+" ");
//                }
                for(int j=0;j<length;j++){
                    y=y+(weight[j]*temp.get(j));
                }
                if(y>0){
                    Class=1.0;
                    add=0;
                    //System.out.println("1");
                }
                else {
                    Class=2.0;
                    add=-1;
                    //System.out.println("2");
                }
                //System.out.println("Add "+add);
                for(int j=0;j<length;j++){
                    error[j]+=(add*trainData[i].get(j));
                }

            }
//            for(int j=0;j<length;j++){
//                System.out.print(error[j]+" ");
//            }
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
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Kesler kesler=new Kesler();
        
        kesler.traindata();
        kesler.testdata();
        for(int i=0;i<weight.length;i++){
            //weight[i]=Math.random();
            weight[i]=0;
            error[i]=0;
        }
        kesler.vectorLength=numberOfClass*numberOfFeatures;
        System.out.println("LEn "+weight.length);
        
        ArrayList<Double> temp=new ArrayList<>();
        
//        for(int i=0;i<trainData.length;i++){
//            temp = (ArrayList<Double>) trainData[i];
//            System.out.println("SIZE "+temp.size());
//            for(int j=0;j<temp.size();j++){
//                System.out.print(temp.get(j)+" ");
//            }
//            System.out.println("");
//        }
        
        kesler.getFeatureVector(kesler.vectorLength);
        for(int i=0;i<4;i++){
            temp.add(weight[i]);
        }
        testFeatures.add(temp);
        //temp.clear();
        ArrayList<Double> temp1=new ArrayList<>();
        for(int i=4;i<8;i++){
            temp1.add(weight[i]);
        }
        testFeatures.add(temp1);
        
        ArrayList<Double> temp2=new ArrayList<>();
        for(int i=8;i<12;i++){
            temp2.add(weight[i]);
        }
        testFeatures.add(temp2);
        
        
        for(int i=0;i<testFeatures.size();i++){
            temp=testFeatures.get(i);
            System.out.println("SIZZe "+temp.size());
            for(int j=0;j<temp.size();j++){
                System.out.println(temp.get(j)+" ");
            }
            System.out.println("");
        }
        
        kesler.Keslertest();
        
    }
    
}
