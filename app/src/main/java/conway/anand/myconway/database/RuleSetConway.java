/**
 * Copyright (C) 2016-2017 Anand M Joseph.
 */
package conway.anand.myconway.database;

import conway.anand.myconway.rules.RuleEditorActivity;

/**
 * Represents our model, that contains all data being saved in the database and shown in {@link RuleEditorActivity}.
 *
 * Created by Anand M Joseph on 28/06/16.
 **/
public class RuleSetConway {

	/**
	 * The id the rule has in the database.
	 */
	private long	id;

	/**
	 * The name of the rule.
	 */
	private String	name;

	/**
	 * The rule itself presented as integer array.
	 */
	private int[]	ruleSet	= new int[9];

	/**
	 * Getter for {@link RuleSetConway#id}.
	 * 
	 * @return {@link RuleSetConway#id}.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for {@link RuleSetConway#id}.
	 * 
	 * @param id
	 *            The new {@link RuleSetConway#id}.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for {@link RuleSetConway#name}.
	 * 
	 * @return {@link RuleSetConway#name}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for {@link RuleSetConway#name}.
	 * 
	 * @param id
	 *            The new {@link RuleSetConway#name}.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for {@link RuleSetConway#ruleSet}.
	 * 
	 * @return {@link RuleSetConway#ruleSet}.
	 */
	public int[] getRuleSet() {
		return ruleSet;
	}

	/**
	 * Setter for {@link RuleSetConway#ruleSet}.
	 * 
	 * @param id
	 *            The new {@link RuleSetConway#ruleSet}.
	 */
	public void setRuleSet(int[] ruleSet) {
		this.ruleSet = ruleSet;
	}
}
