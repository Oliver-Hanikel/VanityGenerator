package com.fatsoapps.vanitygenerator.core.search;

import com.fatsoapps.vanitygenerator.core.network.GlobalNetParams;
import com.fatsoapps.vanitygenerator.core.query.Query;
import org.bitcoinj.core.ECKey;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Search is a Runnable that takes a @BaseSearchListener and searches for Query's defined by a user. Updates occur by
 * definitions in place of BaseSearchListener. Each Search has its own list of queries that it is searching against.
 * @see com.fatsoapps.vanitygenerator.core.search.BaseSearchListener for usage.
 * @see com.fatsoapps.vanitygenerator.core.search.PoolSearch for a more multi-threaded approach.
 * <b>If this is implemented on Android, you need to extend this class and set the appropriate thread priority.</b>
 */
public class Search implements Runnable {

    private BaseSearchListener listener;
    private GlobalNetParams netParams;
    private ArrayList<Query> queries;
    private long updateAmount = 1000;

    public Search(BaseSearchListener listener, GlobalNetParams netParams, long updateAmount, Query... queries) {
        this(listener, netParams, queries);
        this.updateAmount = updateAmount;
    }

    public Search(BaseSearchListener listener, GlobalNetParams netParams, Query... queries) {
        this.listener = listener;
        this.netParams = netParams;
        this.queries = new ArrayList<Query>(Arrays.asList(queries));
    }

    public void run() {
        ECKey key = new ECKey();
        long generated = 1;
        while (!Thread.interrupted() && queries.size() > 0) {
            for (Query query: queries) {
                if (query.matches(key, netParams)) {
                    if (listener != null) {
                        listener.onAddressFound(key, query.isCompressed());
                    }
                    if (!query.isFindUnlimited()) {
                        queries.remove(query);
                        break;
                    }
                }
            }
            if (generated % updateAmount == 0 && listener != null) {
                listener.updateBurstGenerated(generated, updateAmount);
            }
            key = new ECKey();
            generated++;

        }
        if (listener != null) {
            listener.onTaskCompleted(generated);
        }
    }

}