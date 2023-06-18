package testScripts;

import java.time.Instant;

public class VehicleCreateDTO {

	private String driverName;

	private String vehNumber;

	private Instant vehicleInTime;

	private Instant vehicleOutTime;

	private Long phonenumber;

	private String siteMasterId;
	private String gateInType;

	private String workFlowTenantId;
	public String getGateInType() {
		return gateInType;
	}

   public void setGateInType(String gateInType) {
		this.gateInType = gateInType;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getVehNumber() {
		return vehNumber;
	}

	public void setVehNumber(String vehNumber) {
		this.vehNumber = vehNumber;
	}

	public Instant getVehicleInTime() {
		return vehicleInTime;
	}

	public void setVehicleInTime(Instant vehicleInTime) {
		this.vehicleInTime = vehicleInTime;
	}

	public Instant getVehicleOutTime() {
		return vehicleOutTime;
	}

	public void setVehicleOutTime(Instant vehicleOutTime) {
		this.vehicleOutTime = vehicleOutTime;
	}

	public Long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(Long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getSiteMasterId() {
		return siteMasterId;
	}

	public void setSiteMasterId(String siteMasterId) {
		this.siteMasterId = siteMasterId;
	}

	public String getWorkFlowTenantId() {
		return workFlowTenantId;
	}

	public void setWorkFlowTenantId(String workFlowTenantId) {
		this.workFlowTenantId = workFlowTenantId;
	}

}
