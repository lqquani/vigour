package org.qql.vigour.framework.common;

/**
 * Title: 通用的下拉框数据模型
 * Description: 程序功能的描述
 */
public class CommonComboBoxNode implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5860466484662566219L;

	private String id;
	private String text;
	
	public CommonComboBoxNode(){
		
	}
	
	public CommonComboBoxNode(String id, String text){
		this.id = id;
		this.text = text;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommonComboBoxNode other = (CommonComboBoxNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
