package com.namsor.oss.classify.bayes;

import com.namsor.oss.classify.bayes.rocksdb.NaiveBayesClassifierRocksDBImpl;
import com.namsor.oss.samples.MainSample3;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.namsor.oss.samples.MainSample1.colName;
import static com.namsor.oss.samples.MainSample1.data;
import static com.namsor.oss.samples.MainSample1.*;
import static com.namsor.oss.samples.MainSample2.*;
import static com.namsor.oss.samples.MainSample3.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;


public class NaiveBayesClassifierRocksDBImplTest {
    private static final String ROCKSDB_DIR = "/tmp/rocksdb";

    public NaiveBayesClassifierRocksDBImplTest() {
    }


    @BeforeEach
    public void setUp() {
        File rocksdb = new File(ROCKSDB_DIR);
        if (rocksdb.exists() && rocksdb.isDirectory()) {
            // ok
        } else {
            rocksdb.mkdirs();
        }
    }


    /**
     * Test based on https://taylanbil.github.io/boostedNB or
     * http://ai.fon.bg.ac.rs/wp-content/uploads/2015/04/ML-Classification-NaiveBayes-2014.pdf
     */
    @Test
    public void testTransientLearnClassifySample1() throws Exception {
        String[] cats = {YES, NO};
        NaiveBayesClassifierRocksDBImpl bayes = new NaiveBayesClassifierRocksDBImpl("tennis", cats, ROCKSDB_DIR);
        for (int i = 0; i < data.length; i++) {
            Map<String, String> features = new HashMap();
            for (int j = 0; j < colName.length - 1; j++) {
                features.put(colName[j], data[i][j]);
            }
            bayes.learn(data[i][colName.length - 1], features);
        }
        Map<String, String> features = new HashMap();
        features.put("outlook", "Sunny");
        features.put("temp", "Cool");
        features.put("humidity", "High");
        features.put("wind", "Strong");
        IClassification predict = bayes.classify(features, true);
        assertNotNull(predict);
        assertEquals(predict.getClassProbabilities().length, 2);
        assertEquals(predict.getClassProbabilities()[0].getCategory(), "No");
        assertEquals(predict.getClassProbabilities()[1].getCategory(), "Yes");
        assertEquals(predict.getClassProbabilities()[0].getProbability(), 0.795417348608838, .0001);
        assertEquals(predict.getClassProbabilities()[1].getProbability(), 0.204582651391162, .0001);
        bayes.dbCloseAndDestroy();
    }

    /**
     * Test based on
     * https://towardsdatascience.com/introduction-to-na%C3%AFve-bayes-classifier-fa59e3e24aaf
     */
    @Test
    public void testLearnClassifySample2() throws Exception {
        String[] cats = {ZERO, ONE};
        // Create a new bayes classifier with string categories and string features.
        // INaiveBayesClassifier bayes1 = new NaiveBayesClassifierLevelDBImpl("sentiment", cats, ".", 100);
        NaiveBayesClassifierRocksDBImpl bayes = new NaiveBayesClassifierRocksDBImpl("sentiment", cats, ROCKSDB_DIR);
        //NaiveBayesClassifierRocksDBImpl bayes = new NaiveBayesClassifierRocksDBImpl("intro", cats, ".", 100);

// Examples to learn from.
        for (int i = 0; i < Y.length; i++) {
            Map<String, String> features = new HashMap();
            features.put("X1", X1[i]);
            features.put("X2", X2[i]);
            bayes.learn(Y[i], features);
        }

// Here are is X(B,S) to classify.
        Map<String, String> features = new HashMap();
        features.put("X1", "B");
        features.put("X2", "S");
        IClassification predict = bayes.classify(features, true);
        assertNotNull(predict);
        assertEquals(predict.getClassProbabilities().length, 2);
        assertEquals(predict.getClassProbabilities()[0].getCategory(), "0");
        assertEquals(predict.getClassProbabilities()[1].getCategory(), "1");
        assertEquals(predict.getClassProbabilities()[0].getProbability(), 0.75, .0001);
        assertEquals(predict.getClassProbabilities()[1].getProbability(), 0.25, .0001);
        bayes.dbCloseAndDestroy();

    }


    /**
     * Test based on
     * https://www.machinelearningplus.com/predictive-modeling/how-naive-bayes-algorithm-works-with-example-and-full-code/
     */
    @Test
    public void testLearnClassifySample3() throws Exception {
        String[] cats = {BANANA, ORANGE, OTHER};
        // Create a new bayes classifier with string categories and string features.
        NaiveBayesClassifierRocksDBImpl bayes = new NaiveBayesClassifierRocksDBImpl("fruit", cats, ROCKSDB_DIR);
        //NaiveBayesClassifierTransientLaplacedImpl bayes = new NaiveBayesClassifierTransientLaplacedImpl("fruit", cats);
        //NaiveBayesClassifierRocksDBImpl bayes = new NaiveBayesClassifierRocksDBImpl("intro", cats, ".", 100);

        // Examples to learn from.
        for (int i = 0; i < MainSample3.data.length; i++) {
            Map<String, String> features = new HashMap();
            features.put(MainSample3.colName[1], MainSample3.data[i][1]);
            features.put(MainSample3.colName[2], MainSample3.data[i][2]);
            features.put(MainSample3.colName[3], MainSample3.data[i][3]);
            bayes.learn(MainSample3.data[i][0], features, Long.parseLong(MainSample3.data[i][4]));
        }
        /**
         * Calculate the likelihood that: Long, Sweet, Yellow is a Banana
         */

        // Here are is X(B,S) to classify.
        Map<String, String> features = new HashMap();
        features.put("Long", "Yes");
        features.put("Sweet", "Yes");
        features.put("Yellow", "Yes");
        features.put("Dummy", "Yes");
        IClassification predict = bayes.classify(features, true);
        assertNotNull(predict);
        assertEquals(predict.getClassProbabilities().length, 3);
        assertEquals(predict.getClassProbabilities()[0].getCategory(), BANANA);
        assertEquals(predict.getClassProbabilities()[1].getCategory(), OTHER);
        assertEquals(predict.getClassProbabilities()[2].getCategory(), ORANGE);
        assertEquals(predict.getClassProbabilities()[0].getProbability(), 0.9307479224376731, .0001);
        assertEquals(predict.getClassProbabilities()[1].getProbability(), 0.06925207756232689, .0001);
        assertEquals(predict.getClassProbabilities()[2].getProbability(), 0, .0001);
    }

}
