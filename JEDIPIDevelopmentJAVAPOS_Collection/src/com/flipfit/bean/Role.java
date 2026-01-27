// TODO: Auto-generated Javadoc
package com.flipfit.bean;

/**
 * The Class Role.
 *
 * @author team pi
 * @ClassName "Role"
 */
public class Role {

	/** The role id. */
	private String roleId;

	/** The role name. */
	private String roleName;

	/** The description. */
	private String description;

	/**
	 * Instantiates a new role.
	 */
	public Role() {

	}

	/**
	 * Instantiates a new role with the specified parameters.
	 *
	 * @param roleId      the unique identifier for the role
	 * @param roleName    the name of the role (e.g., CUSTOMER, OWNER, ADMIN)
	 * @param description a brief description of the role's responsibilities
	 */
	public Role(String roleId, String roleName, String description) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.description = description;
	}

	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * Gets the role name.
	 *
	 * @return the role name
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * Sets the role name.
	 *
	 * @param roleName the new role name
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns a string representation of the role.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", description=" + description + "]";
	}

}
