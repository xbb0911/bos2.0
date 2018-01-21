package com.xbb.bos.domain.transit;

import com.xbb.bos.domain.take_delivery.WayBill;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * @description: 运输配送信息
 */
@Entity
@Table(name = "T_TRANSIT_INFO")
public class TransitInfo implements Serializable{
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "C_WAYBILL_ID")
	private WayBill wayBill;

	//规避懒加载,转换json数据时也将集合的数据加载
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "C_TRANSIT_INFO_ID")
	@OrderColumn(name = "C_IN_OUT_INDEX")
	private List<InOutStorageInfo> inOutStorageInfos = new ArrayList<InOutStorageInfo>();//出入库信息

	@OneToOne
	@JoinColumn(name = "C_DELIVERY_INFO_ID")
	private DeliveryInfo deliveryInfo;//配送信息

	@OneToOne
	@JoinColumn(name = "C_SIGN_INFO_ID")
	private SignInfo signInfo;//签收信息

	@Column(name = "C_STATUS")
	// 出入库中转、到达网点、开始配置、正常签收、异常
	private String status;

	@Column(name = "C_OUTLET_ADDRESS")
	private String outletAddress;


	//添加getTransferInfo方法返回物流信息
	@Transient
	public String getTransferInfo(){
		StringBuffer buffer = new StringBuffer();
		//添加出入库信息
		for (InOutStorageInfo inOutStorageInfo : inOutStorageInfos) {
			buffer.append(inOutStorageInfo.getDescription()+"</br>");
		}
		//添加配送信息
		if(deliveryInfo != null){
			buffer.append(deliveryInfo.getDescription()+"</br>");
		}
		//添加签收信息
		if(signInfo != null){
			buffer.append(signInfo.getDescription()+"</br>");
		}
		return buffer.toString();
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WayBill getWayBill() {
		return wayBill;
	}

	public void setWayBill(WayBill wayBill) {
		this.wayBill = wayBill;
	}

	public List<InOutStorageInfo> getInOutStorageInfos() {
		return inOutStorageInfos;
	}

	public void setInOutStorageInfos(List<InOutStorageInfo> inOutStorageInfos) {
		this.inOutStorageInfos = inOutStorageInfos;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public SignInfo getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(SignInfo signInfo) {
		this.signInfo = signInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutletAddress() {
		return outletAddress;
	}

	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}

}
