package com.jz.rp.library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sattha.p on 4/21/2016.
 */
public class RPResult {

    public String[] getSuccessPermissions() {

        List<String> successPermission = new ArrayList<>();
        for (RPDetail rpDetail : rpDetails) {
            if (rpDetail.isGranted()) {
                successPermission.add(rpDetail.getPermission());
            }
        }

        return successPermission.toArray(new String[successPermission.size()]);
    }

    public String[] getFailurePermissions() {

        List<String> failurePermission = new ArrayList<>();
        for (RPDetail rpDetail : rpDetails) {
            if (!rpDetail.isGranted()) {
                failurePermission.add(rpDetail.getPermission());
            }
        }

        return failurePermission.toArray(new String[failurePermission.size()]);
    }

    public List<RPDetail> getFailurePermissionsWithDetail() {

        List<RPDetail> failurePermission = new ArrayList<>();
        for (RPDetail rpDetail : rpDetails) {
            if (!rpDetail.isGranted()) {
                failurePermission.add(rpDetail);
            }
        }

        return failurePermission;
    }

    public boolean isSomePermissionDisabled() {
        for (RPDetail rpDetail : rpDetails) {
            if (rpDetail.isPermissionDisable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String[] successPermissions = getSuccessPermissions();
        String[] failurePermissions = getFailurePermissions();
        return new StringBuilder().append("Permission Requested: ")
                .append(successPermissions.length + failurePermissions.length)
                .append("\n")
                .append("Permission Granted: ")
                .append(successPermissions.length)
                .append(" ")
                .append(Arrays.toString(successPermissions))
                .append("\n")
                .append("Permission Denied: ")
                .append(failurePermissions.length)
                .append(" ")
                .append(Arrays.toString(failurePermissions))
                .toString();
    }

    private boolean isSuccess;

    private ArrayList<RPDetail> rpDetails;

    public RPResult( ArrayList<RPDetail> rpDetails) {
        this.rpDetails = rpDetails;
    }

    public ArrayList<RPDetail> getRpDetails() {
        return rpDetails;
    }

    public boolean isSuccess() {
        return getFailurePermissions().length == 0;
    }
}
