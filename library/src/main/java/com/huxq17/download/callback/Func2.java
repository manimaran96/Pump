package com.huxq17.download.callback;

import android.support.annotation.Nullable;

public interface Func2<R> {
    /**
     *
     * @param result Result maybe null
     */
    void call(@Nullable R result);
}