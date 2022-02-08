package weka;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffLoader;

public class demo {

    public static void main(String[] args) throws Exception {
        
        Classifier m_classifier = new RandomForest();  
        File inputFile = new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\weka\\traindata.arff");//Training corpus file  
        ArffLoader atf = new ArffLoader();   
        atf.setFile(inputFile);  
        Instances instancesTrain = atf.getDataSet(); // Read in training documents      
        inputFile = new File("C:\\Users\\mouna\\git\\TraceTool\\TraceTool\\src\\weka\\testdata.arff");//Test corpus file  
        atf.setFile(inputFile);            
        Instances instancesTest = atf.getDataSet(); // Read in the test file  
        instancesTest.setClassIndex(0); //Setting the line number of the categorized attribute (No. 0 of the first action), instancesTest.numAttributes() can get the total number of attributes.  
        instancesTrain.setClassIndex(0);  
        m_classifier.buildClassifier(instancesTrain); //train
        System.out.println(m_classifier);
        
        // Preservation model
        SerializationHelper.write("LibSVM.model", m_classifier);//Parameter 1 saves the file for the model, and classifier 4 saves the model.
        double TP_T=0.0; double FP_T=0.0; double FN_T=0.0; 
        double TP_N=0.0; double FP_N=0.0; double FN_N=0.0;
        classify(m_classifier, instancesTest, TP_T, FP_T, FN_T, TP_N, FP_N, FN_N); 
        
        
       

       

        
    }

	private static void classify(Classifier m_classifier, Instances instancesTest, double TP_T, double FP_T, double FN_T, double TP_N, double FP_N, double FN_N) throws Exception {
        double sum = instancesTest.numInstances();//Examples of test corpus  


		for(int  i = 0;i<sum;i++)//Test classification result 1
    {  
	       
			
        if(m_classifier.classifyInstance(instancesTest.instance(i))==instancesTest.instance(i).classValue())//If the predictive value is equal to the answer value (the correct answer must be provided by the categorized column in the test corpus, then the result will be meaningful)  
        {  
            if(instancesTest.instance(i).classValue()==1.0) {
            	TP_T++; 
            }else if(instancesTest.instance(i).classValue()==0.0) {
            	TP_N++; 
            }
        	
        	
        }else {
        	if(m_classifier.classifyInstance(instancesTest.instance(i))==1.0 && instancesTest.instance(i).classValue()==0.0  ) {
            	FP_T++; FN_N++; 
            }else if(m_classifier.classifyInstance(instancesTest.instance(i))==0.0 && instancesTest.instance(i).classValue()==1.0 ) {
            	FN_T++; FP_N++; 
            }
        	
        }
    } 
		
		 	System.out.println("T Prec: "+TP_T/(TP_T+FP_T)*100);
	        System.out.println("T Rec: "+TP_T/(TP_T+FN_T)*100);
	        System.out.println("N Prec: "+TP_N/(TP_N+FP_N)*100);
	        System.out.println("N Rec: "+TP_N/(TP_N+FN_N)*100);
	        System.out.println();
		
	
	}
}