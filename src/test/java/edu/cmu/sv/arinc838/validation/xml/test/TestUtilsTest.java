package edu.cmu.sv.arinc838.validation.xml.test;

import static org.testng.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.arinc.arinc838.FileDefinition;
import com.arinc.arinc838.IntegrityDefinition;
import com.arinc.arinc838.SoftwareDescription;
import com.arinc.arinc838.ThwDefinition;

import edu.cmu.sv.arinc838.builder.FileDefinitionDao;
import edu.cmu.sv.arinc838.builder.IntegrityDefinitionDao;
import edu.cmu.sv.arinc838.builder.IntegrityDefinitionDao.IntegrityType;
import edu.cmu.sv.arinc838.builder.SoftwareDefinitionFileDao;
import edu.cmu.sv.arinc838.builder.SoftwareDescriptionDao;
import edu.cmu.sv.arinc838.builder.TargetHardwareDefinitionBuilder;
import edu.cmu.sv.arinc838.validation.ReferenceData;

public class TestUtilsTest {
	
	@Test
	public void testCheckPrimitivesFileDefinitionGood() {
		FileDefinition def = new FileDefinition();
		def.setFileName("someFile");
		def.setFileSize(1234);

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, FileDefinitionDao.class,
				exceptionList);

		assertTrue(exceptionList.isEmpty());
	}

	@Test
	public void testCheckPrimitivesFileDefinitionBad() {
		FileDefinition def = new FileDefinition();
		def.setFileName("someFile<>");
		def.setFileSize(-1);

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, FileDefinitionDao.class,
				exceptionList);

		assertEquals(exceptionList.size(), 2);
	}
	
	@Test
	public void testCheckPrimitivesThwDefintionGood() {
		ThwDefinition def = new ThwDefinition();
		def.setThwId("LEFT");

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, TargetHardwareDefinitionBuilder.class,
				exceptionList);

		assertTrue(exceptionList.isEmpty());
	}

	@Test
	public void testCheckPrimitivesThwDefintionBad() {
		ThwDefinition def = new ThwDefinition();
		def.setThwId("LEFT<>");

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, TargetHardwareDefinitionBuilder.class,
				exceptionList);

		assertEquals(exceptionList.size(), 1);
	}
	
	@Test
	public void testCheckPrimitivesIntegrityDefinitionGood() {
		IntegrityDefinition def = new IntegrityDefinition();
		def.setIntegrityType(IntegrityType.CRC16.getType());

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, IntegrityDefinitionDao.class,
				exceptionList);

		assertTrue(exceptionList.isEmpty());
	}

	@Test
	public void testCheckPrimitivesIntegrityDefinitionBad() {
		IntegrityDefinition def = new IntegrityDefinition();
		def.setIntegrityType(-1);

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(def, IntegrityDefinitionDao.class,
				exceptionList);

		assertEquals(exceptionList.size(), 1);
	}
	
	@Test
	public void testCheckPrimitivesSoftwareDescriptionGood() {
		SoftwareDescription desc = new SoftwareDescription();
		desc.setSoftwarePartnumber(ReferenceData.SOFTWARE_PART_NUMBER_REFERENCE);
		desc.setSoftwareTypeDescription("something");

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(desc, SoftwareDescriptionDao.class,
				exceptionList);

		assertTrue(exceptionList.isEmpty());
	}

	@Test
	public void testCheckPrimitivesSoftwareDescriptionBad() {
		SoftwareDescription desc = new SoftwareDescription();
		desc.setSoftwarePartnumber(ReferenceData.SOFTWARE_PART_NUMBER_REFERENCE + "asdfl");
		desc.setSoftwareTypeDescription("somethingbad<>");

		List<Exception> exceptionList = new ArrayList<Exception>();

		TestUtils.checkPrimitives(desc, SoftwareDescriptionDao.class,
				exceptionList);

		assertEquals(exceptionList.size(), 2);
	}
}