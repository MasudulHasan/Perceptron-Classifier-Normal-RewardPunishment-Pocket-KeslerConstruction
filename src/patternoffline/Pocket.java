/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patternoffline;

import static patternoffline.PatternOffline.error;
import static patternoffline.PatternOffline.learningRate;
import static patternoffline.PatternOffline.numberOfFeatures;
import static patternoffline.PatternOffline.sizeOfData;
import static patternoffline.PatternOffline.sizeOfTestData;
import static patternoffline.PatternOffline.testFeatures;
import static patternoffline.PatternOffline.trainFeatures;
import static patternoffline.PatternOffline.weight;

/**
 *
 * @author masud
 */
public class Pocket {
    static double[] Finalweight=new double[numberOfFeatures+1];;
    public static void Pockettest(){
        double y=0;
        int totalCorrect=0;
        double Class=1.0;
        for(int i=0;i<sizeOfTestData;i++){
            y=0;
            for(int j=0;j<numberOfFeatures;j++){
                y=y+(Finalweight[j]*testFeatures[j].get(i));
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
    
    
    public static void Pocket(){
        
        double Class=1.0;
        double y;
        double add=0;
        int K=0;
        int mistake=0;
        int Minimum=99999;
        while(K++<=100){
            for(int i=0;i<numberOfFeatures+1;i++){
                error[i]=0;
            }
            mistake=0;
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
                    
                    mistake++;
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
            if(mistake<Minimum){
                Minimum=mistake;
                for(int j=0;j<weight.length;j++){
                    Finalweight[j]=weight[j];
                }
                
            }
            int Count=0;
            for(int j=0;j<error.length;j++){
                if(error[j]!=0.0)Count++;
            }
            if(Count==0)break;
        }
        Pockettest();
    }
}
