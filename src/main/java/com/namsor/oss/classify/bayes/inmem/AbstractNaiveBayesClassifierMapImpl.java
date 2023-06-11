package com.namsor.oss.classify.bayes.inmem;

import com.namsor.oss.classify.bayes.AbstractNaiveBayesClassifierImpl;
import com.namsor.oss.classify.bayes.PersistentClassifierException;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A simple, scalable Naive Bayes Classifier, based on a key-value store in
 * memory using ConcurrentHashMap
 *
 * @author elian
 */
public abstract class AbstractNaiveBayesClassifierMapImpl extends AbstractNaiveBayesClassifierImpl {

    private final Map<String, Long> db;

    /**
     * Create in-memory classifier using ConcurrentHashMap
     *
     * @param classifierName The classifier name
     * @param categories The classification categories
     */
    public AbstractNaiveBayesClassifierMapImpl(String classifierName, String[] categories) {
        super(classifierName, categories);
        this.db = new ConcurrentHashMap();
    }

    @Override
    public void dbClose() throws PersistentClassifierException {
    }

    @Override
    public void dbCloseAndDestroy() throws PersistentClassifierException {
        dbClose();
    }

    @Override
    public long dbSize() throws PersistentClassifierException {
        return getDb().size();
    }

    @Override
    public synchronized void dumpDb(Writer w) throws PersistentClassifierException {
        for (Map.Entry<String, Long> entry : getDb().entrySet()) {
            String key = entry.getKey();
            long value = entry.getValue();
            try {
                w.append(key + "|" + value + "\n");
            } catch (IOException ex) {
                Logger.getLogger(NaiveBayesClassifierMapImpl.class.getName()).log(Level.SEVERE, null, ex);
                throw new PersistentClassifierException(ex);
            }
        }
    }

    /**
     * @return the db
     */
    protected Map<String, Long> getDb() {
        return db;
    }

}
