/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */
package conway.anand.myconway.app;

import android.app.Application;

/**
 * Created by Anand M Joseph on 28/06/16.
 *
 * AppConstants Application implementation is used for global variables and constants.
 *
 */
public class AppConstants extends Application {

	/**
	 * Identifier for the application name.
	 */
	public static final String	APP_NAME	= "Android Game of Life";

	/**
	 * Identifier for the fact, that the rule is undefined.
	 */
	public static final int		UNDEFINED	= 0;

	/**
	 * Identifier for the birth rule.
	 */
	public static final int		BIRTH_RULE	= 1;

	/**
	 * Identifier for the death rule.
	 */
	public static final int		DEATH_RULE	= 2;

	/**
	 * Identifier for the ruleset that is currently used.
	 */
	private static int[]		ruleSet;

	/**
	 * The width of the device.
	 */
	private static int			viewportWidth;

	/**
	 * The height of the device.
	 */
	private static int			viewportHeight;

	/**
	 * Getter for {@link AppConstants#viewportWidth}.
	 *
	 * @return {@link AppConstants#viewportWidth}.
	 */
	public static int getViewportWidth() {
		return viewportWidth;
	}

	/**
	 * Setter for {@link AppConstants#viewportWidth}.
	 *
	 * @param newViewportWidth
	 *            The new width for the device.
	 */
	public static void setViewportWidth(final int newViewportWidth) {
		viewportWidth = newViewportWidth;
	}

	/**
	 * Getter for {@link AppConstants#viewportHeight}.
	 *
	 * @return {@link AppConstants#viewportHeight}.
	 */
	public static int getViewportHeight() {
		return viewportHeight;
	}

	/**
	 * Setter for {@link AppConstants#viewportHeight}.
	 *
	 * @param newViewportHeight
	 *            The new height for the device.
	 */
	public static void setViewportHeight(final int newViewportHeight) {
		viewportHeight = newViewportHeight;
	}

	/**
	 * Getter for {@link AppConstants#ruleSet}.
	 *
	 * @return {@link AppConstants#ruleSet}.
	 */
	public static int[] getRuleSet() {
		return ruleSet;
	}

	/**
	 * Setter for {@link AppConstants#ruleSet}.
	 *
	 * @param newRuleSet
	 *            The new ruleset for the game.
	 */
	public static void setRuleSet(int[] newRuleSet) {
		ruleSet = newRuleSet;
	}
}
