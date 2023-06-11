package com.namsor.oss.classify.bayes.rocksdb;

import com.google.common.primitives.Longs;
import com.namsor.oss.classify.bayes.ClassificationImpl;
import com.namsor.oss.classify.bayes.ClassifyException;
import com.namsor.oss.classify.bayes.IClassification;
import com.namsor.oss.classify.bayes.INaiveBayesClassifier;
import com.namsor.oss.classify.bayes.PersistentClassifierException;
import org.rocksdb.ReadOptions;
import org.rocksdb.RocksDBException;

import java.util.HashMap;
import java.util.Map;

/**
 * Naive Bayes Classifier with Laplace smoothing and implementation with RocksDB/Speedb
 * as key/value store. Learning is Synchronized but classification is not.
 * Also, this implementation is using log product log(a*b)=log(a)+log(b) to reduce overflow risk
 * @author elian carsenat, NamSor SAS
 */
public class NaiveBayesClassifierRocksDBLaplacedLogImpl extends NaiveBayesClassifierRocksDBLaplacedImpl implements INaiveBayesClassifier {

    public NaiveBayesClassifierRocksDBLaplacedLogImpl(String classifierName, String[] categories, String rootPathWritable, double alpha, boolean variant) throws PersistentClassifierException {
        super(classifierName, categories, rootPathWritable, alpha, variant);
    }

    public NaiveBayesClassifierRocksDBLaplacedLogImpl(String classifierName, String[] categories, String rootPathWritable) throws PersistentClassifierException {
        super(classifierName, categories, rootPathWritable);
    }

    @Override
    public IClassification classify(Map<String, String> features, final boolean explainData) throws ClassifyException {
        Map<String, Long> explanation = null;
        if (explainData) {
            explanation = new HashMap();
        }
        ReadOptions ro = new ReadOptions();
        ro.setSnapshot(getDb().getSnapshot());
        try {
            String pathGlobal = pathGlobal();
            String pathGlobalCountCategories = pathGlobalCountCategories();
            long globalCount = (getDb().get(ro, bytes(pathGlobal)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathGlobal))));
            if (explainData) {
                explanation.put(pathGlobal, globalCount);
            }
            long globalCountCategories = (getDb().get(ro, bytes(pathGlobalCountCategories)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathGlobalCountCategories))));
            if (explainData) {
                explanation.put(pathGlobalCountCategories, globalCountCategories);
            }
            double[] likelyhood = new double[getCategories().length];
            double likelyhoodTot = 0;
            for (int i = 0; i < getCategories().length; i++) {
                String category = getCategories()[i];
                String pathCategory = pathCategory(category);
                long categoryCount = (getDb().get(ro, bytes(pathCategory)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathCategory))));
                if (explainData) {
                    explanation.put(pathCategory, categoryCount);
                }
                double logProduct = 0.0d;
                for (Map.Entry<String, String> feature : features.entrySet()) {
                    String pathFeatureKey = pathFeatureKey(feature.getKey());
                    long featureCount = (getDb().get(ro, bytes(pathFeatureKey)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathFeatureKey))));
                    if (explainData) {
                        explanation.put(pathFeatureKey, featureCount);
                    }
                    if (featureCount > 0) {
                        String pathCategoryFeatureKey = pathCategoryFeatureKey(category, feature.getKey());
                        long categoryFeatureCount = (getDb().get(ro, bytes(pathCategoryFeatureKey)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathCategoryFeatureKey))));
                        if (explainData) {
                            explanation.put(pathCategoryFeatureKey, categoryFeatureCount);
                        }
                        String pathFeatureKeyCountValueTypes = pathFeatureKeyCountValueTypes(feature.getKey());
                        long featureCountValueTypes = (getDb().get(ro, bytes(pathFeatureKeyCountValueTypes)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathFeatureKeyCountValueTypes))));
                        if (explainData) {
                            explanation.put(pathFeatureKeyCountValueTypes, featureCountValueTypes);
                        }
                        String pathCategoryFeatureKeyValue = pathCategoryFeatureKeyValue(category, feature.getKey(), feature.getValue());
                        long categoryFeatureValueCount = (getDb().get(ro, bytes(pathCategoryFeatureKeyValue)) == null ? 0 : Longs.fromByteArray(getDb().get(ro, bytes(pathCategoryFeatureKeyValue))));
                        if (explainData) {
                            explanation.put(pathCategoryFeatureKeyValue, categoryFeatureValueCount);
                        }
                        double basicLogProbability = (categoryFeatureCount == 0 ? 0 : 0d + Math.log(categoryFeatureValueCount + getAlpha()) - Math.log(categoryFeatureCount + featureCountValueTypes * getAlpha()));
                        logProduct += basicLogProbability;
                    }
                }
                if (isVariant()) {
                    likelyhood[i] = Math.exp( 0d + Math.log(categoryCount + getAlpha()) - Math.log((globalCount + globalCountCategories * getAlpha())) + logProduct );
                } else {
                    likelyhood[i] = Math.exp( 0d + Math.log(categoryCount) - Math.log(globalCount) + logProduct);
                }
                likelyhoodTot += likelyhood[i];
            }
            return new ClassificationImpl(features, likelihoodsToProbas(likelyhood, likelyhoodTot), explanation, true, isVariant(), getAlpha(), likelyhoodTot, true);
        } catch (RocksDBException ex) {
            throw new PersistentClassifierException(ex);
        } finally {
            // Make sure you close the snapshot to avoid resource leaks.
            ro.snapshot().close();
        }
    }
}
