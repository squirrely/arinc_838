package edu.cmu.sv.arinc838.xml;

import java.util.ArrayList;
import java.util.List;


import com.arinc.arinc838.ThwDefinition;

import edu.cmu.sv.arinc838.specification.TargetHardwareDefinition;

public class XmlTargetHardwareDefinition implements TargetHardwareDefinition {

	private String id;
	private List<String> positions = new ArrayList<String>();

	public XmlTargetHardwareDefinition(ThwDefinition jaxbDef) {
		this.id = jaxbDef.getThwId();
		
		for(String position : jaxbDef.getThwPosition()){
			positions.add(position);
		}
	}

	public XmlTargetHardwareDefinition() {
		
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String value) {
		this.id = value;
	}

	@Override
	public List<String> getPositions() {
		return positions;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj){			
			return true;
		}else if (obj instanceof TargetHardwareDefinition){
			TargetHardwareDefinition other = (TargetHardwareDefinition)obj;
			
			return this.getId().equals(other.getId()) &&
				   this.getPositions().equals(other.getPositions());
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode() ^ getPositions().hashCode();
	}
}
