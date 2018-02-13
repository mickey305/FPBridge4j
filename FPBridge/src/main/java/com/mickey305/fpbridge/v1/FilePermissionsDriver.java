package com.mickey305.fpbridge.v1;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.HashSet;
import java.util.Set;

public class FilePermissionsDriver {
    /**
     * ファイルパーミッションドライバ
     * <p>各ファイルシステムの差異を吸収し共通的なインターフェースでファイルパーミッションを生成する。</p>
     * @param fpArguments アクセス権限定義オブジェクト
     * @return ファイルパーミッション
     */
    public static FileAttribute<?> createByFPArgument(Set<FPArgument> fpArguments) {
        // input data hashing
        fpArguments = new HashSet<>(fpArguments);

        // create posix file permission
        final StringBuilder permissionStmt = new StringBuilder();
        permissionStmt.append(fpArguments.stream()
                .filter(arg -> arg.getFpScope().is(FPScope.Owner)).findFirst()
                .orElseGet(FPArgument::newDenyOwner).buildPosixStmt());
        permissionStmt.append(fpArguments.stream()
                .filter(arg -> arg.getFpScope().is(FPScope.Group)).findFirst()
                .orElseGet(FPArgument::newDenyGroup).buildPosixStmt());
        permissionStmt.append(fpArguments.stream()
                .filter(arg -> arg.getFpScope().is(FPScope.Others)).findFirst()
                .orElseGet(FPArgument::newDenyOthers).buildPosixStmt());

        // file-attribute build implementation
        if (OSCheck.isUnix()) {
            //
            // Unix: POSIX RWX File Permission
            // - transfer
            //
            return PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(permissionStmt.toString()));
        } else if (OSCheck.isWindows()) {
            //
            // Windows(MS-DOS): Acl File Permission
            // - build acl attribute from posix file permission
            //
            return AclFilePermissions.asPosixLikeStmt(permissionStmt.toString());
        } else {
            throw new UnsupportedOperationException();
        }
    }
}
