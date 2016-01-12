package com.n9mtq4.isocreator

import de.tu_darmstadt.informatik.rbg.hatlak.eltorito.impl.ElToritoConfig
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ISO9660File
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.ISO9660RootDirectory
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.CreateISO
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISO9660Config
import de.tu_darmstadt.informatik.rbg.hatlak.iso9660.impl.ISOImageFileHandler
import de.tu_darmstadt.informatik.rbg.hatlak.joliet.impl.JolietConfig
import de.tu_darmstadt.informatik.rbg.hatlak.rockridge.impl.RockRidgeConfig
import java.io.File

/**
 * Created by will on 1/11/16 at 7:48 PM.
 * 
 * http://stackoverflow.com/a/29768496
 * 
 * @author Will "n9Mtq4" Bresnahan
 */
@Throws(Exception::class)
internal fun createIso(files: Array<String>, output: String) {
	
//	output file
	val outputExtension = if (output.endsWith(".iso")) output else "$output.iso"
	val outputFile = File(outputExtension)
	
//	create root
	ISO9660RootDirectory.MOVED_DIRECTORIES_STORE_NAME = "rr_moved";
	val root = ISO9660RootDirectory();
//	val ISO9660File
	
//	add files
	files.map { ISO9660File(it) }.forEach { root.addFile(it) }
	
	val iso9660Config = ISO9660Config();
	iso9660Config.allowASCII(false);
	iso9660Config.setInterchangeLevel(1);
	iso9660Config.restrictDirDepthTo8(true);
	iso9660Config.setPublisher("Java ISO Creator");
	iso9660Config.volumeID = outputFile.name;
	iso9660Config.setDataPreparer(outputFile.name);
	
	iso9660Config.forceDotDelimiter(true);
	
	
	val rrConfig: RockRidgeConfig? = null
	val elToritoConfig: ElToritoConfig? = null
	val jolietConfig = JolietConfig()
	
	// Joliet support
	jolietConfig.setPublisher("Java ISO Creator");
	jolietConfig.volumeID = outputFile.name;
	jolietConfig.setDataPreparer(outputFile.name);
	//jolietConfig.setCopyrightFile(new File("Copyright.txt"));
	jolietConfig.forceDotDelimiter(true);
	
/*	System.out.println("Create ISO");
	ISOImageFileHandler streamHandler = new ISOImageFileHandler(outfile);
	System.out.println("streamHandler");
	CreateISO iso = new CreateISO(streamHandler, root);
	System.out.println("iso");
	iso.process(iso9660Config, rrConfig, jolietConfig, elToritoConfig);
	System.out.println("process");
	System.out.println("Done. File is: " + outfile);*/
	
	val steamHandler = ISOImageFileHandler(outputFile)
	val iso = CreateISO(steamHandler, root)
	iso.process(iso9660Config, rrConfig, jolietConfig, elToritoConfig)
	
}
