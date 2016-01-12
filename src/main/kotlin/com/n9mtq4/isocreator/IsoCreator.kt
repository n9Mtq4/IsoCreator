@file:JvmName("IsoCreator")
package com.n9mtq4.isocreator

import javax.swing.UIManager

/**
 * Created by will on 1/11/16 at 6:41 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
fun main(args: Array<String>) {
	
	try {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}catch (e: Exception) {
		e.printStackTrace()
	}
	
	IsoGui()
	
}
