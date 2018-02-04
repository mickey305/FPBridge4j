package com.mickey305.fpbridge.v1;

import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class AclFilePermissions {
    public static FileAttribute<List<AclEntry>> asPosixLikeStmt(String permissionStmt) {
        final Set<AclEntry> entrySet = new HashSet<>();
        final AclEntryBuildFactory factory = AclEntryBuildFactory.getInstance();
        final Optional<String> user = Optional.of(System.getProperty("user.name"));

        // user attribute
        final Set<AclEntryPermission> usrPms = new HashSet<>();
        usrPms.addAll(Posix2AclBindDefaultImplementor.implOwner(permissionStmt));
        user.ifPresent(userName -> entrySet.add(factory.createAclEntryByName(userName, usrPms)));

        // default system attribute
        final Set<AclEntryPermission> sysPms = new HashSet<>();
        sysPms.addAll(Arrays.asList(AclEntryPermission.values()));
        entrySet.add(factory.createAclEntryByGroupName("SYSTEM", sysPms));
        entrySet.add(factory.createAclEntryByName("Administrators", sysPms));

        // acl entry element check
        entrySet.stream().filter(Objects::isNull).forEach(elm -> { throw new NullPointerException(); });

        return new FileAttribute<List<AclEntry>>() {
            @Override
            public String name() {
                return "acl:acl";
            }

            @Override
            public List<AclEntry> value() {
                return Collections.unmodifiableList(new ArrayList<>(entrySet));
            }
        };
    }

    private AclFilePermissions() {}
}
