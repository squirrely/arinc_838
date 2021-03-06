package edu.cmu.sv.arinc838.reader;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import java.util.ArrayList;

import org.testng.annotations.Test;

import edu.cmu.sv.arinc838.dao.FileDefinitionDao;
import edu.cmu.sv.arinc838.dao.SoftwareDefinitionFileDao;
import edu.cmu.sv.arinc838.dao.TargetHardwareDefinitionDao;
import edu.cmu.sv.arinc838.validation.ReferenceData;

public class BdfReaderTest {

	@Test
	public void testRead() throws Exception {
		BdfReader reader = new BdfReader();
		ArrayList<Exception> errorList = new ArrayList<Exception>();
		SoftwareDefinitionFileDao sdfDao = reader.read(
				"src/test/resources/ACM47-1234-5678/ACM4712345678.BDF", errorList);
		assertEquals(errorList.size(), 0, "Unexpected errors during read");

		assertEquals(sdfDao.getFileFormatVersion(),
				ReferenceData.SDF_TEST_FILE.getFileFormatVersion());
		assertEquals(sdfDao.getSoftwareDescription().getSoftwarePartnumber(),
				ReferenceData.SDF_TEST_FILE.getSoftwareDescription()
						.getSoftwarePartnumber());
		assertEquals(sdfDao.getSoftwareDescription()
				.getSoftwareTypeDescription(), ReferenceData.SDF_TEST_FILE
				.getSoftwareDescription().getSoftwareTypeDescription());
		assertEquals(sdfDao.getSoftwareDescription().getSoftwareTypeId(),
				ReferenceData.SDF_TEST_FILE.getSoftwareDescription()
						.getSoftwareTypeId());

		for (int i = 0; i < ReferenceData.SDF_TEST_FILE
				.getTargetHardwareDefinitions().size(); i++) {
			TargetHardwareDefinitionDao actual = sdfDao
					.getTargetHardwareDefinitions().get(i);
			TargetHardwareDefinitionDao expected = ReferenceData.SDF_TEST_FILE
					.getTargetHardwareDefinitions().get(i);
			assertEquals(actual.getThwId(), expected.getThwId());
			for (int j = 0; j < expected.getPositions().size(); j++) {
				String actPos = actual.getPositions().get(j);
				String expPos = expected.getPositions().get(j);
				assertEquals(actPos, expPos);
			}
		}

		for (int i = 0; i < ReferenceData.SDF_TEST_FILE.getFileDefinitions()
				.size(); i++) {
			FileDefinitionDao actual = sdfDao.getFileDefinitions().get(i);
			FileDefinitionDao expected = ReferenceData.SDF_TEST_FILE
					.getFileDefinitions().get(i);
			assertEquals(actual.getFileName(), expected.getFileName());
			assertEquals(actual.getFileSize(), expected.getFileSize());
			assertEquals(
					actual.getFileIntegrityDefinition().getIntegrityType(),
					expected.getFileIntegrityDefinition().getIntegrityType());
			assertEquals(actual.getFileIntegrityDefinition()
					.getIntegrityValue(), expected.getFileIntegrityDefinition()
					.getIntegrityValue());
		}

		assertEquals(sdfDao.getSdfIntegrityDefinition().getIntegrityType(),
				ReferenceData.SDF_TEST_FILE.getSdfIntegrityDefinition()
						.getIntegrityType());
		assertEquals(sdfDao.getSdfIntegrityDefinition().getIntegrityValue(),
				ReferenceData.SDF_TEST_FILE.getSdfIntegrityDefinition()
						.getIntegrityValue());

		assertEquals(sdfDao.getLspIntegrityDefinition().getIntegrityType(),
				ReferenceData.SDF_TEST_FILE.getLspIntegrityDefinition()
						.getIntegrityType());
		assertEquals(sdfDao.getLspIntegrityDefinition().getIntegrityValue(),
				ReferenceData.SDF_TEST_FILE.getLspIntegrityDefinition()
						.getIntegrityValue());
	}

	@Test
	public void testReadErrors() throws Exception {
		BdfReader reader = new BdfReader();
		ArrayList<Exception> errorList = new ArrayList<Exception>();
		reader.read(
				"src/test/resources/error/ACM4712345678.BDF", errorList);

		assertEquals(errorList.size(), 6, // 6 = 1 Part number + 1 part number characters don't match + LSP CRC + SDF CRC + 2x FileDefinition CRC
				"Did not get expected number of errors");
	}
	
	@Test
	public void testSetsPath(){
		BdfReader reader = new BdfReader();
		ArrayList<Exception> errorList = new ArrayList<Exception>();
		SoftwareDefinitionFileDao sdfDao = reader.read(
				"src/test/resources/ACM47-1234-5678/ACM4712345678.BDF", errorList);
		
		assertTrue(sdfDao.getPath().endsWith("ACM47-1234-5678"));
	}
}
