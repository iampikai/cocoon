package com.iampikai.cocoon.settings;

public class Settings {
    private static boolean scriptBlock = true,
            adsBlock = true, analyticsBlock = true,
            socialBlock = true, safebrowseEnabled = true,
            trackEnabled = true, popupsEnabled = false,
            privateToggle = true, userAgentToggle = false,
            autoCompleteToggle = false;

    public static boolean isPopupsEnabled() {
        return popupsEnabled;
    }

    public static void setPopupsEnabled(boolean popupsEnabled) {
        Settings.popupsEnabled = popupsEnabled;
    }

    public static boolean isPrivateToggle() {
        return privateToggle;
    }

    public static void setPrivateToggle(boolean privateToggle) {
        Settings.privateToggle = privateToggle;
    }

    public static boolean isUserAgentToggle() {
        return userAgentToggle;
    }

    public static void setUserAgentToggle(boolean userAgentToggle) {
        Settings.userAgentToggle = userAgentToggle;
    }

    public static boolean isAutoCompleteToggle() {
        return autoCompleteToggle;
    }

    public static void setAutoCompleteToggle(boolean autoCompleteToggle) {
        Settings.autoCompleteToggle = autoCompleteToggle;
    }

    public static boolean isScriptBlock() {
        return scriptBlock;
    }

    public static void setScriptBlock(boolean scriptBlock) {
        Settings.scriptBlock = scriptBlock;
    }

    public static boolean isAdsBlock() {
        return adsBlock;
    }

    public static void setAdsBlock(boolean adsBlock) {
        Settings.adsBlock = adsBlock;
    }

    public static boolean isAnalyticsBlock() {
        return analyticsBlock;
    }

    public static void setAnalyticsBlock(boolean analyticsBlock) {
        Settings.analyticsBlock = analyticsBlock;
    }

    public static boolean isSocialBlock() {
        return socialBlock;
    }

    public static void setSocialBlock(boolean socialBlock) {
        Settings.socialBlock = socialBlock;
    }

    public static boolean isSafebrowseEnabled() {
        return safebrowseEnabled;
    }

    public static void setSafebrowseEnabled(boolean safebrowseEnabled) {
        Settings.safebrowseEnabled = safebrowseEnabled;
    }

    public static boolean isTrackEnabled() {
        return trackEnabled;
    }

    public static void setTrackEnabled(boolean trackEnabled) {
        Settings.trackEnabled = trackEnabled;
    }
}
