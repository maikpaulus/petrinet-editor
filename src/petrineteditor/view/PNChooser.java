package petrineteditor.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import petrineteditor.model.PetrinetEditor;

/**
 * Erweiterung der Standard-Dateiwähler-Klasse JFileChooser
 * @author Maik Paulus
 * @version 1.0
 * @since 05.01.2015
 */
public class PNChooser extends JFileChooser
{
	private static final long serialVersionUID = 1L;
	
	private static String FILE_DESCRIPTION = "Petrinetz-Datei (.pnml)";
	public static String FILE_SUFFIX = ".pnml";
	
	/**
	 * Erzeugt ein neues Dateiauswahlfenster
	 * @param filename
	 */
	public PNChooser(String filename)
	{
		super();
		
		this.setCurrentDirectory(new File(PetrinetEditor.getLastDirectory()));
		if (filename != null) {
			File file = new File(filename);
			this.setSelectedFile(file);
			this.setName(filename);			
		}
		
		this.setAcceptAllFileFilterUsed(false);
		this.setFileFilter(new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return PNChooser.FILE_DESCRIPTION;
			}
			
			@Override
			public boolean accept(File f) {
				boolean isDir = f.isDirectory();
				boolean isValidFile = f.getName().toLowerCase().endsWith(PNChooser.FILE_SUFFIX																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																						);
				return isDir || isValidFile;
			}
		});
	}	
}
