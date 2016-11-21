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
public class RewardAndPunishment {
    
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
    
    public static void RewardAndPunishment(){
        double Class=1.0;
        double y;
        double add=0;
        int K=0;
        while(K++<=100){
            for(int i=0;i<numberOfFeatures+1;i++){
                PatternOffline.error[i]=0;
            }
            for(int i=0;i<sizeOfData;i++){
                y=0.0;
                for(int I=0;I<numberOfFeatures+1;I++){
                    PatternOffline.error[I]=0;
                }
                for(int j=0;j<numberOfFeatures;j++){
                    y=y+(weight[j]*trainFeatures[j].get(i));
                }
                if(y>0){
                    Class=1.0;
                    System.out.println("1");
                }
                else {
                    Class=2.0;
                    System.out.println("2");
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
                
                
                for(int j=0;j<weight.length;j++){
                   //System.out.println(" weight[j] "+ error[j]);
                    weight[j]=weight[j]-(learningRate*error[j]);
                }
                System.out.println("");

            }
            System.out.println("For Iteration: "+K);
            int Count=0;
            for(int j=0;j<error.length;j++){
                if(error[j]!=0.0)Count++;
            }
            if(Count==0)break;
        
        }
        Normaltest();
    }
    
}
