package com.jz.rp.library;

/**
 * Created by sattha.p on 4/21/2016.
 */
public class RPDetail {

    private String permission;
    private boolean isGranted;
    private boolean isPermissionDisable;

    public RPDetail(String permission, boolean isGranted, boolean isPermissionDisable) {
        this.permission = permission;
        this.isGranted = isGranted;
        this.isPermissionDisable = isPermissionDisable;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isGranted() {
        return isGranted;
    }

    public boolean isPermissionDisable() {
        return isPermissionDisable;
    }
}
