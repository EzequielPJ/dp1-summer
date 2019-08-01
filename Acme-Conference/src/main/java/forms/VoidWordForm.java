
package forms;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class VoidWordForm {

	private String	voidWord;


	@SafeHtml
	@NotBlank
	public String getVoidWord() {
		return this.voidWord;
	}

	public void setVoidWord(final String voidWord) {
		this.voidWord = voidWord;
	}

}
