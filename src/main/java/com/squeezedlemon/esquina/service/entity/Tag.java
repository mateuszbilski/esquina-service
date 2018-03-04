package com.squeezedlemon.esquina.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "TAGS")
public class Tag implements Serializable {

	private static final long serialVersionUID = -8644271949351342171L;

	@Id
	@NotEmpty(message = "error.valid.empty")
	@Column(name = "TA_TAG")
	private String tag;
	
	@OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "TA_ICON")
	private Image icon;
	
	@OneToMany(mappedBy = "tag", fetch = FetchType.EAGER)
	private List<TagTranslation> tagTranslations;
	
	public Tag() {
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public List<TagTranslation> getTagTranslations() {
		return tagTranslations;
	}

	public void setTagTranslations(List<TagTranslation> tagTranslations) {
		this.tagTranslations = tagTranslations;
	}
	
}
