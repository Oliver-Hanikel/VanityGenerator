package com.fatsoapps.vanitygenerator.android.search;

import android.os.Process;
import com.fatsoapps.vanitygenerator.core.network.GlobalNetParams;
import com.fatsoapps.vanitygenerator.core.query.QueryPool;
import com.fatsoapps.vanitygenerator.core.search.BaseSearchListener;
import com.fatsoapps.vanitygenerator.core.search.PoolSearch;

/**
 * AndroidPoolSearch is an extension of PoolSearch built for Android. The main purpose this class serves is setting
 * the proper thread priority for mobile devices. All other logic is held within PoolSearch.
 */
public class AndroidPoolSearch extends PoolSearch {

    public AndroidPoolSearch(BaseSearchListener listener, QueryPool pool, GlobalNetParams netParams) {
        super(listener, pool, netParams);
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        super.run();
    }

}