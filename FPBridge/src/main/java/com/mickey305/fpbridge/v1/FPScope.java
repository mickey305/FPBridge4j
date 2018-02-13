package com.mickey305.fpbridge.v1;

public enum FPScope {
    Owner, Group, Others;

    public boolean is(FPScope fpScope) { return this == fpScope; }
}
