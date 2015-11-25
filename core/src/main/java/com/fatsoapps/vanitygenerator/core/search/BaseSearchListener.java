package com.fatsoapps.vanitygenerator.core.search;

import org.bitcoinj.core.ECKey;

/**
 * BaseSearchListener is an interface that communicates from Search related threads.
 * @see com.fatsoapps.vanitygenerator.core.search.Search
 */
public interface BaseSearchListener {

    /**
     * Called when a Search thread finds a key.
     * @param key - ECKey that contains a matched key
     * @param isCompressed - Whether the query was compressed or not.
     */
    void onAddressFound(ECKey key, boolean isCompressed);

    /**
     * Called when a Search thread has generated @burstGenerated amount of addresses. This is meant to let the user know
     * that the threads are actively searching.
     * @param totalGenerated - the total amount of addresses generated for that thread.
     * @param burstGenerated - the burst amount generated for that thread.
     */
    void updateBurstGenerated(long totalGenerated, long burstGenerated);

    /**
     * Called when a Search thread is done OR has be killed / cancelled by the user. This can be called multiple times
     * if there is another thread running in parallel.
     * @param totalGenerated
     */
    void onTaskCompleted(long totalGenerated);

}