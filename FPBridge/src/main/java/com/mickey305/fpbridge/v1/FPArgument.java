package com.mickey305.fpbridge.v1;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FPArgument {
    private FPScope fpScope;
    private Set<FPType> fpTypes;

    private FPArgument() {}

    private static class FPArgumentHolder {
        public static final FPArgument INSTANCE = new FPArgument();
    }

    public static FPArgument newInstance(FPScope fpScope, Set<FPType> fpTypes) {
        return FPArgumentHolder.INSTANCE
                .setFpScope(fpScope)
                .setFpTypes(fpTypes);
    }

    public static FPArgument newAllowOwner() {
        return newAllow(FPScope.Owner);
    }

    public static FPArgument newAllowGroup() {
        return newAllow(FPScope.Group);
    }

    public static FPArgument newAllowOthers() {
        return newAllow(FPScope.Others);
    }

    private static FPArgument newAllow(FPScope fpScope) {
        return newInstance(fpScope, new HashSet<>(Arrays.asList(FPType.Read, FPType.Write, FPType.Execute)));
    }

    public static FPArgument newDenyOwner() {
        return newDeny(FPScope.Owner);
    }

    public static FPArgument newDenyGroup() {
        return newDeny(FPScope.Group);
    }

    public static FPArgument newDenyOthers() {
        return newDeny(FPScope.Others);
    }

    private static FPArgument newDeny(FPScope fpScope) {
        return newInstance(fpScope, Collections.emptySet());
    }

    public String buildPosixStmt() {
        return (this.getFpTypes().contains(FPType.Read) ? "r" : "-") +
                (this.getFpTypes().contains(FPType.Write) ? "w" : "-") +
                (this.getFpTypes().contains(FPType.Execute) ? "x" : "-");
    }

    public FPScope getFpScope() {
        return fpScope;
    }

    public FPArgument setFpScope(FPScope fpScope) {
        this.fpScope = fpScope;
        return this;
    }

    public Set<FPType> getFpTypes() {
        return fpTypes;
    }

    public FPArgument setFpTypes(Set<FPType> fpTypes) {
        this.fpTypes = Collections.unmodifiableSet(new HashSet<>(fpTypes));
        return this;
    }

    public int hashCode() {
        return this.getFpScope().hashCode();
    }
}
