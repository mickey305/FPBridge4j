package com.mickey305.fpbridge.v1;

import java.nio.file.attribute.AclEntryPermission;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Posix2AclBindDefaultImplementor {
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 9;

    private Posix2AclBindDefaultImplementor() {}

    public static Set<AclEntryPermission> implOwner(String posixPermissionStmt) {
        final Set<AclEntryPermission> entrySet = new HashSet<>();

        // impl logic
        if (!checkStmt(posixPermissionStmt))
            throw new IllegalArgumentException();
        // 先頭3文字を抽出する（所有者の権限情報）
        final String targetStmt = posixPermissionStmt.substring(0, 3);
        switch (targetStmt) {
            case "---":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                break;
            case "r--":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.READ_DATA);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
                break;
            case "-w-":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.WRITE_DATA);
                entrySet.add(AclEntryPermission.APPEND_DATA);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
                break;
            case "--x":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.EXECUTE);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
                break;
            case "rw-":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_ATTRIBUTES);
                entrySet.add(AclEntryPermission.READ_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.WRITE_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.READ_DATA);
                entrySet.add(AclEntryPermission.WRITE_DATA);
                entrySet.add(AclEntryPermission.APPEND_DATA);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
                break;
            case "r-x":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.READ_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.READ_DATA);
                entrySet.add(AclEntryPermission.EXECUTE);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
                break;
            case "-wx":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.WRITE_DATA);
                entrySet.add(AclEntryPermission.APPEND_DATA);
                entrySet.add(AclEntryPermission.EXECUTE);
                entrySet.add(AclEntryPermission.DELETE_CHILD);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
            case "rwx":
                entrySet.add(AclEntryPermission.READ_ACL);
                entrySet.add(AclEntryPermission.READ_ATTRIBUTES);
                entrySet.add(AclEntryPermission.WRITE_ATTRIBUTES);
                entrySet.add(AclEntryPermission.READ_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.WRITE_NAMED_ATTRS);
                entrySet.add(AclEntryPermission.READ_DATA);
                entrySet.add(AclEntryPermission.WRITE_DATA);
                entrySet.add(AclEntryPermission.APPEND_DATA);
                entrySet.add(AclEntryPermission.EXECUTE);
                entrySet.add(AclEntryPermission.DELETE_CHILD);
                entrySet.add(AclEntryPermission.SYNCHRONIZE);
            default:
                break;
        }
        return Collections.unmodifiableSet(entrySet);
    }

    private static boolean checkStmt(String posixPermissionStmt) {
        final int length = posixPermissionStmt.length();
        final List<Character> opeRLst = Arrays.asList('r', '-');
        final List<Character> opeWLst = Arrays.asList('w', '-');
        final List<Character> opeXLst = Arrays.asList('x', '-');

        // check logic
        if (length < MIN_LENGTH || MAX_LENGTH < length) return false;
        for (int i = 0; i < length; i++) {
            final char ch = posixPermissionStmt.charAt(i);
            if (i % 3 == 0 && !opeRLst.contains(ch)) return false;
            if (i % 3 == 1 && !opeWLst.contains(ch)) return false;
            if (i % 3 == 2 && !opeXLst.contains(ch)) return false;
        }
        return true;
    }
}
