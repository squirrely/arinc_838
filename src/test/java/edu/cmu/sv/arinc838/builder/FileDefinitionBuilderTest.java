/*
 * Copyright (c) 2012 Chris Ellison, Mike Deats, Liron Yahdav, Ryan Neal,
 * Brandon Sutherlin, Scott Griffin
 * 
 * This software is released under the MIT license
 * (http://www.opensource.org/licenses/mit-license.php)
 * 
 * Created on Feb 7, 2012
 */
package edu.cmu.sv.arinc838.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.arinc.arinc838.FileDefinition;

import edu.cmu.sv.arinc838.dao.FileDefinitionDao;
import edu.cmu.sv.arinc838.dao.IntegrityDefinitionDao;
import edu.cmu.sv.arinc838.dao.IntegrityDefinitionDao.IntegrityType;
import edu.cmu.sv.arinc838.util.Converter;

public class FileDefinitionBuilderTest {
	FileDefinition xmlFileDef;

	// Our builder
	private FileDefinitionDao fileBuilder;

	@BeforeMethod
	public void setUp() {
		IntegrityDefinitionDao integBuilder = new IntegrityDefinitionDao();
		integBuilder.setIntegrityType(IntegrityType.CRC16.getType());
		integBuilder.setIntegrityValue(Converter.hexToBytes("0000000A"));

		xmlFileDef = new FileDefinition();
		xmlFileDef.setFileLoadable(false);
		xmlFileDef.setFileName("testFile");
		xmlFileDef.setFileSize(1234);
		xmlFileDef.setFileIntegrityDefinition(integBuilder.buildXml());

		fileBuilder = new FileDefinitionDao();
		fileBuilder.setFileLoadable(xmlFileDef.isFileLoadable());
		fileBuilder.setFileName(xmlFileDef.getFileName());
		fileBuilder.setFileSize(xmlFileDef.getFileSize());		
		fileBuilder.setFileIntegrityDefinition(integBuilder);
	}

	/**
	 * Check that the file definition builder can be built from the XML (read:
	 * JAXB) and that all the fields are properly set
	 */
	@Test
	public void testXmlConstructor() {
		FileDefinitionDao tmpBuilder = new FileDefinitionDao(xmlFileDef);
		// check that the fields stuck
		
		assertEquals(xmlFileDef.getFileName(), tmpBuilder.getFileName());
		assertEquals(xmlFileDef.getFileSize(), tmpBuilder.getFileSize());
		assertEquals(xmlFileDef.isFileLoadable(), tmpBuilder.isFileLoadable());

		assertEquals(xmlFileDef.getFileIntegrityDefinition().getIntegrityType(),
				tmpBuilder.getFileIntegrityDefinition().getIntegrityType());
		assertEquals(xmlFileDef.getFileIntegrityDefinition().getIntegrityValue(),
				tmpBuilder.getFileIntegrityDefinition().getIntegrityValue());
	}

	@Test
	public void testFileIntegrityDefinitionAccessors() {
		IntegrityDefinitionDao integDef = new IntegrityDefinitionDao();

		assertNotEquals(integDef, fileBuilder.getFileIntegrityDefinition());

		fileBuilder.setFileIntegrityDefinition(integDef);
		assertEquals(integDef, fileBuilder.getFileIntegrityDefinition(),
				"Should be the same, the set keeps a reference");
	}

	@Test
	public void testFileNameAccessors() {
		String fileName = "TEST";

		fileBuilder.setFileName(fileName);
		assertEquals(fileName, fileBuilder.getFileName());
		fileName += "JUNK";
		assertNotEquals(fileName, fileBuilder.getFileName());
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSetFileNameInvalid() {
		fileBuilder.setFileName("<hello there & stuff>");
	}

	@Test
	public void testFileSizeAccessors() {
		int fSize = 234;

		fileBuilder.setFileSize(fSize);
		assertEquals(fSize, fileBuilder.getFileSize());
		fSize += 234;
		assertNotEquals(fSize, fileBuilder);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSetFileSizeInvalid() {
		fileBuilder.setFileSize(-1);
	}

	@Test
	public void testLoadableAccessors() {
		boolean loadable = false;

		fileBuilder.setFileLoadable(loadable);
		assertEquals(loadable, fileBuilder.isFileLoadable());
		loadable = !loadable;
		assertNotEquals(loadable, fileBuilder.isFileLoadable());
	}

	@Test
	public void testBuilder() {
		FileDefinitionDao newBuilder = new FileDefinitionDao(xmlFileDef);

		FileDefinition built = newBuilder.buildXml();
		assertNotEquals(null, built);
		assertNotEquals(built, xmlFileDef,
				"Should be different, a NEW instance should be built");

		assertNotEquals(null, built.getFileIntegrityDefinition());
		assertNotEquals(built.getFileIntegrityDefinition(),
				xmlFileDef.getFileIntegrityDefinition(),
				"Should have built a new integrity definition");

		assertEquals(built.getFileName(), xmlFileDef.getFileName());
		assertEquals(built.getFileSize(), xmlFileDef.getFileSize());
		assertEquals(built.isFileLoadable(), xmlFileDef.isFileLoadable());
	}
	
	@Test
	public void testHashCode(){
		assertEquals(fileBuilder.hashCode(), fileBuilder.getFileIntegrityDefinition().hashCode());
	}
	
	@Test 
	public void testHashCodeWithNoIntegrity(){
		assertEquals(new FileDefinitionDao().hashCode(), 0);
	}
	
	@Test
	public void testEquals(){
		FileDefinitionDao second = new FileDefinitionDao(xmlFileDef);
		
		assertEquals(fileBuilder, second);	
	}
}
