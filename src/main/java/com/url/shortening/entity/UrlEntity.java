package com.url.shortening.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
* <h1>JPA Url Entity</h1>
*
* @author  Chirag Sheth
* @version 1.0
* @since   2020-07-30
*/

@ApiModel(description="Url Details")
@NoArgsConstructor
@Data
@Entity
@Table(name="TBL_URL")
public class UrlEntity {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="short_url")
	private String shortUrl;
	
	@Column(name="long_url", columnDefinition = "TEXT")
	private String longUrl;
	
	@Column(name="create_date")
	@NonNull private Date createDate;
	
	@Column(name="expiry_date")
	@NonNull private Date expiryDate;

}