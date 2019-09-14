
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	//Atributes
	private String					categoryES;
	private String					categoryEN;

	//Relationship
	private Collection<Category>	children;
	private Category				parent;


	@NotBlank
	@SafeHtml
	public String getCategoryES() {
		return this.categoryES;
	}

	public void setCategoryES(final String categoryES) {
		this.categoryES = categoryES;
	}

	@NotBlank
	@SafeHtml
	public String getCategoryEN() {
		return this.categoryEN;
	}

	public void setCategoryEN(final String categoryEN) {
		this.categoryEN = categoryEN;
	}

	@Valid
	@OneToMany
	public Collection<Category> getChildren() {
		return this.children;
	}

	public void setChildren(final Collection<Category> children) {
		this.children = children;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getParent() {
		return this.parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}

}
