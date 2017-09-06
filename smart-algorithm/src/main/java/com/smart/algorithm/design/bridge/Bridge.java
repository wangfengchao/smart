package com.smart.algorithm.design.bridge;

import com.smart.algorithm.design.bridge.service.Sourceable;

/**
 * Created by fc.w on 2017/9/5.
 */
public abstract class Bridge {

    private Sourceable sourceable;

    public void method() {
        sourceable.method();
    }

    public Sourceable getSourceable() {
        return sourceable;
    }

    public void setSourceable(Sourceable sourceable) {
        this.sourceable = sourceable;
    }
}
