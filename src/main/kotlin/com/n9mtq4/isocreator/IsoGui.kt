package com.n9mtq4.isocreator

import com.n9mtq4.isocreator.lib.FileDrop
import java.awt.BorderLayout
import java.awt.GridLayout
import javax.swing.DefaultListModel
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFrame
import javax.swing.JList
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField
import javax.swing.WindowConstants

/**
 * Created by will on 1/11/16 at 6:43 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class IsoGui {
	
	private val frame: JFrame
	
	private val outputField: JTextField
	private val outputButton: JButton
	
	private val model: DefaultListModel<String>
	private val inputList: JList<String>
	private val addButton: JButton
	private val clearAll: JButton
	private val clear: JButton
	
	init {
		
		this.frame = JFrame("Iso Creator")
		
		this.model = DefaultListModel()
		this.inputList = JList(model)
		val scrollPane = JScrollPane(inputList)
		this.addButton = JButton("Add")
		this.clearAll = JButton("Remove All")
		this.clear = JButton("Remove")
		scrollPane.verticalScrollBarPolicy = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
		
		val buttonPanel = JPanel(GridLayout(1, 3))
		buttonPanel.add(addButton)
		buttonPanel.add(clear)
		buttonPanel.add(clearAll)
		
		this.outputField = JTextField()
		this.outputButton = JButton("Create")
		val outputPane = JPanel(GridLayout(2, 1))
		
		outputPane.add(outputField)
		outputPane.add(outputButton)
		
		frame.add(buttonPanel, BorderLayout.NORTH)
		frame.add(scrollPane, BorderLayout.CENTER)
		frame.add(outputPane, BorderLayout.SOUTH)
		
		frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
		frame.pack()
		frame.isVisible = true
		frame.setLocationRelativeTo(null)
		
		outputButton.addActionListener { 
//			model.toArray()
			val fileList = Array(model.size, {i -> model.getElementAt(i)})
			try {
				createIso(fileList, outputField.text)
			}catch (e: Exception) {
				JOptionPane.showMessageDialog(this.frame, e.message)
			}
			JOptionPane.showMessageDialog(frame, "Done!")
		}
		addButton.addActionListener {
			val fileChooser = JFileChooser()
			val returnInt = fileChooser.showOpenDialog(frame)
			if (returnInt == JFileChooser.APPROVE_OPTION) {
				val file = fileChooser.selectedFile
				model.addElement(file.path)
			}
		}
		clear.addActionListener { 
			try {
				model.removeElementAt(inputList.selectedIndex)
			}catch (e: Exception) {}
		}
		clearAll.addActionListener { 
			try {
				model.removeAllElements()
			}catch (e: Exception) {}
		}
		FileDrop(scrollPane, FileDrop.Listener { files -> files.forEach { model.addElement(it.path) } })
		
	}
	
}
