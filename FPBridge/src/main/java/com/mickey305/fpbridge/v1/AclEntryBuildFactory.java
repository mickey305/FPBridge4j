package com.mickey305.fpbridge.v1;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.Set;

public class AclEntryBuildFactory {
    private UserPrincipalLookupService userPrincipalLookupService;

    @FunctionalInterface
    private interface Supplier<T> {
        T get() throws IOException;
    }

    private static class AclEntryBuildFactoryHolder {
        public final static AclEntryBuildFactory INSTANCE = new AclEntryBuildFactory();
    }

    public static AclEntryBuildFactory getInstance() {
        return AclEntryBuildFactoryHolder.INSTANCE;
    }

    private AclEntryBuildFactory() {
        this.setUserPrincipalLookupService(FileSystems.getDefault().getUserPrincipalLookupService());
    }

    public AclEntry createAclEntryByName(String name, Set<AclEntryPermission> permissions) {
        Supplier<UserPrincipal> supplier = () -> this.getUserPrincipalLookupService().lookupPrincipalByName(name);
        try {
            return this.createAclEntry(supplier, permissions);
        } catch (IOException ignore) {}
        return null;
    }

    public AclEntry createAclEntryByGroupName(String groupName, Set<AclEntryPermission> permissions) {
        Supplier<UserPrincipal> supplier = () -> this.getUserPrincipalLookupService().lookupPrincipalByGroupName(groupName);
        try {
            return this.createAclEntry(supplier, permissions);
        } catch (IOException ignore) {}
        return null;
    }

    private AclEntry createAclEntry(Supplier<UserPrincipal> supplier, Set<AclEntryPermission> permissions) throws IOException {
        final UserPrincipal userPrincipal = supplier.get();
        return AclEntry.newBuilder()
                .setPrincipal(userPrincipal)
                .setPermissions(permissions)
                .setType(AclEntryType.ALLOW)
                .build();
    }

    public UserPrincipalLookupService getUserPrincipalLookupService() {
        return userPrincipalLookupService;
    }

    protected void setUserPrincipalLookupService(UserPrincipalLookupService userPrincipalLookupService) {
        this.userPrincipalLookupService = userPrincipalLookupService;
    }
}
