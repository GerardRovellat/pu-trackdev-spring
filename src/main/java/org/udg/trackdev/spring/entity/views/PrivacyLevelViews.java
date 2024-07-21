package org.udg.trackdev.spring.entity.views;

/**
 * The type Privacy level views.
 */
// Views for use by Json serialization
// to define properties to be viewed only for current user (private)
// or for all users (public)
public class PrivacyLevelViews {
    /**
     * The type Public.
     */
    static public class Public { }

    /**
     * The type Private.
     */
    static public class Private extends Public { }
}