package com.example.SpringBoot.Entiy;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.example.SpringBoot.Enums.LocationEnums.SetupBoxType;
import com.example.SpringBoot.Enums.LocationEnums.Status;

@Document(collection = "setup_box")
public class SetupBox {

	@Id
	@Field("setupBoxId")
	private String setupBoxId;
	private String ipAddress;
	private SetupBoxType setBoxType; //ENUM
	private String software; 
	private Status status;          //ENUM
	private String version;

	public String getSetupBoxId() {
		return setupBoxId;
	}

	public void setSetupBoxId(String setupBoxId) {
		this.setupBoxId = setupBoxId;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public SetupBoxType getSetBoxType() {
		return setBoxType;
	}

	public void setSetBoxType(SetupBoxType setBoxType) {
		this.setBoxType = setBoxType;
	}

	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public SetupBox(String setupBoxId, String ipAddress, SetupBoxType setBoxType, String software, Status status,
			String version) {
		super();
		this.setupBoxId = setupBoxId;
		this.ipAddress = ipAddress;
		this.setBoxType = setBoxType;
		this.software = software;
		this.status = status;
		this.version = version;
	}

	@Override
	public String toString() {
		return "SetupBox [setupBoxId=" + setupBoxId + ", ipAddress=" + ipAddress + ", setBoxType=" + setBoxType
				+ ", software=" + software + ", status=" + status + ", version=" + version + "]";
	}

	public SetupBox() {
		super();
	}

}
