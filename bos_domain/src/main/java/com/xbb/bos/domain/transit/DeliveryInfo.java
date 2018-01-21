package com.xbb.bos.domain.transit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @description: 配送信息
 */
@Entity
@Table(name = "T_DELIVERY_INFO")
public class DeliveryInfo implements Serializable{

	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id;

	@Column(name = "C_COURIER_NUM")
	private String courierNum;

	@Column(name = "C_COURIER_NAME")
	private String courierName;

	@Column(name = "C_DESCRIPTION")
	private String description; // 描述

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCourierNum() {
		return courierNum;
	}

	public void setCourierNum(String courierNum) {
		this.courierNum = courierNum;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
